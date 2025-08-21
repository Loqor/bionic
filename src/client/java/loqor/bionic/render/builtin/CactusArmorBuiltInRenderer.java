package loqor.bionic.render.builtin;

import loqor.bionic.render.feature_renderer.CactusArmorFeatureRenderer;
import loqor.bionic.render.model.CactusArmorModel;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.RotationAxis;

public class CactusArmorBuiltInRenderer implements BuiltinItemRendererRegistry.DynamicItemRenderer {
    private CactusArmorModel armorModel;
    public CactusArmorBuiltInRenderer() {
        this.armorModel = new CactusArmorModel<>(CactusArmorModel.getTexturedModelData().createModel());
    }
    @Override
    public void render(ItemStack stack, ModelTransformationMode mode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        matrices.push();
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(180f));
        switch (mode) {
            case FIRST_PERSON_LEFT_HAND, FIRST_PERSON_RIGHT_HAND -> {
                matrices.translate(0.5f, -0.75f, -0.5f);
                matrices.scale(0.5f, 0.5f, 0.5f);
                float yRotation = mode == ModelTransformationMode.FIRST_PERSON_LEFT_HAND ? 45f : -45f;
                matrices.multiply(RotationAxis.NEGATIVE_Y.rotationDegrees(yRotation));
            }
            case FIXED -> {
                matrices.translate(0.5f, -0.55f, -0.5);
                matrices.scale(0.6f, 0.6f, 0.6f);
                matrices.multiply(RotationAxis.NEGATIVE_Y.rotationDegrees(180));
            }
            case GUI -> {
                matrices.translate(0.5f, -0.56f, 0.5f);
                matrices.scale(0.6f, 0.6f, 0.6f);
                matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(22.5f));
                matrices.multiply(RotationAxis.NEGATIVE_Y.rotationDegrees(45));
            }
            default -> {
                matrices.translate(0.5f, -0.75f, -0.5f);
                matrices.scale(0.5f, 0.5f, 0.5f);
                matrices.multiply(RotationAxis.NEGATIVE_Y.rotationDegrees(45));
            }
        }
        this.armorModel.render(matrices, vertexConsumers.getBuffer(this.armorModel.getLayer(CactusArmorFeatureRenderer.TEXTURE)), light, overlay);
        matrices.pop();
    }
}
