package net.Lucent.ArrayFormations.arrayExecutors.executors;

import net.Lucent.ArrayFormations.arrayExecutors.AbstractArrayExecutor;
import net.Lucent.ArrayFormations.block.AbstractClasses.AbstractFormationCoreBlockEntity;
import net.Lucent.ArrayFormations.component.ModDataComponents;
import net.Lucent.ArrayFormations.entity.ModEntities;
import net.Lucent.ArrayFormations.util.ModTags;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector2d;
import org.joml.Vector3f;
import oshi.util.tuples.Pair;

import java.util.List;

public class BarrierExecutor extends AbstractArrayExecutor {

    public double BARRIER_RADIUS;
    public double MAX_HEALTH;
    public double REGEN_RATE;

    public BarrierExecutor(List<Pair<TagKey<Block>, Vector2d>> formationFlags, Double barrierRadius, double maxHealth, double regenRate,
                           String qi_drain,String qi_gain) {
        super(formationFlags,qi_drain,qi_gain);
        this.BARRIER_RADIUS = barrierRadius;
        this.MAX_HEALTH = maxHealth;
        this.REGEN_RATE = regenRate;

    }

    @Override
    public AbstractArrayExecutor copy() {
        return new BarrierExecutor(formationFlags,BARRIER_RADIUS,MAX_HEALTH,REGEN_RATE,QI_DRAIN,QI_GAIN);
    }

    @Override
    public void saveAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider registries) {

    }

    @Override
    public void loadAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider registries) {

    }

    @Override
    public void cancel() {

    }

    @Override
    public void tick(Level level, BlockPos blockPos, BlockState blockState, int slot, int rotation, AbstractFormationCoreBlockEntity blockEntity) {

        if(!arrayFlagsValid(level,blockPos,rotation)) return;

        if (level.isClientSide()) {
            renderClientEffects(level,blockPos,blockEntity);
        }else{
            System.out.println("checkingCollisions");
            checkCollisions(level,blockPos,blockEntity);
        }

    }

    public void renderClientEffects(Level level,BlockPos blockPos,AbstractFormationCoreBlockEntity blockEntity){


    }


    public void checkCollisions(Level level,BlockPos blockPos,AbstractFormationCoreBlockEntity blockEntity){


        List<Entity> result = level.getEntities((Entity) null,new AABB(blockPos).inflate(BARRIER_RADIUS), entity -> {

            //get distance ^2 to blockpos
            if(entity instanceof Projectile) return false;

            var distance = entity.position().distanceToSqr(blockPos.getCenter());
            return distance < Math.pow(BARRIER_RADIUS, 2) + 2 && distance > Math.pow(BARRIER_RADIUS,2) -2;
        });
        System.out.println(result);
        result_loop:
        for(Entity entity : result){
            if(entity instanceof Player target){
                for(ItemStack itemStack : target.getInventory().items){
                    if((itemStack.is(ModTags.Items.BARRIER_TOKEN))){
                        BlockPos loc = itemStack.get(ModDataComponents.BARRIER_LOCATION);
                        if(loc != null && loc.equals(blockPos)){
                            continue result_loop;
                        }
                    }
                }
            }

            //shove entity back
            Vec3 entityPos = entity.position();
            Vec3 moveDirection = new Vec3(
                    entityPos.x - blockPos.getX(),
                    entityPos.y - blockPos.getY(),
                    entityPos.z - blockPos.getZ()
            );
            moveDirection = moveDirection.normalize().scale(0.5);

            entity.setDeltaMovement(moveDirection);
            if(entity instanceof ServerPlayer player){
                player.connection.send(new ClientboundSetEntityMotionPacket(entity));
            }
        }

    }

    public void renderUVSphere(){

    }


}
