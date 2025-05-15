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

public record SyncArrayBlueprintQuickMovePayload(int slot, BlockPos blockPos) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<SyncArrayBlueprintQuickMovePayload> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(ArrayFormationsMod.MOD_ID, "array_blueprint_quick_move_sync"));
    public static final StreamCodec<ByteBuf, SyncArrayBlueprintQuickMovePayload> STREAM_CODEC = StreamCodec.composite(

            ByteBufCodecs.VAR_INT,
            SyncArrayBlueprintQuickMovePayload::slot,
            BlockPos.STREAM_CODEC,
            SyncArrayBlueprintQuickMovePayload::blockPos,
            SyncArrayBlueprintQuickMovePayload::new
    );
    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
    public static void handlePayload(SyncArrayBlueprintQuickMovePayload payload, IPayloadContext context) {
        if(payload.blockPos == null) return;
        BlockState blockState = context.player().level().getBlockState(payload.blockPos);
        BlockEntity blockEntity = context.player().level().getBlockEntity(payload.blockPos);
        System.out.println("setting new rotation");
        if(blockEntity == null) return;

        if(blockState.is(ModTags.Blocks.FORMATION_CORE)){
            ((AbstractFormationCoreBlockEntity) blockEntity).syncWithQuickMove(payload.slot);
        }

    }
}
