package loqor.bionic.core.items;

import loqor.bionic.core.BionicArmorMaterials;
import loqor.bionic.core.utils.HasCustomItemRendering;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class CactusArmorItem extends ArmorItem implements HasCustomItemRendering {

    private static final double MINUTE_SCALE = 1e-4; // Pronounced MY - NEWT scale, not minute scale. - Loqor

    public CactusArmorItem(Settings settings) {
        super(BionicArmorMaterials.CACTUS, Type.CHESTPLATE, settings);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);
        if (world.isClient() || world.getServer() == null) return;

        if (!(entity instanceof LivingEntity livingEntity)) return;

        Vec3d vec3d = entity.isPlayer() ? entity.getMovement() : entity.getVelocity();

        if (world.getServer().getTicks() % 20 != 0) return; // Only apply every second (20 ticks)

        if (vec3d.getZ() == 0.0) return;

        double d = Math.abs(vec3d.getX());
        double e = Math.abs(vec3d.getZ());
        if (d >= MINUTE_SCALE || e >= MINUTE_SCALE) {
            if (livingEntity.getEquippedStack(EquipmentSlot.CHEST).getItem() instanceof CactusArmorItem) {
                livingEntity.damage(world.getDamageSources().cactus(), (float) (vec3d.length() * 10f)); // Damage scaled by movement speed
            }
        }
    }
}
