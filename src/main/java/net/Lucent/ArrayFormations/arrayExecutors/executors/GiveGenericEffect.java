package net.Lucent.ArrayFormations.arrayExecutors.executors;

import net.Lucent.ArrayFormations.arrayExecutors.AbstractArrayExecutor;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.joml.Vector2d;
import oshi.util.tuples.Pair;

import java.util.ArrayList;
import java.util.List;

public class GiveGenericEffect extends AbstractArrayExecutor {

    private MobEffectInstance effect;

    public GiveGenericEffect(MobEffectInstance effect,List<Pair<TagKey<Block>, Vector2d>> formationFlags){
        super(formationFlags);
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
    public void tick(Level level, BlockPos blockPos, BlockState blockState) {
        System.out.println("executor is running tick");
        if(level.isClientSide()){return;}
        if(!arrayFlagsValid(level,blockPos)){return;}

        //should create a cube 10x10x10

        for(Player player : getNearbyPlayers((ServerLevel) level,blockPos)){
            addEffectToPlayer(player);
        }
    }
}
