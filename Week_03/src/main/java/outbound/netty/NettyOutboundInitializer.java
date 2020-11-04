package outbound.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.codec.http.HttpResponseDecoder;

/**
 * @author com.yangxing
 * @version 1.0
 * @date 2020/11/4 0004 19:03
 */
public class NettyOutboundInitializer extends ChannelInitializer<SocketChannel> {

    FullHttpRequest request;

    ChannelHandlerContext ctx;


    public NettyOutboundInitializer(FullHttpRequest request, ChannelHandlerContext ctx) {
        this.request = request;
        this.ctx = ctx;
    }

    @Override
    public void initChannel(SocketChannel ch) {
        ChannelPipeline p = ch.pipeline();
        // 客户端接收到的是httpResponse响应，所以要使用HttpResponseDecoder进行解码
        ch.pipeline().addLast(new HttpResponseDecoder());
        ch.pipeline().addLast(new NettyInboundHandler(request, ctx));
//                     客户端发送的是httprequest，所以要使用HttpRequestEncoder进行编码
        ch.pipeline().addLast(new HttpRequestEncoder());

    }

}
