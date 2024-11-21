/*
 * This file is generated by jOOQ.
 */
package com.examsystem.jooq.generated.tables;


import com.examsystem.jooq.generated.DefaultSchema;
import com.examsystem.jooq.generated.Keys;
import com.examsystem.jooq.generated.tables.ExaminationQuestions.ExaminationQuestionsPath;
import com.examsystem.jooq.generated.tables.Examinations.ExaminationsPath;
import com.examsystem.jooq.generated.tables.records.QuestionsRecord;

import java.util.Collection;

import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.InverseForeignKey;
import org.jooq.Name;
import org.jooq.Path;
import org.jooq.PlainSQL;
import org.jooq.QueryPart;
import org.jooq.Record;
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
public class Questions extends TableImpl<QuestionsRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>questions</code>
     */
    public static final Questions QUESTIONS = new Questions();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<QuestionsRecord> getRecordType() {
        return QuestionsRecord.class;
    }

    /**
     * The column <code>questions.id</code>.
     */
    public final TableField<QuestionsRecord, Integer> ID = createField(DSL.name("id"), SQLDataType.INTEGER.identity(true), this, "");

    /**
     * The column <code>questions.question</code>.
     */
    public final TableField<QuestionsRecord, String> QUESTION = createField(DSL.name("question"), SQLDataType.CLOB.nullable(false), this, "");

    /**
     * The column <code>questions.optionA</code>.
     */
    public final TableField<QuestionsRecord, String> OPTIONA = createField(DSL.name("optionA"), SQLDataType.CLOB.nullable(false), this, "");

    /**
     * The column <code>questions.optionB</code>.
     */
    public final TableField<QuestionsRecord, String> OPTIONB = createField(DSL.name("optionB"), SQLDataType.CLOB.nullable(false), this, "");

    /**
     * The column <code>questions.optionC</code>.
     */
    public final TableField<QuestionsRecord, String> OPTIONC = createField(DSL.name("optionC"), SQLDataType.CLOB.nullable(false), this, "");

    /**
     * The column <code>questions.optionD</code>.
     */
    public final TableField<QuestionsRecord, String> OPTIOND = createField(DSL.name("optionD"), SQLDataType.CLOB.nullable(false), this, "");

    /**
     * The column <code>questions.answer</code>.
     */
    public final TableField<QuestionsRecord, String> ANSWER = createField(DSL.name("answer"), SQLDataType.CLOB.nullable(false), this, "");

    /**
     * The column <code>questions.type</code>.
     */
    public final TableField<QuestionsRecord, String> TYPE = createField(DSL.name("type"), SQLDataType.CLOB.nullable(false), this, "");

    /**
     * The column <code>questions.score</code>.
     */
    public final TableField<QuestionsRecord, Float> SCORE = createField(DSL.name("score"), SQLDataType.REAL.nullable(false), this, "");

    private Questions(Name alias, Table<QuestionsRecord> aliased) {
        this(alias, aliased, (Field<?>[]) null, null);
    }

    private Questions(Name alias, Table<QuestionsRecord> aliased, Field<?>[] parameters, Condition where) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table(), where);
    }

    /**
     * Create an aliased <code>questions</code> table reference
     */
    public Questions(String alias) {
        this(DSL.name(alias), QUESTIONS);
    }

    /**
     * Create an aliased <code>questions</code> table reference
     */
    public Questions(Name alias) {
        this(alias, QUESTIONS);
    }

    /**
     * Create a <code>questions</code> table reference
     */
    public Questions() {
        this(DSL.name("questions"), null);
    }

    public <O extends Record> Questions(Table<O> path, ForeignKey<O, QuestionsRecord> childPath, InverseForeignKey<O, QuestionsRecord> parentPath) {
        super(path, childPath, parentPath, QUESTIONS);
    }

    /**
     * A subtype implementing {@link Path} for simplified path-based joins.
     */
    public static class QuestionsPath extends Questions implements Path<QuestionsRecord> {

        private static final long serialVersionUID = 1L;
        public <O extends Record> QuestionsPath(Table<O> path, ForeignKey<O, QuestionsRecord> childPath, InverseForeignKey<O, QuestionsRecord> parentPath) {
            super(path, childPath, parentPath);
        }
        private QuestionsPath(Name alias, Table<QuestionsRecord> aliased) {
            super(alias, aliased);
        }

        @Override
        public QuestionsPath as(String alias) {
            return new QuestionsPath(DSL.name(alias), this);
        }

        @Override
        public QuestionsPath as(Name alias) {
            return new QuestionsPath(alias, this);
        }

        @Override
        public QuestionsPath as(Table<?> alias) {
            return new QuestionsPath(alias.getQualifiedName(), this);
        }
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : DefaultSchema.DEFAULT_SCHEMA;
    }

    @Override
    public Identity<QuestionsRecord, Integer> getIdentity() {
        return (Identity<QuestionsRecord, Integer>) super.getIdentity();
    }

    @Override
    public UniqueKey<QuestionsRecord> getPrimaryKey() {
        return Keys.QUESTIONS__PK_QUESTIONS;
    }

    private transient ExaminationQuestionsPath _examinationQuestions;

    /**
     * Get the implicit to-many join path to the
     * <code>examination_questions</code> table
     */
    public ExaminationQuestionsPath examinationQuestions() {
        if (_examinationQuestions == null)
            _examinationQuestions = new ExaminationQuestionsPath(this, null, Keys.EXAMINATION_QUESTIONS__FK_EXAMINATION_QUESTIONS_PK_QUESTIONS.getInverseKey());

        return _examinationQuestions;
    }

    /**
     * Get the implicit many-to-many join path to the <code>examinations</code>
     * table
     */
    public ExaminationsPath examinations() {
        return examinationQuestions().examinations();
    }

    @Override
    public Questions as(String alias) {
        return new Questions(DSL.name(alias), this);
    }

    @Override
    public Questions as(Name alias) {
        return new Questions(alias, this);
    }

    @Override
    public Questions as(Table<?> alias) {
        return new Questions(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    public Questions rename(String name) {
        return new Questions(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Questions rename(Name name) {
        return new Questions(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    public Questions rename(Table<?> name) {
        return new Questions(name.getQualifiedName(), null);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Questions where(Condition condition) {
        return new Questions(getQualifiedName(), aliased() ? this : null, null, condition);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Questions where(Collection<? extends Condition> conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Questions where(Condition... conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Questions where(Field<Boolean> condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public Questions where(SQL condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public Questions where(@Stringly.SQL String condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public Questions where(@Stringly.SQL String condition, Object... binds) {
        return where(DSL.condition(condition, binds));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public Questions where(@Stringly.SQL String condition, QueryPart... parts) {
        return where(DSL.condition(condition, parts));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Questions whereExists(Select<?> select) {
        return where(DSL.exists(select));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Questions whereNotExists(Select<?> select) {
        return where(DSL.notExists(select));
    }
}
