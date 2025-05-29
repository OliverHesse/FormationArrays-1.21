package net.Lucent.ArrayFormations.block.entity;

import net.Lucent.ArrayFormations.screen.custom.ProgrammableArrayMenu.ProgrammableFormationEditorMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class ArrayEditorBlockEntity extends BlockEntity implements MenuProvider {
    public ArrayEditorBlockEntity( BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.ARRAY_CRAFTER_BE.get(), pos, blockState);
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Formation Editor");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
        System.out.println("Trying to make menu");
        return new ProgrammableFormationEditorMenu(containerId,playerInventory,this);
    }
}
