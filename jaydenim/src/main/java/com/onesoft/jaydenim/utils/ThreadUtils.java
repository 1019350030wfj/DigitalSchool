package com.onesoft.jaydenim.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Jayden on 2016/9/28.
 */

public class ThreadUtils {

    public static ExecutorService singleService = Executors.newSingleThreadExecutor();
    public static ExecutorService singleStartIM = Executors.newSingleThreadExecutor();
}
