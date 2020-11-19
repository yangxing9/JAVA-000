package configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.RedisOperations;
import properties.RedisProperties;
import template.RedisTemplateConfiguration;

/**
 * @author yangxing
 * @version 1.0
 * @date 2020/11/18 0018 19:58
 * 只有当 classpath 路径下发现 RedisOperations 类才会进行导入@Import下的配置类进入容器
 */
@ConditionalOnClass(RedisOperations.class)
@Import(value = {RedisConfiguration.class, RedisProperties.class, RedisTemplateConfiguration.class})
public class RedisAutoConfiguration {
}
