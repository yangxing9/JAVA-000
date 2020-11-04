package start;

import config.NettyServerConfig;
import config.ProxyConfig;
import constants.Constants;
import inbound.HttpInboundServer;
import netty_server.NettyRemotingServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;

import javax.annotation.PostConstruct;

/**
 * @author com.yangxing
 * @version 1.0
 * @date 2020/11/4 0004 17:38
 */
@ComponentScan(value = "config")
public class NettyServerApplication {

    private static Logger log = LoggerFactory.getLogger(NettyServerApplication.class);

    @Autowired
    private ProxyConfig proxyConfig;

    @Autowired
    private NettyServerConfig serverConfig;

    /**
     * netty remote server
     */
    private NettyRemotingServer nettyRemotingServer;

    public static void main(String[] args) {
        Thread.currentThread().setName(Constants.THREAD_NAME_NETTY_SERVER);
        new SpringApplicationBuilder(NettyServerApplication.class).web(WebApplicationType.NONE).run(args);
    }

    @PostConstruct
    public void run(){
        log.info(Constants.GATEWAY_NAME + " " + Constants.GATEWAY_VERSION +" starting...");
        log.info(Constants.GATEWAY_NAME + " " + Constants.GATEWAY_VERSION +" started at http://localhost:" + proxyConfig.getProxyPort() + " for server:" + proxyConfig.getProxyServer());

        this.nettyRemotingServer = new NettyRemotingServer(serverConfig,proxyConfig);

        HttpInboundServer server = new HttpInboundServer(nettyRemotingServer);
        server.run();

        /**
         *  添加了进程退出的hook。先发送预警信息，然后调用stop“优雅”退出
         */
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                close("shutdownHook");
            }
        }));
    }

    public void close(String cause) {

        try {

            log.info("netty server is stopping ..., cause : {}", cause);
            this.nettyRemotingServer.close();
        } catch (Exception e) {
            log.error("netty server stop exception ", e);
            System.exit(-1);
        }
    }

}
