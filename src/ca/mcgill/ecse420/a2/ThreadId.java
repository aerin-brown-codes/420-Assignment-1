package ca.mcgill.ecse420.a2;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

/**
 * Assigns IDs to threads
 */
public class ThreadId {
    private static Map<Long, Integer> threadIdMap = new ConcurrentHashMap<Long, Integer>();
    //private static HashMap<Long, Integer> threadIdMap = new HashMap<>();

    public static int getThreadId(){
        long id = Thread.currentThread().threadId();

        if (!threadIdMap.containsKey(id)){
            int key = threadIdMap.size();
            threadIdMap.put(id, key);
        }

        return threadIdMap.get(id);
    }

    public static void clearThread(){
        threadIdMap.clear();
    }
}
