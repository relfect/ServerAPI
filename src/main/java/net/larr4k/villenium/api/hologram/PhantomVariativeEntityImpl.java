package net.larr4k.villenium.api.hologram;

import com.comphenix.packetwrapper.AbstractPacket;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import com.google.common.base.Preconditions;
import lombok.val;
import net.larr4k.villenium.api.graphic.PhantomVariativeEntity;
import net.larr4k.villenium.api.hologram.PhantomEntityImpl;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;


import java.util.*;
import java.util.function.Function;

public abstract class PhantomVariativeEntityImpl extends PhantomEntityImpl implements PhantomVariativeEntity {

    private final Map<String, Variant> variants = new HashMap<>();
    private final Map<String, String> playersToVariants = new HashMap<>();
    private Function<String, String> variantSelector = null;
    private boolean variantsEternal = true;

    PhantomVariativeEntityImpl(int id, EntityType entityType) {
        super(id, entityType);
    }

    @Override
    protected Collection<AbstractPacket> getMetadata() {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void sendPacket(AbstractPacket packet) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void show(Player player, boolean addToCache) {
        val metadata = getMetadata(player.getName());
        if (metadata == null) {
            return;
        }
        if (!player.isOnline()) {
            return;
        }
        validate();
        Preconditions.checkArgument(!super.viewers.contains(player), "This entity is already visible for given player (%s)", player.getName());
        metadata.forEach(wrapper -> wrapper.sendPacket(player));
        getRotationPacket().sendPacket(player);
        super.viewers.add(player);
        if (addToCache) {
            super.viewersCache.add(player);
        }
    }

    @Override
    protected void hide(Player player, boolean removeFromCache) {
        super.hide(player, removeFromCache);
        String variantName = this.playersToVariants.remove(player.getName().toLowerCase());
        if (variantName != null) {
            Variant variant = getVariant(variantName);
            if (variant != null) {
                variant.players.remove(player.getName().toLowerCase());
                if (!this.variantsEternal && variant.players.isEmpty()) {
                    removeVariant(variantName);
                }
            }
        }
    }

    @Override
    public void despawn() {
        super.despawn();
        this.playersToVariants.clear();
        this.variants.values().forEach(variant -> variant.players.clear());
    }

    protected Collection<AbstractPacket> getMetadata(String playerName) {
        String variantName = this.playersToVariants.get(playerName.toLowerCase());
        if (variantName == null) {
            if (this.variantSelector == null) {
                return null;
            }
            variantName = this.variantSelector.apply(playerName);
            if (variantName == null) {
                return null;
            }
            setPlayerVariant(playerName, variantName);
        }
        Variant variant = this.variants.get(variantName);
        Preconditions.checkState(variant != null, "Variant of name " + variantName + " does not exist");
        return variant.getMetadata();
    }

    @Override
    public boolean createVariant(String variant) throws IllegalArgumentException {
        Preconditions.checkArgument(variant != null);
        if (this.variants.containsKey(variant)) {
            return false;
        }
        this.variants.put(variant, createNewVariant());
        return true;
    }

    @Override
    public boolean doesVariantExist(String variant) {
        return this.variants.containsKey(variant);
    }

    @Override
    public boolean removeVariant(String variant) {
        Variant var = this.variants.remove(variant);
        if (var == null) {
            return false;
        }
        new HashSet<>(var.players).forEach(playerName -> setPlayerVariant(playerName, null));
        return true;
    }

    @Override
    public Collection<String> getAllVariants() {
        return Collections.unmodifiableCollection(this.variants.keySet());
    }

    @Override
    public String getPlayerVariant(String playerName) {
        return this.playersToVariants.get(playerName.toLowerCase());
    }

    @Override
    public void setPlayerVariant(String playerName, String variant) throws IllegalArgumentException {
        String loweredPlayerName = playerName.toLowerCase();
        String previousVariant = this.playersToVariants.get(loweredPlayerName);
        if (previousVariant != null) {
            Variant var = this.variants.get(previousVariant);
            var.players.remove(loweredPlayerName);
            this.playersToVariants.remove(loweredPlayerName);
            if (!this.variantsEternal && var.players.isEmpty()) {
                removeVariant(previousVariant);
            }
        }
        if (variant == null) {
            Player player = Bukkit.getPlayerExact(playerName);
            if (player != null && isVisibleFor(player)) {
                hide(player);
            }
            return;
        }
        Variant var = this.variants.get(variant);
        Preconditions.checkArgument(var != null, "Variant of name " + variant + " does not exist");
        var.players.add(loweredPlayerName);
        this.playersToVariants.put(loweredPlayerName, variant);
        sendVariantByNames(var, Collections.singleton(playerName));
    }

    @Override
    public void setVariantSelector(Function<String, String> playerNameToVariantSelector) {
        this.variantSelector = playerNameToVariantSelector;
    }

    @Override
    public void setWhetherVariantsEternal(boolean value) {
        this.variantsEternal = value;
    }

    protected abstract Variant createNewVariant();

    Variant getVariant(String variant) {
        return this.variants.get(variant);
    }

    void updateVariant(Variant variant) {
        sendVariantByNames(variant, variant.players);
    }

    private void sendVariantByNames(Variant variant, Collection<String> playerNames) {
        if (playerNames.isEmpty()) {
            return;
        }
        Collection<Player> players = new HashSet<>();
        for (String playerName : playerNames) {
            Player player = Bukkit.getPlayerExact(playerName);
            if (player != null) {
                players.add(player);
            }
        }
        if (!players.isEmpty()) {
            sendVariantByPlayers(variant, players);
        }
    }

    private void sendVariantByPlayers(Variant variant, Collection<Player> players) {
        val packets = variant.getMetadata();
        players.forEach(player -> packets.forEach(packet -> packet.sendPacket(player)));
    }

    abstract class Variant {

        private final Collection<String> players = new HashSet<>();
        protected final WrappedDataWatcher meta = new WrappedDataWatcher();

        abstract Collection<AbstractPacket> getMetadata();

    }

}
