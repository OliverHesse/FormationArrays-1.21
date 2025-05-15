package net.Lucent.ArrayFormations.entity.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public class AbstractPortalEntity extends Entity {

    public static final EntityDataAccessor<BlockPos> PORTAL_LOCATION = SynchedEntityData.defineId(AbstractPortalEntity.class, EntityDataSerializers.BLOCK_POS);


    public void setEndPoint(BlockPos blockPos){
        this.getEntityData().set(PORTAL_LOCATION,blockPos);
    }

    public AbstractPortalEntity(EntityType<?> entityType, Level level) {
        super( entityType, level);


    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        builder.define(PORTAL_LOCATION,new BlockPos(0,0,0));
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        int x = compound.getInt("portal_location_x");
        int y = compound.getInt("portal_location_y");
        int z = compound.getInt("portal_location_z");
        this.getEntityData().set(PORTAL_LOCATION, new BlockPos(x,y,z));

    }


    @Override
    public void addAdditionalSaveData(CompoundTag compound) {

        compound.putInt("portal_location_x",this.getEntityData().get(PORTAL_LOCATION).getX());
        compound.putInt("portal_location_y",this.getEntityData().get(PORTAL_LOCATION).getY());
        compound.putInt("portal_location_z",this.getEntityData().get(PORTAL_LOCATION).getZ());

    }

    @Override
    public void playerTouch(Player player) {
        if(player.level().isClientSide()) return;
        BlockPos loc = getEntityData().get(PORTAL_LOCATION).above();
        player.teleportTo(loc.getX(),loc.getY(),loc.getZ());
     }

    @Override
    public boolean canBeCollidedWith() {
        return true;

    }

    @Override
    public void tick() {

        super.tick();
    }



    @Override
    public boolean isAttackable() {
        return false;
    }


}
