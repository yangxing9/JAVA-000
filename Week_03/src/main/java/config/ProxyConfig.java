package config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author com.yangxing
 * @version 1.0
 * @date 2020/11/4 0004 18:31
 */
@Component
@Data
public class ProxyConfig {

    @Value("${proxy.server: http://localhost:8088}")
    private String proxyServer;

    @Value("${proxy.port:8888}")
    private int proxyPort;

}

