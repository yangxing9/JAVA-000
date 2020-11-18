package agent;

/**
 * @author yangxing
 * @version 1.0
 * @date 2020/11/18 0018 17:19
 */
public class AgentTest {

    /**
     * 启动参数：-javaagent:E:\JAVA-000\Week_05\target\my-agent.jar=yangxing
     * 结果：    开始 agent.
     *          args:yangxing
     *
     *          这里是测试类的main函数
     */
    public static void main(String[] args) {
        System.out.println("这里是测试类的main函数");

        AgentTest test = new AgentTest();
        test.fun1();
        test.fun2();
    }


    private void fun1() {
        System.out.println("this is fun 1.");
    }

    private void fun2() {
        System.out.println("this is fun 2.");
    }
}
