/*
 * This file is generated by jOOQ.
 */
package com.examsystem.jooq.generated.tables;


import com.examsystem.jooq.generated.DefaultSchema;
import com.examsystem.jooq.generated.Keys;
import com.examsystem.jooq.generated.tables.records.ExaminationsRecord;

import java.util.Collection;

import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.Identity;
import org.jooq.Name;
import org.jooq.PlainSQL;
import org.jooq.QueryPart;
import org.jooq.SQL;
import org.jooq.Schema;
import org.jooq.Select;
import org.jooq.Stringly;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class Examinations extends TableImpl<ExaminationsRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>examinations</code>
     */
    public static final Examinations EXAMINATIONS = new Examinations();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<ExaminationsRecord> getRecordType() {
        return ExaminationsRecord.class;
    }

    /**
     * The column <code>examinations.id</code>.
     */
    public final TableField<ExaminationsRecord, Integer> ID = createField(DSL.name("id"), SQLDataType.INTEGER.identity(true), this, "");

    /**
     * The column <code>examinations.courseID</code>.
     */
    public final TableField<ExaminationsRecord, String> COURSEID = createField(DSL.name("courseID"), SQLDataType.CLOB.nullable(false), this, "");

    /**
     * The column <code>examinations.examTime</code>.
     */
    public final TableField<ExaminationsRecord, Float> EXAMTIME = createField(DSL.name("examTime"), SQLDataType.REAL.nullable(false), this, "");

    /**
     * The column <code>examinations.examName</code>.
     */
    public final TableField<ExaminationsRecord, String> EXAMNAME = createField(DSL.name("examName"), SQLDataType.CLOB.nullable(false), this, "");

    /**
     * The column <code>examinations.publish</code>.
     */
    public final TableField<ExaminationsRecord, String> PUBLISH = createField(DSL.name("publish"), SQLDataType.CLOB.nullable(false), this, "");

    private Examinations(Name alias, Table<ExaminationsRecord> aliased) {
        this(alias, aliased, (Field<?>[]) null, null);
    }

    private Examinations(Name alias, Table<ExaminationsRecord> aliased, Field<?>[] parameters, Condition where) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table(), where);
    }

    /**
     * Create an aliased <code>examinations</code> table reference
     */
    public Examinations(String alias) {
        this(DSL.name(alias), EXAMINATIONS);
    }

    /**
     * Create an aliased <code>examinations</code> table reference
     */
    public Examinations(Name alias) {
        this(alias, EXAMINATIONS);
    }

    /**
     * Create a <code>examinations</code> table reference
     */
    public Examinations() {
        this(DSL.name("examinations"), null);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : DefaultSchema.DEFAULT_SCHEMA;
    }

    @Override
    public Identity<ExaminationsRecord, Integer> getIdentity() {
        return (Identity<ExaminationsRecord, Integer>) super.getIdentity();
    }

    @Override
    public UniqueKey<ExaminationsRecord> getPrimaryKey() {
        return Keys.EXAMINATIONS__PK_EXAMINATIONS;
    }

    @Override
    public Examinations as(String alias) {
        return new Examinations(DSL.name(alias), this);
    }

    @Override
    public Examinations as(Name alias) {
        return new Examinations(alias, this);
    }

    @Override
    public Examinations as(Table<?> alias) {
        return new Examinations(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    public Examinations rename(String name) {
        return new Examinations(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Examinations rename(Name name) {
        return new Examinations(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    public Examinations rename(Table<?> name) {
        return new Examinations(name.getQualifiedName(), null);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Examinations where(Condition condition) {
        return new Examinations(getQualifiedName(), aliased() ? this : null, null, condition);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Examinations where(Collection<? extends Condition> conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Examinations where(Condition... conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Examinations where(Field<Boolean> condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public Examinations where(SQL condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public Examinations where(@Stringly.SQL String condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public Examinations where(@Stringly.SQL String condition, Object... binds) {
        return where(DSL.condition(condition, binds));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public Examinations where(@Stringly.SQL String condition, QueryPart... parts) {
        return where(DSL.condition(condition, parts));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Examinations whereExists(Select<?> select) {
        return where(DSL.exists(select));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Examinations whereNotExists(Select<?> select) {
        return where(DSL.notExists(select));
    }
}