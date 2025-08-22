package loqor.bionic.render.builtin;

import com.mojang.blaze3d.systems.RenderSystem;
import loqor.bionic.Bionic;
import loqor.bionic.core.items.WhirlwindMaceItem;
import loqor.bionic.render.feature_renderer.CactusArmorFeatureRenderer;
import loqor.bionic.render.model.CactusArmorModel;
import loqor.bionic.render.model.WhirlwindMaceModel;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.TridentEntityModel;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;

public class WhirlwindMaceBuiltInRenderer implements BuiltinItemRendererRegistry.DynamicItemRenderer {
    public static final Identifier TEXTURE = Bionic.of("textures/item/whirlwind_mace_wrecker.png");
    private final WhirlwindMaceModel maceModel;

    public WhirlwindMaceBuiltInRenderer() {
        this.maceModel = new WhirlwindMaceModel(WhirlwindMaceModel.getTexturedModelData().createModel());
    }
    @Override
    public void render(ItemStack stack, ModelTransformationMode mode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        AbstractClientPlayerEntity player = MinecraftClient.getInstance().player;
        if (player == null) return;

        matrices.push();
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(180f));
        switch (mode) {
            case FIRST_PERSON_LEFT_HAND, FIRST_PERSON_RIGHT_HAND -> {
                matrices.translate(0.65f, -1.1f, -0.85f);
                matrices.scale(0.5f, 0.5f, 0.5f);
                matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(22.5f));
            }
            case GUI, FIXED -> {
                matrices.translate(mode == ModelTransformationMode.FIXED ? 0.25f : 0.75f, -0.825f, mode == ModelTransformationMode.FIXED ? -0.5f : 0.5f);
                matrices.scale(0.6f, 0.6f, 0.6f);
                matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(mode == ModelTransformationMode.FIXED ? -40f : 40f));
                matrices.multiply(RotationAxis.NEGATIVE_Y.rotationDegrees(22.5f));
            }
            case THIRD_PERSON_LEFT_HAND, THIRD_PERSON_RIGHT_HAND -> {
                matrices.translate(0.5f, -1.5f, -0.6f);
            }
            default -> {
                matrices.translate(0.5f, -1.8f, -0.5f);
            }
        }

        this.maceModel.setItemModelAngles(player, stack);

        VertexConsumer vertexConsumer2 = ItemRenderer.getDynamicDisplayGlintConsumer(vertexConsumers, this.maceModel.getLayer(TEXTURE), matrices.peek());

        this.maceModel.render(matrices, stack.hasGlint() ? vertexConsumer2 : vertexConsumers.getBuffer(this.maceModel.getLayer(TEXTURE)), light, overlay);
        matrices.pop();
    }
}
