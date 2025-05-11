package net.Lucent.ArrayFormations.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ArrayFlagBlock extends HorizontalDirectionalBlock {
    public static final MapCodec<ArrayFlagBlock> CODEC = simpleCodec(ArrayFlagBlock::new);
    private static final VoxelShape SHAPE = Block.box(3.0,0.0,5.0,13.0,16.0,10.0);
    private static final VoxelShape SHAPE2 = Block.box(5.0,0.0,3.0,10.0,16.0,13.0);
    public ArrayFlagBlock(Properties properties) {
        super(properties);
    }


    @Override
    protected @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        if(state.getValue(ArrayFlagBlock.FACING) == Direction.NORTH || state.getValue(ArrayFlagBlock.FACING) == Direction.SOUTH){
            return SHAPE;
        }
        return SHAPE2;
    }

    @Override
    protected @NotNull MapCodec<? extends HorizontalDirectionalBlock> codec() {
        return CODEC;
    }
    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }
}
