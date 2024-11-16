/*
 * This file is generated by jOOQ.
 */
package com.examsystem.jooq.generated.tables;


import com.examsystem.jooq.generated.DefaultSchema;
import com.examsystem.jooq.generated.Keys;
import com.examsystem.jooq.generated.tables.records.CoursesRecord;

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
public class Courses extends TableImpl<CoursesRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>courses</code>
     */
    public static final Courses COURSES = new Courses();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<CoursesRecord> getRecordType() {
        return CoursesRecord.class;
    }

    /**
     * The column <code>courses.id</code>.
     */
    public final TableField<CoursesRecord, Integer> ID = createField(DSL.name("id"), SQLDataType.INTEGER.identity(true), this, "");

    /**
     * The column <code>courses.name</code>.
     */
    public final TableField<CoursesRecord, String> NAME = createField(DSL.name("name"), SQLDataType.CLOB.nullable(false), this, "");

    /**
     * The column <code>courses.department</code>.
     */
    public final TableField<CoursesRecord, String> DEPARTMENT = createField(DSL.name("department"), SQLDataType.CLOB.nullable(false), this, "");

    private Courses(Name alias, Table<CoursesRecord> aliased) {
        this(alias, aliased, (Field<?>[]) null, null);
    }

    private Courses(Name alias, Table<CoursesRecord> aliased, Field<?>[] parameters, Condition where) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table(), where);
    }

    /**
     * Create an aliased <code>courses</code> table reference
     */
    public Courses(String alias) {
        this(DSL.name(alias), COURSES);
    }

    /**
     * Create an aliased <code>courses</code> table reference
     */
    public Courses(Name alias) {
        this(alias, COURSES);
    }

    /**
     * Create a <code>courses</code> table reference
     */
    public Courses() {
        this(DSL.name("courses"), null);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : DefaultSchema.DEFAULT_SCHEMA;
    }

    @Override
    public Identity<CoursesRecord, Integer> getIdentity() {
        return (Identity<CoursesRecord, Integer>) super.getIdentity();
    }

    @Override
    public UniqueKey<CoursesRecord> getPrimaryKey() {
        return Keys.COURSES__PK_COURSES;
    }

    @Override
    public Courses as(String alias) {
        return new Courses(DSL.name(alias), this);
    }

    @Override
    public Courses as(Name alias) {
        return new Courses(alias, this);
    }

    @Override
    public Courses as(Table<?> alias) {
        return new Courses(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    public Courses rename(String name) {
        return new Courses(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Courses rename(Name name) {
        return new Courses(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    public Courses rename(Table<?> name) {
        return new Courses(name.getQualifiedName(), null);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Courses where(Condition condition) {
        return new Courses(getQualifiedName(), aliased() ? this : null, null, condition);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Courses where(Collection<? extends Condition> conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Courses where(Condition... conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Courses where(Field<Boolean> condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public Courses where(SQL condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public Courses where(@Stringly.SQL String condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public Courses where(@Stringly.SQL String condition, Object... binds) {
        return where(DSL.condition(condition, binds));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public Courses where(@Stringly.SQL String condition, QueryPart... parts) {
        return where(DSL.condition(condition, parts));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Courses whereExists(Select<?> select) {
        return where(DSL.exists(select));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Courses whereNotExists(Select<?> select) {
        return where(DSL.notExists(select));
    }
}