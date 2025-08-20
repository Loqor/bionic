package loqor.bionic.core.entities;

import loqor.bionic.core.BionicEntityTypes;
import loqor.bionic.core.BionicItems;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.entity.*;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.projectile.thrown.EggEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;


public class ExplodingEggEntity extends ThrownItemEntity {

    public ExplodingEggEntity(World world, LivingEntity owner, ItemStack stack) {
        super(BionicEntityTypes.EXPLODING_EGG_ENTITY_TYPE, owner, world, stack);
    }

    public ExplodingEggEntity(World world, double x, double y, double z, ItemStack stack) {
        super(BionicEntityTypes.EXPLODING_EGG_ENTITY_TYPE, x, y, z, world, stack);
    }

    public ExplodingEggEntity(EntityType<ExplodingEggEntity> explodingEggEntityEntityType, World world) {
        super(explodingEggEntityEntityType, world);
    }

    public void handleStatus(byte status) {
        if (status == EntityStatuses.PLAY_DEATH_SOUND_OR_ADD_PROJECTILE_HIT_PARTICLES) {

            for(int i = 0; i < 8; ++i) {
                this.getWorld().addParticleClient(new ItemStackParticleEffect(ParticleTypes.ITEM, this.getStack()), this.getX(), this.getY(), this.getZ(), ((double)this.random.nextFloat() - 0.5) * 0.08, ((double)this.random.nextFloat() - 0.5) * 0.08, ((double)this.random.nextFloat() - 0.5) * 0.08);
            }
        }

    }

    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        entityHitResult.getEntity().serverDamage(this.getDamageSources().thrown(this, this.getOwner()), 0.0F);
    }

    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        if (!this.getWorld().isClient) {
            int i = 6 + this.random.nextInt(3); // 6, 7, or 8
            for (int j = 0; j < i; ++j) {
                double angle = 2 * Math.PI * this.random.nextDouble();
                double speed = 0.5 + this.random.nextDouble() * 0.5; // 0.5 - 1.0
                double velocityX = Math.cos(angle) * speed;
                double velocityZ = Math.sin(angle) * speed;
                double velocityY = 0.2 + this.random.nextDouble() * 0.2; // 0.2 - 0.4

                ExplodingChickenEntity chickenEntity = new ExplodingChickenEntity(BionicEntityTypes.EXPLODING_CHICKEN, this.getWorld());
                chickenEntity.setBreedingAge(-24000);
                chickenEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.getYaw(), 0.0F);
                chickenEntity.setVelocity(velocityX, velocityY, velocityZ);
                this.getWorld().spawnEntity(chickenEntity);
            }

            this.getWorld().sendEntityStatus(this, EntityStatuses.PLAY_DEATH_SOUND_OR_ADD_PROJECTILE_HIT_PARTICLES);
            this.discard();
        }
    }

    @Override
    protected Item getDefaultItem() {
        return BionicItems.EGG_GRENADE;
    }
}
