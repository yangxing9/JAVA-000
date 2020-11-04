package constants;

/**
 * @author com.yangxing
 * @version 1.0
 * @date 2020/11/4 0004 18:26
 */
public final class Constants {

    private Constants() {
        throw new IllegalStateException("Constants class");
    }

    public static final String THREAD_NAME_NETTY_SERVER = "Netty-Server";

    public static final String GATEWAY_NAME = "NIOGateway";

    public static final String GATEWAY_VERSION = "1.0.0";

    /**
     *  cpus
     */
    public static final int CPUS = Runtime.getRuntime().availableProcessors();

}
