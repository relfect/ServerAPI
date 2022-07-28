package net.larr4k.villenium.netty;

import org.inmine.network.netty.client.NettyClient;
import org.slf4j.LoggerFactory;
import net.larr4k.villenium.netty.packet.PacketRegister;

import java.util.logging.Logger;

public class Connector extends NettyClient {
    public Connector(){
        super((Logger) LoggerFactory.getLogger("Coordinator"),new PacketRegister());
        connect("localhost", 2342);

    }
    @Override
    public void onConnected() {

    }

    @Override
    public void onDisconnected() {

    }
}
