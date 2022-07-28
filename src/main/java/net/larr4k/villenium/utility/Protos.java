package net.larr4k.villenium.utility;

import com.comphenix.packetwrapper.AbstractPacket;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.google.common.base.Preconditions;
import lombok.val;
import net.larr4k.villenium.ApiPlugin;
import org.bukkit.entity.Player;


import java.util.function.BiConsumer;
import java.util.function.BiFunction;


public class Protos {

    public static <T extends AbstractPacket> void addReceivingListener(Class<T> packetClass, BiFunction<Player, T, Boolean> processor) {
        PacketType type = getPacketType(packetClass);
        val constructor = UtilReflect.createConstructorLambda(packetClass, PacketContainer.class);
        Preconditions.checkNotNull(type);
        Preconditions.checkNotNull(constructor);
        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(ApiPlugin.getInstance(), type) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                try {
                    if (processor.apply(event.getPlayer(), constructor.apply(event.getPacket()))) {
                        event.setCancelled(true);
                    }
                } catch (Exception ex) {

                }
            }
        });
    }

    public static <T extends AbstractPacket> void addSendingListener(Class<T> packetClass, BiFunction<Player, T, Boolean> processor) {
        PacketType type = getPacketType(packetClass);
        val constructor = UtilReflect.createConstructorLambda(packetClass, PacketContainer.class);
        Preconditions.checkNotNull(type);
        Preconditions.checkNotNull(constructor);
        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(ApiPlugin.getInstance(), type) {
            @Override
            public void onPacketSending(PacketEvent event) {
                try {
                    if (processor.apply(event.getPlayer(), constructor.apply(event.getPacket()))) {
                        event.setCancelled(true);
                    }
                } catch (Exception ex) {

                }
            }
        });
    }

    public static <T extends AbstractPacket> void addReceivingListener(Class<T> packetClass, BiConsumer<Player, T> processor) {
        addReceivingListener(packetClass, (p, wrapper) -> {
            processor.accept(p, wrapper);
            return false;
        });
    }

    public static <T extends AbstractPacket> void addSendingListener(Class<T> packetClass, BiConsumer<Player, T> processor) {
        addSendingListener(packetClass, (p, wrapper) -> {
            processor.accept(p, wrapper);
            return false;
        });
    }

    private static PacketType getPacketType(Class<? extends AbstractPacket> packetClass) {
        try {
            return (PacketType) UtilReflect.getStaticField(packetClass, "TYPE");
        } catch (Exception e) {

            return null;
        }
    }

}
