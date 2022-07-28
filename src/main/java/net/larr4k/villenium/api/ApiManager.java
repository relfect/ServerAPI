package net.larr4k.villenium.api;

import net.larr4k.villenium.api.bar.SBarImpl;
import net.larr4k.villenium.api.hologram.PhantomEntityFactoryImpl;

public class ApiManager {

    public static void init(){
        new PhantomEntityFactoryImpl();
        new SBarImpl();
    }

}
