package scripts.advancedwalking.core.logging;

import scripts.advancedwalking.core.logging.loggers.ClientDebugLogger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Takes care of all the logging
 *
 * Do not use this directly, use LogProxy instead.
 *
 * @author Laniax
 */
public class LogManager {

    private static final Object _lock = new Object();

    private static final StringBuilder _builder = new StringBuilder();

    public static List<Logger> _loggers = new ArrayList<>(Arrays.asList(
            //TODO: make this more customizable
            //new BotDebugLogger(),
            new ClientDebugLogger()
    ));

    public static void addLogger(Logger logger) {
        _loggers.add(logger);
    }

    private static String prepare(String source, String message, Object... args) {

        _builder.append("[");
        _builder.append(source);
        _builder.append("] ");
        _builder.append(String.format(message, args));

        String result =_builder.toString();
        _builder.setLength(0);

        return result;
    }

    static void information(String source, String message, Object... args) {
        synchronized (_lock) {
            for(Logger log : _loggers) {

                String output = prepare(source, message, args);
                //String colorCoded = AnsiCode.Black.get() + output;
                log.writeInformation(output);
            }
        }
    }

    static void warning(String source, String message, Object... args) {
        synchronized (_lock) {
            for(Logger log : _loggers) {

                String output = prepare(source, message, args);
//                String colorCoded = AnsiCode.Yellow.get() + output;
                log.writeWarning(output);
            }
        }
    }

    static void error(String source, String message, Object... args) {
        synchronized (_lock) {
            for(Logger log : _loggers) {
                String output = prepare(source, message, args);
//                String colorCoded = AnsiCode.Red.get() + output;
                log.writeError(output);
            }
        }
    }

    static void debug(String source, String message, Object... args) {

       // if (getRepoID() >= 0) TODO
       //     return;

        synchronized (_lock) {
            for(Logger log : _loggers) {
                String output = prepare(source, message, args);
//                String colorCoded = AnsiCode.Red.get() + output;
                log.writeDebug(output);
            }
        }
    }

}
