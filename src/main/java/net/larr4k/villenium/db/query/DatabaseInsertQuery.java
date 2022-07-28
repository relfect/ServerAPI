package net.larr4k.villenium.db.query;

public interface DatabaseInsertQuery extends DatabaseMethodicalQuery {

    /**
     * Установить значение поля в строке.
     * @param fieldName имя столбца.
     * @param fieldValue значение столбца.
     * @return этот же билдер.
     */
    DatabaseInsertQuery field(String fieldName, Object fieldValue);

    /**
     * В случае, если запись под указанными ключами уже существует в таблице, обновить значения указанных полей.
     * @param fieldNames имени столбцов таблицы, которые нужно обновить для данной строки.
     * @return этот же билдер.
     */
    DatabaseInsertQuery onDuplicateKeyUpdate(String... fieldNames);

}