/*
 * This file is generated by jOOQ.
 */
package com.examsystem.jooq.generated.tables;


import com.examsystem.jooq.generated.DefaultSchema;
import com.examsystem.jooq.generated.Keys;
import com.examsystem.jooq.generated.tables.records.DemoRecord;

import java.util.Collection;

import org.jooq.Condition;
import org.jooq.Field;
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
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Demo extends TableImpl<DemoRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>demo</code>
     */
    public static final Demo DEMO = new Demo();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<DemoRecord> getRecordType() {
        return DemoRecord.class;
    }

    /**
     * The column <code>demo.ID</code>.
     */
    public final TableField<DemoRecord, Integer> ID = createField(DSL.name("ID"), SQLDataType.INTEGER, this, "");

    /**
     * The column <code>demo.Name</code>.
     */
    public final TableField<DemoRecord, String> NAME = createField(DSL.name("Name"), SQLDataType.VARCHAR(20), this, "");

    /**
     * The column <code>demo.Hint</code>.
     */
    public final TableField<DemoRecord, String> HINT = createField(DSL.name("Hint"), SQLDataType.CLOB, this, "");

    private Demo(Name alias, Table<DemoRecord> aliased) {
        this(alias, aliased, (Field<?>[]) null, null);
    }

    private Demo(Name alias, Table<DemoRecord> aliased, Field<?>[] parameters, Condition where) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table(), where);
    }

    /**
     * Create an aliased <code>demo</code> table reference
     */
    public Demo(String alias) {
        this(DSL.name(alias), DEMO);
    }

    /**
     * Create an aliased <code>demo</code> table reference
     */
    public Demo(Name alias) {
        this(alias, DEMO);
    }

    /**
     * Create a <code>demo</code> table reference
     */
    public Demo() {
        this(DSL.name("demo"), null);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : DefaultSchema.DEFAULT_SCHEMA;
    }

    @Override
    public UniqueKey<DemoRecord> getPrimaryKey() {
        return Keys.DEMO__PK_DEMO;
    }

    @Override
    public Demo as(String alias) {
        return new Demo(DSL.name(alias), this);
    }

    @Override
    public Demo as(Name alias) {
        return new Demo(alias, this);
    }

    @Override
    public Demo as(Table<?> alias) {
        return new Demo(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    public Demo rename(String name) {
        return new Demo(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Demo rename(Name name) {
        return new Demo(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    public Demo rename(Table<?> name) {
        return new Demo(name.getQualifiedName(), null);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Demo where(Condition condition) {
        return new Demo(getQualifiedName(), aliased() ? this : null, null, condition);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Demo where(Collection<? extends Condition> conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Demo where(Condition... conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Demo where(Field<Boolean> condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public Demo where(SQL condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public Demo where(@Stringly.SQL String condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public Demo where(@Stringly.SQL String condition, Object... binds) {
        return where(DSL.condition(condition, binds));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public Demo where(@Stringly.SQL String condition, QueryPart... parts) {
        return where(DSL.condition(condition, parts));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Demo whereExists(Select<?> select) {
        return where(DSL.exists(select));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Demo whereNotExists(Select<?> select) {
        return where(DSL.notExists(select));
    }
}
