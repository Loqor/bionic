package loqor.bionic.render.feature_renderer;

import loqor.bionic.Bionic;
import loqor.bionic.core.utils.HasCustomItemRendering;
import loqor.bionic.render.model.CactusArmorModel;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class CactusArmorFeatureRenderer extends FeatureRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> {

    public static final Identifier TEXTURE = Bionic.of("textures/entity/cactus_onesie.png");

    private final CactusArmorModel cactusArmorModel;

    public CactusArmorFeatureRenderer(FeatureRendererContext<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> featureRendererContext) {
        super(featureRendererContext);
        this.cactusArmorModel = new CactusArmorModel(CactusArmorModel.getTexturedModelData().createModel());
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, AbstractClientPlayerEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        if (entity.getEquippedStack(EquipmentSlot.CHEST).getItem() instanceof HasCustomItemRendering) {
            VertexConsumer vertexConsumer = vertexConsumers.getBuffer(this.cactusArmorModel.getLayer(TEXTURE));
            int j = LivingEntityRenderer.getOverlay(entity, 0.0F);
            this.cactusArmorModel.setAngles(entity, limbAngle, limbDistance, animationProgress, headYaw, headPitch);
            this.cactusArmorModel.head.copyTransform(this.getContextModel().head);
            this.cactusArmorModel.body.copyTransform(this.getContextModel().body);
            this.cactusArmorModel.render(matrices, vertexConsumer, light, j);
        }
    }
}
