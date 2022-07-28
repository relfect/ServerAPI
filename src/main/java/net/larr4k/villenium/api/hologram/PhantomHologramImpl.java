package net.larr4k.villenium.api.hologram;

import com.comphenix.packetwrapper.AbstractPacket;
import com.comphenix.packetwrapper.WrapperPlayServerEntityMetadata;
import com.comphenix.packetwrapper.WrapperPlayServerSpawnEntityLiving;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import lombok.val;
import net.larr4k.villenium.api.graphic.PhantomHologram;
import net.larr4k.villenium.utility.ChatUtil;
import net.larr4k.villenium.utility.CompPacketWrapperUtil;
import org.bukkit.entity.EntityType;


import java.util.Collection;
import java.util.Collections;

public class PhantomHologramImpl extends PhantomEntityImpl implements PhantomHologram {

    private String text;
    private final WrappedDataWatcher metadata = new WrappedDataWatcher();

    PhantomHologramImpl(int id, String text) {
        super(id, EntityType.ARMOR_STAND);
        this.text = ChatUtil.colorize(text);
        CompPacketWrapperUtil.setWatcherObject(this.metadata, Byte.class, 0, (byte) 0x20); // Invisible
        CompPacketWrapperUtil.setWatcherObject(this.metadata, String.class, 2, this.text); // Custom name
        CompPacketWrapperUtil.setWatcherObject(this.metadata, Boolean.class, 3, true); // Is custom name visible
    }

    @Override
    protected Collection<AbstractPacket> getMetadata() {
        val wrapper = new WrapperPlayServerSpawnEntityLiving();
        wrapper.setType(EntityType.ARMOR_STAND);
        wrapper.setEntityID(getID());
        wrapper.setX(super.location.getX());
        wrapper.setY(super.location.getY());
        wrapper.setZ(super.location.getZ());
        wrapper.setMetadata(this.metadata);
        return Collections.singleton(wrapper);
    }

    @Override
    public String getText() {
        return this.text;
    }

    @Override
    public void setText(String text) {
        this.text = ChatUtil.colorize(text);
        CompPacketWrapperUtil.setWatcherObject(this.metadata, String.class, 2, this.text);
        val meta = new WrapperPlayServerEntityMetadata();
        meta.setEntityID(getID());
        meta.setMetadata(this.metadata.getWatchableObjects());
        sendPacket(meta);
    }

}
