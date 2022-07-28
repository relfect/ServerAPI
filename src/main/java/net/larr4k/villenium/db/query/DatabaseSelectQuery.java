package net.larr4k.villenium.db.query;

import net.larr4k.villenium.db.query.clause.DatabaseOrderClause;
import net.larr4k.villenium.db.query.clause.DatabaseWhereClause;

public interface DatabaseSelectQuery extends DatabaseFunctionalQuery {

    /**
     * Добавить поле, которое нужно получить.
     * @param fieldName имя столбца.
     * @return этот же билдер.
     */
   DatabaseSelectQuery field(String fieldName);

    /**
     * Указать, что мы хотим получить все поля из таблицы.
     * @return этот же билдер.
     */
    default DatabaseSelectQuery allFields() {
        return field("*");
    }

    /**
     * Указать, что мы хотим подсчитать количество всех строк в таблице.
     * @return этот же билдер.
     */
    default DatabaseSelectQuery countAll() {
        return field("COUNT(*)");
    }

    /**
     * Указать, что мы хотим получить указанные поля под флагом DISTINCT.
     * @return этот же билдер.
     */
    DatabaseSelectQuery distinct();

    /**
     * Перейти в WHERE часть запроса.
     * @return билдер WHERE части данного запроса.
     */
    DatabaseWhereClause<? extends DatabaseSelectQuery> where();

    /**
     * Перейти в ORDER_BY часть запроса.
     * @return билдер ORDER_BY части данного запроса.
     */
    DatabaseOrderClause<? extends DatabaseSelectQuery> order();

    /**
     * Установить LIMIT на количество возвращаемых строк.
     * @param limit лимит.
     * @return этот же билдер.
     */
    DatabaseSelectQuery limit(int limit);

}

