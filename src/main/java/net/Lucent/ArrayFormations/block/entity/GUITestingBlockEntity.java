package net.Lucent.ArrayFormations.block.entity;

import net.Lucent.ArrayFormations.screen.custom.testingScreen.TestingScreenMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class GUITestingBlockEntity extends BlockEntity implements MenuProvider {
    public GUITestingBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.GUI_BLOCK_ENTITY.get(), pos, blockState);
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Formation Editor");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
        System.out.println("Trying to make menu");
        return new TestingScreenMenu(containerId,playerInventory,this);
    }
}