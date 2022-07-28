package net.larr4k.villenium.api.menu;

import net.larr4k.villenium.utility.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.List;


public abstract class MenuButton extends MenuItem {

    /**
     * Поддержка базового конструктора.
     *
     * @param itemStack иконка кнопки для меню.
     */
    public MenuButton(ItemStack itemStack) {
        super(itemStack);
    }

    /**
     * Создать кнопку для меню с указанным материалом и названием.
     *
     * @param icon материал.
     * @param name названием (цветовые коды будут заменены).
     */
    public MenuButton(Material icon, String name) {
        super(ItemBuilder.fromMaterial(icon)
                .withItemMeta().setName("&a" + name)
                .setItemFlags(ItemFlag.values())
                .and().build());
    }

    /**
     * Создать кнопку для меню с указанным материалом, названием и описанием.
     *
     * @param icon        материал.
     * @param name        названием (цветовые коды будут заменены).
     * @param description построчное описание (цветовые коды будут заменены).
     */
    public MenuButton(Material icon, String name, List<String> description) {
        super(ItemBuilder.fromMaterial(icon)
                .withItemMeta().setName("&a" + name).setLore(description)
                .setItemFlags(ItemFlag.values())
                .and().build());
    }

    /**
     * Создать кнопку для меню с указанным материалом, датой материала, названием и описанием.
     *
     * @param icon        материал.
     * @param data        дата материала (например, 0-15 у шерсти).
     * @param name        названием (цветовые коды будут заменены).
     * @param description построчное описание (цветовые коды будут заменены).
     */
    public MenuButton(Material icon, int data, String name, List<String> description) {
        super(ItemBuilder.fromMaterial(icon).data(data)
                .withItemMeta().setName("&a" + name).setLore(description)
                .setItemFlags(ItemFlag.values())
                .and().build());
    }
}

