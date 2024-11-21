/*
 * This file is generated by jOOQ.
 */
package com.examsystem.jooq.generated.tables.records;


import com.examsystem.jooq.generated.tables.Demo;

import org.jooq.Record1;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class DemoRecord extends UpdatableRecordImpl<DemoRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>demo.ID</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>demo.ID</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>demo.Name</code>.
     */
    public void setName(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>demo.Name</code>.
     */
    public String getName() {
        return (String) get(1);
    }

    /**
     * Setter for <code>demo.Hint</code>.
     */
    public void setHint(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>demo.Hint</code>.
     */
    public String getHint() {
        return (String) get(2);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Integer> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached DemoRecord
     */
    public DemoRecord() {
        super(Demo.DEMO);
    }

    /**
     * Create a detached, initialised DemoRecord
     */
    public DemoRecord(Integer id, String name, String hint) {
        super(Demo.DEMO);

        setId(id);
        setName(name);
        setHint(hint);
        resetChangedOnNotNull();
    }
}
