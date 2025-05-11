package net.Lucent.ArrayFormations.item;

import net.Lucent.ArrayFormations.ArrayFormationsMod;
import net.Lucent.ArrayFormations.arrayExecutors.executors.GiveGenericEffect;
import net.Lucent.ArrayFormations.item.custom.ArrayBlueprint;
import net.Lucent.ArrayFormations.util.ModTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.joml.Vector2d;
import oshi.util.tuples.Pair;

import java.util.ArrayList;
import java.util.List;

public class ModItems {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ArrayFormationsMod.MOD_ID);



    public static final DeferredItem<Item> REGEN_ARRAY_BLUEPRINT = ITEMS.register("regen_array_blueprint",
            () -> new ArrayBlueprint(new Item.Properties(),
                    new GiveGenericEffect(
                            new MobEffectInstance(
                                    MobEffects.REGENERATION,
                                    20,
                                    1,
                                    false,
                                    true
                            ),
                            List.of(
                                    new Pair<>(ModTags.Blocks.WOOD_ARRAY_FLAG, new Vector2d(-2, 0)),
                                    new Pair<>(ModTags.Blocks.FIRE_ARRAY_FLAG, new Vector2d(2, 0)),
                                    new Pair<>(ModTags.Blocks.WATER_ARRAY_FLAG, new Vector2d(0, 2)),
                                    new Pair<>(ModTags.Blocks.METAL_ARRAY_FLAG, new Vector2d(0, -2))

                                    )
                    )
                    ));

    public static final DeferredItem<Item> SATURATION_ARRAY_BLUEPRINT = ITEMS.register("saturation_array_blueprint",
            () -> new ArrayBlueprint(new Item.Properties(),
                    new GiveGenericEffect(
                            new MobEffectInstance(
                                    MobEffects.SATURATION,
                                    20,
                                    1,
                                    false,
                                    true
                            ),
                            List.of(
                            new Pair<>(ModTags.Blocks.WOOD_ARRAY_FLAG, new Vector2d(-3, 0)),
                            new Pair<>(ModTags.Blocks.FIRE_ARRAY_FLAG, new Vector2d(3, 0)),
                            new Pair<>(ModTags.Blocks.WATER_ARRAY_FLAG, new Vector2d(0, 3)),
                            new Pair<>(ModTags.Blocks.METAL_ARRAY_FLAG, new Vector2d(0, -3))

                    )
                    )));
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
