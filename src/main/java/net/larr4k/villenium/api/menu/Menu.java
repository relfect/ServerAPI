package net.larr4k.villenium.api.menu;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;


public interface Menu {

    /**
     * Получить имя этого меню: то, что отображается игрокам в названии инвентаря.
     *
     * @return имя этого меню.
     */
    String getName();

    /**
     * Открыть меню для указанного игрока.
     *
     * @param player игрок.
     */
    void open(Player player);

    /**
     * Добавить кликабельный предмет в меню по указанному слоту.
     * Нумерация слотов идет с 0, как в обычном инвентаре майнкрафта.
     *
     * @param item кликабельный предмет.
     * @param slot номер слота.
     * @return номер слота, по которому предмет был добавлен.
     */
    int addItem(MenuItem item, int slot);

    /**
     * Добавить кликабельный предмет в меню на первый свободный слот.
     * Нумерация слотов идет с 0, как в обычном инвентаре майнкрафта.
     *
     * @param item кликабельный предмет.
     * @return номер слота, по которому предмет был добавлен.
     * @throws IllegalStateException если в меню нет свободных мест.
     */
    int addItem(MenuItem item) throws IllegalStateException;

    /**
     * Добавить кликабельный предмет в меню по указанному слоту.
     * Номер слота задается номером строки и столбца, которые нумеруется с 1.
     * Нумерация слотов идет с 0, как в обычном инвентаре майнкрафта.
     *
     * @param item   кликабельный предмет.
     * @param row    номер строки (нумеруется с 1).
     * @param column номер столбца (нумеруется с 1).
     * @return номер слота, по которому предмет был добавлен.
     */
    default int addItem(MenuItem item, int row, int column) {
        return addItem(item, getSlotByRowAndColumn(row, column));
    }

    /**
     * Получить кликабельный предмет, который находится в данном меню
     * на указанном слоте.
     * Нумерация слотов идет с 0, как в обычном инвентаре майнкрафта.
     *
     * @param slot номер слота.
     * @return null, если по указанному слоту нет предмета; иначе сам предмет.
     */
    MenuItem getItem(int slot);

    /**
     * Получить кликабельный предмет, который находится в данном меню
     * на указанном слоте.
     * Номер слота задается номером строки и столбца, которые нумеруется с 1.
     * Нумерация слотов идет с 0, как в обычном инвентаре майнкрафта.
     *
     * @param row    номер строки (нумеруется с 1).
     * @param column номер столбца (нумеруется с 1).
     * @return null, если по указанному слоту нет предмета; иначе сам предмет.
     */
    default MenuItem getItem(int row, int column) {
        return getItem(getSlotByRowAndColumn(row, column));
    }

    /**
     * Обновить меню для всех игроков.
     */
    void update();

    /**
     * Обновить иконку кликабельного предмета этого меню, расположенного
     * по указанному слоту, на новую переданную.
     * Нумерация слотов идет с 0, как в обычном инвентаре майнкрафта.
     *
     * @param slot    номер слота.
     * @param newIcon новая иконка.
     */
    void updateItemIcon(int slot, ItemStack newIcon);

    /**
     * Обновить иконку кликабельного предмета этого меню, расположенного
     * по указанному слоту, на новую переданную.
     * Номер слота задается номером строки и столбца, которые нумеруется с 1.
     * Нумерация слотов идет с 0, как в обычном инвентаре майнкрафта.
     *
     * @param row     номер строки (нумеруется с 1).
     * @param column  номер столбца (нумеруется с 1).
     * @param newIcon новая иконка.
     */
    default void updateItemIcon(int row, int column, ItemStack newIcon) {
        updateItemIcon(getSlotByRowAndColumn(row, column), newIcon);
    }

    /**
     * Обновить иконку кликабельного предмета этого меню, расположенного
     * по указанному слоту, на новую, уже установленную в самом предмете.
     * Нумерация слотов идет с 0, как в обычном инвентаре майнкрафта.
     *
     * @param slot номер слота.
     * @throws IllegalArgumentException если передан номер слота, по которому
     *                                  в данном меню нет кликабельного предмета.
     */
    default void updateItemIcon(int slot) throws IllegalArgumentException {
        MenuItem item = getItem(slot);
        if (item == null) {
            throw new IllegalArgumentException("There is no menu item at slot " + slot);
        }
        updateItemIcon(slot, item.getIcon());
    }

    /**
     * Обновить иконку кликабельного предмета этого меню, расположенного
     * по указанному слоту, на новую, уже установленную в самом предмете.
     * Номер слота задается номером строки и столбца, которые нумеруется с 1.
     * Нумерация слотов идет с 0, как в обычном инвентаре майнкрафта.
     *
     * @param row    номер строки (нумеруется с 1).
     * @param column номер столбца (нумеруется с 1).
     * @throws IllegalArgumentException если передан номер слота, по которому
     *                                  в данном меню нет кликабельного предмета.
     */
    default void updateItemIcon(int row, int column) {
        updateItemIcon(getSlotByRowAndColumn(row, column));
    }

    /**
     * Получить номер слота по строке и столбцу.
     * Нумерация слотов идет с 0, как в обычном инвентаре майнкрафта.
     *
     * @param row    строка (нумерация с 1).
     * @param column столбец (нумерация с 1).
     * @return номер слота.
     */
    default int getSlotByRowAndColumn(int row, int column) {
        return (row - 1) * 9 + column - 1;
    }
}
