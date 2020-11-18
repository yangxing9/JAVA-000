package work1;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author yangxing
 * @version 1.0
 * @date 2020/11/18 0018 14:07
 */
@Retention(RetentionPolicy.RUNTIME) // 表示这个注解可以生存到运行期
@Target({ElementType.METHOD }) // 指定注解的使用范围
public @interface EnableProxy {


}
