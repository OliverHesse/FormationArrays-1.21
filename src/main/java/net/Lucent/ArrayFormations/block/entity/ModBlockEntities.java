package net.Lucent.ArrayFormations.block.entity;

import net.Lucent.ArrayFormations.ArrayFormationsMod;
import net.Lucent.ArrayFormations.block.ModBlocks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlockEntities {



    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, ArrayFormationsMod.MOD_ID);


    public static  final Supplier<BlockEntityType<BaseFormationCoreBlockEntity>> MORTAL_FORMATION_CORE_BE =
            BLOCK_ENTITIES.register("mortal_formation_core_be",() -> BlockEntityType.Builder.of(
                    BaseFormationCoreBlockEntity::new, ModBlocks.MORTAL_FORMATION_CORE.get()).build(null));

    public static  final Supplier<BlockEntityType<BaseFormationCoreBlockEntity>> PRIMAL_FORMATION_CORE_BE =
            BLOCK_ENTITIES.register("primal_formation_core_be",() -> BlockEntityType.Builder.of(
                    BaseFormationCoreBlockEntity::new, ModBlocks.PRIMAL_FORMATION_CORE.get()).build(null));

    public static  final Supplier<BlockEntityType<ArrayEditorBlockEntity>> ARRAY_CRAFTER_BE =
            BLOCK_ENTITIES.register("array_crafter_be",() -> BlockEntityType.Builder.of(
                    ArrayEditorBlockEntity::new, ModBlocks.ARRAY_CRAFTER.get()).build(null));

    public static  final Supplier<BlockEntityType<GUITestingBlockEntity>> GUI_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("gui_testing_be",() -> BlockEntityType.Builder.of(
                    GUITestingBlockEntity::new, ModBlocks.GUI_BLOCK.get()).build(null));

    public static void register(IEventBus eventBus){

        BLOCK_ENTITIES.register(eventBus);

    }
}
