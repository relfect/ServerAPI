package net.larr4k.villenium.utility.type;

import net.larr4k.villenium.annotation.SpigotOnly;
import net.larr4k.villenium.api.graphic.PhantomEntityFactory;
import net.larr4k.villenium.api.graphic.bar.SBar;
import net.larr4k.villenium.api.message.MessagesAPI;
import net.larr4k.villenium.api.message.SpigotMessage;

@SpigotOnly
public interface Spigot {


    PhantomEntityFactory getPhantomEntityFactory();

    @SuppressWarnings("unchecked")
    default MessagesAPI<SpigotMessage> getMessagesAPI() {
        return (MessagesAPI<SpigotMessage>) Type.getMESSAGES_API();
    }
    boolean isStaging();

    SBar getBarUtils();
}
