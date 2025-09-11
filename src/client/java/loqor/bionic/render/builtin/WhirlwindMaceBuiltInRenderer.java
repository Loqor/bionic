package loqor.bionic.render.builtin;

import loqor.bionic.Bionic;
import loqor.bionic.render.model.WhirlwindMaceModel;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.*;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
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
        PlayerEntity player = MinecraftClient.getInstance().player;
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
                matrices.translate(mode == ModelTransformationMode.FIXED ? 0.25f : 0.75f, -0.775f, mode == ModelTransformationMode.FIXED ? -0.5f : 0.5f);
                matrices.scale(0.6f, 0.6f, 0.6f);
                matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(mode == ModelTransformationMode.FIXED ? -40f : 40f));
                matrices.multiply(RotationAxis.NEGATIVE_Y.rotationDegrees(22.5f));
            }
            case THIRD_PERSON_LEFT_HAND, THIRD_PERSON_RIGHT_HAND -> {
                if (player.getMainHandStack() == stack) {
                    if (player.isUsingItem()) {
                        if (ModelTransformationMode.THIRD_PERSON_LEFT_HAND == mode) {
                            matrices.translate(0.35f, -1.6f, -0.65f);
                            matrices.multiply(RotationAxis.NEGATIVE_Y.rotationDegrees(31f));
                            matrices.multiply(RotationAxis.NEGATIVE_Z.rotationDegrees(10));
                        }if (ModelTransformationMode.THIRD_PERSON_RIGHT_HAND == mode) {
                            matrices.translate(0.55f, -1.6f, -0.55f);
                            matrices.multiply(RotationAxis.NEGATIVE_Y.rotationDegrees(-31f));
                            matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(5));
                        }
                        break;
                    }
                }
                matrices.translate(0.5f, -1.5f, -0.6f);
            }
            default -> {
                matrices.translate(0.5f, -1.8f, -0.5f);
            }
        }

        this.maceModel.setItemModelAngles(player, stack);
        float m = (float)player.age + MinecraftClient.getInstance().getRenderTickCounter().getTickDelta(false);


        VertexConsumerProvider.Immediate immediate = MinecraftClient.getInstance().getBufferBuilders().getEntityVertexConsumers();

        VertexConsumer vertexConsumer2 = ItemRenderer.getDynamicDisplayGlintConsumer(vertexConsumers, RenderLayer.getEntityTranslucent(TEXTURE), matrices.peek());
        VertexConsumer vertexConsumer = immediate.getBuffer(RenderLayer.getEntityTranslucent(TEXTURE));

        VertexConsumer scrollingVertexConsumer = new VertexConsumer() {
            @Override
            public VertexConsumer vertex(float x, float y, float z) {
                return vertexConsumer.vertex(x, y, z);
            }

            @Override
            public VertexConsumer color(int red, int green, int blue, int alpha) {
                return vertexConsumer.color(red, green, blue, alpha);
            }
            @Override
            public VertexConsumer texture(float u, float v) {
                // Scroll the u coordinate
                return vertexConsumer.texture(u + (getXOffset(m) % 1.0F), v);
            }
            @Override
            public VertexConsumer overlay(int u, int v) {
                return vertexConsumer.overlay(u, v);
            }
            @Override
            public VertexConsumer light(int u, int v) {
                return vertexConsumer.light(u, v);
            }
            @Override
            public VertexConsumer normal(float x, float y, float z) {
                return vertexConsumer.normal(x, y, z);
            }
        };

        this.maceModel.wind_bone.render(matrices, scrollingVertexConsumer, light, overlay);
        this.maceModel.bone.render(matrices, stack.hasGlint() ? vertexConsumer2 : vertexConsumers.getBuffer(RenderLayer.getEntityTranslucent(TEXTURE)),
                light, overlay);
        matrices.pop();
    }

    private float getXOffset(float tickDelta) {
        return tickDelta * 0.02F;
    }
}
