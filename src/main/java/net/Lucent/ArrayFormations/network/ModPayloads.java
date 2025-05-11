package net.Lucent.ArrayFormations.network;

import net.Lucent.ArrayFormations.ArrayFormationsMod;
import net.Lucent.ArrayFormations.network.custom.SyncFormationCoreStatePayload;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public class ModPayloads {

    @SubscribeEvent
    public static void registerPayloads(final RegisterPayloadHandlersEvent event){
        final PayloadRegistrar registrar =event.registrar(ArrayFormationsMod.MOD_ID).versioned("1.0");
        registrar.playToServer(
                SyncFormationCoreStatePayload.TYPE,
                SyncFormationCoreStatePayload.STREAM_CODEC,
                SyncFormationCoreStatePayload::handlePayload

        );
    }
}
