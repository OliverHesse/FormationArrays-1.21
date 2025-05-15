package net.Lucent.ArrayFormations.component;

import com.mojang.serialization.Codec;
import net.Lucent.ArrayFormations.ArrayFormationsMod;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.world.level.dimension.DimensionType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.UUID;
import java.util.function.UnaryOperator;
import java.util.logging.Level;

public class ModDataComponents {
    public static final DeferredRegister.DataComponents DATA_COMPONENT_TYPES =
            DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE,ArrayFormationsMod.MOD_ID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<BlockPos>> PORTAL_LOCATION =
            register("portal_location",builder -> builder.persistent(BlockPos.CODEC));



    public static final DeferredHolder<DataComponentType<?>, DataComponentType<BlockPos>> BARRIER_LOCATION =
            register("barrier_location",builder -> builder.persistent(BlockPos.CODEC));



    private static  <T>DeferredHolder<DataComponentType<?>,DataComponentType<T>> register(String name,
                                                                                          UnaryOperator<DataComponentType.Builder<T>> builderOperator){
        return DATA_COMPONENT_TYPES.register(name,()->builderOperator.apply(DataComponentType.builder()).build());
    }

    public static void register(IEventBus eventBus){
        DATA_COMPONENT_TYPES.register(eventBus);
    }
}
