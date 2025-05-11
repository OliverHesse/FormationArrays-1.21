package net.Lucent.ArrayFormations.block.custom;

import com.mojang.serialization.MapCodec;

import net.Lucent.ArrayFormations.block.AbstractClasses.AbstractFormationCoreBlockEntity;
import net.Lucent.ArrayFormations.block.entity.ModBlockEntities;
import net.Lucent.ArrayFormations.block.entity.MortalFormationCoreBlockEntity;
import net.Lucent.ArrayFormations.block.entity.PrimalFormationCoreBlockEntity;
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
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class FormationCoreBlock extends BaseEntityBlock {

    public static final MapCodec<FormationCoreBlock> CODEC = simpleCodec(FormationCoreBlock::new);

    public Class<? extends AbstractFormationCoreBlockEntity> toInstance;
    public static final BooleanProperty FORMATION_CORE_STATE = BooleanProperty.create("formation_core_states");



    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FORMATION_CORE_STATE);
    }
    public FormationCoreBlock(Properties properties,Class<? extends AbstractFormationCoreBlockEntity> toInstance){
        this(properties);
        this.toInstance = toInstance;
        this.registerDefaultState(this.defaultBlockState().setValue(FORMATION_CORE_STATE, false));


    }

    public FormationCoreBlock(Properties properties) {
        super(properties);

    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return null;
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }


    //TODO Modify to work regardless off which block was pressed;
    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
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

        Constructor<? extends AbstractFormationCoreBlockEntity> constructor = null;
        try {
            constructor = toInstance.getConstructor(BlockPos.class,BlockState.class);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        try {
            return constructor.newInstance(blockPos,blockState);
        } catch (InstantiationException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        if(level.isClientSide()) {
            return null;
        }
        Map<Class<? extends AbstractFormationCoreBlockEntity>, BlockEntityType<?>> classToObjectMap = new HashMap<>();

        classToObjectMap.put(
                MortalFormationCoreBlockEntity.class,
                ModBlockEntities.MORTAL_FORMATION_CORE_BE.get()
        );
        classToObjectMap.put(
                PrimalFormationCoreBlockEntity.class,
                ModBlockEntities.PRIMAL_FORMATION_CORE_BE.get()
        );
        return createTickerHelper(blockEntityType, classToObjectMap.get(this.toInstance),
                (level1, blockPos, blockState, blockEntity) ->
                        ((AbstractFormationCoreBlockEntity) blockEntity).tick(level1, blockPos, blockState));
    }
}