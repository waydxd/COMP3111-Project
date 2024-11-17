/*
 * This file is generated by jOOQ.
 */
package com.examsystem.jooq.generated.tables.records;


import com.examsystem.jooq.generated.tables.Examinations;

import org.jooq.Record1;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class ExaminationsRecord extends UpdatableRecordImpl<ExaminationsRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>examinations.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>examinations.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>examinations.courseID</code>.
     */
    public void setCourseid(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>examinations.courseID</code>.
     */
    public String getCourseid() {
        return (String) get(1);
    }

    /**
     * Setter for <code>examinations.examTime</code>.
     */
    public void setExamtime(Float value) {
        set(2, value);
    }

    /**
     * Getter for <code>examinations.examTime</code>.
     */
    public Float getExamtime() {
        return (Float) get(2);
    }

    /**
     * Setter for <code>examinations.examName</code>.
     */
    public void setExamname(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>examinations.examName</code>.
     */
    public String getExamname() {
        return (String) get(3);
    }

    /**
     * Setter for <code>examinations.publish</code>.
     */
    public void setPublish(Boolean value) {
        set(4, value);
    }

    /**
     * Getter for <code>examinations.publish</code>.
     */
    public Boolean getPublish() {
        return (Boolean) get(4);
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
     * Create a detached ExaminationsRecord
     */
    public ExaminationsRecord() {
        super(Examinations.EXAMINATIONS);
    }

    /**
     * Create a detached, initialised ExaminationsRecord
     */
    public ExaminationsRecord(Integer id, String courseid, Float examtime, String examname, Boolean publish) {
        super(Examinations.EXAMINATIONS);

        setId(id);
        setCourseid(courseid);
        setExamtime(examtime);
        setExamname(examname);
        setPublish(publish);
        resetChangedOnNotNull();
    }
}
