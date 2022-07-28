package net.larr4k.villenium.utility;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import io.papermc.paper.enchantments.EnchantmentRarity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.kyori.adventure.text.Component;
import net.minecraft.server.v1_16_R3.NBTTagCompound;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.EntityCategory;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

@SuppressWarnings({"unused", "WeakerAccess", "UnusedReturnValue"})
public abstract class ItemBuilder {

    Consumer<ItemMeta> metaModifyConsumer;
    Function<NBTTagCompound, NBTTagCompound> nbtModifyOperator;
    private Material material;
    private int amount;
    private short durability;
    private MaterialData materialData;

    private ItemBuilder(Material material, int amount, short durability, MaterialData materialData) {
        this.material = material;
        this.amount = amount;
        this.durability = durability;
        this.materialData = materialData;
    }

    public static ItemBuilder empty() {
        return new FromZeroItemBuilder(null);
    }

    public static ItemBuilder fromMaterial(Material material) {
        Preconditions.checkNotNull(material, "material");
        return new FromZeroItemBuilder(material);
    }

    public static ItemBuilder fromItem(ItemStack itemStack) {
        Preconditions.checkNotNull(itemStack, "itemStack");
        return new FromPresentItemBuilder(itemStack);
    }

    public final ItemBuilder material(Material material) {
        Preconditions.checkNotNull(material, "material");
        this.material = material;
        return this;
    }

    public final ItemBuilder amount(int amount) {
        this.amount = amount;
        return this;
    }

    public final ItemBuilder data(short data) {
        this.durability = data;
        return this;
    }

    public final ItemBuilder data(int data) {
        return data((short) data);
    }

    public final ItemBuilder data(MaterialData data) {
        Preconditions.checkNotNull(data, "materialData");
        this.materialData = data;
        return this;
    }

    private void addMetaModifyConsumer(Consumer<ItemMeta> metaModifyConsumer) {
        this.metaModifyConsumer = this.metaModifyConsumer != null
                ? this.metaModifyConsumer.andThen(metaModifyConsumer)
                : metaModifyConsumer;
    }

    private void addNbtModifyOperator(UnaryOperator<NBTTagCompound> nbtModifyConsumer) {
        this.nbtModifyOperator = this.nbtModifyOperator != null
                ? this.nbtModifyOperator.andThen(nbtModifyConsumer)
                : nbtModifyConsumer;
    }

    public final ItemMetaBuilder<ItemMeta> withItemMeta() {
        return withItemMeta(ItemMeta.class);
    }

    public abstract <T extends ItemMeta> ItemMetaBuilder<T> withItemMeta(Class<T> itemMetaClass);

    public abstract ItemNbtBuilder withNbt();

    public final ItemStack build() {
        final ItemStack itemStack = new ItemStack(material, amount, durability);

        if (materialData != null) {
            itemStack.setData(materialData);
        }
        return applyModifications(itemStack);
    }

    private ItemStack applyModifications(ItemStack itemStack) {
        if (nbtModifyOperator != null) {
            net.minecraft.server.v1_16_R3.ItemStack nmsItemStack = CraftItemStack.asNMSCopy(itemStack);
            NBTTagCompound nbtTagCompound = getTagToUse();
            nmsItemStack.setTag(nbtModifyOperator.apply(nbtTagCompound));
            itemStack = CraftItemStack.asBukkitCopy(nmsItemStack);
        }

        if (metaModifyConsumer != null) {
            ItemMeta itemMeta = getMetaToUse(itemStack);
            metaModifyConsumer.accept(itemMeta);
            itemStack.setItemMeta(itemMeta);
        }
        return itemStack;
    }

    abstract ItemMeta getMetaToUse(ItemStack itemStack);

    public abstract NBTTagCompound getTagToUse();

    private static class FromZeroItemBuilder extends ItemBuilder {

        private FromZeroItemBuilder(Material material) {
            super(material, 1, (short) 0, null);
        }

        @Override
        public <T extends ItemMeta> ItemMetaBuilder<T> withItemMeta(Class<T> itemMetaClass) {
            return new FromZeroItemMetaBuilder<>(itemMetaClass);
        }

        @Override
        public ItemNbtBuilder withNbt() {
            return new ItemNbtBuilder();
        }

        @Override
        ItemMeta getMetaToUse(ItemStack itemStack) {
            return itemStack.getItemMeta();
        }

        @Override
        public NBTTagCompound getTagToUse() {
            return new NBTTagCompound();
        }
    }

    private static class FromPresentItemBuilder extends ItemBuilder {

        private final ItemStack initialItem;

        private FromPresentItemBuilder(ItemStack itemStack) {
            super(itemStack.getType(), itemStack.getAmount(), itemStack.getDurability(), itemStack.getData());
            this.initialItem = itemStack;
        }

        @Override
        public <T extends ItemMeta> ItemMetaBuilder<T> withItemMeta(Class<T> itemMetaClass) {
            return new FromPresentItemMetaBuilder<>(itemMetaClass, initialItem.getItemMeta());
        }

