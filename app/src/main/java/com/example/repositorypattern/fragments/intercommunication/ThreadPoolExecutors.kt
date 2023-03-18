package com.example.repositorypattern.fragments.intercommunication

import java.util.concurrent.*

fun main(args: Array<String>) {
    executeTask()
}

fun executeTask() {
    //firstExecutor()
    //secondExecutor()
    thirdExecutor()
}

fun thirdExecutor() {
    val executorService: ExecutorService = Executors.newFixedThreadPool(10)
    val cachedExecutors: ExecutorService = Executors.newCachedThreadPool()

    val lambdaFunctionSquare = { number: Int -> number * number }

    //below loop takes time. This is like a long-running network request
    val lambdaFunctionWhile: (Long) -> Long = { number: Long ->
        var myVal = 0L; for (i in 0..number + 9000000000) {
        myVal += i
    }; myVal
    }

    /*
    Limitations of Future interface
        Futures cannot be completed manually.
        There is no way to execute multiple futures (or results) in parallel and then combine the results together.
        There are no exception handling constructs for Future.
        Future doesn’t have the mechanism to create multiple stages of processing that can be chained together. It needs to be done manually.
        Future doesn’t have the mechanism to notify you of the completion of an API.
     */

    val completableFuture: CompletableFuture<Long>? = CompletableFuture.supplyAsync { 5 }
        .thenApply { intVal -> 2 * intVal }
        .thenApplyAsync(lambdaFunctionSquare, executorService /* in different thread pool*/)
        .thenApplyAsync(
            { number: Int -> number + number },
            cachedExecutors /* in different thread pool*/
        )
        .thenApply { intVal -> intVal.toLong() }
        .thenApplyAsync(lambdaFunctionWhile, executorService)

    println(completableFuture?.get()) //Waits if necessary for this future to complete, and then returns its result.
    println("After CompletableFuture get done!")
}

fun secondExecutor() {
    //val workQueue = SynchronousQueue<Runnable>()
    //val workQueue = ArrayBlockingQueue<Runnable>(2)
    val workQueue = LinkedBlockingQueue<Runnable>(/*unbounded*/)
    //val
    val customThreadFactory = CustomThreadFactory()
    val myRunnable = Runnable {
        val thread = Thread.currentThread()
        println(
            thread.threadGroup?.let {
                String.format(
                    "in runnable, thread id: %s, name: %s, group name %s",
                    thread.id, thread.name, it.name
                )
            }
        )
        try {
            Thread.sleep(1000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

    val threadPoolExecutor = ThreadPoolExecutor(5, 30, 1000, TimeUnit.SECONDS, workQueue)

    val rejectedExecutionHandler =
        RejectedExecutionHandler { runnable, threadPoolExecutor1 ->
            threadPoolExecutor1.execute(
                runnable
            )
        }

    /*
    Method that may be invoked by a ThreadPoolExecutor when execute cannot accept a task.
    This may occur when no more threads or queue slots are available because their bounds would be
    exceeded, or upon shutdown of the Executor.
     */
    rejectedExecutionHandler.rejectedExecution(myRunnable, threadPoolExecutor)

    val executorService: ExecutorService = ThreadPoolExecutor(
        3 /* corePoolSize */,
        5 /* max. pool size */,
        2000 /* keep alive time */,
        TimeUnit.SECONDS,
        workQueue,
        customThreadFactory,
        rejectedExecutionHandler
    )

    for (i in 0..400) {
        executorService.submit(myRunnable) //4 (i.e., coreSize+1) parallel execution
    }
}

fun firstExecutor() {
    //val executorService = Executors.newSingleThreadExecutor()
    //val executorService = Executors.newFixedThreadPool(10)
    val executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors())

    executorService.submit { MyRunnable().run() } //return null

    val future1: Future<Int> = executorService.submit(TaskCallable())
    val futureVal1 = future1.get()
    println(futureVal1)

    executorService.shutdown()
}

class TaskCallable : Callable<Int> {
    override fun call(): Int {
        Thread.sleep(1000)
        return 100
    }

    fun method1(): Int {
        return 100
    }

    fun method2(intPass: Int): Int {
        return intPass
    }

    fun method3(intPass: Int): Int {
        return intPass
    }

    fun method4(intPass: Int): Int {
        return intPass
    }
}

class MyRunnable : Runnable {
    override fun run() {
        println("just run, no return!")
    }
}

internal class CustomThreadFactory : ThreadFactory {
    override fun newThread(runnable: Runnable): Thread {
        return Thread(runnable)
    }
}




