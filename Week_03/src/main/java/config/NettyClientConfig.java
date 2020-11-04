package config;

import constants.Constants;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
/**
 * @author com.yangxing
 * @version 1.0
 * @date 2020/11/4 0004 18:40
 */
public class NettyClientConfig {

    /**
     *  worker threads，default get machine cpus
     */
    private int workerThreads = Constants.CPUS;

    /**
     *  whether tpc delay
     */
    private boolean tcpNoDelay = true;

    /**
     * whether keep alive
     */
    private boolean soKeepalive = true;

    /**
     *  send buffer size
     */
    private int sendBufferSize = 65535;

    /**
     *  receive buffer size
     */
    private int receiveBufferSize = 65535;


}
