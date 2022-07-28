package net.larr4k.villenium.api.graphic;

import net.larr4k.villenium.annotation.SpigotOnly;
import org.bukkit.inventory.ItemStack;



@SpigotOnly
public interface PhantomEquippableEntity extends PhantomEntity {

    /**
     * Получить предмет в руке.
     * @return предмет в руке.
     */
    ItemStack getHand();

    /**
     * Получить шлем.
     * @return шлем.
     */
    ItemStack getHelmet();

    /**
     * Получить нагрудник.
     * @return нагрудник.
     */
    ItemStack getChestplate();

    /**
     * Получить штаны.
     * @return штаны.
     */
    ItemStack getLeggings();

    /**
     * Получить ботинки.
     * @return ботинки.
     */
    ItemStack getBoots();

    /**
     * Установить предмет в руке.
     * @param hand предмет в руке.
     */
    void setHand(ItemStack hand);

    /**
     * Установить шлем.
     * @param helmet шлем.
     */
    void setHelmet(ItemStack helmet);

    /**
     * Установить нагрудник.
     * @param chestplate нагрудник.
     */
    void setChestplate(ItemStack chestplate);

    /**
     * Установить штаны.
     * @param leggings штаны.
     */
    void setLeggings(ItemStack leggings);

    /**
     * Установить ботинки.
     * @param boots ботинки.
     */
    void setBoots(ItemStack boots);

    /**
     * Убрать всю экипировку.
     */
    default void clearEquipment() {
        setHand(null);
        clearArmor();
    }

    /**
     * Убрать всю броню.
     */
    default void clearArmor() {
        setHelmet(null);
        setChestplate(null);
        setLeggings(null);
        setBoots(null);
    }

}
