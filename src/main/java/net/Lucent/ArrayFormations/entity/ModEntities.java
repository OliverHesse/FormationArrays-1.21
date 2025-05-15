package net.Lucent.ArrayFormations.entity;

import net.Lucent.ArrayFormations.ArrayFormationsMod;

import net.Lucent.ArrayFormations.entity.custom.AbstractPortalEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.flag.FeatureFlag;
import net.minecraft.world.flag.FeatureFlags;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, ArrayFormationsMod.MOD_ID);



    public static final Supplier<EntityType<AbstractPortalEntity>> BASIC_PORTAL =
            ENTITY_TYPES.register("basic_portal",()->
                    EntityType.Builder.<AbstractPortalEntity>of(AbstractPortalEntity::new, MobCategory.MISC)
                            .sized(1f,2f)
                            .build("basic_portal"));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);


    }

}

