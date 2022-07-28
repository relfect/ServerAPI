package net.larr4k.villenium.api.graphic;

import net.larr4k.villenium.annotation.SpigotOnly;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;


import java.util.Collection;


@SpigotOnly
public interface PhantomEntity {

    /**
     * Получить ID этой сущности.
     * @return ID сущности.
     */
    int getID();

    /**
     * Получить мир этой сущности.
     * @return мир этой сущности.
     */
    World getWorld();

    /**
     * Получить локацию этой сущности.
     * @return локация этой сущности.
     */
    Location getLocation();

    /**
     * Получить тип этой сущности.
     * @return тип этой сущности.
     */
    EntityType getType();

    /**
     * Получить игроков, которым видна эта сущность.
     * @return игроки, которым видна эта сущность.
     */
    Collection<Player> getViewers();

    /**
     * Показать эту сущность данному игроку.
     * @param player игрок.
     * @throws IllegalArgumentException если сущность уже видна этому игроку.
     * @throws IllegalStateException если сущность не заспавнена.
     */
    void show(Player player) throws IllegalArgumentException, IllegalStateException;

    /**
     * Спрятать эту сущность от данного игрока.
     * @param player игрок.
     * @throws IllegalArgumentException если сущность и так не видна для этого игрока.
     * @throws IllegalStateException если сущность не заспавнена.
     */
    void hide(Player player) throws IllegalArgumentException, IllegalStateException;

    /**
     * Проверить, видна ли эта сущность данному игроку.
     * @param player игрок.
     * @return true/false.
     */
    boolean isVisibleFor(Player player);

    /**
     * Заспавнить сущность в мире.
     * @param autoVisible если установлено в true, методы show() и hide()
     *                    становятся бессмысленны, а сущность будет автоматически
     *                    показываться игрокам, в зоне видимости которых находится,
     *                    и скрываться от более дальних игроков.
     * @throws IllegalStateException если сущность уже заспавнена.
     */
    void spawn(boolean autoVisible) throws IllegalStateException;

    /**
     * Задеспавнить сущность из мира.
     * @throws IllegalStateException если сущность и так не заспавнена.
     */
    void despawn() throws IllegalStateException;

    /**
     * Проверить, заспавнена ли сущность.
     * @return true/false.
     */
    boolean isSpawned();

    /**
     * Полностью забыть об этой сущности, без возможности вновь ее заспавнить.
     */
    void invalidate();

    /**
     * Повернуть эту сущность лицом к указанной локации.
     * @param location локация.
     * @throws IllegalStateException если сущность не заспавнена.
     */
    void lookAt(Location location) throws IllegalStateException;

    /**
     * Повернуть эту сущность лицом к указанной сущности.
     * @param entity сущность.
     * @throws IllegalStateException если сущность не заспавнена.
     */
    default void lookAt(Entity entity) throws IllegalStateException {
        lookAt(entity.getLocation());
    }

    /**
     * Повернуть эту сущность лицом к указанной сущности.
     * @param entity сущность.
     * @throws IllegalStateException если сущность не заспавнена.
     */
    default void lookAt(PhantomEntity entity) throws IllegalStateException {
        lookAt(entity.getLocation());
    }

    /**
     * Сместить эту сущность без поворота тела.
     * @param dx смещение по иксу.
     * @param dy смещение по игреку.
     * @param dz смещение по зету.
     * @throws IllegalStateException если сущность не заспавнена.
     */
    void moveWithoutBodyRotation(double dx, double dy, double dz) throws IllegalStateException;

    /**
     * Сместить эту сущность с поворотом тела.
     * @param dx смещение по иксу.
     * @param dy смещение по игреку.
     * @param dz смещение по зету.
     * @throws IllegalStateException если сущность не заспавнена.
     */
    void move(double dx, double dy, double dz) throws IllegalStateException;

    /**
     * @see PhantomEntity#move(double, double, double)
     * @param dx смещение по иксу.
     * @param dy смещение по игреку.
     * @param dz смещение по зету.
     * @throws IllegalStateException если сущность не заспавнена.
     */
    default void moveWithBodyRotation(double dx, double dy, double dz) throws IllegalStateException {
        move(dx, dy, dz);
    }

    /**
     * Телепортировать эту сущность на указанные координаты (локацию).
     * @param location локация.
     */
    void teleport(Location location);

    /**
     * @see PhantomEntity#teleport(Location)
     */
    default void setLocation(Location location) {
        teleport(location);
    }

    /**
     * Получить интерфейс для работы с анимациями данной сущности.
     * @return реализацию PhantomEntityAnimations.
     * @throws IllegalStateException если сущность не заспавнена.
     */
    PhantomEntityAnimations getAnimations() throws IllegalStateException;

    /**
     * Получить интерфейс взаимодействия с данной сущностью.
     * @return реализацию PhantomEntityInteraction или null, если не установлено.
     */
    PhantomEntityInteraction getInteraction();

    /**
     * Установить взаимодействия с данной сущностью.
     * @param interaction взаимодействия с данной сущностью или null.
     */
    void setInteraction(PhantomEntityInteraction interaction);

}
