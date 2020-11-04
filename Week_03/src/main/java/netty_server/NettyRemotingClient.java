package netty_server;

import config.NettyClientConfig;
import config.ProxyConfig;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import outbound.netty.NettyOutboundInitializer;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author com.yangxing
 * @version 1.0
 * @date 2020/11/4 0004 19:36
 */
public class NettyRemotingClient {

    private final Logger logger = LoggerFactory.getLogger(NettyRemotingClient.class);

    /**
     * client bootstrap
     */
    private final Bootstrap bootstrap = new Bootstrap();

    /**
     *  worker group
     */
    private final NioEventLoopGroup workerGroup;

    /**
     *  client config
     */
    private final NettyClientConfig clientConfig;

    /**
     * Proxy Config
     */
    private final ProxyConfig proxyConfig;

    private final String host;
    private final int port;

    public NettyRemotingClient(final NettyClientConfig clientConfig,final ProxyConfig proxyConfig) {
        this.clientConfig = clientConfig;
        this.proxyConfig = proxyConfig;
        URI uri = null;
        try {
            uri = new URI(proxyConfig.getProxyServer());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        this.host = uri.getHost();
        this.port = uri.getPort();

        this.workerGroup = new NioEventLoopGroup(clientConfig.getWorkerThreads(), new ThreadFactory() {
            private AtomicInteger threadIndex = new AtomicInteger(0);

            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, String.format("NettyClient_%d", this.threadIndex.incrementAndGet()));
            }
        });

    }

    public void connect(FullHttpRequest request, ChannelHandlerContext ctx) {
        try {
            this.bootstrap
                    .group(this.workerGroup)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE, clientConfig.isSoKeepalive())
                    .option(ChannelOption.TCP_NODELAY, clientConfig.isTcpNoDelay())
                    .option(ChannelOption.SO_SNDBUF, clientConfig.getSendBufferSize())
                    .option(ChannelOption.SO_RCVBUF, clientConfig.getReceiveBufferSize())
                    .handler(new NettyOutboundInitializer(request,ctx));


            ChannelFuture f = this.bootstrap.connect(host, port).sync();

            URI uri = new URI(request.uri());

            FullHttpRequest fullRequest = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET, uri.toASCIIString());
            // 构建http请求
            fullRequest.headers().set(HttpHeaders.Names.HOST, host);
            fullRequest.headers().set(HttpHeaders.Names.CONTENT_LENGTH, fullRequest.content().readableBytes());
            // 发送http请求
            f.channel().writeAndFlush(fullRequest);
            f.channel().closeFuture().sync();
        } catch (InterruptedException | URISyntaxException e) {
            e.printStackTrace();
        } finally {
            this.workerGroup.shutdownGracefully();
        }
    }

}
