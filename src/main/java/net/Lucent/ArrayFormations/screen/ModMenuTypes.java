package net.Lucent.ArrayFormations.screen;

import net.Lucent.ArrayFormations.ArrayFormationsMod;
import net.Lucent.ArrayFormations.screen.custom.OldArrayMenue.FormationCoreMenu;
import net.Lucent.ArrayFormations.screen.custom.ProgrammableArrayMenu.ProgrammableFormationEditorMenu;
import net.Lucent.ArrayFormations.screen.custom.testingScreen.TestingScreenMenu;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.network.IContainerFactory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(Registries.MENU, ArrayFormationsMod.MOD_ID);

    public static final DeferredHolder<MenuType<?>, MenuType<FormationCoreMenu>> FORMATION_CORE_MENU =
            registerMenuType("pedestal_menu", FormationCoreMenu::new);

    public static final DeferredHolder<MenuType<?>,MenuType<ProgrammableFormationEditorMenu>> PROGRAMMABLE_ARRAY_MENU =
            registerMenuType("programmable_array_menu",ProgrammableFormationEditorMenu::new);

    public static final DeferredHolder<MenuType<?>,MenuType<TestingScreenMenu>> GUI_TESTING_MENU =
            registerMenuType("gui_testing",TestingScreenMenu::new);

    private static <T extends AbstractContainerMenu>DeferredHolder<MenuType<?>, MenuType<T>> registerMenuType(String name,

                                                                                                              IContainerFactory<T> factory) {

        return MENUS.register(name, () -> IMenuTypeExtension.create(factory));

    }

    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus);
    }
}
