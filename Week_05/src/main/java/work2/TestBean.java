package work2;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import work1.UserService;

import javax.annotation.Resource;

/**
 * @author yangxing
 * @version 1.0
 * @date 2020/11/18 0018 14:54
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=BookConfig.class)
//@ContextConfiguration("classpath:applicationContext.xml")
public class TestBean {

    ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");

    @Autowired
    private Book book;

    @Test
    public void play() {

        book.printBook();

        ApplicationContext context = new AnnotationConfigApplicationContext(BookScan.class);
        Book annotationBook = context.getBean("annotationBook", Book.class);
        annotationBook.printBook();

        Book bookXml = (Book) applicationContext.getBean("bookXml");
        bookXml.printBook();
    }



}
