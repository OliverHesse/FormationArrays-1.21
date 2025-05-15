package net.Lucent.ArrayFormations.entity.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.Lucent.ArrayFormations.ArrayFormationsMod;

import net.Lucent.ArrayFormations.entity.custom.AbstractPortalEntity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import org.joml.Matrix4f;

import java.io.StringWriter;

public class BasicPortalRenderer <T extends Entity> extends EntityRenderer<AbstractPortalEntity> {


    public BasicPortalRenderer(EntityRendererProvider.Context context) {
        super(context);

    }

    @Override
    public void render(AbstractPortalEntity p_entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        poseStack.pushPose();
        Minecraft mc = Minecraft.getInstance();
        float yaw = mc.getEntityRenderDispatcher().camera.getYRot();
        float pitch = mc.getEntityRenderDispatcher().camera.getXRot();
        poseStack.mulPose(Axis.YP.rotationDegrees(-yaw));
        poseStack.mulPose(Axis.XP.rotationDegrees(pitch));

        float radius = 0.5F; // size of the circle/quad


        VertexConsumer consumer = bufferSource.getBuffer(RenderType.solid());
        Matrix4f matrix = poseStack.last().pose();

        // Render a flat colored quad (simulate a circle — visually it's a square)
        drawQuad(consumer, matrix, -radius, -radius, radius, radius, packedLight);

        poseStack.popPose();
        super.render(p_entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }

    private void drawQuad(VertexConsumer buffer, Matrix4f matrix,
                          float x1, float y1, float x2, float y2, int light) {
        int r = 0, g = 0, b = 255, a = 255; // solid blue

        buffer.addVertex(matrix, x1, y1, 0).setOverlay(OverlayTexture.NO_OVERLAY).setNormal(0, 0, 1).setColor(r, g, b, a).setUv(0, 0).setLight(light);
        buffer.addVertex(matrix, x2, y1, 0).setOverlay(OverlayTexture.NO_OVERLAY).setNormal(0, 0, 1).setColor(r, g, b, a).setUv(1, 0).setLight(light);
        buffer.addVertex(matrix, x2, y2, 0).setOverlay(OverlayTexture.NO_OVERLAY).setNormal(0, 0, 1).setColor(r, g, b, a).setUv(1, 1).setLight(light);
        buffer.addVertex(matrix, x1, y2, 0).setOverlay(OverlayTexture.NO_OVERLAY).setNormal(0, 0, 1).setColor(r, g, b, a).setUv(0, 1).setLight(light);
    }

    @Override
    public ResourceLocation getTextureLocation(AbstractPortalEntity entity) {
        return ResourceLocation.fromNamespaceAndPath(ArrayFormationsMod.MOD_ID,"textures/entity/portal/portal_frames.png");
    }






}
