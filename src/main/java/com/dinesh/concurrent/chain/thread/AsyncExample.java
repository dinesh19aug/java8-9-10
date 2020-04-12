package com.dinesh.concurrent.chain.thread;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import com.dinesh.concurrent.chain.simple.User;


public class AsyncExample
{
//PIPELINE: Given a list of Ids --> Get Users for those Ids --> Print those user ids
    // Running tasks in differemt threads
    public static void main(String[] args)
    {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        //task 1: Supply Ids
        Supplier <List<Long>> supplyIds = () ->{
            sleep(200);
            return Arrays.asList(1L,2L,3L);
        };
        //task 2: fetch List of Users
        Function<List<Long>, List<User>> fetchUserIds = ids ->{
            sleep(300);
            return ids.stream().map(User::new).collect(Collectors.toList());
        };

        //task 3: Given the list of users, print thier Ids
        Consumer<List<User>> displayUsers = users -> {
            System.out.println("Thread: " + Thread.currentThread().getName());
            users.forEach(System.out::println);
        };


        // Create CompletableFuture pipeline
        CompletableFuture.supplyAsync(supplyIds)
                         .thenApply(fetchUserIds )
                         .thenAcceptAsync( displayUsers,executorService );
        sleep(1_600);
        executorService.shutdown();
    }

    private static void sleep(int time){
        try
        {
            Thread.sleep(time);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}
