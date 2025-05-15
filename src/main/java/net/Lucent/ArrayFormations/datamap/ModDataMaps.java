package net.Lucent.ArrayFormations.datamap;

import net.Lucent.ArrayFormations.ArrayFormationsMod;
import net.Lucent.ArrayFormations.datamap.datamaps.FormationCoreFuelMap;
import net.Lucent.ArrayFormations.datamap.datamaps.FormationCoreStats;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.datamaps.DataMapType;

public class ModDataMaps {


    public static final DataMapType<Item, FormationCoreFuelMap> FORMATION_FUEL_COST = DataMapType.builder(

            ResourceLocation.fromNamespaceAndPath(ArrayFormationsMod.MOD_ID, "formation_fuel_cost"),
            // The registry to register the data map for.
            Registries.ITEM,
            // The codec of the data map entries.
            FormationCoreFuelMap.CODEC
    ).synced(
            FormationCoreFuelMap.CODEC,
            false
        ).build();

    public static final DataMapType<Block, FormationCoreStats> FORMATION_CORE_STATS = DataMapType.builder(

            ResourceLocation.fromNamespaceAndPath(ArrayFormationsMod.MOD_ID, "formation_core_stats"),
            // The registry to register the data map for.
            Registries.BLOCK,
            // The codec of the data map entries.
            FormationCoreStats.CODEC
    ).synced(
            FormationCoreStats.CODEC,
            false
    ).build();
}
