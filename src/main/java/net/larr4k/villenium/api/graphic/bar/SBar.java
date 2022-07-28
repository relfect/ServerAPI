package net.larr4k.villenium.api.graphic.bar;

import net.larr4k.villenium.annotation.SpigotOnly;
import net.larr4k.villenium.api.message.Message;
import net.larr4k.villenium.utility.type.Type;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;



@SpigotOnly
public interface SBar {

    /**
     * @see SBar#createDefaultBar(Message, BarColor, BarStyle, BarFlag...)
     */
    default SBossBar createDefaultBar(Enum title, BarColor color, Object... args) {
        return createDefaultBar(Type.getMESSAGES_API().get(title, args), color, BarStyle.SOLID);
    }

    /**
     * @see SBar#createDefaultBar(Message, BarColor, BarStyle, BarFlag...)
     */
    default SBossBar createDefaultBar(Enum title, BarFlag... flags) {
        return createDefaultBar(title, BarColor.PURPLE, BarStyle.SOLID, flags);
    }

    /**
     * @see SBar#createDefaultBar(Message, BarColor, BarStyle, BarFlag...)
     */
    default SBossBar createDefaultBar(Enum title, BarStyle style, BarFlag... flags) {
        return createDefaultBar(title, BarColor.PURPLE, style, flags);
    }

    /**
     * @see SBar#createDefaultBar(Message, BarColor, BarStyle, BarFlag...)
     */
    default SBossBar createDefaultBar(Enum title, BarColor color, BarFlag... flags) {
        return createDefaultBar(title, color, BarStyle.SOLID, flags);
    }

    /**
     * @see SBar#createDefaultBar(Message, BarColor, BarStyle, BarFlag...)
     */
    default SBossBar createDefaultBar(String title, BarColor color, BarFlag... flags) {
        return createDefaultBar(title, color, BarStyle.SOLID, flags);
    }

    /**
     * @see SBar#createDefaultBar(Message, BarColor, BarStyle, BarFlag...)
     */
    default SBossBar createDefaultBar(Message title, BarColor color, BarFlag... flags) {
        return createDefaultBar(title, color, BarStyle.SOLID, flags);
    }

    /**
     * @see SBar#createDefaultBar(Message, BarColor, BarStyle, BarFlag...)
     */
    default SBossBar createDefaultBar(Enum title, BarColor color, BarStyle style, BarFlag... flags) {
        return createDefaultBar(Type.getMESSAGES_API().get(title), color, style, flags);
    }

    /**
     * @see SBar#createDefaultBar(Message, BarColor, BarStyle, BarFlag...)
     */
    default SBossBar createDefaultBar(String title, BarColor color, BarStyle style, BarFlag... flags) {
        return createDefaultBar(Type.getMESSAGES_API().getExactMessage(title), color, style, flags);
    }

    /**
     * Создать и получить обычный босс-бар с собственными настройками.
     *
     * @param title текст, который будет находиться над баром. Все & будут заменены на цветовые коды.
     * @param color цвет бара.
     * @param style стиль деления бара.
     * @param flags флаги, которые будут применены к бару.
     * @return мапу с барами для всех языков.
     */
    SBossBar createDefaultBar(Message title, BarColor color, BarStyle style, BarFlag... flags);

    /**
     * @see SBar#createExpiredBar(Message, int, BarColor, BarStyle, BarFlag...)
     */
    default SBossBar createExpiredBar(Enum title, int duration, BarColor color, Object... args) {
        return createExpiredBar(Type.getMESSAGES_API().get(title, args), duration, color, BarStyle.SOLID);
    }

    /**
     * Создать и получить босс-бар с стандартными настройками и заданными временем и именем,
     * который пропадет через определенное время.
     *
     * @param title    текст, который будет находиться над баром. Все & будут заменены на цветовые коды.
     * @param duration время жизни бара в секундах.
     * @param flags    флаги, которые будут применены к бару.
     * @return мапу с барами для всех языков.
     */
    default SBossBar createExpiredBar(Enum title, int duration, BarFlag... flags) {
        return createExpiredBar(title, duration, BarColor.PURPLE, BarStyle.SOLID, flags);
    }

    /**
     * Создать и получить босс-бар с собственными настройками,
     * который пропадет через определенное время.
     *
     * @param title    текст, который будет находиться над баром. Все & будут заменены на цветовые коды.
     * @param duration время жизни бара в секундах.
     * @param style    стиль деления бара.
     * @param flags    флаги, которые будут применены к бару.
     * @return мапу с барами для всех языков.
     */
    default SBossBar createExpiredBar(Enum title, int duration, BarStyle style, BarFlag... flags) {
        return createExpiredBar(title, duration, BarColor.PURPLE, style, flags);
    }

    /**
     * Создать и получить босс-бар с собственными настройками,
     * который пропадет через определенное время.
     *
     * @param title    текст, который будет находиться над баром. Все & будут заменены на цветовые коды.
     * @param duration время жизни бара в секундах.
     * @param color    цвет бара.
     * @param flags    флаги, которые будут применены к бару.
     * @return мапу с барами для всех языков.
     */
    default SBossBar createExpiredBar(Enum title, int duration, BarColor color, BarFlag... flags) {
        return createExpiredBar(title, duration, color, BarStyle.SOLID, flags);
    }

    /**
     * Создать и получить босс-бар с собственными настройками,
     * который пропадет через определенное время.
     *
     * @param title    текст, который будет находиться над баром. Все & будут заменены на цветовые коды.
     * @param duration время жизни бара в секундах.
     * @param color    цвет бара.
     * @param style    стиль деления бара.
     * @param flags    флаги, которые будут применены к бару.
     * @return мапу с барами для всех языков.
     */
    default SBossBar createExpiredBar(Enum title, int duration, BarColor color, BarStyle style, BarFlag... flags) {
        return createExpiredBar(Type.getMESSAGES_API().get(title), duration, color, style, flags);
    }

    /**
     * @see SBar#createExpiredBar(Message, int, BarColor, BarStyle, BarFlag...)
     */
    default SBossBar createExpiredBar(String title, int duration, BarColor color, BarStyle style, BarFlag... flags) {
        return createExpiredBar(Type.getMESSAGES_API().getExactMessage(title), duration, color, style, flags);
    }

    /**
     * Создать и получить босс-бар с собственными настройками,
     * который пропадет через определенное время.
     *
     * @param title    текст, который будет находиться над баром. Все & будут заменены на цветовые коды.
     * @param duration время жизни бара в секундах.
     * @param color    цвет бара.
     * @param style    стиль деления бара.
     * @param flags    флаги, которые будут применены к бару.
     * @return мапу с барами для всех языков.
     */
    SBossBar createExpiredBar(Message title, int duration, BarColor color, BarStyle style, BarFlag... flags);

}
