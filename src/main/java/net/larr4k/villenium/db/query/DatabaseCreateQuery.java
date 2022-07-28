package net.larr4k.villenium.db.query;

public interface DatabaseCreateQuery extends DatabaseMethodicalQuery {

    /**
     * Добавить столбец в новосоздаваемую таблицу.
     * @param columnName имя столбца.
     * @param columnType тип столбца.
     * @param isPrimary является ли primary/combined-ключом.
     * @return этот же билдер.
     */
    default DatabaseCreateQuery column(String columnName, ColumnType columnType, boolean isPrimary) {
        return column(columnName, columnType, null, isPrimary);
    }

    /**
     * Добавить столбец в новосоздаваемую таблицу.
     * @param columnName имя столбца.
     * @param columnType тип столбца.
     * @param defaultValue базовое значение для столбца.
     * @param isPrimary является ли primary/combined-ключом.
     * @return этот же билдер.
     */
    default DatabaseCreateQuery column(String columnName, ColumnType columnType, Object defaultValue, boolean isPrimary) {
        return column(columnName, columnType, defaultValue, isPrimary, false);
    }

    /**
     * Добавить столбец в новосоздаваемую таблицу.
     * @param columnName имя столбца.
     * @param columnType тип столбца.
     * @param defaultValue базовое значение для столбца.
     * @param isPrimary является ли primary/combined-ключом.
     * @param nullable может ля колонка иметь значение null
     * @return этот же билдер.
     */
    DatabaseCreateQuery column(String columnName, ColumnType columnType, Object defaultValue, boolean isPrimary, boolean nullable);

    enum ColumnType {
        VARCHAR_16,
        VARCHAR_32,
        VARCHAR_64,
        VARCHAR_128,
        TEXT,
        TINY_INT,
        INT,
        BIG_INT,
        BOOLEAN,
        DOUBLE,
        FLOAT
    }

}

