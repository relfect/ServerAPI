package net.larr4k.villenium.api.graphic.bar;

import net.larr4k.villenium.api.message.Message;
import net.larr4k.villenium.utility.type.Type;
import org.bukkit.Bukkit;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;


import java.util.Collection;
import java.util.List;


public interface SBossBar extends BossBar {

    /**
     * Установить имя босс-бару.
     *
     * @param title имя босс-бара.
     */
    void setTitle(Message title);

    /**
     * Установить имя босс-бару.
     *
     * @param title имя босс-бара.
     */
    default void setTitle(Enum title) {
        setTitle(Type.getMESSAGES_API().get(title));
    }

    /**
     * Установить имя босс-бару.
     *
     * @param title имя босс-бара.
     * @param args аргументы форматирования.
     */
    default void setTitle(Enum title, Object... args) {
        setTitle(Type.getMESSAGES_API().get(title, args));
    }

    /**
     * Перестать отправлять босс-бар указанному игроку.
     *
     * @param player villenium-игрок, которого нужно удалить из босс-бара.
     */
    void removePlayer(Player player);

    /**
     * Отправить босс-бар игроку с нужной локализацией.
     *
     * @param player Dms-игрок
     */
    void addPlayer(Player player);

    /**
     * Отправить босс-бар всем спигот-игрокам коллекции.
     *
     * @param players коллекция с игроками.
     */
    default void addSpigotPlayers(Collection<Player> players) {
        players.forEach(this::addPlayer);
    }

    /**
     * Отправить босс-бар всем dms-игрокам коллекции.
     *
     * @param players коллекция с игроками.
     */
    default void addDmsPlayers(Collection<Player> players) {
        players.forEach(this::addPlayer);
    }

    /**
     * Отправить босс-бар всему серверу.
     */
    default void broadcast() {
        Bukkit.getOnlinePlayers().forEach(this::addPlayer);
    }

    /**
     * Получить оставшуюся длительность босс-бара.
     *
     * @return длительность босс-бара.
     */
    default int getDuration() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    default int getTimeLeft(){
        throw new UnsupportedOperationException("Not supported yet.");
    }



    /**
     * Получить локализованное имя босс-бара.
     * @return локализованное имя босс-бара.
     */
    Message getLocalizedTitle();

    @Deprecated
    @Override
    default void setTitle(String s) {
        throw new UnsupportedOperationException("Not supported.");
    }

    @Deprecated
    @Override
    default List<Player> getPlayers() {
        throw new UnsupportedOperationException("Not supported.");
    }

    @Deprecated
    @Override
    default String getTitle() {
        throw new UnsupportedOperationException("Not supported.");
    }
}
