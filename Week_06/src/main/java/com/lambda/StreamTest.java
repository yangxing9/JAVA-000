package com.lambda;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author yangxing
 * @version 1.0
 * @date 2020/11/24 0024 17:09
 */
public class StreamTest {

    public static void main(String[] args) {
        Arrays.stream(new int[]{1,2,3}).forEach(System.out::println);
        Stream.of(1,2,3).forEach(System.out::println);
        Stream.of(new int[]{1,2,3}).forEach(System.out::println);
        Arrays.asList(1,2,3).stream().forEach(System.out::println);
        Stream.iterate(1, x -> ++x).limit(10).collect(Collectors.toList()).forEach(System.out::println);
        Stream.generate(() -> "a").limit(10).collect(Collectors.toList()).forEach(System.out::println);
        Optional<String> res1 = Stream.generate(() -> "a").limit(10).findAny();
        res1.ifPresent(s -> System.out.println(s));
        boolean res2 = Stream.generate(() -> "a").limit(10).anyMatch(a -> a.equals("a"));
        System.out.println(res2);
        Optional<Integer> res3 = Stream.iterate(1, x -> ++x).limit(10).min(Comparator.comparingInt(a -> a));
        res3.ifPresent(System.out::println);

        IntStream.of(new int[]{1, 2, 3}).forEach(System.out::println);
        IntStream.of(1, 2, 3).forEach(System.out::println);
        IntStream.range(1, 3).forEach(System.out::println);
        IntStream.rangeClosed(1, 3).forEach(System.out::println);

        int[] arr = new int[]{0,1};
        Optional<Integer> res4 = Stream.iterate(arr, x -> {
            int tmp = arr[0];
            arr[0] = arr[1];
            arr[1] = tmp + arr[1];
            return arr;
        }).limit(10).map(x -> x[0]).max(Comparator.comparingInt(a -> a));

        res4.ifPresent(System.out::println);

        Stream.iterate(new int[]{0,1}, x -> new int[]{x[1],x[0] + x[1]}).limit(10).map(x -> x[0]).forEach(System.out::println);
        Stream.iterate(new int[]{0,1}, x -> new int[]{x[1],x[0] + x[1]}).limit(10).map(x -> x[0]).max(Comparator.comparingInt(a -> a)).get();

        Person person = new Person("ya",10);
        Optional<Person> result = Optional.ofNullable(person).filter(u -> u.getName() != null && u.getName().equals("ya"));
        System.out.println(result.get());
    }

}
