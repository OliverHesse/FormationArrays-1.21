package net.Lucent.ArrayFormations.arrayExecutors.executors;

import net.Lucent.ArrayFormations.arrayExecutors.AbstractArrayExecutor;
import net.Lucent.ArrayFormations.block.AbstractClasses.AbstractFormationCoreBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector2d;
import oshi.util.tuples.Pair;

import java.util.ArrayList;
import java.util.List;

public class GiveGenericEffect extends AbstractArrayExecutor {

    private final MobEffectInstance effect;

    public GiveGenericEffect(MobEffectInstance effect,List<Pair<TagKey<Block>, Vector2d>> formationFlags,String  qi_drain,String  qi_gain){
        super(formationFlags,qi_drain,qi_gain);
        this.effect =  effect;

    }


    public MobEffectInstance newInstance(){
        return new MobEffectInstance(
                effect.getEffect(),
                effect.getDuration(),
                effect.getAmplifier(),
                effect.isAmbient(),
                effect.isVisible()
        );
    }

    public boolean hasEffect(Player player){
        return player.hasEffect(effect.getEffect());
    }

    public void addEffectToPlayer(Player player){
        if(hasEffect(player)){ return;}
        player.addEffect(newInstance());

    }


    public List<Player> getNearbyPlayers(ServerLevel level,BlockPos blockPos){
        List<Player> playerList= new ArrayList<>(List.of());
        AABB area = new AABB(-10+blockPos.getX(),-10+blockPos.getY(),-10+blockPos.getZ(),
                10+blockPos.getX(),10+blockPos.getY(),10+blockPos.getZ());
        for(ServerPlayer player : level.players()){

            //cus why not
            boolean isValid =  area.contains(player.getX(),player.getY(),player.getZ());
            if(isValid){playerList.add(player);}

        }
        return playerList;

    }

    @Override
    public void saveAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider registries) {

    }

    @Override
    public void loadAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider registries) {

    }

    @Override
    public AbstractArrayExecutor copy() {
        return new GiveGenericEffect(effect,formationFlags,QI_DRAIN,QI_GAIN);
    }

    @Override
    public void cancel() {

    }

    @Override
    public void tick(Level level, BlockPos blockPos, BlockState blockState,int slot,int rotation, AbstractFormationCoreBlockEntity blockEntity) {

        if(level.isClientSide()){return;}
        if(!arrayFlagsValid(level,blockPos,rotation)){return;}

        //should create a cube 10x10x10

        for(Player player : getNearbyPlayers((ServerLevel) level,blockPos)){
            addEffectToPlayer(player);
        }
    }
}
