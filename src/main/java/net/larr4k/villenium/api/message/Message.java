package net.larr4k.villenium.api.message;

import org.bukkit.entity.Player;

import java.util.*;
import java.util.stream.Collectors;


public interface Message {

    /**
     * Отправить сообщение всем игрокам сервера.
     */
    void broadcast();

    /**
     * Отправить сообщение конкретному игроку.
     * @param player игрок.
     */
    default void send(Player player) {
        send((Player) Collections.singleton(player));
    }

    /**
     * Отравить сообщение указанным игрокам.
     * @param players игроки.
     */
    void send(Collection<Player> players);



    /**
     * Должно ли это сообщение обрабатывать цветовые коды (&)?
     * Изначальное значение - да.
     * @param value да/нет.
     */
    void colorize(boolean value);

}