        @Override
        public ItemNbtBuilder withNbt() {
            return new ItemNbtBuilder();
        }

        @Override
        ItemMeta getMetaToUse(ItemStack itemStack) {
            return initialItem.getItemMeta();
        }

        @Override
        public NBTTagCompound getTagToUse() {
            net.minecraft.server.v1_16_R3.ItemStack nmsItemStack = CraftItemStack.asNMSCopy(initialItem);
            return nmsItemStack.hasTag() ? nmsItemStack.getTag() : new NBTTagCompound();
        }
    }

    private static class Glow extends Enchantment {

        static {
            if (Enchantment.getByName("GLOW") == null) {
                Enchantment.registerEnchantment(new Glow());
            }
        }

        public Glow() {
            super(NamespacedKey.minecraft("glow"));
        }

        @Override
        public @NotNull String getName() {
            return "GLOW";
        }

        @Override
        public int getMaxLevel() {
            return 1;
        }

        @Override
        public int getStartLevel() {
            return 1;
        }

        @Override
        public @NotNull EnchantmentTarget getItemTarget() {
            return EnchantmentTarget.ALL;
        }

        @Override
        public boolean isTreasure() {
            return false;
        }

        @Override
        public boolean isCursed() {
            return false;
        }

        @Override
        public boolean conflictsWith(@NotNull Enchantment other) {
            return false;
        }

        @Override
        public boolean canEnchantItem(@NotNull ItemStack item) {
            return true;
        }

        @Override
        public @NotNull Component displayName(int level) {
            return Component.empty();
        }

        @Override
        public boolean isTradeable() {
            return false;
        }

        @Override
        public boolean isDiscoverable() {
            return false;
        }

        @Override
        public @NotNull EnchantmentRarity getRarity() {
            return EnchantmentRarity.VERY_RARE;
        }

        @Override
        public float getDamageIncrease(int level, @NotNull EntityCategory entityCategory) {
            return 0;
        }

        @Override
        public @NotNull Set<EquipmentSlot> getActiveSlots() {
            return Arrays.stream(EquipmentSlot.values()).collect(Collectors.toSet());
        }
    }

    public abstract class ItemMetaBuilder<T extends ItemMeta> {

        private final Class<T> itemMetaClass;
        private Map<Enchantment, Integer> enchantmentsMap;
        private String displayName;
        private List<String> lore;
        private List<ItemFlag> itemFlags;
        private boolean unbreakable;
        private Consumer<T> customModifyingConsumer;

        private ItemMetaBuilder(Class<T> itemMetaClass) {
            this.itemMetaClass = itemMetaClass;
        }

        public ItemMetaBuilder<T> setEnchantment(Enchantment enchantment, int level) {
            Preconditions.checkNotNull(enchantment, "enchantment");
            enchantmentsMap = new HashMap<>();
            enchantmentsMap.put(enchantment, level);
            return this;
        }

        public ItemMetaBuilder<T> setEnchantments(Map<Enchantment, Integer> enchantments) {
            Preconditions.checkNotNull(enchantments, "enchantments");
            enchantmentsMap = new HashMap<>();

            enchantments.forEach((enchantment, level) -> {
                Preconditions.checkNotNull(enchantment, "enchantment");
                enchantmentsMap.put(enchantment, level);
            });
            return this;
        }

        public ItemMetaBuilder<T> addEnchantment(Enchantment enchantment, int level) {
            if (enchantmentsMap == null) {
                return setEnchantment(enchantment, level);
            }

            Preconditions.checkNotNull(enchantment, "enchantment");
            enchantmentsMap.put(enchantment, level);
            return this;
        }

        public ItemMetaBuilder<T> addEnchantments(Map<Enchantment, Integer> enchantments) {
            if (enchantmentsMap == null) {
                return this.setEnchantments(enchantments);
            }

            Preconditions.checkNotNull(enchantments, "enchantments");
            enchantments.forEach((enchantment, level) -> {
                Preconditions.checkNotNull(enchantment, "enchantment");
                enchantmentsMap.put(enchantment, level);
            });
            return this;
        }

        public ItemMetaBuilder<T> blankEnchantment() {
            Enchantment enchantment = Enchantment.getByName("GLOW") != null
                    ? Enchantment.getByName("GLOW") : new Glow();
            return setEnchantment(enchantment, 1);
        }

        public ItemMetaBuilder<T> setName(String displayName) {
            Preconditions.checkNotNull(displayName, "name");
            this.displayName = displayName;
            return this;
        }

        public ItemMetaBuilder<T> setLore(Collection<String> lore) {
            Preconditions.checkNotNull(lore, "lore");
            this.lore = new ArrayList<>(lore);
            return this;
        }

        public ItemMetaBuilder<T> setLore(String... lore) {
            Preconditions.checkNotNull(lore, "lore");
            this.lore = Lists.newArrayList(lore);
            return this;
        }

