package com.dinesh.concurrent;

import java.util.concurrent.*;

public class CallableExample
{

    public static void main(String[] args)
            throws Exception
    {
        System.out.println("\nCallable  Blocking Example...");
        callableBlockingExample();

        System.out.println("\nCallable Non Blocking Example...");
        callableNonBlockingExample();


    }

    private static void callableBlockingExample()
            throws ExecutionException, InterruptedException
    {
        //A callable method must have a return type
        Callable callable = () -> waitforMe(500);
        ExecutorService service = Executors.newSingleThreadExecutor();
        Future future = service.submit(callable);
        future.get();
        System.out.println("Done");
    }

    private static void callableNonBlockingExample()
    {
        //A callable method must have a return type
        Callable callable = () -> waitforMe(500);
        ExecutorService service = Executors.newSingleThreadExecutor();
        service.submit(callable);
        System.out.println("Done");
    }

    static String waitforMe(int seconds)
    {
        String name = "Dinesh";
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
        return name;
    }
}