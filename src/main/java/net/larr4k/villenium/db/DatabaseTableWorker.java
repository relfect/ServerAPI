package net.larr4k.villenium.db;


import net.larr4k.villenium.db.query.*;

public interface DatabaseTableWorker {

    /**
     * Получить билдер INSERT запроса.
     * @return билдер INSERT запроса.
     */
    DatabaseInsertQuery insert();

    /**
     * Получить билдер UPDATE запроса.
     * @return билдер UPDATE запроса.
     */
    DatabaseUpdateQuery update();

    /**
     * Получить билдер DELETE запроса.
     * @return билдер DELETE запроса.
     */
    DatabaseDeleteQuery delete();

    /**
     * Интерфейс работы с конкретной таблицей базы данных в синхронном режиме.
     */
    interface SyncDmsDatabaseTableWorker extends DatabaseTableWorker {

        /**
         * Получить билдер запроса на создание таблицы.
         * @return билдер запроса на создание таблицы.
         */
        DatabaseCreateQuery create();

        /**
         * Получить билдер SELECT запроса.
         * @return билдер SELECT запроса.
         */
        DatabaseSelectQuery select();
    }

    /**
     * Интерфейс работы с конкретной таблицей базы данных в асинхронном режиме.
     */
    interface AsyncDmsDatabaseTableWorker extends DatabaseTableWorker {

    }

}
