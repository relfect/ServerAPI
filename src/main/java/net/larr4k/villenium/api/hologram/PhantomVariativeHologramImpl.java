package net.larr4k.villenium.api.hologram;

import com.comphenix.packetwrapper.AbstractPacket;
import com.comphenix.packetwrapper.WrapperPlayServerSpawnEntityLiving;
import com.google.common.base.Preconditions;
import lombok.val;
import net.larr4k.villenium.api.graphic.PhantomVariativeHologram;
import net.larr4k.villenium.utility.ChatUtil;
import net.larr4k.villenium.utility.CompPacketWrapperUtil;
import org.bukkit.entity.EntityType;



import java.util.Collection;
import java.util.Collections;

public class PhantomVariativeHologramImpl extends PhantomVariativeEntityImpl implements PhantomVariativeHologram {

    PhantomVariativeHologramImpl(int id) {
        super(id, EntityType.ARMOR_STAND);
    }

    @Override
    protected HologramVariant createNewVariant() {
        return new HologramVariant();
    }

    @Override
    public String getText(String variant) {
        Variant var = getVariant(variant);
        return var == null ? null : ((HologramVariant) var).text;
    }

    @Override
    public void setText(String variant, String text) {
        Variant var = getVariant(variant);
        Preconditions.checkArgument(var != null, "Variant of name " + variant + " does not exist");
        ((HologramVariant) var).setText(ChatUtil.colorize(text));
        updateVariant(var);
    }

    protected class HologramVariant extends Variant {

        private String text;

        private HologramVariant() {
            CompPacketWrapperUtil.setWatcherObject(super.meta, Byte.class, 0, (byte) 0x20); // Invisible
            CompPacketWrapperUtil.setWatcherObject(super.meta, Boolean.class, 3, true); // Is custom name visible
        }

        private void setText(String text) {
            this.text = text;
            CompPacketWrapperUtil.setWatcherObject(super.meta, String.class, 2, this.text); // Custom name
        }

        @Override
        public Collection<AbstractPacket> getMetadata() {
            val wrapper = new WrapperPlayServerSpawnEntityLiving();
            wrapper.setType(EntityType.ARMOR_STAND);
            wrapper.setEntityID(getID());
            wrapper.setX(location.getX());
            wrapper.setY(location.getY());
            wrapper.setZ(location.getZ());
            wrapper.setMetadata(super.meta);
            return Collections.singleton(wrapper);
        }

    }

}
