package edu.maven.selftry.java8;

import org.junit.Test;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.*;
import java.util.IntSummaryStatistics;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class OtherFeatures {
    public static void main(String[] args){
    }

    @Test
    public void tryParameter() throws NoSuchMethodException {
        System.out.println("Parameter: ");
        Method method = OtherFeatures.class.getMethod("main", String[].class);
        for (Parameter parameter: method.getParameters()) {
            System.out.println(parameter.getName());
        }
    }

    @Test
    public void tryOptional(){
        System.out.println("Optional: ");
        Optional<String> fullName = Optional.ofNullable("嘉穗");
        //Optional<String> fullName = Optional.ofNullable(null);
        System.out.println("Full Name is set? " + fullName.isPresent());
        System.out.println("Full Name: " + fullName.orElseGet(() -> "[无名氏]"));
        System.out.println(fullName.map(s -> "Hey " + s + "!").orElse("你是哪个"));
    }

    @Test
    public void tryStream() {
        System.out.println("Stream: ");
        List<Integer> numbers = Arrays.asList(3, 2, 2, 3, 7, 3, 5);

        IntSummaryStatistics stats = numbers.stream().mapToInt((x) -> x).summaryStatistics();
        System.out.print("最大: " + stats.getMax());
        System.out.print("， 最小: " + stats.getMin());
        System.out.print("， 和： " + stats.getSum());
        System.out.println("， 平均: " + stats.getAverage());

        System.out.print("2, 2, 3, 3, 3的平方：");
        numbers.stream().filter(i -> i < 5).map(i -> i * i).sorted().forEach(System.out::print);
        System.out.println();
    }

    @Test
    public void tryBase64(){
        System.out.println("Base64: ");

        final String text = "明天周五啦！";

        final String encoded = Base64
                .getEncoder()
                .encodeToString(text.getBytes( StandardCharsets.UTF_8 ));
        System.out.println(encoded);

        final String decoded = new String(
                Base64.getDecoder().decode(encoded),
                StandardCharsets.UTF_8);
        System.out.println(decoded);
    }
}
