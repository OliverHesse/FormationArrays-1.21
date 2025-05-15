package net.Lucent.ArrayFormations.block.custom;

import com.mojang.serialization.MapCodec;

import net.Lucent.ArrayFormations.block.AbstractClasses.AbstractFormationCoreBlockEntity;
import net.Lucent.ArrayFormations.block.entity.BaseFormationCoreBlockEntity;
import net.Lucent.ArrayFormations.block.entity.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class BaseFormationCoreBlock extends BaseEntityBlock {



    public static final MapCodec<BaseFormationCoreBlock> CODEC = simpleCodec(BaseFormationCoreBlock::new);

    public Class<? extends AbstractFormationCoreBlockEntity> toInstance;
    public static final BooleanProperty FORMATION_CORE_STATE = BooleanProperty.create("formation_core_states");


    public  String displayName = "Note Set";
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FORMATION_CORE_STATE);

    }
    public BaseFormationCoreBlock(Properties properties, Class<? extends AbstractFormationCoreBlockEntity> toInstance, String displayName){
        this(properties);
        this.toInstance = toInstance;
        this.displayName = displayName;
        this.registerDefaultState(this.defaultBlockState().setValue(FORMATION_CORE_STATE, false));






    }

    public BaseFormationCoreBlock(Properties properties) {
        super(properties);

    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return null;
    }


    @Override
    protected @NotNull RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }


    //TODO Modify to work regardless off which block was pressed;
    @Override
    protected @NotNull ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (level.getBlockEntity(pos) instanceof AbstractFormationCoreBlockEntity formationCoreBlockEntity) {

            if (!level.isClientSide()) {
                ((ServerPlayer) player).openMenu(new SimpleMenuProvider(formationCoreBlockEntity, Component.literal("Formation Core")), pos);
                return ItemInteractionResult.SUCCESS;
            }
        }
        return ItemInteractionResult.SUCCESS;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {

        BaseFormationCoreBlockEntity newBlockEntity = new BaseFormationCoreBlockEntity(
                AbstractFormationCoreBlockEntity.nameToType.get(displayName),
                blockPos,
                blockState
        );
        newBlockEntity.setDisplayName(displayName);
        return newBlockEntity;



    }




    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        if(level.isClientSide()) {
            return null;
        }

        return createTickerHelper(blockEntityType, AbstractFormationCoreBlockEntity.nameToType.get(this.displayName),
                (level1, blockPos, blockState, blockEntity) ->
                        blockEntity.tick(level1, blockPos, blockState));
    }
}