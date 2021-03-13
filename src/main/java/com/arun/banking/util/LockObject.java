package com.arun.banking.util;

/**
 * A simple object that helps with refernce counting used to assist in locking
 * accounts for transaction
 */
public class LockObject {
    private int referenceCounter;

    /**
     * Increments the reference counter by 1
     */
    public void incrementReferenceCounter() {
        referenceCounter++;
    }

    /**
     * Gets the current amount of users referring to this object for
     * synchronization.
     * 
     * @return The current amount of users / the current reference counter value
     */
    public int getReferenceCounter() {
        return referenceCounter;
    }

    /**
     * Decrements the reference counter by 1
     */
    public void decrementReferenceCounter() {
        referenceCounter--;
    }
}
