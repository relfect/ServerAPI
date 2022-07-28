package net.larr4k.villenium.db.query.clause;

import net.larr4k.villenium.db.query.DatabaseQuery;

public interface DatabaseWhereClause<T extends DatabaseQuery> extends DatabaseClause<T> {

    /**
     * Добавить требование: значение поля = указанного значения.
     * @param fieldName имя столбца.
     * @param fieldValue значение.
     * @return этот же билдер WHERE части запроса.
     */
   DatabaseWhereClause<T> fieldEquals(String fieldName, Object fieldValue);

    /**
     * Добавить требование: значение поля > указанного значения.
     * @param fieldName имя столбца.
     * @param value значение.
     * @return этот же билдер WHERE части запроса.
     */
   DatabaseWhereClause<T> fieldGreater(String fieldName, Object value);

    /**
     * Добавить требование: значение поля >= указанного значения.
     * @param fieldName имя столбца.
     * @param value значение.
     * @return этот же билдер WHERE части запроса.
     */
    DatabaseWhereClause<T> fieldGreaterOrEquals(String fieldName, Object value);

    /**
     * Добавить требование: значение поля < указанного значения.
     * @param fieldName имя столбца.
     * @param value значение.
     * @return этот же билдер WHERE части запроса.
     */
    DatabaseWhereClause<T> fieldLower(String fieldName, Object value);

    /**
     * Добавить требование: значение поля <= указанного значения.
     * @param fieldName имя столбца.
     * @param value значение.
     * @return этот же билдер WHERE части запроса.
     */
    DatabaseWhereClause<T> fieldLowerOrEquals(String fieldName, Object value);

    /**
     * Добавить требование: значение поля != указанного значения.
     * @param fieldName имя столбца.
     * @param value значение.
     * @return этот же билдер WHERE части запроса.
     */
    DatabaseWhereClause<T> fieldNotEqual(String fieldName, Object value);
}

