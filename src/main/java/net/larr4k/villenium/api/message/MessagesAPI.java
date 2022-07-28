package net.larr4k.villenium.api.message;

public interface MessagesAPI<M extends Message> {

    /**
     * Получить сообщение по ключу без аргументов форматирования.
     * @param messageKey ключ сообщения.
     * @return сообщение.
     */
    M get(Enum messageKey);

    /**
     * Получить сообщение по ключу с аргументами форматирования.
     * @param messageKey ключ сообщения.
     * @param args аргументы форматирования. Имейте ввиду, что аргументами могут являться и другие сообщения!
     * @return сообщение.
     */
    M get(Enum messageKey, Object... args);

    /**
     * Получить сообщение, состоящее из указанной строки.
     * @param message строка.
     * @return сообщение.
     */
    default M getExactMessage(String message) {
        return get(DefaultMessages.EXACT_MESSAGE, message);
    }

    /**
     * Получить сообщение, зависящее от указанного числа (день/дня/дней).
     * @param messageKey ключ сообщения.
     * @param value число.
     * @return сообщение.
     */
    M getPlural(Enum messageKey, int value);

    /**
     * Внутренний метод для сериализации сообщения в строку для дальнейшей отправки игроку и перехвату этого сообщения
     * слушателем пакетов, который будет осуществлять перезапись.
     * @param message сообщение.
     * @param references количество получателей конкретно этой сериализации сообщения.
     * @return сериализованное сообщение.
     */
    String serialize(M message, int references);

    /**
     * Внутренний метод для десериализации строки в сообщение.
     * @param serialized сериализованное сообщение.
     * @return сообщение.
     */
    M deserialize(String serialized);

    /**
     * Очистить кеш сериализации API.
     * Рекомендуется использовать после завершения игры, если сервер между ними не перезагружается.
     */
    void freeCache();

}
