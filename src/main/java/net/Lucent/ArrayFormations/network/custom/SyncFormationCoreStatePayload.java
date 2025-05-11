package net.Lucent.ArrayFormations.network.custom;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufConvertible;
import net.Lucent.ArrayFormations.ArrayFormationsMod;
import net.Lucent.ArrayFormations.block.custom.FormationCoreBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record SyncFormationCoreStatePayload(boolean newState, BlockPos blockPos) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<SyncFormationCoreStatePayload> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(ArrayFormationsMod.MOD_ID, "formation_core_state_sync"));
    public static final StreamCodec<ByteBuf, SyncFormationCoreStatePayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL,
            SyncFormationCoreStatePayload::newState,
            BlockPos.STREAM_CODEC,
            SyncFormationCoreStatePayload::blockPos,
            SyncFormationCoreStatePayload::new
    );
    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handlePayload(SyncFormationCoreStatePayload payload, IPayloadContext context) {
        if(payload.blockPos == null){
            return;
        }
        BlockState blockState = context.player().level().getBlockState(payload.blockPos);
        System.out.println("Changing state");
        blockState.setValue(
                FormationCoreBlock.FORMATION_CORE_STATE,
                payload.newState
        );
        context.player().level().setBlockAndUpdate(payload.blockPos, blockState.setValue( FormationCoreBlock.FORMATION_CORE_STATE, payload.newState));

    }
}
