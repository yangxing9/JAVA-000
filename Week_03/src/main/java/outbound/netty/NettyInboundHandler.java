package outbound.netty;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;

import java.nio.charset.Charset;

/**
 * @author com.yangxing
 * @version 1.0
 * @date 2020/11/4 0004 19:33
 */
public class NettyInboundHandler extends ChannelInboundHandlerAdapter {
    public static String result;
    private ChannelHandlerContext parentCtx;
    private FullHttpRequest request;

    public NettyInboundHandler(FullHttpRequest request, ChannelHandlerContext ctx){
        this.parentCtx = ctx;
        this.request = request;
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(msg instanceof HttpContent)
        {
            HttpContent content = (HttpContent)msg;
            final String byteBuf = content.content().toString(Charset.defaultCharset());

            FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, Unpooled.wrappedBuffer(byteBuf.getBytes()));
            // 通过filter,新增 Heand -> nio:tanghui
            response.headers().add(request.headers());
            response.headers().set("Content-Type", "application/json");
            response.headers().setInt("Content-Length", byteBuf.length());
            parentCtx.write(response).addListener(ChannelFutureListener.CLOSE);
            parentCtx.flush();
            ctx.close();
        }
    }



}
