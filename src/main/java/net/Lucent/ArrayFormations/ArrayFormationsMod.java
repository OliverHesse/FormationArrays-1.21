package net.Lucent.ArrayFormations;

import net.Lucent.ArrayFormations.block.ModBlocks;
import net.Lucent.ArrayFormations.block.entity.ModBlockEntities;
import net.Lucent.ArrayFormations.component.ModDataComponents;
import net.Lucent.ArrayFormations.datamap.ModDataMaps;
import net.Lucent.ArrayFormations.entity.ModEntities;

import net.Lucent.ArrayFormations.entity.renderers.BasicPortalRenderer;

import net.Lucent.ArrayFormations.item.ModCreativeModeTabs;
import net.Lucent.ArrayFormations.item.ModItems;
import net.Lucent.ArrayFormations.network.ModPayloads;
import net.Lucent.ArrayFormations.programmableArrays.FormationNodeWrapper;
import net.Lucent.ArrayFormations.programmableArrays.conditionalNodes.ListInputContainsCondition;
import net.Lucent.ArrayFormations.programmableArrays.constantNodes.ConstantInputNode;
import net.Lucent.ArrayFormations.programmableArrays.coreNodes.GenericBarrierCoreFormationNode;
import net.Lucent.ArrayFormations.programmableArrays.sensoryNodes.EntityDetectionNode;
import net.Lucent.ArrayFormations.programmableArrays.serializers.ProgrammableNodesSerializer;
import net.Lucent.ArrayFormations.screen.ModMenuTypes;
import net.Lucent.ArrayFormations.screen.custom.FormationCoreScreen;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.registries.datamaps.RegisterDataMapTypesEvent;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

import java.util.List;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(ArrayFormationsMod.MOD_ID)
public class ArrayFormationsMod
{
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "array_formations";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();


    private void generateAndSerializeTestArray(){
        //create a simple barrier array with a connection to an entity array a list search array and then a constant doble for radius
        EntityDetectionNode detectionNode = new EntityDetectionNode();
        ListInputContainsCondition listSearchNode = new ListInputContainsCondition();
        ConstantInputNode<String> listStringInput = new ConstantInputNode<String>("Pleeu");
        ConstantInputNode<Double> radiusInputNode = new ConstantInputNode<>(20.0);
        GenericBarrierCoreFormationNode rootNode = new GenericBarrierCoreFormationNode();

        rootNode.addNodeConnection("canRun",listSearchNode,rootNode,"evaluate");
        rootNode.addNodeConnection("barrierRadius",radiusInputNode,rootNode,"data");
        listSearchNode.addNodeConnection("listInput",detectionNode,listSearchNode,"nearbyEntities");
        listSearchNode.addNodeConnection("containsInput",listStringInput,listSearchNode,"data");

        FormationNodeWrapper wrapper = new FormationNodeWrapper(List.of(rootNode));
        ProgrammableNodesSerializer.serialize(wrapper.rootCoreNodes);


    }


    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public ArrayFormationsMod(IEventBus modEventBus, ModContainer modContainer)
    {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        ModCreativeModeTabs.register(modEventBus);

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModBlockEntities.register(modEventBus);
        ModMenuTypes.register(modEventBus);
        ModEntities.register(modEventBus);
        ModDataComponents.register(modEventBus);
        // Register ourselves for server and other game events we are interested in.
        // Note that this is necessary if and only if we want *this* class (ExampleMod) to respond directly to events.
        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.
        generateAndSerializeTestArray();
        NeoForge.EVENT_BUS.register(this);



;
        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");

        if (Config.logDirtBlock)
            LOGGER.info("DIRT BLOCK >> {}", BuiltInRegistries.BLOCK.getKey(Blocks.DIRT));

        LOGGER.info(Config.magicNumberIntroduction + Config.magicNumber);

        Config.items.forEach((item) -> LOGGER.info("ITEM >> {}", item.toString()));
    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {


    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @EventBusSubscriber(modid = MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            // Some client setup code
            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
        }
        @SubscribeEvent
        public static void registerScreens(RegisterMenuScreensEvent event) {
            event.register(ModMenuTypes.FORMATION_CORE_MENU.get(), FormationCoreScreen::new);
        }

        @SubscribeEvent
        public static void registerPayloads(RegisterPayloadHandlersEvent event){
            ModPayloads.registerPayloads(event);
        }
        @SubscribeEvent
        public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {

            event.registerEntityRenderer(ModEntities.BASIC_PORTAL.get(), BasicPortalRenderer::new);

        }
        @SubscribeEvent
        private static void registerDataMapTypes(RegisterDataMapTypesEvent event) {
            event.register(ModDataMaps.FORMATION_FUEL_COST);
            event.register(ModDataMaps.FORMATION_CORE_STATS);
        }

    }
}
