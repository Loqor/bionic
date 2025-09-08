package loqor.bionic.render.model;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;

public class EggGrenadeModel extends EntityModel<Entity> {
	private final ModelPart bone;
	private final ModelPart egg;
	private final ModelPart handle;

	public EggGrenadeModel(ModelPart root) {
		this.bone = root.getChild("bone");
		this.egg = this.bone.getChild("egg");
		this.handle = this.bone.getChild("handle");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData bone = modelPartData.addChild("bone", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 19.5F, 0.0F));

		ModelPartData egg = bone.addChild("egg", ModelPartBuilder.create().uv(0, 16).cuboid(-7.0F, -2.0F, -1.0F, 8.0F, 1.0F, 8.0F, new Dilation(0.0F))
				.uv(0, 0).cuboid(-7.0F, -7.0F, -2.0F, 8.0F, 5.0F, 10.0F, new Dilation(0.0F))
				.uv(29, 34).cuboid(1.0F, -7.0F, -1.0F, 1.0F, 5.0F, 8.0F, new Dilation(0.0F))
				.uv(0, 37).cuboid(-8.0F, -7.0F, -1.0F, 1.0F, 5.0F, 8.0F, new Dilation(0.0F))
				.uv(48, 39).cuboid(-1.0F, -10.0F, 1.0F, 1.0F, 1.0F, 4.0F, new Dilation(0.0F))
				.uv(48, 45).cuboid(-6.0F, -10.0F, 1.0F, 1.0F, 1.0F, 4.0F, new Dilation(0.0F))
				.uv(33, 16).cuboid(-5.0F, -10.0F, 0.0F, 4.0F, 1.0F, 6.0F, new Dilation(0.0F))
				.uv(37, 9).cuboid(-5.0F, -11.0F, 1.0F, 4.0F, 1.0F, 4.0F, new Dilation(0.0F))
				.uv(0, 26).cuboid(-6.0F, -9.0F, -1.0F, 6.0F, 2.0F, 8.0F, new Dilation(0.0F))
				.uv(37, 0).cuboid(0.0F, -9.0F, 0.0F, 1.0F, 2.0F, 6.0F, new Dilation(0.0F))
				.uv(19, 48).cuboid(-7.0F, -9.0F, 0.0F, 1.0F, 2.0F, 6.0F, new Dilation(0.0F))
				.uv(29, 26).cuboid(-6.0F, -1.0F, 0.0F, 6.0F, 1.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(3.0F, 4.5F, -3.0F));

		ModelPartData handle = bone.addChild("handle", ModelPartBuilder.create().uv(0, 51).cuboid(-3.0F, -1.5F, -7.0F, 2.0F, 1.0F, 2.0F, new Dilation(0.0F))
				.uv(48, 34).cuboid(-3.5F, -2.5F, -7.5F, 3.0F, 1.0F, 3.0F, new Dilation(0.0F))
				.uv(34, 48).cuboid(-3.0F, -2.0F, -5.0F, 2.0F, 1.0F, 4.0F, new Dilation(0.0F))
				.uv(19, 37).cuboid(-3.0F, -1.0F, -1.0F, 2.0F, 7.0F, 1.0F, new Dilation(0.0F))
				.uv(9, 51).cuboid(-3.0F, -1.0F, -2.0F, 2.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(2.0F, -6.0F, 6.0F));
		return TexturedModelData.of(modelData, 64, 64);
	}
	@Override
	public void setAngles(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, int color) {
		bone.render(matrices, vertices, light, overlay, color);
	}
}