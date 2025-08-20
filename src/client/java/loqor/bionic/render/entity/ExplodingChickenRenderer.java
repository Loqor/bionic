/*
 * Decompiled with CFR 0.2.2 (FabricMC 7c48b8c4).
 */
package loqor.bionic.render.entity;

import com.google.common.collect.Maps;
import java.util.Map;

import loqor.bionic.core.entities.ExplodingChickenEntity;
import loqor.bionic.render.model.ExplodingChickenModel;
import loqor.bionic.render.render_states.ExplodingChickenRenderState;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.BabyModelPair;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.state.ChickenEntityRenderState;
import net.minecraft.client.render.entity.state.CreeperEntityRenderState;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.client.texture.MissingSprite;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.passive.ChickenVariant;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

@Environment(value=EnvType.CLIENT)
public class ExplodingChickenRenderer
        extends MobEntityRenderer<ExplodingChickenEntity, ExplodingChickenRenderState, ExplodingChickenModel> {
    private final Map<ChickenVariant.Model, BabyModelPair<ExplodingChickenModel>> babyModelPairMap;

    public ExplodingChickenRenderer(EntityRendererFactory.Context context) {
        super(context, new ExplodingChickenModel(context.getPart(EntityModelLayers.CHICKEN)), 0.3f);
        this.babyModelPairMap = ExplodingChickenRenderer.createBabyModelPairMap(context);
    }

    private static Map<ChickenVariant.Model, BabyModelPair<ExplodingChickenModel>> createBabyModelPairMap(EntityRendererFactory.Context context) {
        return Maps.newEnumMap(
                Map.of(
                        ChickenVariant.Model.NORMAL,
                        new BabyModelPair<>(
                                new ExplodingChickenModel(context.getPart(EntityModelLayers.CHICKEN)),
                                new ExplodingChickenModel(context.getPart(EntityModelLayers.CHICKEN_BABY))
                        )
                )
        );
    }

    @Override
    public void render(ExplodingChickenRenderState chickenEntityRenderState, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        if (chickenEntityRenderState.variant == null) {
            return;
        }
        this.model = this.babyModelPairMap.get(chickenEntityRenderState.variant.modelAndTexture().model()).get(chickenEntityRenderState.baby);
        super.render(chickenEntityRenderState, matrixStack, vertexConsumerProvider, i);
    }

    @Override
    public Identifier getTexture(ExplodingChickenRenderState chickenEntityRenderState) {
        return chickenEntityRenderState.variant == null ? MissingSprite.getMissingSpriteId() : chickenEntityRenderState.variant.modelAndTexture().asset().texturePath();
    }

    @Override
    protected void scale(ExplodingChickenRenderState chickenEntityRenderState, MatrixStack matrixStack) {
        float f = chickenEntityRenderState.fuseTime;
        float g = 1.0f + MathHelper.sin(f * 100.0f) * f * 0.01f;
        f = MathHelper.clamp((float)f, 0.0f, (float)1.0f);
        f *= f;
        f *= f;
        float h = (1.0f + f * 0.4f) * g;
        float i = (1.0f + f * 0.1f) / g;
        matrixStack.scale(h, i, h);
    }

    @Override
    protected float getAnimationCounter(ExplodingChickenRenderState chickenEntityRenderState) {
        float f = chickenEntityRenderState.fuseTime;
        if ((int)(f * 10.0f) % 2 == 0) {
            return 0.0f;
        }
        return MathHelper.clamp((float)f, (float)0.5f, (float)1.0f);
    }

    @Override
    public ExplodingChickenRenderState createRenderState() {
        return new ExplodingChickenRenderState();
    }

    @Override
    public void updateRenderState(ExplodingChickenEntity chickenEntity, ExplodingChickenRenderState chickenEntityRenderState, float f) {
        super.updateRenderState(chickenEntity, chickenEntityRenderState, f);
        chickenEntityRenderState.flapProgress = MathHelper.lerp((float)f, (float)chickenEntity.lastFlapProgress, (float)chickenEntity.flapProgress);
        chickenEntityRenderState.maxWingDeviation = MathHelper.lerp((float)f, (float)chickenEntity.lastMaxWingDeviation, (float)chickenEntity.maxWingDeviation);
        chickenEntityRenderState.variant = (ChickenVariant)chickenEntity.getVariant().value();
        chickenEntityRenderState.fuseTime = chickenEntity.getLerpedFuseTime(f);
    }
}

