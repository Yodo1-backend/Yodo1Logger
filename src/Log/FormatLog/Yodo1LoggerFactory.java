package FormatLog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by YanFeng on 2017/6/6.
 */
public final class Yodo1LoggerFactory {

    public static Yodo1Logger getLogger(Class clazz)
    {
        Yodo1Logger result = new Yodo1Logger();
        Logger innerLogger = LoggerFactory.getLogger(clazz.getName());
        result.SetLogger(innerLogger);
        return result;
    }
}
