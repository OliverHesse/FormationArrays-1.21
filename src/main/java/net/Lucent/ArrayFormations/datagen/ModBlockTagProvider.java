package net.Lucent.ArrayFormations.datagen;

import net.Lucent.ArrayFormations.ArrayFormationsMod;
import net.Lucent.ArrayFormations.block.ModBlocks;
import net.Lucent.ArrayFormations.util.ModTags;
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
        tag(ModTags.Blocks.ARRAY_FLAG)
                .add(ModBlocks.ARRAY_FLAG_EARTH.get())
                .add(ModBlocks.ARRAY_FLAG_METAL.get())
                .add(ModBlocks.ARRAY_FLAG_WOOD.get())
                .add(ModBlocks.ARRAY_FLAG_WATER.get())
                .add(ModBlocks.ARRAY_FLAG_FIRE.get());
        tag(ModTags.Blocks.EARTH_ARRAY_FLAG)
                .add(ModBlocks.ARRAY_FLAG_EARTH.get());
        tag(ModTags.Blocks.FIRE_ARRAY_FLAG)
                .add(ModBlocks.ARRAY_FLAG_FIRE.get());
        tag(ModTags.Blocks.WOOD_ARRAY_FLAG)
                .add(ModBlocks.ARRAY_FLAG_WOOD.get());
        tag(ModTags.Blocks.WATER_ARRAY_FLAG)
                .add(ModBlocks.ARRAY_FLAG_WATER.get());
        tag(ModTags.Blocks.METAL_ARRAY_FLAG)
                .add(ModBlocks.ARRAY_FLAG_METAL.get());

    }
}
