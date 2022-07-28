package net.larr4k.villenium.netty.packet;

import org.inmine.network.Buffer;
import org.inmine.network.Packet;

public class Packet2CreateMine extends Packet {
    @Override
    public int getId() {
        return 2;
    }

    @Override
    public void write(Buffer buffer) {
         buffer.newBuffer(2);
    }

    @Override
    public void read(Buffer buffer) {

    }
}
