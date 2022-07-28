package net.larr4k.villenium.api.message;

import net.larr4k.villenium.annotation.SpigotOnly;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;





@SpigotOnly
public interface SpigotMessage extends Message {

    default void send(Player player) {
    }

    void send(CommandSender commandSender);



}
