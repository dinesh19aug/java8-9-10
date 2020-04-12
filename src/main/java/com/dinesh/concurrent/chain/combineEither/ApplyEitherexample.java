package com.dinesh.concurrent.chain.combineEither;

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

public class ApplyEitherexample
{


        //PIPELINE: Given a list of Ids --> Get Users for those Ids AND Get emails for those ids --> Print those user ids
        // IDS ----- FETCH USERS 1---
        //      |                    | --- PRINT Ether User1 or User 2 which ever finishes earlier
        //      ---- FETCH USERS 2 --
        public static void main(String[] args)
        {
            ExecutorService executorService = Executors.newSingleThreadExecutor();
            //task 1: Supply Ids
            Supplier<List<Long>> supplyIds = () -> {
                sleep(200);
                return Arrays.asList(1L, 2L, 3L);
            };

            //task 2: fetch List of Users -- AYNCHRONOUS version
            Function<List<Long>, CompletableFuture<List<User>>> fetchUsers1= ids -> {
                sleep(250);
                Supplier<List<User>> userSupplier = () -> {
                    System.out.println("Thread: " + Thread.currentThread().getName());
                    return ids.stream().map(User::new).collect(Collectors.toList());
                };
                return CompletableFuture.supplyAsync(userSupplier);
            };

            Function<List<Long>, CompletableFuture<List<User>>> fetchUsers2 = ids -> {
                sleep(4000);
                Supplier<List<User>> userSupplier = () -> {
                    System.out.println("Thread: " + Thread.currentThread().getName());
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
            CompletableFuture<List<Long>> idsFuture = CompletableFuture.supplyAsync(supplyIds);

            CompletableFuture<List<User>> userFuture1 = idsFuture.thenComposeAsync(fetchUsers1);
            CompletableFuture<List<User>> userFuture2 = idsFuture.thenComposeAsync(fetchUsers2);

            userFuture1.thenRun(()-> System.out.println("Users 1 Done!"));
            userFuture2.thenRun(()-> System.out.println("Users 2 Done!"));

            userFuture2.acceptEither(userFuture1,displayUsers);

            sleep(6_000);
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
