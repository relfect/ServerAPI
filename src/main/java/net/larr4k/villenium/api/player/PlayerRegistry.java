package net.larr4k.villenium.api.player;

import com.google.common.util.concurrent.ListenableFuture;
import net.larr4k.villenium.annotation.SpigotOnly;
import org.bukkit.entity.Player;

import java.util.List;

public interface PlayerRegistry<P extends Player> extends Registry<String, P> {

    /**
     * Получить и предзагрузить для указанных игроков все секции, необходимые для спигота.
     * @param playerNames ники игроков.
     * @return игроки.
     */
    @SpigotOnly
    List<P> loadPlayersForSpigot(List<String> playerNames);


    /**
     * Получить и предзагрузить для указанных игроков все секции, необходимые для их успешного отображения в топе.
     * Сюда входят секции, которые как-либо влияют на отображаемые имена игроков.
     * @param playerNames ники игроков.
     * @return игроки.
     */
    @SpigotOnly
    List<P> loadPlayersForMinigameTop(List<String> playerNames);

    /**
     * @see PlayerRegistry#loadPlayersForMinigameTop(List)
     * @param playerNames ники игроков.
     * @return фьючер на игроков.
     */
    @SpigotOnly
    ListenableFuture<List<P>> loadPlayersForMinigameTopAsync(List<String> playerNames);



}