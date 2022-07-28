package net.larr4k.villenium.api.player;

import java.util.Collection;

public interface Registry<K, V> {

    /**
     * Получить объект из регистра, если он там есть, или загрузить его.
     * @param key ключ.
     * @return объект.
     */
    V get(K key);

    /**
     * Получить объект из регистра, если он там есть, иначе null.
     * Наличие объекта в регистре не гарантирует каких-то свойств объекта (например, это не гарантирует онлайн игрока).
     * @param key ключ.
     * @return объект или null.
     */
    V getIfPresent(K key);

    /**
     * Инвалидировать объект из регистра. Если объект по ключу в регистре был, возвращает его.
     * @param key ключ.
     * @return объект или null.
     */
    V invalidate(K key);

    /**
     * Получить все объекты, которые есть в регистре.
     * @return коллекцию объектов.
     */
    Collection<V> getAllPresent();

    /**
     * Получить все объекты по заданным ключам. Если всех или некоторых из них в регистре нет, загрузить их.
     * @param keys ключи.
     * @return коллекцию объектов.
     */
    Collection<V> getAll(Collection<K> keys);

    /**
     * Получить все объекты по заданным ключам, которые есть в регистре.
     * @param keys ключи.
     * @return коллекцию объектов.
     */
    Collection<V> getAllIfPresent(Collection<K> keys);

}

