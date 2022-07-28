package net.larr4k.villenium.db.query;

import net.larr4k.villenium.db.query.clause.DatabaseWhereClause;

public interface DatabaseDeleteQuery extends DatabaseMethodicalQuery {

    /**
     * Перейти в WHERE часть запроса.
     * @return билдер WHERE части данного запроса.
     */
    DatabaseWhereClause<? extends DatabaseDeleteQuery> where();

}