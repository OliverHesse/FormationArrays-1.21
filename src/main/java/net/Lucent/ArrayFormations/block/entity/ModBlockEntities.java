package net.Lucent.ArrayFormations.block.entity;

import com.mojang.datafixers.types.Type;
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


    public static  final Supplier<BlockEntityType<MortalFormationCoreBlockEntity>> MORTAL_FORMATION_CORE_BE =
            BLOCK_ENTITIES.register("mortal_formation_core_be",() -> BlockEntityType.Builder.of(
                    MortalFormationCoreBlockEntity::new, ModBlocks.MORTAL_FORMATION_CORE.get()).build(null));

    public static  final Supplier<BlockEntityType<PrimalFormationCoreBlockEntity>> PRIMAL_FORMATION_CORE_BE =
            BLOCK_ENTITIES.register("primal_formation_core_be",() -> BlockEntityType.Builder.of(
                    PrimalFormationCoreBlockEntity::new, ModBlocks.PRIMAL_FORMATION_CORE.get()).build(null));


    public static void register(IEventBus eventBus){

        BLOCK_ENTITIES.register(eventBus);

    }
}
