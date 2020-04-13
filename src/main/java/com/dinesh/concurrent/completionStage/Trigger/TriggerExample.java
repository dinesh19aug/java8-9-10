package com.dinesh.concurrent.completionStage.Trigger;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import com.dinesh.concurrent.completionStage.vo.User;


public class TriggerExample
{
    //PIPELINE: Given a list of Ids --> Get Users for those Ids --> Print those user ids
    public static void main(String[] args)
    {
        //task 1: Supply Ids
        Supplier<List<Long>> supplyIds = () ->{
            sleep(200);
            return Arrays.asList(1L, 2L, 3L);
        };
        //task 2: fetch List of Users
        Function<List<Long>, List<User>> fetchUserIds = ids ->{
            sleep(300);
            return ids.stream().map(User::new).collect(Collectors.toList());
        };

        //task 3: Given the list of users, print thier Ids
        Consumer<List<User>> displayUsers = users -> {
            System.out.println("In Thread: " +  Thread.currentThread().getName());
            users.forEach(System.out::println);
        };

        //Void CompletableFuture start
        CompletableFuture<Void> start = new CompletableFuture<>();
        // Create CompletableFuture pipeline
        CompletableFuture<List<Long>> supply = start.thenApply(nil -> supplyIds.get());
        CompletableFuture<List<User>> fetch = supply.thenApply(fetchUserIds);
        CompletableFuture<Void> display = fetch.thenAccept( displayUsers );
        start.complete(null);//Without this line, start will not be triggered. This runs in main Thread
        
        sleep(1_600);
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
