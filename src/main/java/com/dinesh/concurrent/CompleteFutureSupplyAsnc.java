package com.dinesh.concurrent;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

public class CompleteFutureSupplyAsnc
{

    public static void main(String[] args)
    {
        exampleForkJoinPool();

        exampleMainThreadPool();
    }

    private static void exampleForkJoinPool()
    {
        Supplier<String> supplier = () -> {
            System.out.println(
                    "exampleForkJoinPool: Running Supplier task in thread pool: " + Thread.currentThread().getName());
            return "Hello";
        };

        CompletableFuture future = CompletableFuture.supplyAsync(supplier);
        future.join(); //This is a blocking method call
    }

    private static void exampleMainThreadPool()
    {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Supplier<String> supplier = () -> {
            System.out.println(
                    "exampleMainThreadPool: Running Supplier task in thread pool: " + Thread.currentThread().getName());
            return "Hello";
        };

        CompletableFuture future = CompletableFuture.supplyAsync(supplier, executorService);
        //future.join(); //This is a blocking method call
        System.out.printf((String) future.join());
        executorService.shutdown();
    }
}
