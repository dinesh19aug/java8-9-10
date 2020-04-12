package com.dinesh.vo.com.dinesh.lambda;

import java.util.function.BiFunction;

public class BiFunctionInterface
{
    //biFunction<T,U,R> T and U are params and R is the result 
    static BiFunction<Integer, Integer, String> addTwoString =(x,y)->{
        String result;
        Integer sum = x+y;
        result = x + " + " + y + " = " + sum;
        return result;
    };
    public static void main(String[] args)
    {
        int a = 3;
        int b = 4;
        System.out.println(addTwoString.apply(a,b));
    }
}
