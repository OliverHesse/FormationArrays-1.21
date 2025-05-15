package net.Lucent.ArrayFormations.arrayExecutors.executors;

import net.Lucent.ArrayFormations.arrayExecutors.AbstractArrayExecutor;
import net.Lucent.ArrayFormations.arrayExecutors.AbstractPortalExecutor;
import net.Lucent.ArrayFormations.block.AbstractClasses.AbstractFormationCoreBlockEntity;
import net.Lucent.ArrayFormations.component.ModDataComponents;
import net.Lucent.ArrayFormations.entity.ModEntities;

import net.Lucent.ArrayFormations.entity.custom.AbstractPortalEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ChunkLevel;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.world.chunk.ForcedChunkManager;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector2d;
import oshi.util.tuples.Pair;

import java.util.List;
import java.util.UUID;

public class SimplePortalExecutor extends AbstractPortalExecutor {

    public AbstractPortalEntity entity = null;
    public UUID entityID;

    public SimplePortalExecutor(List<Pair<TagKey<Block>, Vector2d>> formationFlags,String  qi_drain,String qi_gain) {
        super(formationFlags,qi_drain,qi_gain);
    }

    @Override
    public void saveAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider registries) {
        if(entity == null) return;
        tag.putUUID("simple_portal_id",entity.getUUID());
    }

    @Override
    public void loadAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider registries) {

        if(tag.contains("simple_portal_id")) {

            entityID = tag.getUUID("simple_portal_id");
        }

    }


    private boolean findExistingPortal(Level level,BlockPos blockPos){
        AbstractPortalEntity existingPortal = (AbstractPortalEntity) ((ServerLevel) level).getEntity(entityID);
        if(existingPortal == null){
            return false;
        }

        this.entity = existingPortal;
        return true;
    }

    private void createFreshPortal(Level level,BlockPos blockPos,int slot, AbstractFormationCoreBlockEntity blockEntity){
        AbstractPortalEntity portal = new AbstractPortalEntity(ModEntities.BASIC_PORTAL.get(),level);
        BlockPos pos = blockEntity.getItemStack(slot).get(ModDataComponents.PORTAL_LOCATION);
        if(pos == null) return;

        portal.setEndPoint(pos);
        portal.setPos(blockPos.above().getCenter());
        level.addFreshEntity(portal);
        entity = portal;
    }


    @Override
    public AbstractArrayExecutor copy() {
        return new SimplePortalExecutor(formationFlags,QI_DRAIN,QI_GAIN);
    }

    @Override
    public void cancel() {
        if(entity != null) entity.kill();

        entity = null;
        entityID = null;
    }

    @Override
    public void tick(Level level, BlockPos blockPos, BlockState blockState,int slot, int rotation, AbstractFormationCoreBlockEntity blockEntity) {

        if(level.isClientSide()) { return;}
        if(!arrayFlagsValid(level,blockPos,rotation)){return;}


        if(entity == null || entity.isRemoved()){
            if(!findExistingPortal(level,blockPos)){
                createFreshPortal(level,blockPos,slot,blockEntity);
            }
        }

    }
}
