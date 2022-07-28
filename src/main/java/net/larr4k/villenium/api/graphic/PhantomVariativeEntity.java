package net.larr4k.villenium.api.graphic;

import net.larr4k.villenium.annotation.SpigotOnly;
import org.bukkit.entity.Player;


import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Function;


@SpigotOnly
public interface PhantomVariativeEntity extends PhantomEntity {

    /**
     * Создать новый вариант этой сущности.
     * @param variant название варианта.
     * @throws IllegalArgumentException если название варианта null.
     * @return true, если варианта с таким названием не существовало и он был создан; false иначе.
     */
    boolean createVariant(String variant) throws IllegalArgumentException;

    /**
     * Проверка существования варианта этой сущности указанного названия.
     * @param variant название варианта.
     * @return true/false.
     */
    boolean doesVariantExist(String variant);

    /**
     * Удаление варианта этой сущности.
     * @param variant название варианта.
     * @return true, если варианта существовал; false иначе.
     */
    boolean removeVariant(String variant);

    /**
     * Получение названий всех существующих вариантов этой сущности.
     * @return коллекция названий всех существующих вариантов этой сущности.
     */
    Collection<String> getAllVariants();


    /**
     * Получить вариант указанного игрока.
     * @param player игрок.
     * @return название варианта.
     */
    default String getPlayerVariant(Player player) {
        return getPlayerVariant(player.getName());
    }

    /**
     * Получить вариант игрока с указанным ником
     * @param playerName ник игрока.
     * @return название варианта.
     */
    String getPlayerVariant(String playerName);



    /**
     * Установить вариант этой сущности для указанного игрока на переданный.
     * @param player игрок.
     * @param variant название варианта или null, чтобы обнулить вариант игрока.
     * @throws IllegalArgumentException если название варианта null или такого варианта не существует.
     */
    default void setPlayerVariant(Player player, String variant) throws IllegalArgumentException {
        setPlayerVariant(player.getName(), variant);
    }

    /**
     * Установить вариант этой сущности для игрока с указанным ником на переданный.
     * @param playerName ник игрока.
     * @param variant название варианта или null, чтобы обнулить вариант игрока.
     * @throws IllegalArgumentException если название варианта null или такого варианта не существует.
     */
    void setPlayerVariant(String playerName, String variant) throws IllegalArgumentException;

    /**
     * @see PhantomVariativeEntity#setVariantSelector(Function)
     * Для каждого игрока создается собственный вариант сущности.
     * @param setupper консумер, который должен настраивать новый вариант в случае создания оного.
     *                 На вход получает название нового варианта этой сущности.
     */
    default void setPersonalVariantSelector(Consumer<String> setupper) {
        setVariantSelector(playerName -> {
            if (createVariant(playerName)) {
                setupper.accept(playerName);
            }
            return playerName;
        });
        setWhetherVariantsEternal(false);
    }

    /**
     * Установить селектор варианта этой сущности для игрока по его нику, когда эта сущность ему впервые отображается.
     * Если не использовать этот метод, сущность не будет отображаться для игроков, у которых вариант не проставлен вручную.
     * @param playerNameToVariantSelector преобразователь ника игрока в название варианта данной сущности.
     */
    void setVariantSelector(Function<String, String> playerNameToVariantSelector);

    /**
     * Изначально параметр установлен в true.
     * Если установить в false, информация о вариантах этой сущности будут автоматически удаляться, когда в этих
     * вариантах не будет оставаться ни одного игрока онлайн.
     * @param value true/false.
     */
    void setWhetherVariantsEternal(boolean value);

}
