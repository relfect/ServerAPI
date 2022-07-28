package net.larr4k.villenium.api.skins;

import com.comphenix.protocol.wrappers.WrappedGameProfile;
import com.comphenix.protocol.wrappers.WrappedSignedProperty;
import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import org.bukkit.Bukkit;

import java.util.UUID;


public class SkinFactory {

    private static SignedTexturesProviderInterface textureProvider;



    public static SignedTexturesProviderInterface getTextureProvider() {
        return textureProvider;
    }

    /**
     * Создаёт профиль игрока из UUID и ника с скином и плащём пренадлижащим собственному имени.
     *
     * @param uuid UUID профиля.
     * @param name Имя профиля.
     * @return объект профиля игрока.
     */
    public static WrappedGameProfile makeProfile(UUID uuid, String name) {
        return makeProfile(uuid, name, name);
    }

    /**
     * @param uuid     UUID профиля.
     * @param name     Имя профиля.
     * @param skinName Имя профиля с которогу будут взяты скины и плащ.
     * @return объект профиля игрока.
     */
    public static WrappedGameProfile makeProfile(UUID uuid, String name, String skinName) {
        WrappedGameProfile profile = new WrappedGameProfile(uuid, name);
        ProfileProperty profileProperty = textureProvider.getTexturesProperty(skinName);
        if (profileProperty != null) {
            profile.getProperties().put("textures", new WrappedSignedProperty(profileProperty.getName(), profileProperty.getValue(), profileProperty.getSignature()));
        }
        return profile;
    }

    public static PlayerProfile makePlayerProfile(UUID uuid, String name, String skinName) {
        PlayerProfile profile = Bukkit.createProfile(uuid, name);
        ProfileProperty textures = textureProvider.getTexturesProperty(skinName);
        if (textures != null) {
            profile.getProperties().clear();
            profile.getProperties().add(new ProfileProperty("textures", textures.getValue(), textures.getSignature()));
        }
        return profile;
    }

}