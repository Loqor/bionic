package loqor.bionic.render.feature_renderer;

import loqor.bionic.Bionic;
import loqor.bionic.core.utils.CustomRendering;
import loqor.bionic.render.model.CactusArmorModel;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.equipment.EquipmentRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class CactusArmorFeatureRenderer extends FeatureRenderer<PlayerEntityRenderState, PlayerEntityModel> {

    private static final Identifier TEXTURE = Bionic.of("textures/entity/cactus_onesie.png");

    private final CactusArmorModel cactusArmorModel;

    public CactusArmorFeatureRenderer(FeatureRendererContext<PlayerEntityRenderState, PlayerEntityModel> context, EquipmentRenderer entityModels) {
        super(context);
        this.cactusArmorModel = new CactusArmorModel(CactusArmorModel.getTexturedModelData().createModel());
    }

    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, PlayerEntityRenderState playerEntityRenderState, float f, float g) {
        if (playerEntityRenderState.equippedChestStack.getItem() instanceof CustomRendering) {
            VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(this.cactusArmorModel.getLayer(TEXTURE));
            int j = LivingEntityRenderer.getOverlay(playerEntityRenderState, 0.0F);
            this.getContextModel().copyTransforms(this.cactusArmorModel);
            this.cactusArmorModel.setAngles(playerEntityRenderState);
            this.cactusArmorModel.render(matrixStack, vertexConsumer, i, j);
        }
    }
}
