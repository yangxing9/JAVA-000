package filter;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

/**
 * @author com.yangxing
 * @version 1.0
 * @date 2020/11/4 0004 20:32
 */
public interface HttpRequestFilter {

    void filter(FullHttpRequest fullHttpRequest, ChannelHandlerContext ctx);

}
