package net.larr4k.villenium.netty.packet;


import org.inmine.network.Buffer;
import org.inmine.network.Packet;

public class Packet1BungeeConnect extends Packet {
    @Override
    public int getId() {
        return 1;
    }

    @Override
    public void write(Buffer buffer) {
    }

    @Override
    public void read(Buffer buffer) {

    }
}
