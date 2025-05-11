package net.Lucent.ArrayFormations.block.entity;

import net.Lucent.ArrayFormations.block.AbstractClasses.AbstractFormationCoreBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PrimalFormationCoreBlockEntity extends AbstractFormationCoreBlockEntity {

    @Override
    public int MAX_ARRAY_BLUEPRINTS() {
        return 3;
    }

    @Override
    public double MAX_QI() {
        return 10000;
    }

    public PrimalFormationCoreBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.PRIMAL_FORMATION_CORE_BE.get(), pos, blockState);
        for(int i = 0; i<MAX_ARRAY_BLUEPRINTS();i++){
            arrayFlagDirections.put(i, Direction.NORTH);
        }
        this.inventory = new ItemStackHandler(MAX_ARRAY_BLUEPRINTS()){

            @Override
            protected int getStackLimit(int slot, @NotNull ItemStack stack) {
                return 1;
            }

            @Override
            protected void onContentsChanged(int slot) {
                setChanged();
                assert level != null;
                if (!level.isClientSide()) {
                    level.sendBlockUpdated(getBlockPos(),getBlockState(),getBlockState(),3);
                }
            }
        };

    }


    @Override
    public Component getDisplayName() {
        return Component.literal("Primal Formation Core");
    }


}