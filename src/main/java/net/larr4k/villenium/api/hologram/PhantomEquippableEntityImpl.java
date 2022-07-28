package net.larr4k.villenium.api.hologram;

import com.comphenix.packetwrapper.WrapperPlayServerEntityEquipment;
import com.comphenix.protocol.utility.MinecraftVersion;
import com.comphenix.protocol.wrappers.EnumWrappers;
import lombok.RequiredArgsConstructor;
import lombok.val;
import net.larr4k.villenium.api.graphic.PhantomEquippableEntity;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;


import java.util.EnumMap;
import java.util.Map;


public abstract class PhantomEquippableEntityImpl extends PhantomEntityImpl implements PhantomEquippableEntity {

    private final static ItemStack AIR = new ItemStack(Material.AIR, 1);

    private final Map<ItemType, ItemStack> inventory = new EnumMap<>(ItemType.class);

    PhantomEquippableEntityImpl(int id, EntityType entityType) {
        super(id, entityType);
    }

    @Override
    protected void show(Player player, boolean addToCache) throws IllegalArgumentException {
        super.show(player, addToCache);
        this.inventory.forEach((type, is) -> {
            val wrapper = new WrapperPlayServerEntityEquipment();
            wrapper.setEntityID(getID());
            if (MinecraftVersion.getCurrentVersion().getMinor() == 8) {
                wrapper.getHandle().getIntegers().write(1, type.ordinal());
            } else {
                wrapper.setSlot(type.v1_12);
            }
            wrapper.setItem(is);
            wrapper.sendPacket(player);
        });
    }

    @Override
    public ItemStack getHand() {
        return this.inventory.get(ItemType.HAND);
    }

    @Override
    public ItemStack getHelmet() {
        return this.inventory.get(ItemType.HELMET);
    }

    @Override
    public ItemStack getChestplate() {
        return this.inventory.get(ItemType.CHESTPLATE);
    }

    @Override
    public ItemStack getLeggings() {
        return this.inventory.get(ItemType.LEGGINGS);
    }

    @Override
    public ItemStack getBoots() {
        return this.inventory.get(ItemType.BOOTS);
    }

    @Override
    public void setHand(ItemStack hand) {
        updateItem(ItemType.HAND, hand);
    }

    @Override
    public void setHelmet(ItemStack helmet) {
        updateItem(ItemType.HELMET, helmet);
    }

    @Override
    public void setChestplate(ItemStack chestplate) {
        updateItem(ItemType.CHESTPLATE, chestplate);
    }

    @Override
    public void setLeggings(ItemStack leggings) {
        updateItem(ItemType.LEGGINGS, leggings);
    }

    @Override
    public void setBoots(ItemStack boots) {
        updateItem(ItemType.BOOTS, boots);
    }

    private void updateItem(ItemType type, ItemStack is) {
        if (is == null) {
            this.inventory.remove(type);
            is = AIR;
        } else {
            this.inventory.put(type, is);
        }
        if (!super.spawned) {
            return;
        }
        val wrapper = new WrapperPlayServerEntityEquipment();
        wrapper.setEntityID(getID());
        if (MinecraftVersion.getCurrentVersion().getMinor() == 8) {
            wrapper.getHandle().getIntegers().write(1, type.ordinal());
        } else {
            wrapper.setSlot(type.v1_12);
        }
        wrapper.setItem(is);
        sendPacket(wrapper);
    }

    @RequiredArgsConstructor
    private enum ItemType {
        HAND(EnumWrappers.ItemSlot.MAINHAND),
        BOOTS(EnumWrappers.ItemSlot.FEET),
        LEGGINGS(EnumWrappers.ItemSlot.LEGS),
        CHESTPLATE(EnumWrappers.ItemSlot.CHEST),
        HELMET(EnumWrappers.ItemSlot.HEAD);

        private final EnumWrappers.ItemSlot v1_12;
    }

}
