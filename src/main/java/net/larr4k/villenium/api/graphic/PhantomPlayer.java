package net.larr4k.villenium.api.graphic;

import net.larr4k.villenium.annotation.SpigotOnly;

import java.util.UUID;


@SpigotOnly
public interface PhantomPlayer extends PhantomEquippableEntity {

    /**
     * Получить UUID этого фантомного игрока.
     * @return UUID этого фантомного игрока.
     */
    UUID getUUID();

    /**
     * Получить имя этого фантомного игрока.
     * @return имя этого фантомного игрока.
     */
    String getName();

    /**
     * Получить ник скина этого фантомного игрока.
     * @return ник скина этого фантомного игрока.
     */
    String getSkinName();

    /**
     * Обновить профиль этого фантомного игрока.
     * @param name новый ник.
     * @param skinName новый ник скина. Регистр важен!
     */
    void updateProfile(String name, String skinName);

    /**
     * Обновить скин (и плащ) этого фантомного игрока.
     * @param skinName новый ник скина. Регистр важен!
     */
    default void setSkin(String skinName) {
        updateProfile(getName(), skinName);
    }

    /**
     * Обновить имя этого фантомного игрока.
     * @param name новый ник.
     */
    default void setName(String name) {
        updateProfile(name, name);
    }

}
