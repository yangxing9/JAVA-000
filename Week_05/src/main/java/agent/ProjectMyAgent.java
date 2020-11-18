package agent;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;

/**
 * @author yangxing
 * @version 1.0
 * @date 2020/11/18 0018 17:37
 */
public class ProjectMyAgent {

    public static void premain(String agentArgs, Instrumentation inst) {
        System.out.println("项目测试 monitor 的 agent.");
        // 添加 Transformer
        ClassFileTransformer transformer = new PerformMonitorTransformer();
        inst.addTransformer(transformer);
    }

}
