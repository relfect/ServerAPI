package net.larr4k.villenium.db.query;

import net.larr4k.villenium.db.query.clause.DatabaseWhereClause;

public interface DatabaseUpdateQuery extends DatabaseMethodicalQuery {

    /**
     * Обновить значение поля в строке.
     * @param fieldName имя столбца.
     * @param fieldValue значение столбца.
     * @return этот же билдер.
     */
    DatabaseUpdateQuery fieldValue(String fieldName, Object fieldValue);

    /**
     * Обновить значение поля в строке, но не на значение, а на определенную величину относительно его нынешнего значения.
     * @param fieldName имя столбца.
     * @param delta величина изменения значения столбца относительно нынешнего.
     * @return этот же билдер.
     */
    DatabaseUpdateQuery fieldAddition(String fieldName, int delta);

    /**
     * Перейти в WHERE часть запроса.
     * @return билдер WHERE части данного запроса.
     */
    DatabaseWhereClause<? extends DatabaseUpdateQuery> where();

}