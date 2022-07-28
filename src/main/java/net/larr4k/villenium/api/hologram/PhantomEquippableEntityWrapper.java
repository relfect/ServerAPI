package net.larr4k.villenium.api.hologram;

import com.comphenix.packetwrapper.AbstractPacket;
import org.bukkit.entity.EntityType;

import java.util.Collection;
import java.util.Collections;


public class PhantomEquippableEntityWrapper extends PhantomEquippableEntityImpl {

    PhantomEquippableEntityWrapper(int id, EntityType entityType) {
        super(id, entityType);
    }

    @Override
    protected Collection<AbstractPacket> getMetadata() {
        return Collections.singleton(getRawSpawnPacket());
    }

}
