package com.dinesh.concurrent;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Thread.run is old way of creating threads.
 * New way is to use Executor Service.
 *
 */
public class ExecutorServiceExample
{

    public static void main(String[] args)
            throws ExecutionException, InterruptedException
    {
        System.out.println("Blocking Example");
        blockingExample();

        System.out.println("\nNon Blocking Example");
        nonBlockingExample();


    }

    private static void nonBlockingExample()
    {
        //Define a executor service
        ExecutorService service = Executors.newSingleThreadExecutor();
        //define the task
        Runnable task = () -> wait(5);
        //Submit the task and get a future object back
        Future future = service.submit(task);
        // future.get(); //This line will block printing the "Done"
        System.out.println("Done");
    }

    private static void blockingExample()
            throws ExecutionException, InterruptedException
    {
        //Define a executor service
        ExecutorService service = Executors.newSingleThreadExecutor();
        //define the task
        Runnable task = () -> wait(5);
        //Submit the task and get a future object back
        Future future = service.submit(task);
        //To execute Asyncronously you can call future.get().
        future.get(); //This line will block printing the "Done"
        System.out.println("Done");
    }

    static void wait(int seconds)
    {
        try
        {
            System.out.println("Going to sleep for " + seconds);
            Thread.sleep(seconds);
            System.out.println("Woke up after " + seconds);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}