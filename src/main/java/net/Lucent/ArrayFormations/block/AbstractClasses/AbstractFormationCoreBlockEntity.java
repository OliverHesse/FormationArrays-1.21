package net.Lucent.ArrayFormations.block.AbstractClasses;

import net.Lucent.ArrayFormations.arrayExecutors.AbstractArrayExecutor;
import net.Lucent.ArrayFormations.block.custom.BaseFormationCoreBlock;
import net.Lucent.ArrayFormations.block.entity.ModBlockEntities;
import net.Lucent.ArrayFormations.datamap.ModDataMaps;
import net.Lucent.ArrayFormations.datamap.datamaps.FormationCoreFuelMap;
import net.Lucent.ArrayFormations.datamap.datamaps.FormationCoreStats;
import net.Lucent.ArrayFormations.formation.QiContainer;
import net.Lucent.ArrayFormations.item.custom.ArrayBlueprint;
import net.Lucent.ArrayFormations.network.custom.clientbound.SyncFormationCoreQiAmountPayload;
import net.Lucent.ArrayFormations.screen.custom.OldArrayMenue.FormationCoreMenu;
import net.Lucent.ArrayFormations.util.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public abstract class AbstractFormationCoreBlockEntity extends BlockEntity implements MenuProvider {


    public static Map<String,BlockEntityType<? extends AbstractFormationCoreBlockEntity>> nameToType = new HashMap<>(){{
        put("Mortal Formation Core", ModBlockEntities.MORTAL_FORMATION_CORE_BE.get());
        put("Primal Formation Core", ModBlockEntities.PRIMAL_FORMATION_CORE_BE.get());
    }};

    public QiContainer qiContainer;


    public List<ArrayBlueprintSlotData> blueprintSlotData = new ArrayList<>();

    public ItemStackHandler inventory;
    public final ItemStackHandler fuelInventory =  new ItemStackHandler(1){

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

    public class ArrayBlueprintSlotData{


        public int rotation;
        @NotNull
        public ItemStack lastSlotItem;
        public ArrayBlueprintSlotData(int initialRotation, @NotNull ItemStack lastSlotItem){
                this.rotation = initialRotation;
                this.lastSlotItem = lastSlotItem;
        }

    }

    public abstract void setDisplayName(String name);



    private void createInventory(int slots){
        inventory = new ItemStackHandler(slots){
            @Override
            protected int getStackLimit(int slot, @NotNull ItemStack stack) {
                return 1;
            }

            @Override
            protected void onContentsChanged(int slot) {
                setChanged();

                assert level != null;
                if (!level.isClientSide()) {

                    if(blueprintSlotData.get(slot).lastSlotItem.is(ModTags.Items.ARRAY_BLUEPRINT)){
                        ((ArrayBlueprint) blueprintSlotData.get(slot).lastSlotItem.getItem()).executorWrapper.removeExecutor(AbstractFormationCoreBlockEntity.this);
                    }
                    if (inventory.getStackInSlot(slot).is(ModTags.Items.ARRAY_BLUEPRINT)) {
                        ((ArrayBlueprint) inventory.getStackInSlot(slot).getItem()).executorWrapper.getExecutor(AbstractFormationCoreBlockEntity.this);
                    }
                    blueprintSlotData.get(slot).lastSlotItem = inventory.getStackInSlot(slot);
                    level.sendBlockUpdated(getBlockPos(),getBlockState(),getBlockState(),3);
                }
            }
        };
    }


    public ItemStack getItemStack(int slot){
        return inventory.getStackInSlot(slot);
    }

    public AbstractFormationCoreBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);

        //data map data
        FormationCoreStats stats = blockState.getBlockHolder().getData(ModDataMaps.FORMATION_CORE_STATS);
        System.out.println(stats);
        if(stats == null) throw new IllegalArgumentException("formation core expects valid data maps");
        createInventory(stats.maxBlueprintSlots());
        qiContainer = new QiContainer(stats.maxQi());

        for(int i = 0;i < stats.maxBlueprintSlots();i++){
            blueprintSlotData.add(new ArrayBlueprintSlotData(0, ItemStack.EMPTY));
        }



    }

    public void syncWithQuickMove(int slot){
        ItemStack stack = blueprintSlotData.get(slot).lastSlotItem;
        if(stack.is(ModTags.Items.ARRAY_BLUEPRINT)) {
            ((ArrayBlueprint) stack.getItem()).executorWrapper.removeExecutor(this);
        }
        ItemStack newItem = inventory.getStackInSlot(slot);
        if(newItem.is(ModTags.Items.ARRAY_BLUEPRINT)){
            ((ArrayBlueprint) stack.getItem()).executorWrapper.getExecutor(this);
        }
        blueprintSlotData.get(slot).lastSlotItem = newItem;


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
        int[] rotations = new int[blueprintSlotData.size()];
        for(int slot =0; slot<blueprintSlotData.size();slot++){
            rotations[slot] = blueprintSlotData.get(slot).rotation;
        }
        tag.putIntArray("array_blueprint_rotations",rotations);
        tag.putString("array_current_qi",qiContainer.getQi().toString());
        for(int slot = 0;slot < inventory.getSlots();slot++){
            if(inventory.getStackInSlot(slot).is(ModTags.Items.ARRAY_BLUEPRINT)){
                //try to call it
                AbstractArrayExecutor executor = ((ArrayBlueprint) inventory.getStackInSlot(slot).getItem()).executorWrapper.getExecutor(this);
                executor.saveAdditional(tag,registries);

            }
        }
    }


    @Override
    protected void loadAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider registries) {
        super.loadAdditional(tag, registries);

        int[] temp = tag.getIntArray("array_blueprint_rotations");
        for(int slot = 0;slot<temp.length;slot++){
            blueprintSlotData.get(slot).rotation = temp[slot];

        }
        inventory.deserializeNBT(registries,tag.getCompound("inventory"));
        fuelInventory.deserializeNBT(registries,tag.getCompound("fuelInventory"));
        qiContainer.setQiAmount(tag.getString("array_current_qi"));
        for(int slot = 0;slot < inventory.getSlots();slot++){
            if(inventory.getStackInSlot(slot).is(ModTags.Items.ARRAY_BLUEPRINT)){
                AbstractArrayExecutor executor = ((ArrayBlueprint) inventory.getStackInSlot(slot).getItem()).executorWrapper.getExecutor(this);
                executor.loadAdditional(tag,registries);;
            }
        }

    }





    public void attemptToRefillContainer(){
        ItemStack fuelStack = fuelInventory.getStackInSlot(0);

        if(fuelStack == ItemStack.EMPTY) return;

        if(!fuelStack.is(ModTags.Items.FORMATION_ARRAY_FUEL)) return;
        FormationCoreFuelMap fuelVal = fuelStack.getItemHolder().getData(ModDataMaps.FORMATION_FUEL_COST);
        if(fuelVal != null){
            if(qiContainer.regenQiNoOverflow(fuelVal.qi())){
                fuelStack.shrink(1);
            }
        }
    }




    public void tick(Level level, BlockPos blockPos, BlockState blockState) {

        if(!level.isClientSide() && blockState.getValue(BaseFormationCoreBlock.FORMATION_CORE_STATE)){
            //is active run tick
            System.out.println(qiContainer.getQi().toString());

            //try to refill qiContainer
            attemptToRefillContainer();
            //run array blueprint logic
            for(int slot = 0;slot < inventory.getSlots();slot++){
                if(inventory.getStackInSlot(slot).is(ModTags.Items.ARRAY_BLUEPRINT)){
                    AbstractArrayExecutor executor = ((ArrayBlueprint) inventory.getStackInSlot(slot).getItem()).executorWrapper.getExecutor(this);
                    //first try to drain energy
                    qiContainer.regenQi(executor.QI_GAIN);
                    if(qiContainer.drainQi(executor.QI_DRAIN)){
                        executor.tick(level,blockPos,blockState,slot,blueprintSlotData.get(slot).rotation,this);

                    }else{
                        //no qi so run cancel code
                        executor.cancel();
                    }
                    PacketDistributor.sendToAllPlayers(new SyncFormationCoreQiAmountPayload(
                            qiContainer.getQi().toString(),
                            this.getBlockPos()
                    ));
                }
            }
        }
    }



    public void cancelFormationBlueprints(){

        for(int slot = 0;slot < inventory.getSlots();slot++){
            if(inventory.getStackInSlot(slot).is(ModTags.Items.ARRAY_BLUEPRINT)){
                AbstractArrayExecutor executor = ((ArrayBlueprint) inventory.getStackInSlot(slot).getItem()).executorWrapper.getExecutor(this);

                executor.cancel();
            }
        }
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

    public ContainerData dataSlot = new ContainerData() {
        @Override
        public int get(int index) {
            return switch (index){
                case 0 ->  qiContainer.currentQiPercentage();
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {

        }

        @Override
        public int getCount() {
            return 1;
        }
    };

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
        System.out.println(this.dataSlot.get(0));
        return new FormationCoreMenu(containerId,playerInventory,this,dataSlot);
    }

}
