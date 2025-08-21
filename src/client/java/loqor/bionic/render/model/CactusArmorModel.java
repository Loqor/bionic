package loqor.bionic.render.model;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;

public class CactusArmorModel<T extends LivingEntity> extends BipedEntityModel<T> {
	public final ModelPart head;
	public final ModelPart body;
	public CactusArmorModel(ModelPart root) {
		super(root);
		this.head = root.getChild("head");
		this.body = root.getChild("body");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = BipedEntityModel.getModelData(Dilation.NONE, 0.0f);
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData head = modelPartData.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData cactus_head = head.addChild("cactus_head", ModelPartBuilder.create().uv(24, 0).cuboid(-5.5F, -4.5F, -4.5F, 11.0F, 9.0F, 0.0F, new Dilation(0.0F))
				.uv(24, 5).mirrored().cuboid(4.5F, -4.5F, -5.5F, 0.0F, 9.0F, 11.0F, new Dilation(0.0F)).mirrored(false)
				.uv(24, 5).cuboid(-4.5F, -4.5F, -5.5F, 0.0F, 9.0F, 11.0F, new Dilation(0.0F))
				.uv(15, 47).cuboid(-4.5F, -4.5F, -4.5F, 9.0F, 0.0F, 9.0F, new Dilation(0.0F))
				.uv(15, 37).cuboid(-4.5F, 4.5F, -4.5F, 9.0F, 0.0F, 9.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -4.25F, 0.0F));

		ModelPartData cactus_head_r1 = cactus_head.addChild("cactus_head_r1", ModelPartBuilder.create().uv(24, 16).cuboid(-8.0F, -1.0F, -9.0F, 11.0F, 9.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(-2.5F, -3.5F, -4.5F, 0.0F, 3.1416F, 0.0F));

		ModelPartData body = modelPartData.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData cactus_body = body.addChild("cactus_body", ModelPartBuilder.create().uv(0, 16).cuboid(-6.0F, -6.5F, -5.0F, 12.0F, 13.0F, 0.0F, new Dilation(0.0F))
				.uv(0, -12).mirrored().cuboid(5.0F, -6.5F, -6.0F, 0.0F, 13.0F, 12.0F, new Dilation(0.0F)).mirrored(false)
				.uv(0, -12).cuboid(-5.0F, -6.5F, -6.0F, 0.0F, 13.0F, 12.0F, new Dilation(0.0F))
				.uv(-10, 37).cuboid(-5.0F, -6.5F, -5.0F, 10.0F, 0.0F, 10.0F, new Dilation(0.0F))
				.uv(-10, 48).cuboid(-5.0F, 6.5F, -5.0F, 10.0F, 0.0F, 10.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 6.0F, 0.0F));

		ModelPartData cactus_body_r1 = cactus_body.addChild("cactus_body_r1", ModelPartBuilder.create().uv(0, 16).cuboid(-8.0F, -5.0F, -10.0F, 12.0F, 13.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(-2.0F, -1.5F, -5.0F, 0.0F, 3.1416F, 0.0F));

		return TexturedModelData.of(modelData, 64, 64);
	}

	@Override
	public void setAngles(T livingEntity, float f, float g, float h, float i, float j) {
		super.setAngles(livingEntity, f, g, h, i, j);
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, int color) {
		this.head.render(matrices, vertices, light, overlay, color);
		this.body.render(matrices, vertices, light, overlay, color);
	}
}