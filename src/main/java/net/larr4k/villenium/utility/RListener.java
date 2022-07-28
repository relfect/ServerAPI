package net.larr4k.villenium.utility;

import jdk.jfr.internal.Logger;
import lombok.Getter;
import net.larr4k.villenium.ApiPlugin;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;



public class RListener implements Listener {

    private final JavaPlugin plugin;

    @Getter
    private final String listenerName;

    @Getter
    private boolean registered = false;

    public RListener(JavaPlugin plugin) {
        this.plugin = plugin;
        this.listenerName = getClass().getSimpleName();
        register();
        ApiPlugin.getAllRListeners().add(this);
    }

    public RListener() {
        this(ApiPlugin.getInstance());
    }

    public void register() {
        Bukkit.getPluginManager().registerEvents(this, this.plugin);
        registered = true;
    }

    public void unregister() {
        HandlerList.unregisterAll(this);
        registered = false;
    }

}
