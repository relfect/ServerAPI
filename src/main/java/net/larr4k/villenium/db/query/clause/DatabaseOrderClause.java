package net.larr4k.villenium.db.query.clause;

import net.larr4k.villenium.db.query.DatabaseQuery;
import net.larr4k.villenium.db.query.DatabaseSelectQuery;

public interface DatabaseOrderClause<T extends DatabaseSelectQuery & DatabaseQuery> extends DatabaseClause<T> {

    /**
     * Добавить сортировку по указанному полю.
     * @param fieldName имя столбца.
     * @param direction тип сортировки.
     * @return этот же билдер ORDER_BY части запроса.
     */
    DatabaseOrderClause<T> orderBy(String fieldName, OrderDirection direction);

    enum OrderDirection {
        ASCENDING,
        DESCENDING
    }

}