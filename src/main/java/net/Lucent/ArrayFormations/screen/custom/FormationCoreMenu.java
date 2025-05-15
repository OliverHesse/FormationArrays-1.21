package net.Lucent.ArrayFormations.screen.custom;

import net.Lucent.ArrayFormations.block.AbstractClasses.AbstractFormationCoreBlockEntity;
import net.Lucent.ArrayFormations.block.ModBlocks;


import net.Lucent.ArrayFormations.network.custom.serverbound.SyncArrayBlueprintQuickMovePayload;
import net.Lucent.ArrayFormations.screen.ModMenuTypes;
import net.Lucent.ArrayFormations.util.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.items.SlotItemHandler;
import net.neoforged.neoforge.network.PacketDistributor;

public class FormationCoreMenu extends AbstractContainerMenu {

    public final AbstractFormationCoreBlockEntity formationCore;
    private final Level level;
    private final ContainerData data;

    public FormationCoreMenu(int containerId, Inventory inventory, FriendlyByteBuf extraData) {
        this(containerId,inventory,inventory.player.level().getBlockEntity(extraData.readBlockPos()),new SimpleContainerData(1));
    }

    public FormationCoreMenu(int containerId, Inventory inventory, BlockEntity blockEntity,ContainerData dataSlot) {
        super(ModMenuTypes.FORMATION_CORE_MENU.get(),containerId);
        this.formationCore = (AbstractFormationCoreBlockEntity) blockEntity;
        this.TE_INVENTORY_SLOT_COUNT = formationCore.inventory.getSlots();
        this.TE_FUEL_INVENTORY_SLOT_INDEX = TE_INVENTORY_FIRST_SLOT_INDEX+TE_INVENTORY_SLOT_COUNT;
        this.level = inventory.player.level();
        this.data = dataSlot;
        addPlayerHotBar(inventory);
        addPlayerInventory(inventory);
        System.out.println("CREATING INVENTORY SLOTS");
        int xOffset = 18;

        for(int i = 0; i<formationCore.inventory.getSlots(); i++) {
            System.out.println("CREATING SLOT " + i);
            this.addSlot(new SlotItemHandler(this.formationCore.inventory, i, 62+xOffset*i, 35));
        }
        this.addSlot(new SlotItemHandler(this.formationCore.fuelInventory, 0,25,55));

        addDataSlots(dataSlot);
    }

    public int getCurrentQiPercent(){
        return this.data.get(0);
    }



    @Override
    public boolean stillValid(Player player) {
        return stillValid(ContainerLevelAccess.create(level, formationCore.getBlockPos()),
                player, ModBlocks.MORTAL_FORMATION_CORE.get()) ||
                stillValid(ContainerLevelAccess.create(level, formationCore.getBlockPos()),
                player,ModBlocks.PRIMAL_FORMATION_CORE.get());
    }

