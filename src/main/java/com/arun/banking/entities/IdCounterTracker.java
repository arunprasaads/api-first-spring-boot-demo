package com.arun.banking.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Entity to track a counter for different ID fields that will kept in the DB
 */
@Document(collection = "IdCounterTracker")
public class IdCounterTracker {
    @Id
    private String id;

    private String counterField;

    private Integer counterValue;

    public IdCounterTracker(String counterField, Integer counterValue) {
        this.counterField = counterField;
        this.counterValue = counterValue;
    }

    public String getId() {
        return this.id;
    }

    /**
     * get the Field for which counter is managed
     * 
     * @return the filed for the counter
     */
    public String getCounterField() {
        return this.counterField;
    }

    /**
     * Set the Field for which Counter is managed
     * 
     * @param counterField the field for the counter
     */
    public void setCounterField(String counterField) {
        this.counterField = counterField;
    }

    /**
     * get the current counter values
     * 
     * @return returns current counter value
     */
    public Integer getCounterValue() {
        return this.counterValue;
    }

    /**
     * Set the updated counter value
     * 
     * @param counterValue the counter value to which it needs to be updated
     */
    public void setCounterValue(Integer counterValue) {
        this.counterValue = counterValue;
    }

}
