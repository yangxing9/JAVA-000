package inbound;

import config.ProxyConfig;
import netty_server.NettyRemotingServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author com.yangxing
 * @version 1.0
 * @date 2020/11/4 0004 18:37
 */
public class HttpInboundServer {

    private static Logger logger = LoggerFactory.getLogger(HttpInboundServer.class);

    private NettyRemotingServer nettyRemotingServer;


    public HttpInboundServer(NettyRemotingServer nettyRemotingServer) {
        this.nettyRemotingServer = nettyRemotingServer;
    }

    public void run() {
        nettyRemotingServer.start();
    }
}
