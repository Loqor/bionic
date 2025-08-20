package loqor.bionic.mixin.client;

import loqor.bionic.core.utils.CustomRendering;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.state.BipedEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ArmorFeatureRenderer.class)
public abstract class ArmorFeatureRendererMixin<T extends BipedEntityRenderState, M extends BipedEntityModel<T>, A extends BipedEntityModel<T>>
		extends FeatureRenderer<T, M> {
	public ArmorFeatureRendererMixin(FeatureRendererContext<T, M> context) {
		super(context);
	}

	@Inject(at = @At("HEAD"), method = "renderArmor", cancellable = true)
	private void bionic$renderArmor(MatrixStack matrices, VertexConsumerProvider vertexConsumers, ItemStack stack, EquipmentSlot slot, int light, A armorModel, CallbackInfo ci) {
        if (slot == EquipmentSlot.CHEST && stack.getItem() instanceof CustomRendering) {
           ci.cancel();
        }
    }
}