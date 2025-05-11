package net.Lucent.ArrayFormations.arrayExecutors;

import net.Lucent.ArrayFormations.util.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import org.joml.Vector2d;
import oshi.util.tuples.Pair;

import java.util.List;
import java.util.Vector;

public abstract class AbstractArrayExecutor {

    public List<Pair<TagKey<Block>, Vector2d>> formationFlags;



    public boolean arrayFlagsValid(Level level,BlockPos blockPos){
        for(Pair<TagKey<Block>,Vector2d> flagLocation: formationFlags){
            Vector2d offset = flagLocation.getB();
            BlockState blockState = level.getBlockState(new BlockPos((int) (blockPos.getX()+offset.x),blockPos.getY(), (int) (blockPos.getZ()+offset.y)));
            if(!blockState.is(flagLocation.getA())){
                return false;
            }
        }
        return true;
    }


    protected AbstractArrayExecutor(List<Pair<TagKey<Block>,Vector2d>> formationFlags){
        this.formationFlags = formationFlags;
    }



    public abstract void tick(Level level, BlockPos blockPos, BlockState blockState);

}

