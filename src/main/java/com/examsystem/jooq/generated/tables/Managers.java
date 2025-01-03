/*
 * This file is generated by jOOQ.
 */
package com.examsystem.jooq.generated.tables;


import com.examsystem.jooq.generated.DefaultSchema;
import com.examsystem.jooq.generated.Keys;
import com.examsystem.jooq.generated.tables.records.ManagersRecord;

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
public class Managers extends TableImpl<ManagersRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>managers</code>
     */
    public static final Managers MANAGERS = new Managers();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<ManagersRecord> getRecordType() {
        return ManagersRecord.class;
    }

    /**
     * The column <code>managers.id</code>.
     */
    public final TableField<ManagersRecord, Integer> ID = createField(DSL.name("id"), SQLDataType.INTEGER.identity(true), this, "");

    /**
     * The column <code>managers.username</code>.
     */
    public final TableField<ManagersRecord, String> USERNAME = createField(DSL.name("username"), SQLDataType.CLOB.nullable(false), this, "");

    /**
     * The column <code>managers.password</code>.
     */
    public final TableField<ManagersRecord, String> PASSWORD = createField(DSL.name("password"), SQLDataType.CLOB.nullable(false), this, "");

    private Managers(Name alias, Table<ManagersRecord> aliased) {
        this(alias, aliased, (Field<?>[]) null, null);
    }

    private Managers(Name alias, Table<ManagersRecord> aliased, Field<?>[] parameters, Condition where) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table(), where);
    }

    /**
     * Create an aliased <code>managers</code> table reference
     */
    public Managers(String alias) {
        this(DSL.name(alias), MANAGERS);
    }

    /**
     * Create an aliased <code>managers</code> table reference
     */
    public Managers(Name alias) {
        this(alias, MANAGERS);
    }

    /**
     * Create a <code>managers</code> table reference
     */
    public Managers() {
        this(DSL.name("managers"), null);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : DefaultSchema.DEFAULT_SCHEMA;
    }

    @Override
    public Identity<ManagersRecord, Integer> getIdentity() {
        return (Identity<ManagersRecord, Integer>) super.getIdentity();
    }

    @Override
    public UniqueKey<ManagersRecord> getPrimaryKey() {
        return Keys.MANAGERS__PK_MANAGERS;
    }

    @Override
    public Managers as(String alias) {
        return new Managers(DSL.name(alias), this);
    }

    @Override
    public Managers as(Name alias) {
        return new Managers(alias, this);
    }

    @Override
    public Managers as(Table<?> alias) {
        return new Managers(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    public Managers rename(String name) {
        return new Managers(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Managers rename(Name name) {
        return new Managers(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    public Managers rename(Table<?> name) {
        return new Managers(name.getQualifiedName(), null);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Managers where(Condition condition) {
        return new Managers(getQualifiedName(), aliased() ? this : null, null, condition);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Managers where(Collection<? extends Condition> conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Managers where(Condition... conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Managers where(Field<Boolean> condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public Managers where(SQL condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public Managers where(@Stringly.SQL String condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public Managers where(@Stringly.SQL String condition, Object... binds) {
        return where(DSL.condition(condition, binds));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public Managers where(@Stringly.SQL String condition, QueryPart... parts) {
        return where(DSL.condition(condition, parts));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Managers whereExists(Select<?> select) {
        return where(DSL.exists(select));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Managers whereNotExists(Select<?> select) {
        return where(DSL.notExists(select));
    }
}
