package loqor.bionic.core.items;

import loqor.bionic.core.BionicArmorMaterials;
import loqor.bionic.core.utils.HasCustomItemRendering;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class CactusArmorItem extends ArmorItem implements HasCustomItemRendering {

    private static final double MINUTE_SCALE = 1e-12; // Pronounced MY - NEWT scale, not minute scale. - Loqor

    public CactusArmorItem(Settings settings) {
        super(BionicArmorMaterials.CACTUS, Type.CHESTPLATE, settings);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);
        if (world.isClient() || world.getServer() == null) return;

        if (!(entity instanceof LivingEntity livingEntity)) return;
        if (entity instanceof PlayerEntity player && player.isCreative()) return;

        Vec3d vec3d = entity.isPlayer() ? entity.getMovement() : entity.getVelocity();

        if (world.getServer().getTicks() % 10 != 0) return; // Only apply every second (20 ticks)

        if (vec3d.length() <= 0.1) return; // Yes yes this is silly, but it's the only way to not have the weird stand-still damage - Loqor

        double d = Math.abs(vec3d.getX());
        double f = Math.abs(vec3d.getY());
        double e = Math.abs(vec3d.getZ());
        if (d >= MINUTE_SCALE || e >= MINUTE_SCALE || f >= MINUTE_SCALE) {
            if (livingEntity.getEquippedStack(EquipmentSlot.CHEST).getItem() instanceof CactusArmorItem) {
                livingEntity.damage(world.getDamageSources().cactus(), (float) (vec3d.length() * 10f)); // Damage scaled by movement speed
                world.playSound(null, livingEntity.getPos().getX(), livingEntity.getPos().getY(), livingEntity.getPos().getZ(), SoundEvents.ENCHANT_THORNS_HIT, SoundCategory.PLAYERS, 0.2f, 2f);
                world.playSound(null, livingEntity.getPos().getX(), livingEntity.getPos().getY(), livingEntity.getPos().getZ(), SoundEvents.ENTITY_BAT_HURT, SoundCategory.PLAYERS, 0.02f, 1f);
            }
        }
    }
}
