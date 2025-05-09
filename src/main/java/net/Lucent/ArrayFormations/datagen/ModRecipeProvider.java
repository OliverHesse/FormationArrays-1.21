package net.Lucent.ArrayFormations.datagen;

import net.Lucent.ArrayFormations.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }


    @Override
    protected void buildRecipes(@NotNull RecipeOutput recipeOutput) {

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,ModItems.SPIRIT_STONE_DUST.get(),4)
                        .requires(ModItems.SPIRIT_STONE)
                        .unlockedBy("has_spirit_stone",has(ModItems.SPIRIT_STONE)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.SPIRIT_STONE_PASTE.get())
                .pattern("FFF")
                .pattern("FBF")
                .pattern("FFF")
                .define('F',ModItems.SPIRIT_STONE_DUST.get())
                .define('B',ModItems.CONDENSED_QI_BUCKET.get())
                .unlockedBy("has_spirit_stone_dust",has(ModItems.SPIRIT_STONE_DUST))
                .save(recipeOutput);
    }
}
