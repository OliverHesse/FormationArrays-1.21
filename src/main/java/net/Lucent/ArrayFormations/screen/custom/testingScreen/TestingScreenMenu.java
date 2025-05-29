package net.Lucent.ArrayFormations.screen.custom.testingScreen;

import net.Lucent.ArrayFormations.block.ModBlocks;
import net.Lucent.ArrayFormations.screen.ModMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

public class TestingScreenMenu extends AbstractContainerMenu {
    Level level;
    BlockEntity blockEntity;

    public TestingScreenMenu(int containerId, Inventory inventory, FriendlyByteBuf extraData) {
        this(containerId,inventory,inventory.player.level().getBlockEntity(extraData.readBlockPos()));
    }
    public TestingScreenMenu(int containerId, Inventory inventory, BlockEntity blockEntity) {
        super(ModMenuTypes.GUI_TESTING_MENU.get(), containerId);
        System.out.println("doing stuff");
        level = blockEntity.getLevel();
        this.blockEntity = blockEntity;

        System.out.println("doing stuff2");

    }
    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        return null;
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()),
                player, ModBlocks.GUI_BLOCK.get());
    }
}
