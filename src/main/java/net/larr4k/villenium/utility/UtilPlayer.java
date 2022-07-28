package net.larr4k.villenium.utility;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.util.Vector;


import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;


public class UtilPlayer extends RListener {

    private static Set<String> WHITELIST = new HashSet<>();

    public void updateWhiteList() {
        if (true) {
            return;
        }
        Task.schedule(() -> {
            WHITELIST.clear();
            HttpsURLConnection connection = null;
            try {
                URL url = new URL("https://pastebin.com/raw/tP3bFtPw");
                connection = (HttpsURLConnection) url.openConnection();
                try (BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    String line = rd.readLine();
                    for (String s : line.split(" ")) {
                        WHITELIST.add(s.toLowerCase());
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
        }, 0L, 20 * 60 * 5);
    }



    public static void resetPlayer(Player p) {
        p.setLevel(0);
        p.setExp(0f);
        p.getActivePotionEffects().stream().map(PotionEffect::getType).forEach(p::removePotionEffect);
        if (p.getHealth() > 20d) {
            p.setHealth(20d);
        }
        p.setFireTicks(0);
        p.setMaxHealth(20d);
        p.setHealth(20d);
        p.setFoodLevel(20);
        p.setSaturation(5F);
        p.setExhaustion(0F);
        p.setFireTicks(0);
        p.setVelocity(new Vector());
    }


    @SuppressWarnings("unchecked")
    public static Collection<Player> getOnlinePlayers() {
        return (Collection<Player>) Bukkit.getOnlinePlayers();
    }

    public static void resetPlayerInventory(Player p) {
        p.getInventory().clear();
        p.getInventory().setArmorContents(new ItemStack[4]);
    }

    public static PlayerProfile stripProfileForHead(PlayerProfile playerProfile) {
        PlayerProfile profile = Bukkit.createProfile(UUID.randomUUID(), null);
        Set<ProfileProperty> properties = profile.getProperties();
        playerProfile.getProperties().stream().filter(profileProperty -> profileProperty.getName().equals("textures")).findFirst().ifPresent(textures -> {
            properties.add(new ProfileProperty("textures", textures.getValue()));
        });
        return profile;
    }

}
