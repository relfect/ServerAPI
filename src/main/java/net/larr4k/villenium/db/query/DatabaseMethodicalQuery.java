package net.larr4k.villenium.db.query;

import org.bukkit.Bukkit;

import java.sql.SQLException;
import java.util.function.Supplier;


public interface DatabaseMethodicalQuery extends DatabaseQuery {

    /**
     * Выполнить данный запрос.
     * @throws SQLException
     */
    void execute() throws SQLException;

    /**
     * Выполнить данный запрос с автоматической обработкой возможной SQL-ошибки.
     */
    default void executeUnchecked() {
        try {
            execute();
        } catch (SQLException exception) {
            Bukkit.getLogger().info((Supplier<String>) exception);
        }
    }

}
