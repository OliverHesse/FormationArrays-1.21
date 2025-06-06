package net.Lucent.ArrayFormations.datagen;

import net.Lucent.ArrayFormations.ArrayFormationsMod;
import net.Lucent.ArrayFormations.item.ModItems;
import net.Lucent.ArrayFormations.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.level.block.Block;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends ItemTagsProvider {


    public ModItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags,
                              @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTags, ArrayFormationsMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        tag(ModTags.Items.FORMATION_ARRAY_FUEL)
                .add(ModItems.SPIRIT_STONE.get())
                .add(ModItems.CONDENSED_QI_BUCKET.get())
                .add(ModItems.SPIRIT_STONE_DUST.get())
                .add(ModItems.SPIRIT_STONE_PASTE.get());

        tag(ModTags.Items.SPIRIT_STONE)
                .add(ModItems.SPIRIT_STONE.get());

        tag(ModTags.Items.ARRAY_BLUEPRINT)
                .add(ModItems.REGEN_ARRAY_BLUEPRINT.get())
                .add(ModItems.SATURATION_ARRAY_BLUEPRINT.get())
                .add(ModItems.BARRIER_ARRAY_BLUEPRINT.get())
                .add(ModItems.SIMPLE_PORTAL_ARRAY_BLUEPRINT.get());

        tag(ModTags.Items.BARRIER_TOKEN)
                .add(ModItems.BARRIER_TOKEN.get());

    }
}
