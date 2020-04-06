package com.dinesh.concurrent;

import java.util.concurrent.*;
import java.util.function.Supplier;

/**
 * Completeable future DOES NOT work with Callable
 */
public class CompleteableFutureExample
{

    public static void main(String[] args)
            throws ExecutionException, InterruptedException
    {

        /*System.out.println("Non Blocking example with Supplier: Will not print the line 'Woke up after...'");
        nonBlockingExampleWithSupplier();*/

        System.out.println("\nNon Blocking example with Runnable: Will not print the line 'Woke up after...'");

        //nonBlockingExampleWithRunnable();
        for(int i=0; i< 100000;i++)
        {
            nonBlockingExampleWithRunnable();
            //Uncomment the below and comment the above line to see how daemon running threads
            //nonBlockingExampleWithRunnableWithdaemonThreads();
        }
    }

    /**
     * Wthout calling the executorService.shutdown(); the daemon threads will exhaust the memory.
     * Checkout the task Manager --> Performance and notice the memory.
     */
    private static void nonBlockingExampleWithRunnableWithdaemonThreads()
    {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Runnable runnableTask = () -> waitforMe(1000, "nonBlockingExampleWithRunnable");
        CompletableFuture future =CompletableFuture.runAsync(runnableTask, executorService); //passing executorService will not run it in ForkJoin pool
        System.out.println("Done nonBlockingExampleWithRunnable");
        //executorService.shutdown();

    }

    /**
     * Calling the executorService.shutdown(); the daemon threads will "NOT" exhaust the memory.
     * Checkout the task Manager --> Performance and notice the memory.
     */
    private static void nonBlockingExampleWithRunnable()
    {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Runnable runnableTask = () -> waitforMe(1000, "nonBlockingExampleWithRunnable");
        CompletableFuture future =CompletableFuture.runAsync(runnableTask, executorService); //passing executorService will not run it in ForkJoin pool
        System.out.println("Done nonBlockingExampleWithRunnable");
        executorService.shutdown(); //Without this the executor thread will keep running and soon exhaust memory
        //Thread.sleep(1000) // If  I add this line then future will complete and line 'Woke up after ...'" would be printed
    }

    static String waitforMe(int seconds,
                            String calledFrom)
    {
        String name = "Dinesh";
        try
        {
            System.out.println(calledFrom + ": Going to sleep for " + seconds);
           // Thread.sleep(seconds);
            for(int i=0;i<100000;i++){

            }
            System.out.println(calledFrom+ ": Woke up after " + seconds);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return name;
    }
}
