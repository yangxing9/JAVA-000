## 常见配置汇总
### 堆设置
- -Xms:初始堆大小,默认为物理内存的1/64(<1GB)
- -Xmx:最大堆大小 (该参数一般和Xms设置一致，防止每次垃圾回收完成后JVM重新分配内存)
- -Xmn： 等价于同时设置-XX:NewSize和-XX:MaxNewSize，使用G1垃圾收集器 不应该 设置该选项，在其他的某些业务场景下可以设置。官方建议设置为 -Xmx 的 1/2 ~ 1/4新生代的内存空间大小，注意：此处的大小是（eden+ 2 survivor space)。与jmap -heap中显示的New gen是不同的。整个堆大小=新生代大小 + 老生代大小 + 永久代大小。
      在保证堆大小不变的情况下，增大新生代后,将会减小老生代大小。此值对系统性能影响较大,Sun官方推荐配置为整个堆的3/8。
- -Xss512K  //设置每个线程栈的字节数。 例如 -Xss1m 指定线程栈为1MB，与XX:ThreadStackSize=1m等价
- -XX:MaxMetaspaceSize=size, Java8默认不限制Meta空间, 一般不允许设置该选项。
- -XX:MaxDirectMemorySize=size，系统可以使用的最大堆外内存，这个参数跟-Dsun.nio.MaxDirectMemorySize效果相同。
- -XX:NewSize=n:设置年轻代大小
- -XX:NewRatio=n:设置年轻代和年老代的比值.如:为2,表示年轻代与年老代比值为1:2,年轻代占整个年轻代年老代和的1/3 （默认为2）
- -XX:SurvivorRatio=n:年轻代中Eden区与两个Survivor区的比值.注意Survivor区有两个.如:8,表示Eden:Survivor=8:2,一个Survivor区占整个年轻代的1/10 （默认为8）
> 1. S0C S1C 当前能使用的容量，并不是最大的容量，最大容量是一样的
> 2. -XX:-UseAdaptiveSizePolicy -XX:SurvivorRatio=8 加这两个参数，能让严格8:1:1，并且s0和s1一样
> 3. ratio之类的控制的比例都是最大值
> 4. 当前young区很小的时候，各个指标到不了最大值，所以，默认UseAdaptiveSizePolicy开了的情况下，可能会有调整。比如不是严格的8：1：1，S0和S1不一样大
- -XX:MaxPermSize=64m   //持久代最大值 （java8以后取消永久代，增加元空间，该参数会忽略）
- -XX:PermSize=16m  //持久代初始值 （java8以后取消永久代，增加元空间，该参数会忽略）
- -XX:+UseAdaptiveSizePolicy //自动调整年轻代各区大小及晋升年龄
  > 1. java8默认使用该参数
  > 2. 在 JDK 1.8 中，如果使用 CMS，无论 UseAdaptiveSizePolicy 如何设置，都会将 UseAdaptiveSizePolicy 设置为 false；不过不同版本的JDK存在差异；
  > 3. UseAdaptiveSizePolicy不要和SurvivorRatio参数显示设置搭配使用，一起使用会导致参数失效；
  > 4. 由于AdaptiveSizePolicy会动态调整 Eden、Survivor 的大小，有些情况存在Survivor 被自动调为很小，比如十几MB甚至几MB的可能，这个时候YGC回收掉 Eden区后，还存活的对象进入Survivor 装不下，就会直接晋升到老年代，导致老年代占用空间逐渐增加，从而触发FULL GC，如果一次FULL GC的耗时很长（比如到达几百毫秒），那么在要求高响应的系统就是不可取的。
  > 5. 对于面向外部的大流量、低延迟系统，不建议启用此参数，建议关闭该参数。

