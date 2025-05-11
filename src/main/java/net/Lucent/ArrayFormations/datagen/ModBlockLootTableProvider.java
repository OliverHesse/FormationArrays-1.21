package net.Lucent.ArrayFormations.datagen;

import net.Lucent.ArrayFormations.block.ModBlocks;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class ModBlockLootTableProvider extends BlockLootSubProvider {


    protected ModBlockLootTableProvider(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected void generate() {

        dropSelf(ModBlocks.PRIMAL_FORMATION_CORE.get());
        dropSelf(ModBlocks.MORTAL_FORMATION_CORE.get());
    }




    @Override
    protected @NotNull Iterable<Block> getKnownBlocks(){

        return ModBlocks.BLOCKS.getEntries().stream().map(Holder::value)::iterator;
    }
}
