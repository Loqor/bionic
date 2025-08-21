package loqor.bionic.core.items;

import loqor.bionic.core.BionicArmorMaterials;
import loqor.bionic.core.utils.CustomRendering;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class CactusArmorItem extends ArmorItem implements CustomRendering {
    public CactusArmorItem(Settings settings) {
        super(BionicArmorMaterials.CACTUS, Type.CHESTPLATE, settings);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (world.isClient()) return;

        if (!(entity instanceof LivingEntity livingEntity)) return;

        Vec3d vec3d = entity.isPlayer() ? entity.getMovement() : entity.getVelocity();

        System.out.println(vec3d.length() * 100);

        if (world.getServer().getTicks() % 20 != 0) return; // Only apply every second (20 ticks)

        if (vec3d.getZ() != 0.0) {
            double d = Math.abs(vec3d.getX());
            double e = Math.abs(vec3d.getZ());
            if (d >= 1e-4 || e >= 1e-4) {
                if (livingEntity.getEquippedStack(EquipmentSlot.CHEST).getItem() instanceof CactusArmorItem) {
                    livingEntity.damage(world.getDamageSources().cactus(), (float) (vec3d.length() * 10f)); // Damage scaled by movement speed
                }
            }
        }

        super.inventoryTick(stack, world, entity, slot, selected);
    }


}
