package com.yodo1.formatlog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by YanFeng on 2017/6/6.
 */
public final class Yodo1LoggerFactory {

    public static Yodo1RequestLogger getRequestLogger(Class clazz)
    {
        Yodo1RequestLogger result = new Yodo1RequestLogger();
        Logger innerLogger = LoggerFactory.getLogger(clazz.getName());
        result.SetLogger(innerLogger);
        return result;
    }
    public static Yodo1ServiceLogger getServiceLogger(Class clazz)
    {
        Yodo1ServiceLogger result = new Yodo1ServiceLogger();
        Logger innerLogger = LoggerFactory.getLogger(clazz.getName());
        result.SetLogger(innerLogger);
        return result;
    }
}
