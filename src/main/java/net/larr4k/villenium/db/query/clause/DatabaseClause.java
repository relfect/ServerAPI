package net.larr4k.villenium.db.query.clause;

import net.larr4k.villenium.db.query.DatabaseQuery;

public interface DatabaseClause<T extends DatabaseQuery> {

    /**
     * Вернуться к билдеру полного запроса.
     * @return билдер полного запроса.
     */
    T doneClause();

}