        public ItemMetaBuilder<T> addLore(Collection<String> lore) {
            if (this.lore == null) {
                return setLore(lore);
            }

            Preconditions.checkNotNull(lore, "lore");
            this.lore.addAll(lore);
            return this;
        }

        public ItemMetaBuilder<T> addLore(String... lore) {
            if (this.lore == null) {
                return setLore(lore);
            }

            Preconditions.checkNotNull(lore, "lore");
            this.lore.addAll(Arrays.asList(lore));
            return this;
        }

        public ItemMetaBuilder<T> addBlankLore() {
            return addLore(" ");
        }

        public ItemMetaBuilder<T> setItemFlags(Collection<ItemFlag> itemFlags) {
            Preconditions.checkNotNull(itemFlags, "itemFlags");
            this.itemFlags = new ArrayList<>(itemFlags);
            return this;
        }

        public ItemMetaBuilder<T> setItemFlags(ItemFlag... itemFlags) {
            Preconditions.checkNotNull(itemFlags, "itemFlags");
            this.itemFlags = Lists.newArrayList(itemFlags);
            return this;
        }

        public ItemMetaBuilder<T> addItemFlags(Collection<ItemFlag> itemFlags) {
            if (this.itemFlags == null) {
                return setItemFlags(itemFlags);
            }

            Preconditions.checkNotNull(itemFlags, "itemFlags");
            this.itemFlags.addAll(itemFlags);
            return this;
        }

        public ItemMetaBuilder<T> addItemFlags(ItemFlag... itemFlags) {
            if (this.itemFlags == null) {
                return setItemFlags(itemFlags);
            }

            Preconditions.checkNotNull(itemFlags, "itemFlags");
            this.itemFlags.addAll(Arrays.asList(itemFlags));
            return this;
        }

        public ItemMetaBuilder<T> setUnbreakable(boolean unbreakable) {
            this.unbreakable = unbreakable;
            return this;
        }

        public ItemMetaBuilder<T> customModifying(Consumer<T> customModifyingConsumer) {
            this.customModifyingConsumer = customModifyingConsumer;
            return this;
        }

        public ItemBuilder and() {
            ItemBuilder.this.addMetaModifyConsumer(meta -> {
                if (enchantmentsMap != null) {
                    enchantmentsMap.forEach((enchantment, level) -> meta.addEnchant(enchantment, level, true));
                }

                if (displayName != null) {
                    meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', displayName));
                }

                if (lore != null) {
                    meta.setLore(lore.stream()
                            .map(l -> ChatColor.translateAlternateColorCodes('&', l))
                            .collect(Collectors.toList()));
                }

                if (itemFlags != null) {
                    meta.addItemFlags(itemFlags.toArray(new ItemFlag[0]));
                }

                meta.setUnbreakable(unbreakable);

                if (itemMetaClass.isInstance(meta)) {
                    if (customModifyingConsumer != null) {
                        customModifyingConsumer.accept(itemMetaClass.cast(meta));
                    }
                }
            });
            return ItemBuilder.this;
        }
    }

    private final class FromZeroItemMetaBuilder<T extends ItemMeta> extends ItemMetaBuilder<T> {

        private FromZeroItemMetaBuilder(Class<T> itemMetaClass) {
            super(itemMetaClass);
        }
    }

    private final class FromPresentItemMetaBuilder<T extends ItemMeta> extends ItemMetaBuilder<T> {

        private FromPresentItemMetaBuilder(Class<T> itemMetaClass, ItemMeta meta) {
            super(itemMetaClass);

            if (meta.hasEnchants()) {
                setEnchantments(meta.getEnchants());
            }
            if (meta.hasDisplayName()) {
                setName(meta.getDisplayName());
            }
            if (meta.hasLore()) {
                setLore(meta.getLore());
            }
            setItemFlags(meta.getItemFlags());

            setUnbreakable(meta.isUnbreakable());
        }
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public final class ItemNbtBuilder {

        public ItemNbtBuilder setNbtTag(Consumer<NBTTagCompound> nbtTagInitializingConsumer) {
            Preconditions.checkNotNull(nbtTagInitializingConsumer, "consumer");
            ItemBuilder.this.addNbtModifyOperator(nbtTagCompound -> {
                nbtTagCompound = new NBTTagCompound();
                nbtTagInitializingConsumer.accept(nbtTagCompound);
                return nbtTagCompound;
            });
            return this;
        }

        public ItemNbtBuilder addNbtTag(Consumer<NBTTagCompound> nbtTagModifyingConsumer) {
            Preconditions.checkNotNull(nbtTagModifyingConsumer, "consumer");
            ItemBuilder.this.addNbtModifyOperator(nbtTagCompound -> {
                nbtTagModifyingConsumer.accept(nbtTagCompound);
                return nbtTagCompound;
            });
            return this;
        }

        public ItemBuilder and() {
            ItemBuilder.this.addNbtModifyOperator(nbtTagCompound -> nbtTagCompound);
            return ItemBuilder.this;
        }
    }
}
