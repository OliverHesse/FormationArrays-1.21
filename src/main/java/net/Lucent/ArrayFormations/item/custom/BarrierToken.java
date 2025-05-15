package net.Lucent.ArrayFormations.item.custom;

import net.Lucent.ArrayFormations.block.ModBlocks;
import net.Lucent.ArrayFormations.component.ModDataComponents;
import net.Lucent.ArrayFormations.util.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;
import java.util.Objects;

public class BarrierToken extends Item {
    public BarrierToken(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockState blockState = level.getBlockState(context.getClickedPos());
        if(blockState.is(ModTags.Blocks.FORMATION_CORE)){
            context.getItemInHand().set(ModDataComponents.BARRIER_LOCATION,context.getClickedPos());
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        if(stack.get(ModDataComponents.BARRIER_LOCATION) != null){
            BlockPos loc= stack.get(ModDataComponents.BARRIER_LOCATION);
            BlockState state = Objects.requireNonNull(context.level()).getBlockState(loc);
            if(state.is(ModTags.Blocks.FORMATION_CORE)){
                tooltipComponents.add(Component.literal("status: Linked"));
            }else{
                tooltipComponents.add(Component.literal("status: Link Broken"));
            }

        }else {
            tooltipComponents.add(Component.literal("status: Not Linked"));
        }
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
}
