package com.arun.banking.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Tracks all the Lock Object based on account number
 */
public class AccountLockTracker {
    // TODO: Can use Concurrent hash map here
    private static final Map<Integer, LockObject> LOCK_MAP = new HashMap<>();

    private AccountLockTracker() {
        // static class only
    }

    /**
     * Creates and returns a new lock object in case no request for the provided id
     * is currently active. In case there is an active request going on, the same
     * lock object will be returned in order to successfully use it as
     * synchronisation object.
     * 
     * @param id The id for which the lock object should be created
     * @return The lock object for the id
     */
    public static LockObject getLockObject(Integer id) {
        synchronized (LOCK_MAP) {
            LockObject lockObject = LOCK_MAP.computeIfAbsent(id, idParam -> new LockObject());
            lockObject.incrementReferenceCounter();
            return lockObject;
        }
    }

    /**
     * Decrements the reference counter for the lock object associated with the
     * provided id. In case the reference counter drops to 0, the lock object is
     * freed.
     * 
     * @param id The id for which the lock object should be freed
     */
    public static void freeLockObject(Integer id) {
        synchronized (LOCK_MAP) {
            LockObject lockObject = LOCK_MAP.get(id);
            if (lockObject != null) {
                lockObject.decrementReferenceCounter();
                if (lockObject.getReferenceCounter() == 0) {
                    LOCK_MAP.remove(id);
                }
            }
        }
    }
}
