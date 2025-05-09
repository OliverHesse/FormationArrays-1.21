package net.Lucent.ArrayFormations.item;

import net.Lucent.ArrayFormations.ArrayFormationsMod;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ArrayFormationsMod.MOD_ID);

    public static final DeferredItem<Item> ARRAY_BLUEPRINT = ITEMS.register("array_blueprint",
            () -> new Item(new Item.Properties()));


    //TODO temp items
    public static final DeferredItem<Item> SPIRIT_STONE_DUST = ITEMS.register("spirit_stone_dust",
            () -> new Item(new Item.Properties()));

    //TODO temp items
    public static final DeferredItem<Item> SPIRIT_STONE_PASTE = ITEMS.register("spirit_stone_paste",
            () -> new Item(new Item.Properties()));

    //TODO temp items
    public static final DeferredItem<Item> SPIRIT_STONE = ITEMS.register("spirit_stone",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> CONDENSED_QI_BUCKET = ITEMS.register("condensed_qi_bucket",
            () -> new Item(new Item.Properties()));

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }

}
