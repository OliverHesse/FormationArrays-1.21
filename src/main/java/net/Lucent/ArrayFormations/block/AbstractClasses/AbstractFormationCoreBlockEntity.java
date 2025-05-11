package net.Lucent.ArrayFormations.block.AbstractClasses;

import net.Lucent.ArrayFormations.screen.custom.FormationCoreMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractFormationCoreBlockEntity extends BlockEntity implements MenuProvider {

    public double QI;

    public ItemStackHandler inventory;
    public final ItemStackHandler fuelInventory;

    public abstract int MAX_ARRAY_BLUEPRINTS();
    public abstract double MAX_QI();
    public Map<Integer, Direction> arrayFlagDirections = new HashMap<Integer,Direction>();

    public AbstractFormationCoreBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
        fuelInventory =  new ItemStackHandler(1){

            @Override
            protected int getStackLimit(int slot, @NotNull ItemStack stack) {
                return stack.getMaxStackSize();
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

    public void removeAllBlueprints(){
        for(int i = 0;i<inventory.getSlots();i++){
            inventory.setStackInSlot(i, ItemStack.EMPTY);
        }
    }
    public void removeBlueprint(int slot){
        inventory.setStackInSlot(slot,ItemStack.EMPTY);
    }
    public void removeBlueprint(ItemStack item){
        for(int i = 0;i<inventory.getSlots();i++){
            if(inventory.getStackInSlot(i) == item){
                inventory.insertItem(i,ItemStack.EMPTY,false);
                return;
            }
        }
    }
    @Nullable
    public Integer getAvailableSlot(){
        for(int i = 0;i<inventory.getSlots();i++){
            if(inventory.getStackInSlot(i).isEmpty()){
                return i;
            }
        }
        return null;
    }
    public void addItem(ItemStack item){
        Integer slot = getAvailableSlot();

        if(slot != null)  inventory.insertItem(slot,item,false);

    }

    public void drops() {
        SimpleContainer inv = new SimpleContainer(inventory.getSlots());
        removeAllBlueprints();

        assert this.level != null;
        Containers.dropContents(this.level, this.worldPosition, inv);
    }
    @Override
    protected void saveAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put("inventory",inventory.serializeNBT(registries));
        tag.put("fuelInventory",fuelInventory.serializeNBT(registries));
        tag.putDouble("qi",QI);
    }


    @Override
    protected void loadAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider registries) {
        super.loadAdditional(tag, registries);
        this.QI = tag.getDouble("qi");
        inventory.deserializeNBT(registries,tag.getCompound("inventory"));
        fuelInventory.deserializeNBT(registries,tag.getCompound("fuelInventory"));

    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return saveWithoutMetadata(registries);
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
        return new FormationCoreMenu(containerId,playerInventory,this);
    }

}
