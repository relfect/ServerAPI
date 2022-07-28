package net.larr4k.villenium.api.hologram;

import com.comphenix.packetwrapper.WrapperPlayClientUseEntity;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.google.common.base.Preconditions;
import lombok.RequiredArgsConstructor;
import net.larr4k.villenium.api.graphic.*;
import net.larr4k.villenium.utility.Protos;
import net.larr4k.villenium.utility.Task;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;


import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


@SuppressWarnings("Duplicates")
public class PhantomEntityFactoryImpl implements PhantomEntityFactory {

    private final Map<Integer, PhantomEntityImpl> entities = new ConcurrentHashMap<>();
    private final List<PhantomAction> actions = new ArrayList<>();

    private boolean initialized;

    @Override
    public PhantomEntity getById(int id) {
        return this.entities.get(id);
    }

    @Override
    public PhantomPlayer createPlayer(UUID uuid, String name, String skinName) {
        initialize();
        return new PhantomPlayerImpl(getRandomIdForPhantomEntity(), uuid, name, skinName);
    }

    @Override
    public PhantomEntity createEntity(int id, EntityType type) {
        initialize();
        Preconditions.checkArgument(type != EntityType.PLAYER, "You can't use that method for player. Use createPlayer() instead.");
        return new PhantomEntityWrapper(id, type);
    }

    @Override
    public PhantomEquippableEntity createEquippableEntity(int id, EntityType type) throws IllegalArgumentException {
        initialize();
        Preconditions.checkArgument(type != EntityType.PLAYER, "You can't use that method for player. Use createPlayer() instead.");
        //TODO: check
        return new PhantomEquippableEntityWrapper(id, type);
    }

    @Override
    public PhantomHologram createHologram(int id, String text) {
        initialize();
        return new PhantomHologramImpl(id, text);
    }

    @Override
    public PhantomVariativeHologram createVariativeHologram(int id) {
        initialize();
        return new PhantomVariativeHologramImpl(id);
    }



    void registerEntity(PhantomEntityImpl entity) {
        this.entities.put(entity.getID(), entity);
    }

    void unregisterEntity(PhantomEntityImpl entity) {
        this.entities.remove(entity.getID());
    }

    private void initialize() {
        if (this.initialized) {
            return;
        }
        this.initialized = true;
        Task.schedule(() -> this.entities.values().forEach(PhantomEntityImpl::tick), 0L, 30L);
        Protos.addReceivingListener(WrapperPlayClientUseEntity.class, (p, wrapper) -> {

            PhantomEntityImpl entity = this.entities.get(wrapper.getTargetID());
            if (entity == null) {
                return false;
            }
            if (entity.getInteraction() == null) {
                return true;
            }

            return true;
        });
        Task.schedule(() -> {
            synchronized (this.actions) {
                this.actions.forEach(trigger -> {
                    if (!trigger.entity.spawned) {
                        return;
                    }
                    PhantomEntityInteraction interaction = trigger.entity.getInteraction();
                    if (!trigger.player.isOnline() || interaction == null) {
                        return;
                    }
                    if (trigger.action == EnumWrappers.EntityUseAction.ATTACK) {
                        interaction.onLeftClick(trigger.player);
                    } else if (trigger.action == EnumWrappers.EntityUseAction.INTERACT ||
                            trigger.action == EnumWrappers.EntityUseAction.INTERACT_AT && trigger.entity instanceof PhantomHologram) {
                        interaction.onRightClick(trigger.player);
                    }
                });
                this.actions.clear();
            }
        }, 0L, 1L);
    }

    @RequiredArgsConstructor
    private static class PhantomAction {

        private final Player player;
        private final PhantomEntityImpl entity;
        private final EnumWrappers.EntityUseAction action;

    }

}
