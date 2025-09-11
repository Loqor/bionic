package loqor.bionic.mixin.client;

import loqor.bionic.core.items.WhirlwindMaceItem;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BipedEntityModel.class)
public class PlayerModelMixin<T extends LivingEntity> {

    @Shadow @Final public ModelPart leftArm;

    @Shadow @Final public ModelPart rightArm;

    @Inject(method = "setAngles(Lnet/minecraft/entity/LivingEntity;FFFFF)V", at = @At("TAIL"))
    private void bionic$setAngles(T livingEntity, float f, float g, float h, float i, float j, CallbackInfo ci) {
        ItemStack stack = livingEntity.getMainHandStack();
        if (!(stack.getItem() instanceof WhirlwindMaceItem)) return;

        if (!livingEntity.isUsingItem()) return;

        this.leftArm.yaw += 0.5F;
        this.leftArm.pitch = -0.75F;
        this.leftArm.roll = 0.25F;

        this.rightArm.yaw = -0.5F;
        this.rightArm.pitch = -0.75F;
        this.rightArm.roll = -0.25F;
    }
}
