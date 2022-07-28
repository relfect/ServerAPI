package net.larr4k.villenium.api.graphic;

import net.larr4k.villenium.annotation.SpigotOnly;
import org.bukkit.entity.Player;



@SpigotOnly
public interface PhantomEntityInteraction {

    void onLeftClick(Player player);

    void onRightClick(Player player);

}
