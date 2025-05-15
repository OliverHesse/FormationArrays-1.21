package net.Lucent.ArrayFormations.recipes;


import net.Lucent.ArrayFormations.ArrayFormationsMod;
import net.Lucent.ArrayFormations.component.ModDataComponents;
import net.Lucent.ArrayFormations.item.ModItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.AnvilUpdateEvent;

@EventBusSubscriber(modid = ArrayFormationsMod.MOD_ID,bus = EventBusSubscriber.Bus.GAME)
public class Events {

    @SubscribeEvent
    public static void onAnvilUpdate(AnvilUpdateEvent event) {
        ItemStack left = event.getLeft();
        ItemStack right = event.getRight();
        if (left.is(ModItems.SIMPLE_PORTAL_ARRAY_BLUEPRINT) && right.is(ModItems.SIMPLE_LOCATION_CRYSTAL)) {
            if(right.get(ModDataComponents.PORTAL_LOCATION) == null) return;
            ItemStack output = left.copy();
            output.set(ModDataComponents.PORTAL_LOCATION,right.get(ModDataComponents.PORTAL_LOCATION));
            event.setOutput(output);

            event.setMaterialCost(1);
            event.setCost(3);
        }
    }
}
