package com.guava;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Joiner;
import com.google.common.collect.*;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.lambda.Person;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author yangxing
 * @version 1.0
 * @date 2020/11/25 0025 10:25
 */
public class GuavaTest {

    static EventBus bus = new EventBus();

    static {
        bus.register(new GuavaTest());
    }

    public static void main(String[] args) {

        testStr();

        testList();

        testMap();

        testBiMap();

        testEventBus();
    }

    private static void testEventBus() {
        Person p = new Person("yangxing",22);
        System.out.println("person:" + p.getName() + " send event");
        bus.post(new PEvent(p));
    }

    private static void testBiMap() {
        List<String> list = Lists.newArrayList("1","2","3","4","5","6");
        BiMap<String, Integer> words = HashBiMap.create();
        words.put("First", 1);
        words.put("Second", 2);
        words.put("Third", 3);

        System.out.println(words.get("Second").intValue());
        System.out.println(words.inverse().get(3));

        Map<String,String> map1 = Maps.toMap(list.listIterator(), a -> a + "-value");
        print(map1);
    }

    private static void testMap() {
        List<Integer> list = Lists.newArrayList(1,2,3,4,5,6);
        Map map = list.stream().collect(Collectors.toMap(a -> a, a -> (a+1)));
        print(map);
        Multimap<Integer,Integer> bMultimap = ArrayListMultimap.create();
        list.forEach(
                a -> bMultimap.put(a,a + 1)
        );
        print(bMultimap);
    }

    private static void testList() {
        List<Integer> list = Lists.newArrayList(1,2,3,4,5,6);
        List<List<Integer>> list2 = Lists.partition(list,4);
        print(list2);
    }

    private static void testStr() {
        List<Integer> list = Stream.iterate(1, x -> x += 1).limit(10).collect(Collectors.toList());

        String str1 = Joiner.on(".").join(list);
        String str2 = Joiner.on(".").join("x","y","z");
        String str3 = Joiner.on(".").join(new Object[]{7,8,9});
        System.out.println(str1);
        System.out.println(str2);
        System.out.println(str3);
    }

    private static void print(Object obj) {
        System.out.println(JSON.toJSONString(obj));
    }

    @Data
    @AllArgsConstructor
    static class PEvent{
        private Person person;
    }

    @Subscribe
    public void enentHandle(PEvent event){
        System.out.println("person:" + event.getPerson().getName() + " get message");
    }

}
