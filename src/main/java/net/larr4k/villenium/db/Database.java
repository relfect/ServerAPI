package net.larr4k.villenium.db;

public interface Database {

    /**
     * Перейти в синхронный режим работы с базой данных.
     * @return интерфейс для работы с базой данных в синхронном режиме.
     */
    DmsDatabaseWorker<DatabaseTableWorker.SyncDmsDatabaseTableWorker> sync();

    /**
     * Перейти в асинхронный режим работы с базой данных.
     * @return интерфейс для работы с базой данных в асинхронном режиме.
     */
    DmsDatabaseWorker<DatabaseTableWorker.AsyncDmsDatabaseTableWorker> async();

    interface DmsDatabaseWorker<T extends DatabaseTableWorker> {

        /**
         * Перейти к работе с таблицей указанного имени.
         * @param tableName имя таблицы.
         * @return интерфейс для работы с таблицей указанного имени.
         * @throws IllegalArgumentException если у данного сервера нет доступа к таблице указанного имени.
         */
        T table(String tableName) throws IllegalArgumentException;

    }

}
