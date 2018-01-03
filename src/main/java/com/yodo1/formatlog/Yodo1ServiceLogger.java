package com.yodo1.formatlog;

import net.sf.json.JSONObject;
import org.slf4j.Logger;

/**
 * Created by nijing on 18/1/3.
 */
public class Yodo1ServiceLogger {
    private static final String PARAM_CONNECTOR = " ";
    private static final String PARAM_SEP_LEFT = "[";
    private static final String PARAM_SEP_RIGHT = "]";
    public enum ResultDefine
    {
        SUCCESS,
        FAILURE;
        public String GetResultStr()
        {
            return this.toString();
        }
    }
    private Logger innerLogger;
    public void SetLogger(Logger logger)
    {
        this.innerLogger = logger;
    }
    private JSONObject paramCollection = new JSONObject();
    public void AddParam(String key,Object value)
    {
        this.paramCollection.put(key,value);
    }

    public void ServiceFunctionLog(ResultDefine functionResult,String customStr)
    {
        LOG(MakeLogStr(functionResult.GetResultStr(),customStr));
    }
    public void ServiceFunctionLog(String customStr)
    {
        LOG(MakeLogStr("",customStr));
    }
    private String MakeLogStr(String funcResult,String customStr)
    {
        StringBuilder builder = new StringBuilder();
        builder.append(PARAM_SEP_LEFT).append(paramCollection.toString()).append(PARAM_SEP_RIGHT).append(PARAM_CONNECTOR);
        builder.append(PARAM_SEP_LEFT).append(customStr).append(PARAM_SEP_RIGHT).append(PARAM_CONNECTOR);
        builder.append(PARAM_SEP_LEFT).append(funcResult.isEmpty()?"":funcResult).append(PARAM_SEP_RIGHT).append(PARAM_CONNECTOR);
        return builder.toString();
    }

    private void LOG(String logStr)
    {
        if(null != innerLogger)
        {
            innerLogger.info(logStr);
        }
    }
}
