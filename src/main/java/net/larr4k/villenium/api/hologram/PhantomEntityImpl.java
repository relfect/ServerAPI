package net.larr4k.villenium.api.hologram;

import com.comphenix.packetwrapper.*;
import com.google.common.base.Preconditions;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.val;
import net.larr4k.villenium.api.graphic.PhantomEntity;
import net.larr4k.villenium.api.graphic.PhantomEntityAnimations;
import net.larr4k.villenium.api.graphic.PhantomEntityInteraction;
import net.larr4k.villenium.api.hologram.PhantomEntityFactoryImpl;
import net.larr4k.villenium.utility.UtilPlayer;
import net.larr4k.villenium.utility.type.Spigot;
import net.larr4k.villenium.utility.type.Type;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.util.NumberConversions;


import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;


@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public abstract class PhantomEntityImpl implements PhantomEntity {

    private final int id;
    private final EntityType entityType;
    private final PhantomEntityAnimations animations = new PhantomEntityAnimations() {
        @Override
        public void playAnimation(int animationID) {
            val wrapper = new WrapperPlayServerAnimation();
            wrapper.setEntityID(id);
            wrapper.setAnimation(animationID);
            sendPacket(wrapper);
        }

        @Override
        public void playAnimationDeath() {
            val wrapper = new WrapperPlayServerEntityStatus();
            wrapper.setEntityID(id);
            wrapper.setEntityStatus((byte) 3);
            sendPacket(wrapper);
        }
    };
    private PhantomEntityInteraction interaction;

    protected Location location;
    protected final Collection<Player> viewers = new HashSet<>();
    protected final Collection<Player> viewersCache = new HashSet<>();

    protected boolean spawned;
    protected boolean invalidated;
    protected boolean autoVisible;

    protected abstract Collection<AbstractPacket> getMetadata();

    @Override
    public int getID() {
        return this.id;
    }

    @Override
    public World getWorld() {
        return this.location == null ? null : this.location.getWorld();
    }

    @Override
    public Location getLocation() {
        return this.location.clone();
    }

    @Override
    public EntityType getType() {
        return this.entityType;
    }

    @Override
    public Collection<Player> getViewers() {
        return Collections.unmodifiableCollection(this.viewers);
    }

    @Override
    public void show(Player player) throws IllegalArgumentException {
        show(player, true);
    }

    protected void show(Player player, boolean addToCache) {
        if (!player.isOnline()) {
            return;
        }
        validate();
        Preconditions.checkArgument(!this.viewers.contains(player), "This entity is already visible for given player (%s)", player.getName());
        getMetadata().forEach(wrapper -> wrapper.sendPacket(player));
        getRotationPacket().sendPacket(player);
        this.viewers.add(player);
        if (addToCache) {
            this.viewersCache.add(player);
        }
    }

    @Override
    public void hide(Player player) throws IllegalArgumentException {
        hide(player, true);
    }

    protected void hide(Player player, boolean removeFromCache) {
        if (!player.isOnline()) {
            return;
        }
        validate();
        Preconditions.checkArgument(this.viewers.contains(player), "This entity is not visible for given player (%s)", player.getName());
        val destroy = new WrapperPlayServerEntityDestroy();
        destroy.setEntityIds(new int[]{this.id});
        destroy.sendPacket(player);
        this.viewers.remove(player);
        if (removeFromCache) {
            this.viewersCache.remove(player);
        }
    }

    @Override
    public boolean isVisibleFor(Player player) {
        return this.viewers.contains(player);
    }

    @Override
    public void spawn(boolean autoVisible) {
        Preconditions.checkState(!this.invalidated, "This entity had been invalidated, it can't be spawned again");
        Preconditions.checkState(!this.spawned, "This entity is already spawned");
        getFactory().registerEntity(this);
        this.spawned = true;
        this.autoVisible = autoVisible;
        if (autoVisible) {
            tick();
        }
    }

    @Override
    public void despawn() {
        validate();
        val destroy = new WrapperPlayServerEntityDestroy();
        destroy.setEntityIds(new int[]{this.id});
        sendPacket(destroy);
        this.viewers.clear();
        this.viewersCache.clear();
        getFactory().unregisterEntity(this);
        this.spawned = false;
    }

    @Override
    public boolean isSpawned() {
        return this.spawned;
    }

    @Override
    public void invalidate() {
        if (isSpawned()) {
            despawn();
        }
        this.invalidated = true;
    }

    @Override
    public void lookAt(Location location) {
        float[] angles = getRotationAngles(location);
        setRotation(angles[0], angles[1]);
    }

    @Override
    public void moveWithoutBodyRotation(double dx, double dy, double dz) {
        teleport(this.location.add(dx, dy, dz));
    }

    @Override
    public void move(double dx, double dy, double dz) {
        final float maxDa = 2F, minDa = -maxDa;
        float[] angles = getRotationAngles(dx, dy, dz);
        float yaw = this.location.getYaw(), pitch = this.location.getPitch();
        float dyaw = angles[0] - yaw, dpitch = angles[1] - pitch;
        this.location.setYaw(yaw + Math.max(Math.min(minDa, dyaw), maxDa));
        this.location.setPitch(pitch + Math.max(Math.min(minDa, dpitch), maxDa));
        teleport(this.location.add(dx, dy, dz));
    }

    @Override
    public void teleport(Location location) {
        this.location = location.clone();
        if (!this.spawned) {
            return;
        }
        val teleport = new WrapperPlayServerEntityTeleport();
        teleport.setEntityID(this.id);
        teleport.setYaw(this.location.getYaw());
        teleport.setPitch(this.location.getPitch());
        teleport.setX(this.location.getX());
        teleport.setY(this.location.getY());
        teleport.setZ(this.location.getZ());
        sendPacket(teleport);
        sendPacket(getRotationPacket());
    }

    @Override
    public PhantomEntityAnimations getAnimations() {
        return this.animations;
    }

    @Override
    public PhantomEntityInteraction getInteraction() {
        return this.interaction;
    }

    @Override
    public void setInteraction(PhantomEntityInteraction interaction) {
        this.interaction = interaction;
    }

    protected void tick() {
        int maxDistance = 48;
        if (!this.viewers.isEmpty()) {
            this.viewers.removeIf(p -> !p.isOnline());
        }
        if (!this.viewersCache.isEmpty()) {
            this.viewersCache.removeIf(p -> !p.isOnline());
        }
        UtilPlayer.getOnlinePlayers().forEach(p -> {
            boolean valid = p.getWorld() == getWorld() && p.getLocation().distance(this.location) <= maxDistance;
            boolean visible = this.viewers.contains(p);
            if (visible && !valid) {
                hide(p, false);
            } else if (!visible && valid) {
                if (this.autoVisible || this.viewersCache.contains(p)) {
                    show(p, false);
                }
            }
        });
    }

    protected PhantomEntityFactoryImpl getFactory() {
        return (PhantomEntityFactoryImpl) Type.getSPIGOT().getPhantomEntityFactory();
    }

    protected void sendPacket(AbstractPacket packet) {
        this.viewers.forEach(packet::sendPacket);
    }

    protected void validate() {
        Preconditions.checkState(this.spawned, "This entity is not spawned yet");
    }

    protected void setRotation(float yaw, float pitch) {
        this.location.setYaw(yaw);
        this.location.setPitch(pitch);
        teleport(this.location);
    }

    protected AbstractPacket getRotationPacket() {
        val head = new WrapperPlayServerEntityHeadRotation();
        head.setEntityID(this.id);
        head.setHeadYaw((byte) (this.location.getYaw() * 256.0f / 360.0f));
        return head;
    }

    protected float[] getRotationAngles(Location loc) {
        return getRotationAngles(
                loc.getX() - this.location.getX(),
                loc.getY() - this.location.getY(),
                loc.getZ() - this.location.getZ()
        );
    }

    protected float[] getRotationAngles(double x, double y, double z) {
        float yaw, pitch;
        if (x == 0D && z == 0D) {
            yaw = 0F;
            pitch = y > 0D ? -90F : 90F;
        } else {
            double theta = Math.atan2(-x, z);
            yaw = (float) Math.toDegrees((theta + 6.283185307179586D) % 6.283185307179586D);
            pitch = (float) Math.toDegrees(Math.atan(-y / Math.sqrt(NumberConversions.square(x) + NumberConversions.square(z))));
        }
        return new float[]{yaw, pitch};
    }

    protected AbstractPacket getRawSpawnPacket() {
        val wrapper = new WrapperPlayServerSpawnEntityLiving();
        wrapper.setType(this.entityType);
        wrapper.setEntityID(this.id);
        wrapper.setX(this.location.getX());
        wrapper.setY(this.location.getY());
        wrapper.setZ(this.location.getZ());
        wrapper.setYaw(this.location.getYaw());
        wrapper.setPitch(this.location.getPitch());
        wrapper.setHeadPitch(this.location.getPitch());
        return wrapper;
    }

}
