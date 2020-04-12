package com.dinesh.concurrent.chain.combineAll;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import com.dinesh.concurrent.chain.vo.Email;
import com.dinesh.concurrent.chain.vo.User;

public class AyncMultiBranchExample
{

    //PIPELINE: Given a list of Ids --> Get Users for those Ids AND Get emails for those ids --> Print those user ids
    // IDS ----- FETCH USERS ---
    //      |                   | --- PRINT
    //      ---- FETCH EMAILS --
    public static void main(String[] args)
    {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        //task 1: Supply Ids
        Supplier<List<Long>> supplyIds = () -> {
            sleep(200);
            return Arrays.asList(1L, 2L, 3L);
        };

        //task 2: fetch List of Users -- AYNCHRONOUS version
        Function<List<Long>, CompletableFuture<List<User>>> fetchUserIds = ids -> {
            sleep(250);
            Supplier<List<User>> userSupplier = () -> {
                System.out.println("Thread: " + Thread.currentThread().getName());
                return ids.stream().map(User::new).collect(Collectors.toList());
            };
            return CompletableFuture.supplyAsync(userSupplier);
        };

        Function<List<Long>, CompletableFuture<List<Email>>> fetchEmails = ids -> {
            sleep(600);
            Supplier<List<Email>> emailSupplier = () -> {
                System.out.println("Thread: " + Thread.currentThread().getName());
                return ids.stream().map(Email::new).collect(Collectors.toList());
            };
            return CompletableFuture.supplyAsync(emailSupplier);
        };




        // Create CompletableFuture pipeline
        CompletableFuture<List<Long>> idsFuture = CompletableFuture.supplyAsync(supplyIds);
        CompletableFuture<List<User>> userFuture = idsFuture.thenCompose(fetchUserIds);
        CompletableFuture<List<Email>> emailFuture = idsFuture.thenCompose(fetchEmails);
        userFuture.thenAcceptBoth
                           (emailFuture,
                            (users,emails)->{
                                System.out.println(users.size() + " - " + emails.size()) ;
                            });
        sleep(1_600);
        executorService.shutdown();
    }

    private static void sleep(int time)
    {
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