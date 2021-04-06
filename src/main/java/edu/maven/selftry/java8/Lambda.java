package edu.maven.selftry.java8;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.function.Function;
import java.util.function.Predicate;

public class Lambda {
    @Test
    public void testSimple(){
        Arrays.asList("a", "b", "d").sort(String::compareTo);
        Arrays.asList("a", "b", "d").sort((e1, e2) -> {
            int result = e1.compareTo(e2);
            return result;});
    }

    @Test
    public void tryConsumer(){
        System.out.println("Consumer: ");
        Consumer<String> consumer1 = s -> System.out.print("先来");
        Consumer<String> consumer2 = s -> System.out.println("后到");
        consumer1.andThen(consumer2).accept("s"); // “先来后到“
    }

    @Test
    public void trySupplier(){
        System.out.println("Supplier: ");
        Supplier<String> supplier = () -> "我充满活力";
        System.out.println(supplier.get());
    }

    @Test
    public void tryFunction(){
        System.out.println("Function: ");
        Function<Integer, Integer> function1 = e -> e * 2;
        Function<Integer, Integer> function2 = e -> e * e;

        Integer apply2 = function1.compose(function2).apply(3);
        // 3 * 3 * 2 = 18
        System.out.println("compose：" + apply2);

        //（3 * 2）^ 2 = 36
        Integer apply3 = function1.andThen(function2).apply(3);
        System.out.println("andThen: " + apply3);

        Function<Integer, Integer> identity = Function.identity();
        Integer apply = identity.apply(3);
        System.out.println("identity: " + apply);
    }

    @Test
    public void tryPredicate(){
        System.out.println("Predicate: ");
        int threshold = 3;
        Predicate<String> predicate1 = s -> s.length() < threshold;
        Predicate<String> predicate2 = Objects::nonNull;

        //threshold = 5;
        String str = "我比3大";
        boolean test1 = predicate1.and(predicate2).test(str);
        System.out.println("and: " + test1); //false
        boolean test2 = predicate1.or(predicate2).test(str);
        System.out.println("or: " + test2); //true
        boolean test3 = predicate1.negate().test(str);
        System.out.println("negate: " + test3); //true
        boolean test4 = Predicate.isEqual("不一样").test(str);
        System.out.println("isEqual: " + test4); //false
    }

    @FunctionalInterface
    public interface MyOwn{
        int squared(int value);
        default void defaultMethod() {
            System.out.println("默认是啥都没有");
        }
    }

    public static void  MyOwnPrint(int value, MyOwn mine){
        System.out.println(mine.squared(value));
    }

    @Test
    public void tryMyOwn(){
        System.out.println("My own function: ");
        MyOwnPrint(10, e -> e * e);
        MyOwn mine = s -> s;
        mine.defaultMethod();
    }
}
