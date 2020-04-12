package com.dinesh.vo.com.dinesh.lambda;

import java.util.function.Function;

public class MyFunctionalInterface
{
    //In Functional interface First Param(Integer) refers to input param and second param refers to result(String)
    static Function<Integer, String>  doubleX = (x)->{
        String result=null;
        result = "X: " +x + "| 2 Times X: " + 2*x;
        return result;
    };

    public static void main(String [] args){
        int x = 2;
        doubleAndprint(x);
    }

    private static void doubleAndprint(int x)
    {
        System.out.println(doubleX.apply(x));
    }
}
