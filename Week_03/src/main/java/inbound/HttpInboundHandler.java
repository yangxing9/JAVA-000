package inbound;

import config.ProxyConfig;
import filter.HttpRequestFilter;
import filter.HttpRequestHeaderFilter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import outbound.OutboundHandler;
import outbound.netty.NettyClientOutboundHandler;

public class HttpInboundHandler extends ChannelInboundHandlerAdapter {

    private static Logger logger = LoggerFactory.getLogger(HttpInboundHandler.class);
    private final ProxyConfig proxyConfig;

    private OutboundHandler handler;
    private HttpRequestFilter filter;
    
    public HttpInboundHandler(final ProxyConfig proxyConfig) {
        this.proxyConfig = proxyConfig;
        this.filter = new HttpRequestHeaderFilter();

        // http 方式调用
//        this.handler = new HttpOutboundHandler(proxyConfig.getProxyServer());
        // netty 方式调用
        this.handler = new NettyClientOutboundHandler(proxyConfig);
    }
    
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        try {
            logger.info("channelRead流量接口请求开始");
            FullHttpRequest fullRequest = (FullHttpRequest) msg;
            String uri = fullRequest.uri();
            logger.info("接收到的请求url为{}", uri);

            filter.filter(fullRequest, ctx);

            handler.handle(fullRequest, ctx);
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

}
