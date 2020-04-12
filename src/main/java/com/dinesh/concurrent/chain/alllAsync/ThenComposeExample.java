package com.dinesh.concurrent.chain.alllAsync;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import com.dinesh.concurrent.chain.vo.User;


public class ThenComposeExample
{
//PIPELINE: Given a list of Ids --> Get Users for those Ids --> Print those user ids
    // fetchUserId is currently is a synchronous operation. We need to make it Aysnchronous.
    public static void main(String[] args)
    {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        //task 1: Supply Ids
        Supplier <List<Long>> supplyIds = () ->{
            sleep(200);
            return Arrays.asList(1L,2L,3L);
        };
        /*//task 2: fetch List of Users
        Function<List<Long>, List<User>> fetchUserIds = ids ->{
            sleep(300);
            return ids.stream().map(User::new).collect(Collectors.toList());
        };*/

        //task 2: fetch List of Users -- AYNCHRONOUS version
        Function<List<Long>, CompletableFuture< List<User>>> fetchUserIds = ids ->{
            sleep(300);
            Supplier<List<User>> userSupplier = () ->{
                System.out.println("Thread: " +  Thread.currentThread().getName());
                return ids.stream().map(User::new).collect(Collectors.toList());
            };
            return CompletableFuture.supplyAsync(userSupplier);
        };

        //task 3: Given the list of users, print thier Ids
        Consumer<List<User>> displayUsers = users -> {
            System.out.println("Thread: " + Thread.currentThread().getName());
            users.forEach(System.out::println);
        };


        // Create CompletableFuture pipeline
        CompletableFuture.supplyAsync(supplyIds)
                         .thenCompose(fetchUserIds ) //CHANGED THEN COMPOSE
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
