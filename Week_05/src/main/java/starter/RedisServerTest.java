package starter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.TimeUnit;

/**
 * @author yangxing
 * @version 1.0
 * @date 2020/11/18 0018 20:08
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class RedisServerTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void play() {
        redisTemplate.boundValueOps("yx").set("yx",1, TimeUnit.MINUTES);

        System.out.println(redisTemplate.hasKey("yx"));
    }
}
