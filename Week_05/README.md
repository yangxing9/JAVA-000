## 作业：
1. （选做）使Java里的动态代理，实现一个简单的AOP。
    > 使用自定义注解，加上jdk proxy实现
    >
    > 代码见： [/Week_05/src/main/java/work1](../Week_05/src/main/java/work1/)

2. （必做）写代码实现Spring Bean的装配，方式越多越好（XML、Annotation都可以）,提交到Github。
    > 主要有三种，其他都是在此基础上变种
    > 1. 在@Configuration类下使用 @Bean
    > 2. 使用@Component("annotationBook")，与 @ComponentScan 结合注入
    > 3. 使用 xml 注入 

    ```xml
       <bean id = "bookXml" class="work2.Book">
           <constructor-arg name="name" value="xml测试" />
           <constructor-arg name="author" value="yangxing" />
           <constructor-arg name="price" value="666" />
       </bean>
    ```
   > 代码见： [/Week_05/src/main/java/work2](../Week_05/src/main/java/work2/)
   
3. （选做）实现一个Spring XML自定义配置，配置一组Bean，例如Student/Klass/School。

    > 代码见： 
    >
    > [/Week_05/src/main/java/work3](../Week_05/src/main/java/work3/)
    >
    > [/Week_05/src/main/resources/spring-config.xml](../Week_05/src/main/resources/spring-config.xml)
    
4. （选做，会添加到高手附加题）

    1. （超级挑战）尝试使用ByteBuddy与Instrument实现一个简单JavaAgent实现无侵入下的AOP；
    
        > 使用ByteBuddy对字节码增强，Instrument实现agent,整体实现对指定包类下的方法进行耗时分析
        >
        > 代码见：[/Week_05/src/main/java/agent/ByteBuddyAgent.java](../Week_05/src/main/java/agent/ByteBuddyAgent.java)
        >
        > 代码见：[/Week_05/src/main/java/agent/TimeInterceptor.java](../Week_05/src/main/java/agent/TimeInterceptor.java)

5. （选做）总结一下，单例的各种写法，比较它们的优劣。
    > 主要有4种，饿汉式，懒汉式双重检查，内部类，枚举
    >
    > 实际上单例是在本类加载的时候才实例化的，只有当你以某种方式调用了这个类的时候，它才会进行初始化，
    > 而不是jvm启动的时候就初始化，而jvm本身会确保类的初始化只执行一次
    > 如果不使用这个单例对象的话，内存中根本没有Singleton实例对象，也就是和“懒汉模式”是一样的效果
    > - 当单例类里有其他静态方法的时候，推荐使用静态内部类的形式。
    > - 当单例类里只有getInstance()方法的时候，推荐直接new一个静态的单例对象。
    > - 当需要防止反射和序列化破坏单例的时候，推荐用枚举类的单例模式
    >
    > 代码见： [/Week_05/src/main/java/starter](../Week_05/src/main/java/starter/)

7. （必做）给前面课程提供的 Student/Klass/School 实现自动配置和 Starter。
    > 实现了基于redis的自动装配的starter
    >
    > 代码见：  
     [springboot-redis-autoconfig  starter](../../springboot-redis-autoconfig/src/main/java/configuration/RedisAutoConfiguration.java)
    > 
    > [测试代码](../../springboot-mycache/src/main/java/com/controller/ServiceController.java)

10. （必做）研究一下 JDBC 接口和数据库连接池，掌握它们的设计和用法：
    1. 使用 JDBC 原生接口，实现数据库的增删改查操作。
    2. 使用事务，PrepareStatement 方式，批处理方式，改进上述操作。
    3. 配置 Hikari 连接池，改进上述操作。提交代码到 Github。
    
    > 
    > 代码见：[/Week_05/src/main/java/jdbc](../Week_05/src/main/java/jdbc/)

11. (挑战)基于 AOP 和自定义注解，实现 @MyCache(60) 对于指定方法返回值缓存60秒
    > 实现 @MyCache 注解，缓存方法的返回值
    > 1. 可指定缓存时间，默认为-1(永不过期)
    > 2. 可指定缓存策略（redis，EHCACHE，MEMORYCACHE），目前已实现redis
    > 3. 可指定 是否缓存空值，默认为否
    > 4. 可指定 每访问一次，是否将刷新存活时间，默认否
    > 5. 支持基于SpEL的keyGenerator，可将入参的值 生成key
    > 
    > 代码见： [springboot-mycache](../../springboot-mycache/src/main/java/com/cache/MyCache.java)