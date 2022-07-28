package net.larr4k.villenium.api.hologram;

import com.comphenix.packetwrapper.AbstractPacket;
import net.larr4k.villenium.api.hologram.PhantomEntityImpl;
import org.bukkit.entity.EntityType;

import java.util.Collection;
import java.util.Collections;


public class PhantomEntityWrapper extends PhantomEntityImpl {

    PhantomEntityWrapper(int id, EntityType entityType) {
        super(id, entityType);
    }

    @Override
    protected Collection<AbstractPacket> getMetadata() {
        return Collections.singleton(getRawSpawnPacket());
    }

}
