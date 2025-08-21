package loqor.bionic.render.entity;

import loqor.bionic.core.entities.ExplodingChickenEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.ChickenEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class ExplodingChickenRenderer extends MobEntityRenderer<ExplodingChickenEntity, ChickenEntityModel<ExplodingChickenEntity>> {
    private static final Identifier TEXTURE = Identifier.ofVanilla("textures/entity/chicken.png");

    public ExplodingChickenRenderer(EntityRendererFactory.Context context) {
        super(context, new ChickenEntityModel<>(context.getPart(EntityModelLayers.CHICKEN)), 0.3F);
    }

    public Identifier getTexture(ExplodingChickenEntity explodingChickenEntity) {
        return TEXTURE;
    }

    protected float getAnimationProgress(ExplodingChickenEntity explodingChickenEntity, float f) {
        float g = MathHelper.lerp(f, explodingChickenEntity.prevFlapProgress, explodingChickenEntity.flapProgress);
        float h = MathHelper.lerp(f, explodingChickenEntity.prevMaxWingDeviation, explodingChickenEntity.maxWingDeviation);
        return (MathHelper.sin(g) + 1.0F) * h;
    }

    protected void scale(ExplodingChickenEntity explodingChickenEntity, MatrixStack matrixStack, float f) {
        float g = explodingChickenEntity.getClientFuseTime(f);
        float h = 1.0F + MathHelper.sin(g * 100.0F) * g * 0.01F;
        g = MathHelper.clamp(g, 0.0F, 1.0F);
        g *= g;
        g *= g;
        float i = (1.0F + g * 0.4F) * h;
        float j = (1.0F + g * 0.1F) / h;
        matrixStack.scale(i, j, i);
    }

    protected float getAnimationCounter(ExplodingChickenEntity explodingChickenEntity, float f) {
        float g = explodingChickenEntity.getClientFuseTime(f);
        return (int)(g * 10.0F) % 2 == 0 ? 0.0F : MathHelper.clamp(g, 0.5F, 1.0F);
    }
}
