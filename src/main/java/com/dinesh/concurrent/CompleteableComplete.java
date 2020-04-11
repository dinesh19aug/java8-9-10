package com.dinesh.concurrent;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

public class CompleteableComplete
{

    public static void main(String[] args)
            throws InterruptedException
    {

        //exampleJoin();
        //exampleForceComplete();
        exampleAsyncComplete();


    }

    /**
     * So far we have see that join() method is a blocking call. This a unique pattern to run the code "Async" WHILE using
     * This
     */

    private static void exampleAsyncComplete()
    {
        CompletableFuture<Void> cf = new CompletableFuture<>();
        Supplier<String> supplierTask = () ->{
            String name = null;
            try
            {
                Thread.sleep(5000);
                name="Dinesh";
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            cf.complete(null); //NOTICE the use of cf object.
            return name;
        };
        CompletableFuture.supplyAsync( supplierTask);//NOTICE that we are calling the task in another future call.
        Void nil = cf.join();
        System.out.println( " We are done!");
    }

    /**
     * This method shows that when a task is taking long then we can force the task to complete.
     * If the task is completed before the complete() method is executed then default value will not be used.
     * If the task is not completed then the complete() method will force the task to complete and return the
     * default value
     */
    private static void exampleForceComplete()
    {
        CompletableFuture<String> completableFuture = new CompletableFuture();
        Supplier<String> task = getRunnableTask();
        completableFuture=CompletableFuture.supplyAsync( task);
        //If the below complete() is called after join then default value will not be printed as join() blocks the main thread
        completableFuture.complete("This is taking too long! This is default value of force complete()");
        String result = completableFuture.join();

        System.out.println(result + " We are done");
    }

    /**
     * In this example completableFuture.join(); blocks the main thread from finishing.
     * You will notice that there is no ExecutorService here, which means that Thread task will run in the
     * fork join pool. Without the join(), the task will never complete as Main thread dies.
     */
    private static void exampleJoin()
    {
        CompletableFuture<String> completableFuture = new CompletableFuture();
        Supplier<String> task = getRunnableTask();
        completableFuture=CompletableFuture.supplyAsync( task);

        String result = completableFuture.join();

        System.out.println(result + " We are done");
    }

    private static Supplier<String> getRunnableTask()
    {
        Supplier task   = ()->{
            System.out.println("Task Thread: " + Thread.currentThread());
            try
            {
                System.out.println("Going to sleep ....");
                Thread.sleep(5000);
                System.out.println("Waking up now ...");
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            return "Hello";
        };
        return task;
    }
}
