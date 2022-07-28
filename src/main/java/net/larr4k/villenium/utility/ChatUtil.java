package net.larr4k.villenium.utility;

import lombok.experimental.UtilityClass;
import org.bukkit.ChatColor;


@UtilityClass
public class ChatUtil {

    /**
     * Получить правильное слагательное наклонение в зависимости от количества.
     * Например, 2 -> убийства; 12 -> убийств; 21 -> убийство.
     *
     * @param amount количество.
     * @param uno    наклонение для одного (убийство).
     * @param duo    наклонение для нескольких (убийства).
     * @param many   наклонение для многих (убийств).
     * @return правильное слагательное наклонение в зависимости от количества.
     */
    public String transformByCount(int amount, String uno, String duo, String many) {
        int mod10 = amount % 10, mod100 = amount % 100;
        if (mod10 == 1 && mod100 != 11) {
            return uno;
        }
        if (mod10 >= 2 && mod10 <= 4 && (mod100 < 10 || mod100 > 20)) {
            return duo;
        }
        return many;
    }

    /**
     * Отформатировать сообщение и преобразовать & в цветовые коды.
     *
     * @param message сообщение.
     * @param args    аргументы форматирования.
     * @return отформатированное преобразованное сообщение.
     */
    public String colorize(String message, Object... args) {
        return ChatColor.translateAlternateColorCodes('&', String.format(message, args));

    }

    /**
     * Добавить к сообщению префикс в нашем стиле.
     * Все & будут заменены на цветовые коды.
     *
     * @param prefix  префикс.
     * @param message сообщение.
     * @return сообщение с префиксом.
     */
    public String prefixed(String prefix, String message) {
        return colorize("&e&l%s &8> %s", prefix, message);
    }

    /**
     * Отформатировать сообщение и добавить к нему префикс в нашем стиле.
     * Все & будут заменены на цветовые коды.
     *
     * @param prefix      префикс.
     * @param message     сообщение.
     * @param messageArgs аргументы формативарония сообщения.
     * @return отформатированное сообщение с префиксом.
     */
    public String prefixed(String prefix, String message, Object... messageArgs) {
        return prefixed(prefix, String.format(message, messageArgs));
    }
}
