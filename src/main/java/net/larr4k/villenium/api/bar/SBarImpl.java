package net.larr4k.villenium.api.bar;

import lombok.extern.slf4j.Slf4j;
import net.larr4k.villenium.api.graphic.bar.SBar;
import net.larr4k.villenium.api.graphic.bar.SBossBar;
import net.larr4k.villenium.api.message.Message;
import net.larr4k.villenium.utility.Task;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;


import java.util.HashSet;
import java.util.Set;


@Slf4j
public class SBarImpl implements SBar {

    private final Set<ExpiredSBossBar> bars = new HashSet<>();

    private boolean started = false;

    @Override
    public SBossBar createDefaultBar(Message title, BarColor color, BarStyle style, BarFlag... flags) {
        return new SBossBarImpl(title, color, style, flags);
    }

    @Override
    public SBossBar createExpiredBar(Message title, int duration, BarColor color, BarStyle style, BarFlag... flags) {
        ExpiredSBossBar bossBar = new ExpiredSBossBar(title, duration, color, style, flags);
        bars.add(bossBar);
        start();
        return bossBar;
    }

    private void start() {
        if (started) {
            return;
        }
        started = true;
        Task.schedule(() -> new HashSet<>(bars).forEach(bar -> {
            if (bar.updateDuration()) {
                bars.remove(bar);
            }
        }), 0L, 20L);
    }
}