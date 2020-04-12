package com.dinesh.vo.com.dinesh.lambda;

import java.util.ArrayList;

import java.util.Comparator;
import java.util.List;

import com.dinesh.vo.Apple;

public class MainLambda
{
    static List<Apple> appleList = new ArrayList<Apple>();

    public static void main(String[] args)
    {
        addApple(new Apple(100, Apple.Color.GREEN));
        addApple(new Apple(90, Apple.Color.RED));
        addApple(new Apple(130, Apple.Color.GREEN));
        addApple(new Apple(155, Apple.Color.RED));
        addApple(new Apple(160, Apple.Color.GREEN));
        appleList.sort(Comparator.comparing(Apple::getWeight));
        appleList.stream().forEach(apple -> System.out.println(apple.toString()));

        Comparator<Apple> byWeight = Comparator.comparing(Apple::getWeight);
       // Comparator<Apple> byColor = (Apple a1, Apple a2) -> a1.getColor().compareTo(a2.getColor());

    }

    public static void addApple(Apple apple){
        appleList.add(apple);
    }
}
