package net.potato.eat.item;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ConsumableComponents;
import net.minecraft.component.type.FoodComponents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.potato.eat.Learningfabric;

public class ModItem {

    public static final Item HEART = registerItem("heart", new Item(new Item.Settings()
            .registryKey(RegistryKey.of(RegistryKeys.ITEM, Identifier.of(Learningfabric.MOD_ID,"heart")))));

    public static final Item REVIVE_TOTEM = registerItem("revive_totem", new Item(new Item.Settings().rarity(Rarity.EPIC)
            .component(DataComponentTypes.ENCHANTMENT_GLINT_OVERRIDE, true)
            .registryKey(RegistryKey.of(RegistryKeys.ITEM, Identifier.of(Learningfabric.MOD_ID,"revive_totem")))));


    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(Learningfabric.MOD_ID, name), item);
    }


    public static void registerModItems(){
        Learningfabric.LOGGER.info("Registering ModItems");

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(fabricItemGroupEntries -> {
            fabricItemGroupEntries.add(HEART);
            fabricItemGroupEntries.add(REVIVE_TOTEM);
        });

    }
}
