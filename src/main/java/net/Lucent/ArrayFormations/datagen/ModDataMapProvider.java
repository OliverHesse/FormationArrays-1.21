package net.Lucent.ArrayFormations.datagen;

import net.Lucent.ArrayFormations.block.ModBlocks;
import net.Lucent.ArrayFormations.datamap.ModDataMaps;
import net.Lucent.ArrayFormations.datamap.datamaps.FormationCoreFuelMap;
import net.Lucent.ArrayFormations.datamap.datamaps.FormationCoreStats;
import net.Lucent.ArrayFormations.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.data.DataMapProvider;

import java.util.concurrent.CompletableFuture;

public class ModDataMapProvider extends DataMapProvider {

    //TODO none for now

    protected ModDataMapProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider);
    }

    @Override
    protected void gather(HolderLookup.Provider provider) {
        this.builder(ModDataMaps.FORMATION_FUEL_COST)
                .add(ModItems.SPIRIT_STONE.getId(),new FormationCoreFuelMap("100"),false)
                .add(ModItems.SPIRIT_STONE_DUST.getId(),new FormationCoreFuelMap("8"),false)
                .add(ModItems.SPIRIT_STONE_PASTE.getId(), new FormationCoreFuelMap("200"),false)
                .add(ModItems.CONDENSED_QI_BUCKET.getId(),new FormationCoreFuelMap("150"),false);
        this.builder(ModDataMaps.FORMATION_CORE_STATS)
                .add(ModBlocks.MORTAL_FORMATION_CORE, new FormationCoreStats(1,"10000"),false)
                .add(ModBlocks.PRIMAL_FORMATION_CORE, new FormationCoreStats(3,"100000"),false);
        super.gather(provider);
    }

}
