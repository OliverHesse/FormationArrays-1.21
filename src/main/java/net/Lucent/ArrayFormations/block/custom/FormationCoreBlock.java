package net.Lucent.ArrayFormations.block.custom;

import com.mojang.serialization.MapCodec;

import net.Lucent.ArrayFormations.block.AbstractClasses.AbstractFormationCoreBlockEntity;
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
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class FormationCoreBlock extends BaseEntityBlock {

    public static final MapCodec<FormationCoreBlock> CODEC = simpleCodec(FormationCoreBlock::new);
    public static final BooleanProperty ACTIVES_STATE = BooleanProperty.create("active_state");
    public Class<? extends AbstractFormationCoreBlockEntity> toInstance;
    public FormationCoreBlock(Properties properties,Class<? extends AbstractFormationCoreBlockEntity> toInstance){
        this(properties);
        this.toInstance = toInstance;

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
}