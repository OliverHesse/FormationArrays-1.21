package net.Lucent.ArrayFormations.network.custom.serverbound;

import io.netty.buffer.ByteBuf;
import net.Lucent.ArrayFormations.ArrayFormationsMod;
import net.Lucent.ArrayFormations.block.AbstractClasses.AbstractFormationCoreBlockEntity;
import net.Lucent.ArrayFormations.util.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.Arrays;

public record SyncFormationFlagRotationPayload(int flagSlot,int flagRotation, BlockPos blockPos) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<SyncFormationFlagRotationPayload> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(ArrayFormationsMod.MOD_ID, "formation_flag_rotation"));
    public static final StreamCodec<ByteBuf, SyncFormationFlagRotationPayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT,
            SyncFormationFlagRotationPayload::flagSlot,
            ByteBufCodecs.VAR_INT,
            SyncFormationFlagRotationPayload::flagRotation,
            BlockPos.STREAM_CODEC,
            SyncFormationFlagRotationPayload::blockPos,
            SyncFormationFlagRotationPayload::new
    );
    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
    public static void handlePayload(SyncFormationFlagRotationPayload payload, IPayloadContext context) {
        if(payload.blockPos == null){
            return;
        }
        BlockState blockState = context.player().level().getBlockState(payload.blockPos);
        BlockEntity blockEntity = context.player().level().getBlockEntity(payload.blockPos);
        if(blockEntity == null) return;
        System.out.println("setting new rotation");
        if(blockState.is(ModTags.Blocks.FORMATION_CORE)){
            ((AbstractFormationCoreBlockEntity) blockEntity).blueprintSlotData.get(payload.flagSlot).rotation = payload.flagRotation;
        }

    }
}
