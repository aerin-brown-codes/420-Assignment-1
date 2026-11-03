package ca.mcgill.ecse420.a2;

import java.util.Map;
import java.util.concurrent.*;

/**
 * Assigns IDs to threads
 */
public class ThreadId {
    private static Map<Long, Integer> threadIdMap = new ConcurrentHashMap<Long, Integer>();

    public static int getThreadId(){
        long id = Thread.currentThread().threadId();

        return threadIdMap.get(id);
    }

    public static void clearThread(){
        threadIdMap.clear();
    }
}
