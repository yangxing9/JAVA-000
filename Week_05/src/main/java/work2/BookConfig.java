package work2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author yangxing
 * @version 1.0
 * @date 2020/11/18 0018 14:54
 */
@Configuration
public class BookConfig {



    /**
     * 不写名称，则使用 getBook 方法名作为 bean 名称
     */
    @Bean("book")
    public Book getBook(){
        return new Book("JVM核心技术","yangxing","998");
    }

}

