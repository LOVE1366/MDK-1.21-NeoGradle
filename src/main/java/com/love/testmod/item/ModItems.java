package com.love.testmod.item;

import com.love.testmod.TestMod;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

import static com.love.testmod.TestMod.MODID;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);

    public static final DeferredItem<Item> registerItem(String name, Supplier<Item> itemSupplier){
        return ITEMS.register(name,itemSupplier);
    }

    public static final DeferredItem<Item> TEST_ITEM = registerItem("test_item", () -> new Item(new Item.Properties().fireResistant()));
}
