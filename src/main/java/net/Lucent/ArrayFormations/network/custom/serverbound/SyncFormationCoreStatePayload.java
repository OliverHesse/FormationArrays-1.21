package net.Lucent.ArrayFormations.network.custom.serverbound;

import io.netty.buffer.ByteBuf;
import net.Lucent.ArrayFormations.ArrayFormationsMod;
import net.Lucent.ArrayFormations.block.AbstractClasses.AbstractFormationCoreBlockEntity;
import net.Lucent.ArrayFormations.block.custom.BaseFormationCoreBlock;
import net.Lucent.ArrayFormations.util.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
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
        if(!blockState.is(ModTags.Blocks.FORMATION_CORE)){
            return;
        }
        System.out.println("Changing state");
        blockState.setValue(
                BaseFormationCoreBlock.FORMATION_CORE_STATE,
                payload.newState
        );
        context.player().level().setBlockAndUpdate(payload.blockPos, blockState.setValue( BaseFormationCoreBlock.FORMATION_CORE_STATE, payload.newState));
        if(!payload.newState){
            ((AbstractFormationCoreBlockEntity) context.player().level().getBlockEntity(payload.blockPos)).cancelFormationBlueprints();
        }
    }
}
