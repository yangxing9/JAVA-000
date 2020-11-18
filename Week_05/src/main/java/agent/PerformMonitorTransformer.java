package agent;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtBehavior;
import javassist.CtClass;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.HashSet;
import java.util.Set;

/**
 * @author yangxing
 * @version 1.0
 * @date 2020/11/18 0018 17:38
 */
public class PerformMonitorTransformer implements ClassFileTransformer {

    private static final Set<String> classNameSet = new HashSet<>();

    static {
        classNameSet.add("agent.AgentTest");
    }

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        try {
            String currentClassName = className.replaceAll("/", ".");
            // 仅仅提升Set中含有的类
            if (!classNameSet.contains(currentClassName)) {
                return null;
            }
            System.out.println("增强的类transform: [" + currentClassName + "]");

            CtClass ctClass = ClassPool.getDefault().get(currentClassName);
            CtBehavior[] methods = ctClass.getDeclaredBehaviors();
            for (CtBehavior method : methods) {
                enhanceMethod(method);
            }
            return ctClass.toBytecode();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void enhanceMethod(CtBehavior method) throws Exception {
        if (method.isEmpty()) {
            return;
        }
        String methodName = method.getName();
        // 不提升main方法
        if (methodName.equalsIgnoreCase("main")) {
            return;
        }

        final StringBuilder source = new StringBuilder();
        source.append("{")
                // 前置增强: 打入时间戳
                .append("long start = System.nanoTime();\n")
                // 保留原有的代码处理逻辑
                .append("$_ = $proceed($$);\n")
                .append("System.out.print(\"增强内容：method:[" + methodName + "]\");").append("\n")
                // 后置增强
                .append("System.out.println(\" 耗时:[\" +(System.nanoTime() -start)+ \"ns]\");")
                .append("}");

        ExprEditor editor = new ExprEditor() {
            @Override
            public void edit(MethodCall methodCall) throws CannotCompileException {
                methodCall.replace(source.toString());
            }
        };
        method.instrument(editor);
    }
}
