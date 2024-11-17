/*
 * This file is generated by jOOQ.
 */
package com.examsystem.jooq.generated.tables.records;


import com.examsystem.jooq.generated.tables.StudentExaminations;

import org.jooq.Record2;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class StudentExaminationsRecord extends UpdatableRecordImpl<StudentExaminationsRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>student_examinations.student_id</code>.
     */
    public void setStudentId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>student_examinations.student_id</code>.
     */
    public Integer getStudentId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>student_examinations.examination_id</code>.
     */
    public void setExaminationId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>student_examinations.examination_id</code>.
     */
    public Integer getExaminationId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>student_examinations.score</code>.
     */
    public void setScore(Float value) {
        set(2, value);
    }

    /**
     * Getter for <code>student_examinations.score</code>.
     */
    public Float getScore() {
        return (Float) get(2);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record2<Integer, Integer> key() {
        return (Record2) super.key();
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached StudentExaminationsRecord
     */
    public StudentExaminationsRecord() {
        super(StudentExaminations.STUDENT_EXAMINATIONS);
    }

    /**
     * Create a detached, initialised StudentExaminationsRecord
     */
    public StudentExaminationsRecord(Integer studentId, Integer examinationId, Float score) {
        super(StudentExaminations.STUDENT_EXAMINATIONS);

        setStudentId(studentId);
        setExaminationId(examinationId);
        setScore(score);
        resetChangedOnNotNull();
    }
}
