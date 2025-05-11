package net.Lucent.ArrayFormations.datagen;

import net.Lucent.ArrayFormations.ArrayFormationsMod;
import net.Lucent.ArrayFormations.block.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends BlockTagsProvider {
    public ModBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, ArrayFormationsMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        tag(BlockTags.MINEABLE_WITH_PICKAXE)

                .add(ModBlocks.MORTAL_FORMATION_CORE.get())
                .add(ModBlocks.PRIMAL_FORMATION_CORE.get());

        tag(BlockTags.NEEDS_DIAMOND_TOOL)

                .add(ModBlocks.MORTAL_FORMATION_CORE.get())
                .add(ModBlocks.PRIMAL_FORMATION_CORE.get());

    }
}
