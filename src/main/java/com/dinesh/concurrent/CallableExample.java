package com.dinesh.concurrent;

import java.util.concurrent.*;

/**
 * The problem with usinn Runnable to create task is that it cannot expect a response back from task.
 */
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
        Future<String> future = service.submit(callable);
        String result = future.get();
        System.out.println(result);
        System.out.println("Done");
    }

    private static void callableNonBlockingExample()
    {
        //A callable method must have a return type
        Callable callable = () -> waitforMe(500);
        ExecutorService service = Executors.newSingleThreadExecutor();
        Future<String> future =service.submit(callable);

        System.out.println("Done");
    }

    private static void sleep(int i)
    {
        try
        {
            Thread.sleep(i);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
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