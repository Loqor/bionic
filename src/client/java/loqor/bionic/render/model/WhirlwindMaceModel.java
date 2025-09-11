package loqor.bionic.render.model;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.*;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;

public class WhirlwindMaceModel extends EntityModel<Entity> {

    private float rotationTick;

    public final ModelPart bone;
    public final ModelPart handle;
    public final ModelPart wind_bone;
    public final ModelPart wind_handle;
    public final ModelPart wind_handle_top;
    public final ModelPart wind_handle_bottom;
    public final ModelPart wind_handle_center;
    public final ModelPart mace;
    public final ModelPart wind_charge;
    public final ModelPart wind;
    public final ModelPart wind_outer;
    public final ModelPart wind_inner;
    public WhirlwindMaceModel(ModelPart root) {
        this.bone = root.getChild("bone");
        this.handle = this.bone.getChild("handle");
        this.wind_bone = root.getChild("wind_bone");
        this.wind_handle = this.wind_bone.getChild("wind_handle");
        this.wind_handle_top = this.wind_handle.getChild("wind_handle_top");
        this.wind_handle_bottom = this.wind_handle.getChild("wind_handle_bottom");
        this.wind_handle_center = this.wind_handle.getChild("wind_handle_center");
        this.mace = this.wind_bone.getChild("mace");
        this.wind_charge = this.mace.getChild("wind_charge");
        this.wind = this.mace.getChild("wind");
        this.wind_outer = this.wind.getChild("wind_outer");
        this.wind_inner = this.wind.getChild("wind_inner");
    }
    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData bone = modelPartData.addChild("bone", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        ModelPartData handle = bone.addChild("handle", ModelPartBuilder.create().uv(0, 0).cuboid(-1.5F, -15.5F, -0.5F, 2.0F, 17.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.5F, -3.5F, -0.5F));

        ModelPartData handle_r1 = handle.addChild("handle_r1", ModelPartBuilder.create().uv(9, 0).cuboid(-1.5F, -1.5F, -1.5F, 3.0F, 3.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(-0.5F, 2.0F, 0.5F, 0.0F, 0.0F, 0.7854F));

        ModelPartData wind_bone = modelPartData.addChild("wind_bone", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        ModelPartData wind_handle = wind_bone.addChild("wind_handle", ModelPartBuilder.create(), ModelTransform.of(0.0F, -11.525F, 0.0F, 0.0F, 0.0F, -3.1416F));

        ModelPartData wind_handle_top = wind_handle.addChild("wind_handle_top", ModelPartBuilder.create().uv(0, 56).cuboid(-2.0F, -1.5F, -2.0F, 4.0F, 2.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 4.0F, 0.0F));

        ModelPartData wind_handle_bottom = wind_handle.addChild("wind_handle_bottom", ModelPartBuilder.create().uv(34, 56).cuboid(-2.0F, -3.5F, -2.0F, 4.0F, 2.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -4.0F, 0.0F));

        ModelPartData wind_handle_center = wind_handle.addChild("wind_handle_center", ModelPartBuilder.create().uv(0, 43).cuboid(-2.0F, -2.5F, -2.0F, 4.0F, 2.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData mace = wind_bone.addChild("mace", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -20.025F, 0.0F));

        ModelPartData wind_charge = mace.addChild("wind_charge", ModelPartBuilder.create().uv(0, 32).cuboid(-2.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, new Dilation(1.0F)), ModelTransform.of(0.0F, -2.0F, 0.0F, 0.0F, 0.0F, -3.1416F));

        ModelPartData wind = mace.addChild("wind", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData wind_outer = wind.addChild("wind_outer", ModelPartBuilder.create().uv(15, 52).cuboid(-4.0F, -1.0F, -4.0F, 8.0F, 2.0F, 8.0F, new Dilation(1.0F)), ModelTransform.of(0.0F, -2.0F, 0.0F, 0.0F, 0.0F, -3.1416F));

        ModelPartData wind_inner = wind.addChild("wind_inner", ModelPartBuilder.create().uv(0, 41).cuboid(-3.0F, -2.0F, -3.0F, 6.0F, 4.0F, 6.0F, new Dilation(1.0F)), ModelTransform.of(0.0F, -2.0F, 0.0F, 0.0F, 0.0F, -3.1416F));
        return TexturedModelData.of(modelData, 64, 64);
    }
    @Override
    public void setAngles(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {}

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, int color) {
        bone.render(matrices, vertices, light, overlay, color);
    }

    public void setItemModelAngles(PlayerEntity player, ItemStack stack) {
        float tickDelta = MinecraftClient.getInstance().getRenderTickCounter().getTickDelta(false);
        this.rotationTick += tickDelta;
        if (player.getMainHandStack() == stack && player.isUsingItem()) {
            int useTime = stack.getMaxUseTime(player) - player.getItemUseTimeLeft();
            float useTimeRot = useTime > 40 ? rotationTick / 10f : rotationTick / 20;
            this.wind_charge.yaw = useTimeRot;
            this.wind_outer.yaw = useTimeRot;
            this.wind_inner.yaw = -useTimeRot;
        } else {
            float spin = this.rotationTick / 40.0f;
            this.wind_charge.yaw = spin;
            this.wind_outer.yaw = spin;
            this.wind_inner.yaw = -spin;
        }
        float handleSpin = this.rotationTick / 30.0F;
        this.wind_handle_top.yaw = handleSpin;
        this.wind_handle_bottom.yaw = handleSpin;
        this.wind_handle_center.yaw = -handleSpin;
    }
}