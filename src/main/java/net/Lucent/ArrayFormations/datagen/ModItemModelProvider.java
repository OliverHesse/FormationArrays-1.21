package net.Lucent.ArrayFormations.datagen;

import net.Lucent.ArrayFormations.ArrayFormationsMod;
import net.Lucent.ArrayFormations.item.ModItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, ArrayFormationsMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {

        basicItem(ModItems.SPIRIT_STONE_DUST.get());
        basicItem(ModItems.SPIRIT_STONE_PASTE.get());
        basicItem(ModItems.SPIRIT_STONE.get());
        basicItem(ModItems.CONDENSED_QI_BUCKET.get());
        basicItem(ModItems.REGEN_ARRAY_BLUEPRINT.get());
        basicItem(ModItems.SATURATION_ARRAY_BLUEPRINT.get());
        basicItem(ModItems.BARRIER_ARRAY_BLUEPRINT.get());
        basicItem(ModItems.BARRIER_TOKEN.get());
        basicItem(ModItems.SIMPLE_LOCATION_CRYSTAL.get());
        basicItem(ModItems.SIMPLE_PORTAL_ARRAY_BLUEPRINT.get());

    }
}
