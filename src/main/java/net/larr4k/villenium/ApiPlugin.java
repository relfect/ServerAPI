package net.larr4k.villenium;

import lombok.Getter;
import net.larr4k.villenium.api.ApiManager;
import net.larr4k.villenium.utility.RListener;
import net.larr4k.villenium.utility.type.Type;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class ApiPlugin extends JavaPlugin {

    @Getter static ApiPlugin instance;
    @Getter
    private static boolean debug;

    @Getter
    private final static List<RListener> allRListeners = new ArrayList<>();

    @Override
    public void onEnable() {
       instance = this;
        ApiManager.init();

        Type.getSPIGOT().isStaging();
    }

    @Override
    public void onDisable() {

    }
}
