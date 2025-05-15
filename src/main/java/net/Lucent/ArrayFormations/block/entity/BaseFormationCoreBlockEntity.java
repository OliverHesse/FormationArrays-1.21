package net.Lucent.ArrayFormations.block.entity;

import net.Lucent.ArrayFormations.block.AbstractClasses.AbstractFormationCoreBlockEntity;
import net.Lucent.ArrayFormations.block.custom.BaseFormationCoreBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class BaseFormationCoreBlockEntity extends AbstractFormationCoreBlockEntity {

    private String displayName = "Not set";

    public BaseFormationCoreBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);

    }

    public BaseFormationCoreBlockEntity(BlockPos pos,BlockState blockState){
        this(ModBlockEntities.MORTAL_FORMATION_CORE_BE.get(),pos,blockState);
    }

    @Override
    public void setDisplayName(String name){
        displayName = name;
    }



    @Override
    public Component getDisplayName() {
        return Component.literal(displayName);
    }
}
