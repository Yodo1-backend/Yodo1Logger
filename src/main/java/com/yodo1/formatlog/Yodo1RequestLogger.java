package com.yodo1.formatlog;

import net.sf.json.JSONObject;
import org.slf4j.Logger;

/**
 * Created by YanFeng on 2017/6/6.
 */
public class Yodo1RequestLogger {

    private enum FuncStage
    {
        Start,
        Proccessing,
        End;
        public String GetStageStr()
        {
            return this.toString();
        }
    }
    public enum ResultDefine
    {
        SUCCESS,
        FAILURE,
        SUCCESSWITHERROR;
        public String GetResultStr()
        {
            return this.toString();
        }
    }
    public enum LogLevel {
        DEBUG,
        INFO,
        WARNING,
        ERROR;
        public String GetLevelStr()
        {
            return this.toString();
        }
    }
    private static final String PARAM_CONNECTOR = " ";
    private static final String PARAM_SEP_LEFT = "[";
    private static final String PARAM_SEP_RIGHT = "]";
    private static final String FUNC_STAGE_START = "Start";
    private static final String FUNC_STAGE_PROCESS = "Processing";
    private static final String FUNC_STAGE_END = "End";

    private Logger innerLogger;

    private String remoteIP     = "";
    private String functionName = "Unknown Function";
    private String functionDes  = "Unknown Function Des";
    private JSONObject paramCollection = new JSONObject();
    public void SetLogger(Logger logger)
    {
        this.innerLogger = logger;
    }
    public void SetParamJson(JSONObject param)
    {
        this.paramCollection = param;
    }
    public void SetRemoteIP(String ip)
    {
        this.remoteIP = ip;
    }
    public void SetException(Exception e)
    {
        if(null != e)
        {
            this.paramCollection = new JSONObject();
            this.paramCollection.put("exception",e.getMessage());
        }
    }
    public void AddParam(String key,Object value)
    {
        this.paramCollection.put(key,value== null ? "null" : value);
    }
    public void ClearParam()
    {
        this.paramCollection = new JSONObject();
    }
    public void StartFunctionLog(LogLevel level)
    {
        StartFunctionLog(level, "", "");
    }

    public void StartFunctionLog(LogLevel level,String functionDes)
    {
        StartFunctionLog(level, functionDes, "");
    }

    public void StartFunctionLog(LogLevel level,String functionDes,String customStr)
    {
        this.functionName = new Exception().getStackTrace()[1].getMethodName();
        if("StartFunctionLog".equals(this.functionName))
        {
            this.functionName = new Exception().getStackTrace()[2].getMethodName();
        }
        this.functionDes = functionDes;
        LOG(level,MakeLogStr(FuncStage.Start.GetStageStr(),"",customStr));
       // this.paramCollection = new JSONObject();
    }

    public void ProcessFunctionLog(LogLevel level)
    {
        ProcessFunctionLog(level,"");
    }

    public void ProcessFunctionLog(LogLevel level,String customStr)
    {
        LOG(level,MakeLogStr(FUNC_STAGE_PROCESS,"",customStr));
    }

    public void EndFunctionLog(LogLevel level,ResultDefine functionResult)
    {
        EndFunctionLog(level,functionResult,"");
    }

    public void EndFunctionLog(LogLevel level,ResultDefine functionResult,String customStr)
    {
        LOG(level,MakeLogStr(FUNC_STAGE_END,functionResult.GetResultStr(),customStr));
        ClearParam();
    }
    private String MakeLogStr(String funcStage,String funcResult,String customMsg)
    {
        StringBuilder builder = new StringBuilder();

        builder.append(PARAM_SEP_LEFT).append(remoteIP).append(PARAM_SEP_RIGHT).append(PARAM_CONNECTOR);
        builder.append(PARAM_SEP_LEFT).append(functionName).append(PARAM_SEP_RIGHT).append(PARAM_CONNECTOR);
        builder.append(PARAM_SEP_LEFT).append(functionDes).append(PARAM_SEP_RIGHT).append(PARAM_CONNECTOR);
        builder.append(PARAM_SEP_LEFT).append(funcStage).append(PARAM_SEP_RIGHT).append(PARAM_CONNECTOR);
        builder.append(PARAM_SEP_LEFT).append(paramCollection.toString()).append(PARAM_SEP_RIGHT).append(PARAM_CONNECTOR);
        builder.append(PARAM_SEP_LEFT).append(funcResult.isEmpty()?"Unknown Function Result":funcResult).append(PARAM_SEP_RIGHT).append(PARAM_CONNECTOR);
        builder.append(PARAM_SEP_LEFT).append(customMsg).append(PARAM_SEP_RIGHT);
        return builder.toString();
    }
    private void LOG(LogLevel level,String logStr)
    {
        if(null != innerLogger)
        {
            switch (level)
            {
                case INFO:innerLogger.info(logStr);break;
                case DEBUG:innerLogger.debug(logStr);break;
                case ERROR:innerLogger.error(logStr);break;
                case WARNING:innerLogger.warn(logStr);break;
            }
        }
    }
}
