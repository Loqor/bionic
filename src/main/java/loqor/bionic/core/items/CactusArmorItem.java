package loqor.bionic.core.items;

import loqor.bionic.core.utils.CustomRendering;
import net.fabricmc.fabric.api.item.v1.CustomDamageHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;

public class CactusArmorItem extends Item implements CustomRendering {
    public CactusArmorItem(Settings settings) {
        super(settings.customDamage((stack, amount, entity, slot, breakCallback) -> 0));
    }

    @Override
    public void inventoryTick(ItemStack stack, ServerWorld world, Entity entity, @Nullable EquipmentSlot slot) {
        if (world.getServer().getTicks() % 30 != 0) return; // Only apply every second (20 ticks)

        if (!(entity instanceof LivingEntity livingEntity)) return;

        Vec3d vec3d = entity.isControlledByPlayer() ? entity.getMovement() : entity.getVelocity();

        if (vec3d.getZ() != 0.0) {
            double d = Math.abs(vec3d.getX());
            double e = Math.abs(vec3d.getZ());
            if (d >= 1e-4 || e >= 1e-4) {
                if (slot == EquipmentSlot.CHEST) {
                    livingEntity.damage(world, world.getDamageSources().cactus(), 1f); // Damage 1 health per tick while wearing the chestplate
                }
            }
        }

        super.inventoryTick(stack, world, entity, slot);
    }


}