- -XX:-UseAdaptiveSizePolicy //不自动调整各区大小及晋升年龄
- -XX:PretenureSizeThreshold=2097152 //直接晋升到老年代的对象大小
- -XX:MaxTenuringThreshold=15(default) //晋升到老年代的对象年龄，PSGen无效
- -XX:-DisableExplicitGC //禁止在运行期显式地调用?System.gc()
- -XX:+HeapDumpOnOutOfMemoryError  //在OOM时输出堆内存快照
- -XX:HeapDumpPath=./java_pid<pid>.hprof  //堆内存快照的存储路径
- -XX:+CMSScavengeBeforeRemark //执行CMS重新标记之前，尝试执行一此MinorGC
- -XX:+CMSPermGenSweepingEnabled //开启永久代的并发垃圾收集


### 收集器设置
- -XX:+UseSerialGC://默认不启用，client使用时启用 设置串行收集器 （表示 "Serial" + "Serial Old"组合）
- -XX:+UseParallelGC: //年轻代并行GC，标记-清除 设置并行收集器  (表示 "Parallel Scavenge" + "Serial Old"组合)
- -XX:+UseParalledlOldGC: //老年代并行GC，标记-清除 设置并行年老代收集器 (表示 "Parallel Scavenge" + "Parallel Old"组合) (java8默认的)
- -XX:+UseConcMarkSweepGC:设置并发收集器 (表示 "ParNew" + "CMS". 组合，"CMS" 是针对旧生代使用最多的)
- -XX:+UseCMSCompactAtFullCollection  //FullGC后进行内存碎片整理压缩
- -XX:CMSFullGCsBeforeCompaction=n  //n次FullGC后执行内存整理
- -XX:+CMSParallelRemarkEnabled  //启用并行重新标记,只适用ParNewGC
- -XX:CMSInitiatingOccupancyFraction=80             //cms作为垃圾回收是，回收比例80%
- -XX:+UseParNewGC  //Serial多线程版
- -XX:+UseG1GC //启用G1收集器
> g1 gc推荐 xms==xmx，同时不设置xmn
> 因为g1最好自己完全管内存，
> young区是可以根据需要自己控制有多少小region的，设置了xmn就定死了young

### 垃圾回收统计信息
- -XX:+PrintGC
- -Xloggc:filename
- -Xloggc:gc.log  // 输出gc日志文件
- -XX:+UseGCLogFileRotation  //使用log文件循环输出
- -XX:NumberOfGCLogFiles=1  //循环输出文件数量
- -XX:GCLogFileSize=8k //日志文件大小限制
- -XX:+PrintGCDateStamps //gc日志打印时间
- -XX:+PrintTenuringDistribution            //查看每次minor GC后新的存活周期的阈值
- -XX:+PrintGCDetails //输出gc明细
- -XX:+PrintGCApplicationStoppedTime //输出gc造成应用停顿的时间
- -XX:+PrintReferenceGC //输出堆内对象引用收集时间
- -XX:+PrintHeapAtGC //输出gc前后堆占用情况
- -XX:+-HeapDumpOnOutOfMemoryError 选项, 当 OutOfMemoryError 产生，即内存溢出(堆内存或持久代)时，自动Dump堆内存。
> 示例用法: java -XX:+HeapDumpOnOutOfMemoryError -Xmx256m ConsumeHeap
- -XX:HeapDumpPath 选项, 与HeapDumpOnOutOfMemoryError搭配使用, 指定内存溢出时Dump文件的目录。如果没有指定则默认为启动Java程序的工作目录。
> 示例用法: java -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/usr/local/ ConsumeHeap 

### 并行收集器设置
- -XX:ParallelGCThreads=n: //并行GC线程数， cpu<=8?cpu:5*cpu/8+3 设置并行收集器收集时使用的CPU数.并行收集线程数.
- -XX:MaxGCPauseMillis=n: //每次GC最大停顿时间,单位为毫秒 设置并行收集最大暂停时间
- -XX:GCTimeRatio=n:设置垃圾回收时间占程序运行时间的百分比.公式为1/(1+n)

### 并发收集器设置
- -XX:+CMSIncrementalMode:设置为增量模式.适用于单CPU情况.
- -XX:ParallelGCThreads=n:设置并发收集器年轻代收集方式为并行收集时,使用的CPU数.并行收集线程数

## jvm命令以及作用

