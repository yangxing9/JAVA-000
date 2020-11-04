package netty_server;

import config.NettyServerConfig;
import config.ProxyConfig;
import inbound.HttpInboundInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.epoll.EpollChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author com.yangxing
 * @version 1.0
 * @date 2020/11/4 0004 18:44
 */
public class NettyRemotingServer {

    private final Logger logger = LoggerFactory.getLogger(NettyRemotingServer.class);

    /**
     *  server bootstrap
     */
    private final ServerBootstrap serverBootstrap = new ServerBootstrap();


    /**
     * boss group
     */
    private final NioEventLoopGroup bossGroup;

    /**
     *  worker group
     */
    private final NioEventLoopGroup workGroup;

    /**
     *  server config
     */
    private final NettyServerConfig serverConfig;

    /**
     * Proxy Config
     */
    private final ProxyConfig proxyConfig;

    /**
     * started flag
     */
    private final AtomicBoolean isStarted = new AtomicBoolean(false);


    /**
     *  server init
     *
     * @param serverConfig server config
     * @param proxyConfig
     */
    public NettyRemotingServer(final NettyServerConfig serverConfig, final ProxyConfig proxyConfig){
        this.serverConfig = serverConfig;
        this.proxyConfig = proxyConfig;

        this.bossGroup = new NioEventLoopGroup(1, new ThreadFactory() {
            private AtomicInteger threadIndex = new AtomicInteger(0);

            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, String.format("NettyServerBossThread_%d", this.threadIndex.incrementAndGet()));
            }
        });

        this.workGroup = new NioEventLoopGroup(serverConfig.getWorkerThread(), new ThreadFactory() {
            private AtomicInteger threadIndex = new AtomicInteger(0);

            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, String.format("NettyServerWorkerThread_%d", this.threadIndex.incrementAndGet()));
            }
        });
    }

    public void start(){
        if(this.isStarted.get()){
            return;
        }

        this.serverBootstrap.group(this.bossGroup, this.workGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_REUSEADDR, true)
                .option(ChannelOption.SO_BACKLOG, serverConfig.getSoBacklog())
                .childOption(ChannelOption.SO_KEEPALIVE, serverConfig.isSoKeepalive())
                .childOption(ChannelOption.TCP_NODELAY, serverConfig.isTcpNoDelay())
                .childOption(ChannelOption.SO_SNDBUF, serverConfig.getSendBufferSize())
                .childOption(ChannelOption.SO_RCVBUF, serverConfig.getReceiveBufferSize())
                .option(EpollChannelOption.SO_REUSEPORT, true)
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new HttpInboundInitializer(this.proxyConfig));

        ChannelFuture future;
        try {
            future = serverBootstrap.bind(serverConfig.getListenPort()).sync();
        } catch (Exception e) {
            logger.error("开启netty http服务器 失败 {}, exit",e.getMessage(), e);
            throw new RuntimeException(String.format("netty http服务器 bind %s 失败", serverConfig.getListenPort()));
        }
        if (future.isSuccess()) {
            logger.info("开启netty http服务器，监听地址和端口为 http://127.0.0.1:" + serverConfig.getListenPort() + '/');
        } else if (future.cause() != null) {
            throw new RuntimeException(String.format("netty http服务器 bind %s 失败", serverConfig.getListenPort()), future.cause());
        } else {
            throw new RuntimeException(String.format("netty http服务器 bind %s 失败", serverConfig.getListenPort()));
        }
        isStarted.compareAndSet(false, true);
    }


    public void close() {
        if(isStarted.compareAndSet(true, false)){
            try {
                if(bossGroup != null){
                    this.bossGroup.shutdownGracefully();
                }
                if(workGroup != null){
                    this.workGroup.shutdownGracefully();
                }
            } catch (Exception ex) {
                logger.error("netty server close exception", ex);
            }
            logger.info("netty server closed");
        }
    }
}
