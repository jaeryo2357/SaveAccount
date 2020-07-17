package com.mut_jaeryo.saveaccount.util

import java.util.concurrent.Executor
import java.util.concurrent.Executors

//Executor는 java에서 사용하는 Thread를 용도에 맞게 분리??

class DiskIOThreadExecutor : Executor {

    private val diskIO = Executors.newSingleThreadExecutor()

    override fun execute(command: Runnable) { diskIO.execute(command) }
}