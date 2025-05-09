package net.Lucent.ArrayFormations.item;

import net.Lucent.ArrayFormations.ArrayFormationsMod;
import net.Lucent.ArrayFormations.block.ModBlock;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;

import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModCreativeModeTabs {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ArrayFormationsMod.MOD_ID);

    public static final Supplier<CreativeModeTab> ARRAY_FORMATION_MOD_ITEMS = CREATIVE_MODE_TAB.register("array_formation_items",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(ModItems.ARRAY_BLUEPRINT.get()))
                    .title(Component.translatable("creativetab.array_formations.array_formation_items"))
                    .displayItems((itemDisplayParameters,output) -> {
                        output.accept(ModItems.ARRAY_BLUEPRINT);
                        output.accept(ModItems.SPIRIT_STONE_DUST);
                        output.accept(ModItems.SPIRIT_STONE);
                        output.accept(ModItems.SPIRIT_STONE_PASTE);
                        output.accept(ModBlock.ARRAY_CORE);
                        output.accept(ModItems.CONDENSED_QI_BUCKET);
                    }).build());

    public static void register(IEventBus eventBus){
        CREATIVE_MODE_TAB.register(eventBus);
    }
}
