package loqor.bionic.render.entity;

import loqor.bionic.Bionic;
import loqor.bionic.core.entities.ExplodingEggEntity;
import loqor.bionic.render.model.EggGrenadeModel;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;


public class EggGrenadeItemRenderer<T extends ExplodingEggEntity> extends FlyingItemEntityRenderer<T> {
    float scale;
    private EggGrenadeModel model;
    public EggGrenadeItemRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, 0.25f, true);
        this.model = new EggGrenadeModel(EggGrenadeModel.getTexturedModelData().createModel());
    }

    public static int getOverlay(float whiteOverlayProgress) {
        return OverlayTexture.packUv(OverlayTexture.getU(whiteOverlayProgress), OverlayTexture.getV(false));
    }

    @Override
    public void render(T entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        this.scale = 0.5f;
        int q = getOverlay(this.getAnimationCounter(entity, tickDelta));
        if (entity.age >= 2 || !(this.dispatcher.camera.getFocusedEntity().squaredDistanceTo(entity) < 12.25)) {
            matrices.push();
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(entity.getYaw()));
            matrices.multiply(RotationAxis.NEGATIVE_X.rotationDegrees(entity.getPitch() + 180f));
            matrices.scale(this.scale, this.scale, this.scale);
            this.scale(entity, matrices, tickDelta);
            matrices.translate(0, -1, 0);
            this.model.render(matrices, vertexConsumers.getBuffer(RenderLayer.getEntityCutoutNoCull(Bionic.of("textures/item/egg_grenade.png"))), light, q);
            matrices.pop();
        }
    }

    protected void scale(T explodingEggEntity, MatrixStack matrixStack, float f) {
        float g = explodingEggEntity.getClientFuseTime(f);
        float h = 1.0F + MathHelper.sin(g * 100.0f) * g * 0.01F;
        g = MathHelper.clamp(g, 0.0F, 1.0F);
        g *= g;
        g *= g;
        float i = (1.0F + g * 0.4F) * h;
        float j = (1.0F + g * 0.1F) / h;
        matrixStack.scale(i, j, i);
    }

    protected float getAnimationCounter(T explodingEggEntity, float f) {
        float g = explodingEggEntity.getClientFuseTime(f);
        return (int)(g * 10.0F) % 2 == 0 ? 0.0F : MathHelper.clamp(g, 0.5F, 1.0F);
    }
}
