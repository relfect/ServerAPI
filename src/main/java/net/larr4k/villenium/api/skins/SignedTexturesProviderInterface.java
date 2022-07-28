package net.larr4k.villenium.api.skins;

import com.destroystokyo.paper.profile.ProfileProperty;

public interface SignedTexturesProviderInterface {

    /**
     * Получить свойства скина игрока. Обязательно должны содержать значение и сигнатуру.
     *
     * @param playerName имя игрока
     * @return свойства скина указано игрока
     */
    ProfileProperty getTexturesProperty(String playerName);
}