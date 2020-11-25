package com.lambda;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

/**
 * @author yangxing
 * @version 1.0
 * @date 2020/11/24 0024 15:16
 */
public class PersonTest {

    public static void main(String[] args) {
        Person one = new Person("zhangsan",18);
        Person two = new Person("yangxing",20);
        Person three = new Person("linux",30);
        List<Person> list = Arrays.asList(one,two,three);
        PersonTest test = new PersonTest();

        List<Person> list1 = test.getByUserName("yangxing",list);
        list1.forEach(PersonTest::printName);
        list1.stream().map(a->a.getName()).forEach(System.out::println);
        System.out.println("========================");

        //测试第二种方法
        List<Person> personByAge = test.getByAge(20, list);
        personByAge.forEach(PersonTest::printName);

        //测试第三方法
        List<Person> peopleList = test.getPersonByArg(20, list,
                (age, personList) -> personList.stream().filter(person -> person.getAge() > age).collect(Collectors.toList()));
        peopleList.forEach(PersonTest::printName);
    }

    //使用lambda表达式定义一个 处理的方法
    //filter 方法，参数是一个Predicate 谓语
    //stream 提供了一个将流转换成集合的方法 collect(Collectors.toList())
    public List<Person> getByUserName(String userName,List<Person> personList){
        return personList.stream().filter(person -> person.getName().equals(userName)).collect(Collectors.toList());
    }

    //第二种方式,先直接使用lambda表达式将BiFunction定义好，然后直接将方法的两个参数传入到BiFunction.
    public List<Person> getByAge(int age,List<Person> personList){
        BiFunction<Integer, List<Person>, List<Person>> biFunction = (ageArg, persons) -> {
            return persons.stream().filter(person -> person.getAge() > age).collect(Collectors.toList());
        };
        return biFunction.apply(age,personList);
    }

    //第三种方式，动作也让他自己传递。 函数式接口的好处。
    public List<Person> getPersonByArg(int age, List<Person> personList, BiFunction<Integer, List<Person>, List<Person>> biFunction) {
        return biFunction.apply(age, personList);
    }

    // 函数调用
    public static void printName(Person person){
        System.out.println(person.getName());
        System.out.println(person.getAge());
    }

}
