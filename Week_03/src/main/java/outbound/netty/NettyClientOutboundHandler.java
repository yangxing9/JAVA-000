package outbound.netty;

import config.NettyClientConfig;
import config.ProxyConfig;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import netty_server.NettyRemotingClient;
import outbound.OutboundHandler;

/**
 * @author com.yangxing
 * @version 1.0
 * @date 2020/11/4 0004 20:18
 */
public class NettyClientOutboundHandler implements OutboundHandler {

    private NettyRemotingClient client;

    private ProxyConfig proxyConfig;

    public NettyClientOutboundHandler(ProxyConfig proxyConfig){
        NettyClientConfig nettyClientConfig = new NettyClientConfig();
        this.proxyConfig = proxyConfig;
        this.client = new NettyRemotingClient(nettyClientConfig,proxyConfig);
    }

    @Override
    public void handle(FullHttpRequest fullRequest, ChannelHandlerContext ctx) throws InterruptedException {
        client.connect(fullRequest, ctx);
    }
}
