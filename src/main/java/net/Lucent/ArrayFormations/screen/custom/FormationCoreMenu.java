package net.Lucent.ArrayFormations.screen.custom;

import net.Lucent.ArrayFormations.block.AbstractClasses.AbstractFormationCoreBlockEntity;
import net.Lucent.ArrayFormations.block.ModBlocks;

import net.Lucent.ArrayFormations.screen.ModMenuTypes;
import net.Lucent.ArrayFormations.util.ModTags;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.items.SlotItemHandler;

public class FormationCoreMenu extends AbstractContainerMenu {

    public final AbstractFormationCoreBlockEntity formationCore;
    private final Level level;


    public FormationCoreMenu(int containerId, Inventory inventory, FriendlyByteBuf extraData) {
        this(containerId,inventory,inventory.player.level().getBlockEntity(extraData.readBlockPos()));
    }

    public FormationCoreMenu(int containerId, Inventory inventory, BlockEntity blockEntity) {
        super(ModMenuTypes.FORMATION_CORE_MENU.get(),containerId);
        this.formationCore = (AbstractFormationCoreBlockEntity) blockEntity;
        this.TE_INVENTORY_SLOT_COUNT = formationCore.MAX_ARRAY_BLUEPRINTS();
        this.TE_FUEL_INVENTORY_SLOT_INDEX = TE_INVENTORY_FIRST_SLOT_INDEX+TE_INVENTORY_SLOT_COUNT;
        this.level = inventory.player.level();
        addPlayerHotBar(inventory);
        addPlayerInventory(inventory);
        System.out.println("CREATING INVENTORY SLOTS");
        int xOffset = 18;

        for(int i = 0; i<formationCore.MAX_ARRAY_BLUEPRINTS(); i++) {
            System.out.println("CREATING SLOT " + i);
            this.addSlot(new SlotItemHandler(this.formationCore.inventory, i, 62+xOffset*i, 35));
        }
        this.addSlot(new SlotItemHandler(this.formationCore.fuelInventory, 0,25,55));
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
    private static final int HOTBAR_SLOT_COUNT = 9;
    private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
    private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
    private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
    private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;
    private static final int VANILLA_FIRST_SLOT_INDEX = 0;
    private static final int TE_INVENTORY_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;






    // THIS YOU HAVE TO DEFINE!
    private final int TE_INVENTORY_SLOT_COUNT;  // must be the number of slots you have!
    private final int TE_FUEL_INVENTORY_SLOT_INDEX;

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
        }else if( sourceStack.is(ModTags.Items.ARRAY_FORMATION_FUEL)){
            //use TE_FUEL_INVENTORY_SLOTS
            TE_FINAL_SLOT_COUNT = 1;
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


}
