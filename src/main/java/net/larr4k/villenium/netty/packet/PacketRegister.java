package net.larr4k.villenium.netty.packet;

import org.inmine.network.PacketRegistry;

public class PacketRegister extends PacketRegistry {
    public PacketRegister() {
        super(1,
                Packet1BungeeConnect::new,
                Packet2CreateMine::new
                );
    }
}