### 1. jps
> Java版的ps命令，查看java进程及其相关的信息，如果你想找到一个java进程的pid，那可以用jps命令替代linux中的ps命令了，简单而方便。

命令格式：

``` jps [options] [hostid]```

options参数解释：

- -l : 输出主类全名或jar路径
- -q : 只输出LVMID
- -m : 输出JVM启动时传递给main()的参数
- -v : 输出JVM启动时显示指定的JVM参数

最常用示例：

```shell
jps -l 输出jar包路径，类全名
jps -m 输出main参数
jps -v 输出JVM参数
```
### 2. jinfo
> jinfo是用来查看JVM参数和动态修改部分JVM参数的命令

命令格式：

`jinfo [option] <pid>`

options参数解释：

- -flag <name> 打印指定名称的参数
- -flag [+|-]<name> 打开或关闭参数
- -flag <name>=<value> 设置参数
- -flags 打印所有参数
- -sysprops 打印系统配置

最常用示例：

1. 查看JVM参数和系统配置
```shell
jinfo 11666
jinfo -flags 11666
jinfo -sysprops 11666
```
2. 查看打印GC日志参数
```shell
jinfo -flag PrintGC 11666
jinfo -flag PrintGCDetails 11666
```
3. 打开/关闭GC日志参数
```shell
jinfo -flag +PrintGC 11666
jinfo -flag +PrintGCDetails 11666
jinfo -flag -PrintGC 11666
jinfo -flag -PrintGCDetails 11666
```
## 3. jstat

> jstat命令是使用频率比较高的命令，主要用来查看JVM运行时的状态信息，包括内存状态、垃圾回收等。

命令格式：

`jstat [option] LVMID [interval] [count]`

其中LVMID是进程id，interval是打印间隔时间（毫秒），count是打印次数（默认一直打印）

option参数解释：

- -class class loader的行为统计
- -compiler HotSpt JIT编译器行为统计
- -gc 垃圾回收堆的行为统计
- -gccapacity 各个垃圾回收代容量(young,old,perm)和他们相应的空间统计
- -gcutil 垃圾回收统计概述
- -gccause 垃圾收集统计概述（同-gcutil），附加最近两次垃圾回收事件的原因
- -gcnew 新生代行为统计
- -gcnewcapacity 新生代与其相应的内存空间的统计
- -gcold 年老代和永生代行为统计
- -gcoldcapacity 年老代行为统计
- -gcpermcapacity 永生代行为统计
- -printcompilation HotSpot编译方法统计

常用示例及打印字段解释：
```shell
jstat -gcutil 11666 1000 3
```
11666为pid，每隔1000毫秒打印一次，打印3次

输出：
```
S0     S1     E      O      M     CCS    YGC     YGCT    FGC    FGCT     GCT

6.17   0.00   6.39  33.72  93.42  90.57    976   57.014    68   53.153  110.168

6.17   0.00   6.39  33.72  93.42  90.57    976   57.014    68   53.153  110.168

6.17   0.00   6.39  33.72  93.42  90.57    976   57.014    68   53.153  110.168
````
字段解释:

- S0 survivor0使用百分比
- S1 survivor1使用百分比
- E Eden区使用百分比
- O 老年代使用百分比
- M 元数据区使用百分比
- CCS 压缩使用百分比
- YGC 年轻代垃圾回收次数
- YGCT 年轻代垃圾回收消耗时间
- FGC 老年代垃圾回收次数
- FGCT 老年代垃圾回收消耗时间
- GCT 垃圾回收消耗总时间

```shell
jstat -gc 11666 1000 3
```
-gc和-gcutil参数类似，只不过输出字段不是百分比，而是实际的值。

输出：
```shell
S0C        S1C        S0U    S1U        EC           EU           OC        OU        MC         MU         CCSC     CCSU       YGC   YGCT    FGC     FGCT    GCT

25600.0   25600.0     0.0   1450.0    204800.0     97460.7   512000.0   172668.8  345736.0   322997.7    48812.0     44209.0    977   57.040   68     53.153  110.193

