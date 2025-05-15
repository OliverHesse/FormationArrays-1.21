package net.Lucent.ArrayFormations.arrayExecutors;

import net.Lucent.ArrayFormations.block.AbstractClasses.AbstractFormationCoreBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector2d;
import oshi.util.tuples.Pair;

import java.math.BigDecimal;
import java.util.List;

public abstract class AbstractArrayExecutor {

    public List<Pair<TagKey<Block>, Vector2d>> formationFlags;
    public String  QI_DRAIN;
    public String  QI_GAIN; // some formations might let you gain qi?


    public boolean arrayFlagsValid(Level level,BlockPos blockPos,int rotation){
        rotation -= 2;
        for(Pair<TagKey<Block>,Vector2d> flagLocation: formationFlags){
            Vector2d offset = flagLocation.getB();

            int x = (int) (blockPos.getX()-offset.x*((rotation+1)%2)+offset.y*((rotation)%2));
            int z = (int) (blockPos.getZ()-offset.x*((rotation)%2)-offset.y*((rotation+1)%2));
            BlockState blockState = level.getBlockState(new BlockPos(x,blockPos.getY(), z));
            if(!blockState.is(flagLocation.getA())){
                return false;
            }
        }
        return true;
    }

    public  abstract AbstractArrayExecutor copy();

    public abstract void saveAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider registries);


    public abstract void loadAdditional(@NotNull CompoundTag tag, HolderLookup.@NotNull Provider registries);

    protected AbstractArrayExecutor(List<Pair<TagKey<Block>,Vector2d>> formationFlags,String  qi_drain,String  qi_gain){
        this.formationFlags = formationFlags;
        this.QI_DRAIN =  qi_drain;
        this.QI_GAIN = qi_gain;
    }

    public abstract void cancel();

    public abstract void tick(Level level, BlockPos blockPos, BlockState blockState,int slot, int rotation, AbstractFormationCoreBlockEntity blockEntity);

}

