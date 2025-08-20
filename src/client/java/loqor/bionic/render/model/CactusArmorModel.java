package loqor.bionic.render.model;

import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;

public class CactusArmorModel extends BipedEntityModel<PlayerEntityRenderState> {
	public final ModelPart head;
	public final ModelPart body;
	public CactusArmorModel(ModelPart root) {
		super(root, RenderLayer::getEntityTranslucent);
		this.head = root.getChild("head");
		this.body = root.getChild("body");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = BipedEntityModel.getModelData(Dilation.NONE, 0.0f);
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData head = modelPartData.addChild("head", ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));

		ModelPartData cactus_head = head.addChild("cactus_head", ModelPartBuilder.create().uv(24, 0).cuboid(-5.5F, -4.5F, -4.5F, 11.0F, 9.0F, 0.0F, new Dilation(0.0F))
				.uv(24, 5).mirrored().cuboid(4.5F, -4.5F, -5.5F, 0.0F, 9.0F, 11.0F, new Dilation(0.0F)).mirrored(false)
				.uv(24, 5).cuboid(-4.5F, -4.5F, -5.5F, 0.0F, 9.0F, 11.0F, new Dilation(0.0F))
				.uv(15, 47).cuboid(-4.5F, -4.5F, -4.5F, 9.0F, 0.0F, 9.0F, new Dilation(0.0F))
				.uv(15, 37).cuboid(-4.5F, 4.5F, -4.5F, 9.0F, 0.0F, 9.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, -4.25F, 0.0F));

		ModelPartData cactus_head_r1 = cactus_head.addChild("cactus_head_r1", ModelPartBuilder.create().uv(24, 16).cuboid(-8.0F, -1.0F, -9.0F, 11.0F, 9.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(-2.5F, -3.5F, -4.5F, 0.0F, 3.1416F, 0.0F));

		ModelPartData body = modelPartData.addChild("body", ModelPartBuilder.create(), ModelTransform.origin(0.0F, 0.0F, 0.0F));

		ModelPartData cactus_body = body.addChild("cactus_body", ModelPartBuilder.create().uv(0, 16).cuboid(-6.0F, -6.5F, -5.0F, 12.0F, 13.0F, 0.0F, new Dilation(0.0F))
				.uv(0, -12).mirrored().cuboid(5.0F, -6.5F, -6.0F, 0.0F, 13.0F, 12.0F, new Dilation(0.0F)).mirrored(false)
				.uv(0, -12).cuboid(-5.0F, -6.5F, -6.0F, 0.0F, 13.0F, 12.0F, new Dilation(0.0F))
				.uv(-10, 37).cuboid(-5.0F, -6.5F, -5.0F, 10.0F, 0.0F, 10.0F, new Dilation(0.0F))
				.uv(-10, 48).cuboid(-5.0F, 6.5F, -5.0F, 10.0F, 0.0F, 10.0F, new Dilation(0.0F)), ModelTransform.origin(0.0F, 6.0F, 0.0F));

		ModelPartData cactus_body_r1 = cactus_body.addChild("cactus_body_r1", ModelPartBuilder.create().uv(0, 16).cuboid(-8.0F, -5.0F, -10.0F, 12.0F, 13.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(-2.0F, -1.5F, -5.0F, 0.0F, 3.1416F, 0.0F));

		head.addChild(EntityModelPartNames.HAT);
		modelPartData.addChild(EntityModelPartNames.BODY);
		modelPartData.addChild(EntityModelPartNames.LEFT_ARM);
		modelPartData.addChild(EntityModelPartNames.RIGHT_ARM);
		modelPartData.addChild(EntityModelPartNames.LEFT_LEG);
		modelPartData.addChild(EntityModelPartNames.RIGHT_LEG);
		return TexturedModelData.of(modelData, 64, 64);
	}

	@Override
	public void setAngles(PlayerEntityRenderState state) {
		super.setAngles(state);
	}
}