25600.0   25600.0     0.0   1450.0    204800.0     97460.7   512000.0   172668.8  345736.0   322997.7    48812.0     44209.0    977   57.040   68     53.153  110.193

25600.0   25600.0     0.0   1450.0    204800.0     97460.7   512000.0   172668.8  345736.0   322997.7    48812.0     44209.0    977   57.040   68     53.153  110.193
```
字段解释：

- S0C survivor0大小
- S1C survivor1大小
- S0U survivor0已使用大小
- S1U survivor1已使用大小
- EC Eden区大小
- EU Eden区已使用大小
- OC 老年代大小
- OU 老年代已使用大小
- MC 方法区大小
- MU 方法区已使用大小
- CCSC 压缩类空间大小
- CCSU 压缩类空间已使用大小
- YGC 年轻代垃圾回收次数
- YGCT 年轻代垃圾回收消耗时间
- FGC 老年代垃圾回收次数
- FGCT 老年代垃圾回收消耗时间
- GCT 垃圾回收消耗总时间

## 4. jstack
>jstack是用来查看JVM线程快照的命令，线程快照是当前JVM线程正在执行的方法堆栈集合。使用jstack命令可以定位线程出现长时间卡顿的原因，例如死锁，死循环等。jstack还可以查看程序崩溃时生成的core文件中的stack信息。

命令格式：
```shell
jstack [-l] <pid> (连接运行中的进程)
jstack -F [-m] [-l] <pid> (连接挂起的进程)
jstack [-m] [-l] <executable> <core> (连接core文件)
jstack [-m] [-l] [server_id@]<remote server IP or hostname> (连接远程debug服务器)
```
option参数解释：

- -F 当使用jstack <pid>无响应时，强制输出线程堆栈。
- -m 同时输出java和本地堆栈(混合模式)
- -l 额外显示锁信息

常用示例：
```shell
jstack -l 11666 | more
```
输出信息：
```
Full thread dump Java HotSpot(TM) 64-Bit Server VM (25.25-b02 mixed mode):

"Attach Listener" #25525 daemon prio=9 os_prio=0 tid=0x00007fd374002000 nid=0x70e8 waiting on condition [0x0000000000000000]
   java.lang.Thread.State: RUNNABLE

   Locked ownable synchronizers:
    - None
......
```
## 5. jmap
>jmap是用来生成堆dump文件和查看堆相关的各类信息的命令，例如查看finalize执行队列，heap的详细信息和使用情况。

命令格式：
```shell
jmap [option] <pid> (连接正在执行的进程)
jmap [option] <executable <core> (连接一个core文件)
jmap [option] [server_id@]<remote server IP or hostname> (链接远程服务器)
```
option参数解释：

- [none] to print same info as Solaris pmap
- -heap 打印java heap摘要
- -histo[:live] 打印堆中的java对象统计信息
- -clstats 打印类加载器统计信息
- -finalizerinfo 打印在f-queue中等待执行finalizer方法的对象
- -dump:<dump-options> 生成java堆的dump文件
  - dump-options:
    - live 只转储存活的对象，如果没有指定则转储所有对象
    - format=b 二进制格式
    - file=<file> 转储文件到 <file>
- -F 强制选项

 常用示例：
 ```shell
 //这个命令是要把java堆中的存活对象信息转储到dump.hprof文件
 jmap -dump:live,format=b,file=dump.hprof 11666
```
输出：

```shell
Dumping heap to /dump.hprof ...
Heap dump file created
```
示例二
```shell
jmap -finalizerinfo 11666
```
输出：
```shell
// 输出结果的含义为当前没有在等待执行finalizer方法的对象
Attaching to process ID 11666, please wait...
Debugger attached successfully.
Server compiler detected.
JVM version is 24.71-b01
Number of objects pending for finalization: 0
```
示例三：
```shell
// 输出堆的详细信息
jmap -heap 11666
```
输出：
```shell
Attaching to process ID 11666, please wait...
Debugger attached successfully.
Server compiler detected.
JVM version is 25.25-b02

using thread-local object allocation.
Parallel GC with 4 thread(s)

