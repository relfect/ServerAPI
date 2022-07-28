package net.larr4k.villenium.api.menu;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;


public abstract class MenuItem {

    private ItemStack icon;

    /**
     * Создать кликабельный предмет для меню.
     *
     * @param icon отображаемая иконка.
     */
    public MenuItem(ItemStack icon) {
        this.icon = icon;
    }

    /**
     * Создать кликабельный предмет для меню без отображаемой иконки
     * (а на самом деле с иконкой в виде админиума без названия и описания).
     */
    public MenuItem() {
        this(new ItemStack(Material.BEDROCK));
    }

    /**
     * Действие, которое должно происходить при клике на этот предмет в меню.
     *
     * @param player    игрок, который кликнул.
     * @param clickType тип клика.
     * @param slot      слот, на котором этот предмет находится в меню.
     */
    public abstract void onClick(Player player, ClickType clickType, int slot);

    /**
     * Получить иконку этого предмета, которую можно изменять.
     *
     * @return иконка этого предмета, которую можно изменять.
     */
    public ItemStack getIconModifiable() {
        return this.icon;
    }

    /**
     * Получить иконку этого предмета, которую нельзя изменять.
     *
     * @return иконка этого предмета, которую нельзя изменять.
     */
    public ItemStack getIcon() {
        return getIconModifiable().clone();
    }

    /**
     * Установить этому предмету новую иконку.
     * Не забудьте обновить ее во всех меню, в которых находится этот предмет!
     *
     * @param icon новая иконка.
     */
    public void setIcon(ItemStack icon) {
        this.icon = icon.clone();
    }
}
