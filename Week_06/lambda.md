## 函数式编程
> 高阶函数：如果一个函数接收一个函数作为参数，或者返回一个函数作为返回值，那么该函数就叫做高阶函数。函数式编程语言js等语言里面都支持大量的高阶函数，JAVA从1.8开始也开始支持高阶函数。   
1. 什么是Lambda表达式

    从动态引用到动态定义，简化写法
    
    Lambda 表达式（lambda expression）是一个匿名函数，Lambda表达式基于数学中的λ演算得名，直接对应于其中的lambda抽象（lambda abstraction），是一个匿名函数，即没有函数名的函数。
    
    ```
       // 1. 不需要参数,返回值为 5
       () -> 5
   
       // 2. 接收一个参数(数字类型),返回其2倍的值
       x -> 2 * x
   
       // 3. 接受2个参数(数字),并返回他们的差值
       (x, y) -> x – y
   
       // 4. 接收2个int型整数,返回他们的和
       (int x, int y) -> x + y
   
       // 5. 接受一个 string 对象,并在控制台打印,不返回任何值(看起来像是返回void)
       (String s) -> System.out.print(s)
   
    ```
    @FunctionalInterface
    
    - Predicate<T> 有参数、条件判断
    - Function<T, R> 有参数、有返回值
    - Consumer<T> 无返回值
    - Supplier<T> 无参数、有返回值

2. FunctionInterface接口中提供的方法详解
   - apply
   - compose （function组合）
        > 先应用beforefunction，再应用实例的function
        > 实际上：将两个function组合在一起了。先执行before方法，然后将处理的结果传递给当前对象的apply方法。实现了两个function的串联。既然实现了两个function的串联，就能实现多个函数的串联。
   - andThen
        > 和before函数相反，先应用this function，然后再使用after方法。 实现两个方法的串联。
   - identity
        > 传入什么，返回什么。

    真谛：函数式接口，传递的是行为，而不是数据。
    
    进一步简化：方法引用

3. Lambda表达式的基本结构
    1. 会有自动推断参数类型的功能
    ```
    （pram1,pram2,pram3）->{
        
    }
    ```
4. 函数式接口
    
    抽象方法，抽象接口
    1个接口里面只有一个抽象方法，可以有几个具体的方法
    ```java
       @Documented
       @Retention(RetentionPolicy.RUNTIME)
       @Target(ElementType.TYPE)
       public @interface FunctionalInterface {}
    ```
    关于函数式接口：
    
    1. 如何一个接口只有一个抽象方法，那么这个接口就是函数式接口
    2. 如果我们在某个接口上生命了FunctionalInterface注解，那么编译器就会按照函数式接口的定义来要求该注解
    3. 如果某个接口只有一个抽象方法，但我们没有给该接口生命FunctionalInterface接口，编译器也还会把该接口当做成一个函数是接口。
    4. java8 后，可以有具体实现，需要加上 default

5. 简单认识一下流
    - 串行流，并行流
    - 中间流，节点流
    - Pipeline 管道 （Linux的一种实现方式）
    
    流是需要有一个源头的，是一个来自数据源的元素队列并支持聚合操作
    - 元素：特定类型的对象，形成一个队列。 Java中的Stream并不会存储元素，而是按需计算。
    - 数据源：流的来源。 可以是集合，数组，I/O channel， 产生器generator 等。
    - 聚合操作 类似SQL语句一样的操作， 比如filter, map, reduce, find, match, sorted等。
    - 和以前的Collection操作不同， Stream操作还有两个基础的特征：
    - Pipelining：中间操作都会返回流对象本身。 这样多个操作可以串联成一个管道， 如同流式风
    格（fluent style）。 这样做可以对操作进行优化， 比如延迟执行(laziness)和短路( short- circuiting)。
    - 内部迭代：以前对集合遍历都是通过Iterator或者For-Each的方式, 显式的在集合外部进行迭代，
    这叫做外部迭代。 Stream提供了内部迭代的方式， 通过访问者模式(Visitor)实现。
    
    创建有Stream有哪些方法？
    
    1. Arrays.stream，我们可以通过Arrays的静态方法，传入一个泛型数组，创建一个流
    2. Stream.of，我们可以通过Stream的静态方法，传入一个泛型数组，或者多个参数，创建一个流，这个静态方法，也是调用了Arrays的stream静态方法
    3. Collection.stream/parallelStream,可以用过集合的接口的默认方法，创建一个流；使用这个方法，包括继承Collection的接口，如：Set，List，Map，SortedSet 等等
    4. Stream.iterate，是Stream接口下的一个静态方法，从名字也可以看出，这个静态方法，是以迭代器的形式，创建一个数据流
    5. Stream.generate，也是stream中的一个静态方法
    ```
       Arrays.stream(new int[]{1,2,3}).forEach(System.out::println);
       Stream.of(1,2,3).forEach(System.out::println);
       Stream.of(new int[]{1,2,3}).forEach(System.out::println);
       Arrays.asList(1,2,3).stream().forEach(System.out::println);
       Stream.iterate(1, x -> ++x).limit(10).collect(Collectors.toList()).forEach(System.out::println);
       Stream.generate(() -> "a").limit(10).collect(Collectors.toList()).forEach(System.out::println);
    ```
    > 1-3，是根据具体的数组或者集合对象，创建的流，在创建流之前，这些对象的大小（长度）已经确认，所以这个种方式的流，也被成为有限流，
    >
    > 而4-5中，创建流的方式，是无限大小的流（generate 最大是Long.MAX_VALUE），也被成为无限流，那么我们不可能就这样放任对象被无限创建，直到内存溢出，这样的无限流，也是配合limit使用，指定这个流生成的元素的个数，对于无限流，下面再简单讲个案例，使用传统的方式和无限流的方式，创建一个固定大小的ArrayList，这样大家也会有比较清楚的认识
    6. java.io.BufferedReader.lines()
    7. java.util.stream.IntStream.range()
    8. java.nio.file.Files.walk()
    9. Random.ints()
    10. BitSet.stream()
    11. Pattern.splitAsStream(java.lang.CharSequence)
    12. JarFile.stream()
    
