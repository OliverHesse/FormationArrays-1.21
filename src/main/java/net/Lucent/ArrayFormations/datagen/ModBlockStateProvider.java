package net.Lucent.ArrayFormations.datagen;

import net.Lucent.ArrayFormations.ArrayFormationsMod;
import net.Lucent.ArrayFormations.block.ModBlock;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, ArrayFormationsMod.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {

        //TODO change because array core will be a block entity
        blockWithItem(ModBlock.ARRAY_CORE);

    }



    private void blockWithItem(DeferredBlock<?> deferredBlock){
        simpleBlockWithItem(deferredBlock.get(),cubeAll(deferredBlock.get()));

    }
}
