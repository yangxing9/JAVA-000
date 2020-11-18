package work3;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author yangxing
 * @version 1.0
 * @date 2020/11/18 0018 16:46
 */
public class Test {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");

        Student student1 = (Student) applicationContext.getBean("student1");
        Student student2 = (Student) applicationContext.getBean("student2");
        Student student3 = (Student) applicationContext.getBean("student3");
        Klass klass1 = (Klass) applicationContext.getBean("klass1");
        Klass klass2 = (Klass) applicationContext.getBean("klass2");
        School school = (School) applicationContext.getBean("school");

        System.out.println(student1);
        System.out.println(student2);
        System.out.println(student3);
        System.out.println("=============");
        System.out.println(klass1);
        System.out.println(klass2);
        System.out.println("=============");
        System.out.println(school);
    }

}