    private void addPlayerInventory(Inventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 84 + i * 18));
            }
        }
    }

    private void addPlayerHotBar(Inventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }



    // CREDIT GOES TO: diesieben07 | https://github.com/diesieben07/SevenCommons
    // must assign a slot number to each of the slots used by the GUI.
    // For this container, we can see both the tile inventory's slots as well as the player inventory slots and the hotbar.
    // Each time we add a Slot to the container, it automatically increases the slotIndex, which means
    //  0 - 8 = hotbar slots (which will map to the InventoryPlayer slot numbers 0 - 8)
    //  9 - 35 = player inventory slots (which map to the InventoryPlayer slot numbers 9 - 35)
    //  36 - 44 = TileInventory slots, which map to our TileEntity slot numbers 0 - 8)
    public  final int HOTBAR_SLOT_COUNT = 9;
    public  final int PLAYER_INVENTORY_ROW_COUNT = 3;
    public  final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
    public  final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
    public  final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;
    public  final int VANILLA_FIRST_SLOT_INDEX = 0;
    public  final int TE_INVENTORY_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;






    // THIS YOU HAVE TO DEFINE!
    public final int TE_INVENTORY_SLOT_COUNT;  // must be the number of slots you have!
    public final int TE_FUEL_INVENTORY_SLOT_INDEX;

    @Override
    public ItemStack quickMoveStack(Player playerIn, int pIndex) {
        Slot sourceSlot = slots.get(pIndex);
        if (sourceSlot == null || !sourceSlot.hasItem()) return ItemStack.EMPTY;  //EMPTY_ITEM

        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack copyOfSourceStack = sourceStack.copy();




        int TE_FINAL_SLOT_COUNT;
        int TE_FINAL_INVENTORY_FIRST_SLOT_INDEX;
        System.out.println(sourceStack.is(ModTags.Items.ARRAY_BLUEPRINT));
        if(sourceStack.is(ModTags.Items.ARRAY_BLUEPRINT)){
            //use TE_INVENTORY_SLOTS SLOTS
            System.out.println("TRYING TO MOVE ARRAY BLUEPRINT");
            TE_FINAL_SLOT_COUNT = TE_INVENTORY_SLOT_COUNT;
            TE_FINAL_INVENTORY_FIRST_SLOT_INDEX = TE_INVENTORY_FIRST_SLOT_INDEX;
        }else if( sourceStack.is(ModTags.Items.FORMATION_ARRAY_FUEL)){
            //use TE_FUEL_INVENTORY_SLOTS
            TE_FINAL_SLOT_COUNT = 0;
            TE_FINAL_INVENTORY_FIRST_SLOT_INDEX = TE_FUEL_INVENTORY_SLOT_INDEX;

        }else {return ItemStack.EMPTY;}

        // Check if the slot clicked is one of the vanilla container slots
        if (pIndex < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT) {
            // This is a vanilla container slot so merge the stack into the tile inventory
            if (!moveItemStackTo(sourceStack, TE_FINAL_INVENTORY_FIRST_SLOT_INDEX, TE_FINAL_INVENTORY_FIRST_SLOT_INDEX
                    + TE_FINAL_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;  // EMPTY_ITEM
            }
        } else if (pIndex < TE_FINAL_INVENTORY_FIRST_SLOT_INDEX + TE_FINAL_SLOT_COUNT) {
            // This is a TE slot so merge the stack into the players inventory
            if (!moveItemStackTo(sourceStack, VANILLA_FIRST_SLOT_INDEX, VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;
            }
        } else {
            System.out.println("Invalid slotIndex:" + pIndex);
            return ItemStack.EMPTY;
        }
        // If stack size == 0 (the entire stack was moved) set slot contents to null
        if (sourceStack.getCount() == 0) {
            sourceSlot.set(ItemStack.EMPTY);
        } else {
            sourceSlot.setChanged();
        }
        sourceSlot.onTake(playerIn, sourceStack);
        return copyOfSourceStack;
    }


    public void sendSyncPacket(int slot, BlockPos blockPos){
        PacketDistributor.sendToServer(
                new SyncArrayBlueprintQuickMovePayload(
                        slot,
                        blockPos
                )
        );
    }

    @Override
    protected boolean moveItemStackTo(ItemStack stack, int startIndex, int endIndex, boolean reverseDirection) {




        boolean result = super.moveItemStackTo(stack, startIndex, endIndex, reverseDirection);
        ItemStack item1 = getSlot(startIndex).getItem();
        ItemStack item2 = getSlot(endIndex).getItem();

        if(reverseDirection) return result;
        boolean condition1 = endIndex >= TE_INVENTORY_FIRST_SLOT_INDEX && endIndex < TE_INVENTORY_FIRST_SLOT_INDEX + TE_INVENTORY_SLOT_COUNT;
        boolean condition2 = startIndex >= TE_INVENTORY_FIRST_SLOT_INDEX && startIndex < TE_INVENTORY_FIRST_SLOT_INDEX + TE_INVENTORY_SLOT_COUNT;
        if(condition1 && condition2){
            sendSyncPacket(startIndex-TE_INVENTORY_FIRST_SLOT_INDEX,formationCore.getBlockPos());
        }
        return result;

    }
}
