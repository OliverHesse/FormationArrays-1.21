package net.Lucent.ArrayFormations.network.custom.clientbound;

import io.netty.buffer.ByteBuf;
import net.Lucent.ArrayFormations.ArrayFormationsMod;
import net.Lucent.ArrayFormations.block.AbstractClasses.AbstractFormationCoreBlockEntity;
import net.Lucent.ArrayFormations.network.custom.serverbound.SyncArrayBlueprintQuickMovePayload;
import net.Lucent.ArrayFormations.network.custom.serverbound.SyncFormationCoreStatePayload;
import net.Lucent.ArrayFormations.util.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.math.BigDecimal;

public record SyncFormationCoreQiAmountPayload(String currentQi,BlockPos blockPos) implements CustomPacketPayload{
    public static final CustomPacketPayload.Type<SyncFormationCoreQiAmountPayload> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(ArrayFormationsMod.MOD_ID, "formation_core_qi_amount_sync"));
    public static final StreamCodec<ByteBuf, SyncFormationCoreQiAmountPayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8,
            SyncFormationCoreQiAmountPayload::currentQi,
            BlockPos.STREAM_CODEC,
            SyncFormationCoreQiAmountPayload::blockPos,
            SyncFormationCoreQiAmountPayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handlePayload(SyncFormationCoreQiAmountPayload payload, IPayloadContext context) {
        if(payload.blockPos == null) return;
        BlockState blockState = context.player().level().getBlockState(payload.blockPos);
        BlockEntity blockEntity = context.player().level().getBlockEntity(payload.blockPos);
        System.out.println("setting new rotation");
        if(blockEntity == null) return;

        if(blockState.is(ModTags.Blocks.FORMATION_CORE)){
            ((AbstractFormationCoreBlockEntity) blockEntity).qiContainer.setQiAmount(payload.currentQi);
        }

    }
}
