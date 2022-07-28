package net.larr4k.villenium.api.bar;

import lombok.Getter;
import net.larr4k.villenium.api.graphic.bar.SBossBar;
import net.larr4k.villenium.api.message.Language;
import net.larr4k.villenium.api.message.Message;
import net.larr4k.villenium.utility.ChatUtil;
import net.larr4k.villenium.utility.type.Type;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

@Getter
public class SBossBarImpl implements SBossBar {

    protected final Map<Language, BossBar> bossBarMap = new HashMap<>();

    public Supplier<Message> localizedTitle;
    private BarColor color;
    private BarStyle style;
    private double progress;

    public SBossBarImpl(Message title, BarColor color, BarStyle style, BarFlag[] flags) {
        this.localizedTitle = () -> title;
        this.color = color;
        this.style = style;
        this.progress = 1.0D;

        for (Language language : Language.VALUES) {
            bossBarMap.put(language, Bukkit.createBossBar(
                    ChatUtil.colorize(getTitle()),
                    color,
                    style,
                    flags
            ));
        }
    }

    @Override
    public void setTitle(Message title) {
        localizedTitle = () -> title;
        bossBarMap.forEach((language, bar) -> bar.setTitle(ChatUtil.colorize(getTitle())));
    }



    @Override
    public Message getLocalizedTitle() {
        return localizedTitle.get();
    }

    @Override
    public void setColor(BarColor barColor) {
        this.color = barColor;
        bossBarMap.values().forEach(bar -> bar.setColor(barColor));
    }

    @Override
    public void setStyle(BarStyle barStyle) {
        this.style = barStyle;
        bossBarMap.values().forEach(bar -> bar.setStyle(barStyle));
    }

    @Override
    public void removeFlag(BarFlag barFlag) {
        bossBarMap.values().forEach(bar -> bar.removeFlag(barFlag));
    }

    @Override
    public void addFlag(BarFlag barFlag) {
        bossBarMap.values().forEach(bar -> bar.addFlag(barFlag));
    }

    @Override
    public boolean hasFlag(BarFlag barFlag) {
        return bossBarMap.values().stream().allMatch(bar -> bar.hasFlag(barFlag));
    }

    @Override
    public void setProgress(double progress) {
        this.progress = progress;
        bossBarMap.values().forEach(bar -> bar.setProgress(progress));
    }

    @Override
    public void addPlayer(Player player) {
        addPlayer(Type.getPLAYER_REGISTRY().get(player.getName()));
    }

    @Override
    public void removePlayer(Player player) {
        removePlayer(Type.getPLAYER_REGISTRY().get(player.getName()));
    }

    @Override
    public void removeAll() {
        bossBarMap.values().forEach(BossBar::removeAll);
    }

    @Override
    public void setVisible(boolean b) {
        bossBarMap.values().forEach(bar -> bar.setVisible(b));
    }

    @Override
    public boolean isVisible() {
        return bossBarMap.values().stream().allMatch(BossBar::isVisible);
    }

    @Override
    public void show() {
        broadcast();
    }

    @Override
    public void hide() {
        removeAll();
    }
}
