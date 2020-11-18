package configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import properties.RedisProperties;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import java.net.ConnectException;

/**
 * @author yangxing
 * @version 1.0
 * @date 2020/11/18 0018 19:54
 *
 * @ConditionalOnClass(Jedis.class) 此注解表示当前ClassPath必须包含有Jedis这个类才会入这个配置类到spring容器中
 *  意思就是项目当中存在了jedis客户端依赖才觉得你需要使用，否则就没必要去注入.
 */
@Configuration
@ConditionalOnClass(Jedis.class)
public class RedisConfiguration {

    @Bean
    public JedisPool jedisPool(RedisProperties redisProperties) throws ConnectException {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMinIdle(redisProperties.getMinIdle());
        jedisPoolConfig.setMaxIdle(redisProperties.getMaxIdle());
        jedisPoolConfig.setMaxWaitMillis(redisProperties.getMaxWait());
        jedisPoolConfig.setMaxTotal(redisProperties.getMaxActive());
        String password = isBlank(redisProperties.getPassword()) ? null:redisProperties.getPassword();

        return new JedisPool(jedisPoolConfig,redisProperties.getHost(),redisProperties.getPort(),redisProperties.getTimeout(),password);
    }

    @Bean
    public JedisConnectionFactory redisConnectionFactory(RedisProperties redisProperties) {
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        jedisConnectionFactory.setHostName(redisProperties.getHost());
        jedisConnectionFactory.setPort(redisProperties.getPort());
        jedisConnectionFactory.setPassword(redisProperties.getPassword());
        jedisConnectionFactory.setDatabase(redisProperties.getDatabase());
        return jedisConnectionFactory;
    }



    public static boolean isBlank(String str) {
        return str == null || "".equals(str.trim());
    }

}