6. Stream操作
    
    中间操作：
    1. 选择与过滤
        - filter(Predicate p) 接收 Lambda ， 从流中排除某些元素，留下来符合条件的
        - distinct() 筛选，通过流所生成元素的 hashCode() 和 equals() 去除重复元素
        - limit(long maxSize) 截断流，使其元素不超过给定数量。
        - skip(long n) 跳过元素，返回一个扔掉了前 n 个元素的流。若流中元素不足 n 个，则返回一个空流。
    2. 映射
        - map(Function f) 接收 Lambda ， 将元素转换成其他形式或提取信息;接收一个函数作为参数，该函数会被应用到每个元素上，并将其映射成一个新的元素。
        - mapToDouble(ToDoubleFunction f) 接收一个函数作为参数，该函数会被应用到每个元素上，产生一个新的 DoubleStream。
        - mapToInt(ToIntFunction f) 接收一个函数作为参数，该函数会被应用到每个元素上，产生一个新的 IntStream。
        - mapToLong(ToLongFunction f) 接收一个函数作为参数，该函数会被应用到每个元素上，产生一个新的 LongStream。
        - flatMap(Function f) 接收一个函数作为参数，将流中的每个值都换成另一个流，然后把所有流连接成一个流。
    3. 排序
        - sorted() 产生一个新流，其中按自然顺序排序
        - sorted(Comparator comp) 产生一个新流，其中按比较器顺序排序
    
    终止操作：
    1. 查找与匹配
        - allMatch——检查是否匹配所有元素
        - anyMatch——检查是否至少匹配一个元素
        - noneMatch——检查是否没有匹配的元素
        - findFirst——返回第一个元素
        - findAny——返回当前流中的任意元素
        - count——返回流中元素的总个数
        - max——返回流中最大值
        - min——返回流中最小值
    2. 归约 reduce, 需要初始值（类比Map-Reduce）
    3. 收集 collect
        - toList List<T> 把流中元素收集到List
        - toSet Set<T> 把流中元素收集到Set
        - toCollection Collection<T> 把流中元素收集到创建的集合
        - count 计算流中元素的个数
        - summaryStatistics 统计最大最小平均值
    4. 迭代 forEach
    
    Stream 的特性可以归纳为：
    - 不是数据结构
    - 它没有内部存储，它只是用操作管道从 source（数据结构、数组、generator function、IO channel）抓取数据。
    - 它也绝不修改自己所封装的底层数据结构的数据。例如 Stream 的 filter 操作会产生一个不包含被过滤元素的新 Stream，而不是从 source 删除那些元素。
    - 所有 Stream 的操作必须以 lambda 表达式为参数
    - 不支持索引访问
    - 你可以请求第一个元素，但无法请求第二个，第三个，或最后一个。不过请参阅下一项。
    - 很容易生成数组或者 List
    - 惰性化
    - 很多 Stream 操作是向后延迟的，一直到它弄清楚了最后需要多少数据才会开始。
    - Intermediate 操作永远是惰性化的。
    - 并行能力
    - 当一个 Stream 是并行化的，就不需要再写多线程代码，所有对它的操作会自动并行进行的。
    - 可以是无限的
    - 集合有固定大小，Stream 则不必。limit(n) 和 findFirst() 这类的 short-circuiting 操作可以对无限的 Stream 进行运算并很快完成。

    参考资料：
    - [Java 8 中的 Streams API 详解](https://developer.ibm.com/zh/articles/j-lo-java8streamapi/)
    - [强大的Stream API（二）](https://www.jianshu.com/p/932ef18941fb) 
    - [强大的Stream API（三）](https://www.jianshu.com/p/633f691f9afb)
    
 