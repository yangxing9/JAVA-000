package com.lambda;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * @author yangxing
 * @version 1.0
 * @date 2020/11/24 0024 14:45
 */
public class FunctionTest {

    public static void main(String[] args) {
        FunctionTest test = new FunctionTest();
        // 传递行为，而不是传递值
        System.out.println(test.comput(1, (a) -> 2 * a));


        System.out.println(test.convert(1, (a) -> String.valueOf(a)));

        // 先执行compose方法内的函数的规则，再执行外层的
        System.out.println(test.compute(2, (a) -> a * 3,(a) -> a * a));

        // 按顺序执行，func1，function2
        System.out.println(test.compute2(2, (a) -> a * 3,(a) -> a * a));

        //
        System.out.println(test.compute3(2,3, (a,b) -> String.valueOf(a + b)));


    }

    public int comput(int a, Function<Integer,Integer> function){
        //apply ,传递的是行为
        int result = function.apply(a);
        return result;
    }

    public String convert(int a, Function<Integer,String> function){
        return function.apply(a);
    }

    public int compute(int a, Function<Integer, Integer> function1, Function<Integer, Integer> function2) {
        return function2.compose(function1).apply(a);
    }

    public int compute2(int a, Function<Integer, Integer> function1, Function<Integer, Integer> function2) {
        return function1.andThen(function2).apply(a);
    }

    //BiFunction类的使用。 有两个输入参数
    public String compute3(int a, int b, BiFunction<Integer, Integer, String> biFunction) {
        return biFunction.apply(a, b);
    }

}
