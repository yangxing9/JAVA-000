package config;

import constants.Constants;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


/**
 * @author com.yangxing
 * @version 1.0
 * @date 2020/11/4 0004 18:40
 */

@Component
@Data
public class NettyServerConfig {

    /**
     * init the server connectable queue
     */
    private int soBacklog = 1024;

    /**
     *  whether tpc delay
     */
    private boolean tcpNoDelay = true;

    /**
     *  whether keep alive
     */
    private boolean soKeepalive = true;

    /**
     *  send buffer size
     */
    private int sendBufferSize = 32 * 1024;

    /**
     *  receive buffer size
     */
    private int receiveBufferSize = 32 * 1024;

    /**
     *  worker threads，default get machine cpus
     */
    private int workerThread = Constants.CPUS;

    /**
     *  listen port
     */
    @Value("${proxy.port:8888}")
    private int listenPort;

}
