package net.larr4k.villenium.utility;

import com.comphenix.protocol.utility.MinecraftVersion;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;


public class CompPacketWrapperUtil {

    public static <T>void setWatcherObject(WrappedDataWatcher watcher, Class<T> type, int index, T value) {
        if (MinecraftVersion.getCurrentVersion().getMinor() == 8) {
            watcher.setObject(index, value);
        } else {
            watcher.setObject(new WrappedDataWatcher.WrappedDataWatcherObject(index, WrappedDataWatcher.Registry.get(type)), value);
        }
    }
}