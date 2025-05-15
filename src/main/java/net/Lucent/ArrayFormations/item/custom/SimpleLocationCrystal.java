package net.Lucent.ArrayFormations.item.custom;

import net.Lucent.ArrayFormations.component.ModDataComponents;
import net.Lucent.ArrayFormations.util.ModTags;
import net.minecraft.network.chat.Component;
import net.minecraft.server.ReloadableServerRegistries;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class SimpleLocationCrystal extends Item {


    public SimpleLocationCrystal(Properties properties) {
        super(properties);
    }


    @Override
    public InteractionResult useOn(UseOnContext context) {

        Level level = context.getLevel();
        BlockState blockState = level.getBlockState(context.getClickedPos());

        if(blockState.is(ModTags.Blocks.PORTAL_RECEIVER)){
            context.getItemInHand().set(ModDataComponents.PORTAL_LOCATION,context.getClickedPos());

        }




        return InteractionResult.SUCCESS;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        if(stack.get(ModDataComponents.PORTAL_LOCATION) != null){
            tooltipComponents.add(Component.literal("holds location: "+ stack.get(ModDataComponents.PORTAL_LOCATION)));

        }else {
            tooltipComponents.add(Component.literal("can hold a location"));
        }
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
}
