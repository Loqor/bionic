package loqor.bionic.render.feature_renderer;

import loqor.bionic.core.items.WhirlwindMaceItem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.BreezeEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.mob.BreezeEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class BreezeBodyPlayerFeatureRenderer extends FeatureRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> {
    private static final Identifier TEXTURE = Identifier.ofVanilla("textures/entity/breeze/breeze_wind.png");
    private final BreezeEntityModel<BreezeEntity> model;

    public BreezeBodyPlayerFeatureRenderer(EntityRendererFactory.Context context, FeatureRendererContext<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> featureRendererContext) {
        super(featureRendererContext);
        this.model = new BreezeEntityModel<>(context.getPart(EntityModelLayers.BREEZE_WIND));
    }

    @Override
    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, AbstractClientPlayerEntity player, float f, float g, float h, float j, float k, float l) {
        ItemStack stack = player.getMainHandStack();

        if (!(stack.getItem() instanceof WhirlwindMaceItem)) return;

        if (!player.isUsingItem()) return;

        float m = (float)player.age + h;
        VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(RenderLayer.getBreezeWind(TEXTURE, this.getXOffset(m) % 1.0F, 0.0F));
        ModelPart breezeBody = this.model.getWindBody().getChild(EntityModelPartNames.WIND_BOTTOM).getChild(EntityModelPartNames.WIND_MID);
        breezeBody.traverse().forEach(ModelPart::resetTransform);
        // Yeah so like the what in the ever living what is that kind of naming scheme Mojang? - Loqor
        matrixStack.push();
        matrixStack.translate(0, 1.5f, 0);
        breezeBody.render(matrixStack, vertexConsumer, i, OverlayTexture.DEFAULT_UV);
        matrixStack.pop();
    }

    private float getXOffset(float tickDelta) {
        return tickDelta * 0.02F;
    }
}
