package net.larr4k.villenium.api.hologram;

import com.comphenix.packetwrapper.AbstractPacket;
import com.comphenix.packetwrapper.WrapperPlayServerEntityDestroy;
import com.comphenix.packetwrapper.WrapperPlayServerNamedEntitySpawn;
import com.comphenix.packetwrapper.WrapperPlayServerPlayerInfo;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.PlayerInfoData;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import com.comphenix.protocol.wrappers.WrappedGameProfile;
import com.google.common.collect.Lists;
import lombok.val;
import net.larr4k.villenium.api.graphic.PhantomPlayer;
import net.larr4k.villenium.api.skins.SkinFactory;
import net.larr4k.villenium.utility.CompPacketWrapperUtil;
import net.larr4k.villenium.utility.Task;
import net.larr4k.villenium.utility.UtilChat;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;



import java.util.Collection;
import java.util.Collections;
import java.util.UUID;


public class PhantomPlayerImpl extends PhantomEquippableEntityImpl implements PhantomPlayer {

    private final UUID uuid;
    private String name;
    private String skinName;

    private WrappedGameProfile profile;

    PhantomPlayerImpl(int id, UUID uuid, String name, String skinName) {
        super(id, EntityType.PLAYER);
        this.uuid = uuid;
        this.name = UtilChat.c(name);
        this.skinName = skinName;
        this.profile = SkinFactory.makeProfile(uuid, this.name, skinName);
    }

    @Override
    protected void show(Player player, boolean addToCache) throws IllegalArgumentException {
        super.show(player, addToCache);
        Task.schedule(() -> {
            val info = new WrapperPlayServerPlayerInfo();
            info.setAction(EnumWrappers.PlayerInfoAction.REMOVE_PLAYER);
            info.setData(Collections.singletonList(new PlayerInfoData(
                    this.profile, 0, EnumWrappers.NativeGameMode.SURVIVAL, null
            )));
            info.sendPacket(player);
        }, 20L);
    }

    @Override
    public void despawn() {
        validate();
        val destroy = new WrapperPlayServerEntityDestroy();
        destroy.setEntityIds(new int[]{getID()});
        sendPacket(destroy);
        super.viewers.clear();
        getFactory().unregisterEntity(this);
        this.spawned = false;
    }

    @Override
    protected Collection<AbstractPacket> getMetadata() {
        val info = new WrapperPlayServerPlayerInfo();
        val spawn = new WrapperPlayServerNamedEntitySpawn();
        info.setAction(EnumWrappers.PlayerInfoAction.ADD_PLAYER);
        info.setData(Collections.singletonList(new PlayerInfoData(
                this.profile, 0, EnumWrappers.NativeGameMode.SURVIVAL, null
        )));
        spawn.setEntityID(getID());
        spawn.setX(super.location.getX());
        spawn.setY(super.location.getY());
        spawn.setZ(super.location.getZ());
        spawn.setYaw(super.location.getYaw());
        spawn.setPitch(super.location.getPitch());
        spawn.setPlayerUUID(this.profile.getUUID());
        WrappedDataWatcher meta = new WrappedDataWatcher();
        CompPacketWrapperUtil.setWatcherObject(meta, Byte.class, 13, (byte) 0x40);
        spawn.setMetadata(meta);

        return Lists.newArrayList(info, spawn);
    }

    @Override
    public UUID getUUID() {
        return this.uuid;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getSkinName() {
        return this.skinName;
    }

    @Override
    public void updateProfile(String name, String skinName) {
        this.name = UtilChat.c(name);
        this.skinName = skinName;
        this.profile = SkinFactory.makeProfile(this.uuid, this.name, this.skinName);
        if (isSpawned()) {
            despawn();
            spawn(super.autoVisible);
        }
    }

    private static byte getSkinVisibilityMask() {
        return (byte) (0x01 | 0x02 | 0x04 | 0x08 | 0x10 | 0x20 | 0x40);
    }

}