Heap Configuration: //堆内存初始化配置
   MinHeapFreeRatio         = 0 //对应jvm启动参数-XX:MinHeapFreeRatio设置JVM堆最小空闲比率(default 40)
   MaxHeapFreeRatio         = 100 //对应jvm启动参数 -XX:MaxHeapFreeRatio设置JVM堆最大空闲比率(default 70)
   MaxHeapSize              = 1073741824 (1024.0MB) //对应jvm启动参数-XX:MaxHeapSize=设置JVM堆的最大大小
   NewSize                  = 22020096 (21.0MB) //对应jvm启动参数-XX:NewSize=设置JVM堆的新生代的默认大小
   MaxNewSize               = 357564416 (341.0MB) //对应jvm启动参数-XX:MaxNewSize=设置JVM堆的新生代的最大大小
   OldSize                  = 45088768 (43.0MB) //对应jvm启动参数-XX:OldSize=<value>:设置JVM堆的老年代的大小
   NewRatio                 = 2 //对应jvm启动参数-XX:NewRatio=:新生代和老生代的大小比率
   SurvivorRatio            = 8 //对应jvm启动参数-XX:SurvivorRatio=设置新生代中Eden区与Survivor区的大小比值
   MetaspaceSize            = 21807104 (20.796875MB) // 元数据区大小
   CompressedClassSpaceSize = 1073741824 (1024.0MB) //类压缩空间大小
   MaxMetaspaceSize         = 17592186044415 MB //元数据区最大大小
   G1HeapRegionSize         = 0 (0.0MB) //G1垃圾收集器每个Region大小

Heap Usage: //堆内存使用情况
PS Young Generation
Eden Space: //Eden区内存分布
   capacity = 17825792 (17.0MB) //Eden区总容量
   used     = 12704088 (12.115562438964844MB) //Eden区已使用
   free     = 5121704 (4.884437561035156MB) //Eden区剩余容量
   71.26801434685203% used //Eden区使用比率
From Space: //其中一个Survivor区的内存分布
   capacity = 2097152 (2.0MB)
   used     = 1703936 (1.625MB)
   free     = 393216 (0.375MB)
   81.25% used
To Space: //另一个Survivor区的内存分布
   capacity = 2097152 (2.0MB)
   used     = 0 (0.0MB)
   free     = 2097152 (2.0MB)
   0.0% used
PS Old Generation
   capacity = 52428800 (50.0MB) //老年代容量
   used     = 28325712 (27.013504028320312MB) //老年代已使用
   free     = 24103088 (22.986495971679688MB) //老年代空闲
   54.027008056640625% used //老年代使用比率

15884 interned Strings occupying 2075304 bytes.
```
示例四：
```shell
//输出存活对象统计信息
jmap -histo:live 11666 | more
```
输出：
```shell
num     #instances         #bytes  class name
----------------------------------------------
1:         46608        1111232  java.lang.String
2:          6919         734516  java.lang.Class
3:          4787         536164  java.net.SocksSocketImpl
4:         15935         497100  java.util.concurrent.ConcurrentHashMap$Node
5:         28561         436016  java.lang.Object
```
## 6. jhat
>jhat是用来分析jmap生成dump文件的命令，jhat内置了应用服务器，可以通过网页查看dump文件分析结果，jhat一般是用在离线分析上。

命令格式：
```
jhat [option] [dumpfile]
```
option参数解释：

- -stack false: 关闭对象分配调用堆栈的跟踪
- -refs false: 关闭对象引用的跟踪
- -port <port>: HTTP服务器端口，默认是7000
- -debug <int>: debug级别
  - 0: 无debug输出
  - 1: Debug hprof file parsing
  - 2: Debug hprof file parsing, no server
- -version 分析报告版本
常用示例：

```shell
jhat dump.hprof
```
## 7. java
java命令  【这里是jvm参数，java命令用的】 jar包或类名 【这里是命令行参数，要给类的main方法的】
```shell
java [-options] -jar jarfile [args...]
```
一句话理解一下，java用什么参数启动什么类，最后给这个类传什么参数
