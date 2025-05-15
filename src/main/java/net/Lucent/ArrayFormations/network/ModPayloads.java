package net.Lucent.ArrayFormations.network;

import net.Lucent.ArrayFormations.ArrayFormationsMod;

import net.Lucent.ArrayFormations.network.custom.clientbound.SyncFormationCoreQiAmountPayload;
import net.Lucent.ArrayFormations.network.custom.serverbound.SyncArrayBlueprintQuickMovePayload;
import net.Lucent.ArrayFormations.network.custom.serverbound.SyncFormationCoreStatePayload;
import net.Lucent.ArrayFormations.network.custom.serverbound.SyncFormationFlagRotationPayload;
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
        registrar.playToServer(
                SyncFormationFlagRotationPayload.TYPE,
                SyncFormationFlagRotationPayload.STREAM_CODEC,
                SyncFormationFlagRotationPayload::handlePayload
        );
        registrar.playToServer(
                SyncArrayBlueprintQuickMovePayload.TYPE,
                SyncArrayBlueprintQuickMovePayload.STREAM_CODEC,
                SyncArrayBlueprintQuickMovePayload::handlePayload
        );


        registrar.playToClient(
                SyncFormationCoreQiAmountPayload.TYPE,
                SyncFormationCoreQiAmountPayload.STREAM_CODEC,
                SyncFormationCoreQiAmountPayload::handlePayload);
    }
}
