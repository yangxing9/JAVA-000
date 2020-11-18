package properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author yangxing
 * @version 1.0
 * @date 2020/11/18 0018 19:55
 * 限定使用此starter的redis配置必须以“my.springboot.redis.”为前缀
 * 示例:
 * my.springboot.redis.host
 * my.springboot.redis.prot
 */
@Component
@ConfigurationProperties(prefix="my.springboot.redis")
public class RedisProperties {

    private int port;

    private String host;

    private String password;

    private int timeout;

    private int database;

    private int maxActive;

    private int maxWait;

    private int maxIdle;

    private int minIdle;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public int getDatabase() {
        return database;
    }

    public void setDatabase(int database) {
        this.database = database;
    }

    public int getMaxActive() {
        return maxActive;
    }

    public void setMaxActive(int maxActive) {
        this.maxActive = maxActive;
    }

    public int getMaxWait() {
        return maxWait;
    }

    public void setMaxWait(int maxWait) {
        this.maxWait = maxWait;
    }

    public int getMaxIdle() {
        return maxIdle;
    }

    public void setMaxIdle(int maxIdle) {
        this.maxIdle = maxIdle;
    }

    public int getMinIdle() {
        return minIdle;
    }

    public void setMinIdle(int minIdle) {
        this.minIdle = minIdle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RedisProperties that = (RedisProperties) o;
        return port == that.port &&
                timeout == that.timeout &&
                database == that.database &&
                maxActive == that.maxActive &&
                maxWait == that.maxWait &&
                maxIdle == that.maxIdle &&
                minIdle == that.minIdle &&
                Objects.equals(host, that.host) &&
                Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(port, host, password, timeout, database, maxActive, maxWait, maxIdle, minIdle);
    }

    @Override
    public String toString() {
        return "RedisProperties{" +
                "port=" + port +
                ", host='" + host + '\'' +
                ", password='" + password + '\'' +
                ", timeout=" + timeout +
                ", database=" + database +
                ", maxActive=" + maxActive +
                ", maxWait=" + maxWait +
                ", maxIdle=" + maxIdle +
                ", minIdle=" + minIdle +
                '}';
    }
}
