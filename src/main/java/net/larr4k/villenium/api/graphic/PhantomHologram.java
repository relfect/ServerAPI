package net.larr4k.villenium.api.graphic;

import net.larr4k.villenium.annotation.SpigotOnly;

@SpigotOnly
public interface PhantomHologram extends PhantomEntity {

    /**
     * Получить текст голограммы.
     * @return текст голограммы.
     */
    String getText();

    /**
     * Установить текст голограммы (все & будут заменены на цветовые коды).
     * @param text текст голограммы.
     */
    void setText(String text);

}