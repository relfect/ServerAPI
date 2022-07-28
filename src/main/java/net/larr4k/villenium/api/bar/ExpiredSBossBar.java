package net.larr4k.villenium.api.bar;

import lombok.Getter;
import net.larr4k.villenium.api.message.Message;
import net.larr4k.villenium.utility.ChatUtil;
import net.larr4k.villenium.utility.type.Type;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;



public class ExpiredSBossBar extends SBossBarImpl {

    @Getter
    private final int duration;
    private int timer;

    public ExpiredSBossBar(Message title, int duration, BarColor color, BarStyle style, BarFlag[] flags) {
        super(title, color, style, flags);
        this.duration = timer = duration;

        setTitle(title);
    }

    @Override
    public void setTitle(Message title) {
        this.localizedTitle = () -> title;
        bossBarMap.forEach((language, bar) -> {
            String name = bar.getTitle();
            String time = Type.getTIME_UTIL().getDurationMessage(0, timer * 1000).toString();
            bar.setTitle(ChatUtil.colorize(name.replace("{time}", time)));
        });
    }

    public boolean updateDuration() {
        timer--;
        if (timer <= 0) {
            setProgress(0D);
            hide();
            return true;
        } else {
            double v = (double) timer / duration;
            setProgress(v);
            setTitle(getLocalizedTitle());
            return false;
        }
    }
}
