package net.larr4k.villenium.utility.type;

import lombok.Getter;
import net.larr4k.villenium.api.message.MessagesAPI;
import net.larr4k.villenium.api.player.PlayerRegistry;
import net.larr4k.villenium.db.Database;
import net.larr4k.villenium.utility.TimeUtil;
import org.bukkit.entity.Player;

public class Type {
    @Getter
    private static Spigot SPIGOT;
    @Getter
    private static Database DATABASE;
    @Getter
    private static MessagesAPI<?> MESSAGES_API;
    @Getter
    private static TimeUtil TIME_UTIL = new TimeUtil() {};
    @Getter
    private static PlayerRegistry<Player> PLAYER_REGISTRY;

}
