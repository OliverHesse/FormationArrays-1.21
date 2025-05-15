package net.Lucent.ArrayFormations.item.custom;

import net.Lucent.ArrayFormations.arrayExecutors.AbstractArrayExecutor;
import net.Lucent.ArrayFormations.component.ModDataComponents;
import net.Lucent.ArrayFormations.util.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;
import java.util.Objects;

public class SimplePortalArrayBlueprint extends ArrayBlueprint{
    public SimplePortalArrayBlueprint(Properties properties, AbstractArrayExecutor executor) {
        super(properties, executor);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        if(stack.get(ModDataComponents.PORTAL_LOCATION) != null){
            BlockPos loc= stack.get(ModDataComponents.PORTAL_LOCATION);
            BlockState state = Objects.requireNonNull(context.level()).getBlockState(loc);
            if(!state.is(ModTags.Blocks.PORTAL_RECEIVER)){
                tooltipComponents.add(Component.literal("lost connection to receiver"));
            }else {

                tooltipComponents.add(Component.literal("holds location: " + stack.get(ModDataComponents.PORTAL_LOCATION)));
            }
        }else {
            tooltipComponents.add(Component.literal("not Linked to a location"));
        }
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
}
