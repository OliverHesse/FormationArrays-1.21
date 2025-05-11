package net.Lucent.ArrayFormations.block;

import net.Lucent.ArrayFormations.ArrayFormationsMod;
import net.Lucent.ArrayFormations.block.custom.ArrayFlagBlock;
import net.Lucent.ArrayFormations.block.custom.FormationCoreBlock;

import net.Lucent.ArrayFormations.block.entity.MortalFormationCoreBlockEntity;
import net.Lucent.ArrayFormations.block.entity.PrimalFormationCoreBlockEntity;
import net.Lucent.ArrayFormations.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(ArrayFormationsMod.MOD_ID);




    public static final DeferredBlock<ArrayFlagBlock> ARRAY_FLAG_FIRE = registerBlock("array_flag_fire",
            () -> new ArrayFlagBlock(BlockBehaviour.Properties.of()
                    .instabreak()
                    .noLootTable()
                    .noOcclusion()

                    ));


    public static final DeferredBlock<ArrayFlagBlock> ARRAY_FLAG_EARTH = registerBlock("array_flag_earth",
            () -> new ArrayFlagBlock(BlockBehaviour.Properties.of()
                    .instabreak()
                    .noLootTable()
                    .noOcclusion()

            ));

    public static final DeferredBlock<ArrayFlagBlock> ARRAY_FLAG_WATER = registerBlock("array_flag_water",
            () -> new ArrayFlagBlock(BlockBehaviour.Properties.of()
                    .instabreak()
                    .noLootTable()
                    .noOcclusion()

            ));

    public static final DeferredBlock<ArrayFlagBlock> ARRAY_FLAG_METAL = registerBlock("array_flag_metal",
            () -> new ArrayFlagBlock(BlockBehaviour.Properties.of()
                    .instabreak()
                    .noLootTable()
                    .noOcclusion()

            ));
    public static final DeferredBlock<ArrayFlagBlock> ARRAY_FLAG_WOOD = registerBlock("array_flag_wood",
            () -> new ArrayFlagBlock(BlockBehaviour.Properties.of()
                    .instabreak()
                    .noLootTable()
                    .noOcclusion()

            ));


    public static final DeferredBlock<Block> MORTAL_FORMATION_CORE = registerBlock("mortal_formation_core",
            () -> new FormationCoreBlock(BlockBehaviour.Properties.of()
                    .strength(2f)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.ANVIL)
                    .noOcclusion(),MortalFormationCoreBlockEntity.class));

    public static final DeferredBlock<Block> PRIMAL_FORMATION_CORE = registerBlock("primal_formation_core",
            () -> new FormationCoreBlock(BlockBehaviour.Properties.of()
                    .strength(2f)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.ANVIL)
                    .noOcclusion(), PrimalFormationCoreBlockEntity.class));


    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block){
        DeferredBlock<T> toReturn = BLOCKS.register(name,block);
        registerBlockItems(name,toReturn);
        return toReturn;
    }




    private static <T extends Block> void registerBlockItems(String name, DeferredBlock<T> block){
        ModItems.ITEMS.register(name,() -> new BlockItem(block.get(),new Item.Properties()));
    }


    public static void register(IEventBus eventBus){

        BLOCKS.register(eventBus);
    }
}
