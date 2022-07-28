package net.larr4k.villenium.api.menu;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * Пустая кнопка для меню, то есть при клике на которую ничего не происходит.
 */
public class MenuEmptyButton extends MenuButton {

    /**
     * Поддержка базового конструктора.
     *
     * @param itemStack иконка кнопки для меню.
     */
    public MenuEmptyButton(ItemStack itemStack) {
        super(itemStack);
    }

    /**
     * Создать пустую кнопку для меню с указанным материалом и названием.
     *
     * @param icon материал.
     * @param name названием (цветовые коды будут заменены).
     */
    public MenuEmptyButton(Material icon, String name) {
        super(icon, name);
    }

    /**
     * Создать пустую кнопку для меню с указанным материалом, названием и описанием.
     *
     * @param icon        материал.
     * @param name        названием (цветовые коды будут заменены).
     * @param description построчное описание (цветовые коды будут заменены).
     */
    public MenuEmptyButton(Material icon, String name, List<String> description) {
        super(icon, name, description);
    }

    /**
     * Создать пустую кнопку для меню с указанным материалом, датой материала, названием и описанием.
     *
     * @param icon        материал.
     * @param data        дата материала (например, 0-15 у шерсти).
     * @param name        названием (цветовые коды будут заменены).
     * @param description построчное описание (цветовые коды будут заменены).
     */
    public MenuEmptyButton(Material icon, int data, String name, List<String> description) {
        super(icon, data, name, description);
    }

    @Override
    public void onClick(Player player, ClickType clickType, int slot) {

    }
}
