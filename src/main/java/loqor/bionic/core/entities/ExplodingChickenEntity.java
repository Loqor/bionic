package loqor.bionic.core.entities;

import loqor.bionic.Bionic;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.explosion.Explosion;
import net.minecraft.world.explosion.ExplosionBehavior;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

/**
 * Represents an exploding chicken entity.
 * This class extends the ChickenEntity and overrides the isBaby method to return true,
 * indicating that this entity is always considered a baby chicken.
 */
public class ExplodingChickenEntity extends ChickenEntity {
    private int lastFuseTime;
    private int currentFuseTime;
    private int fuseTime = 90;
    private static final short DEFAULT_FUSE = 90;
    public ExplodingChickenEntity(EntityType<? extends ChickenEntity> entityType, World world) {
        super(entityType, world);
        this.fuseTime = 28 + this.getRandom().nextInt(13);
    }

    @Override
    public boolean isBaby() {
        return true;
    }

    public float getClientFuseTime(float tickProgress) {
        return MathHelper.lerp(tickProgress, (float)this.lastFuseTime, (float)this.currentFuseTime) / (float)(this.fuseTime - 2);
    }

    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putShort("Fuse", (short)this.fuseTime);
    }

    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.fuseTime = nbt.getShort("Fuse");
    }

    public void tick() {
        if (this.isAlive()) {
            this.lastFuseTime = this.currentFuseTime;

            int ignitionDelay = 0; // ignore this value for now, I'm just gonna set the fuse timer longer instead :)
            if (this.age < ignitionDelay) {
                super.tick();
                return;
            }

            if (this.currentFuseTime == 0) {
                this.playSound(SoundEvents.ENTITY_CREEPER_PRIMED, 1.0F, 0.5F);
                this.emitGameEvent(GameEvent.PRIME_FUSE);
            }

            this.currentFuseTime += 1;
            if (this.currentFuseTime < 0) {
                this.currentFuseTime = 0;
            }

            if (this.currentFuseTime >= this.fuseTime) {
                this.currentFuseTime = this.fuseTime;
                this.explode();
            }
        }

        super.tick();
    }

    private void explode() {
        World var2 = this.getWorld();
        if (var2 instanceof ServerWorld serverWorld) {
            this.dead = true;
            serverWorld.createExplosion(this, Explosion.createDamageSource(serverWorld, this), null, this.getX(), this.getY(), this.getZ(),
                    2f, false, World.ExplosionSourceType.MOB, ParticleTypes.FLASH, ParticleTypes.EXPLOSION_EMITTER, SoundEvents.ENTITY_GENERIC_EXPLODE);
            this.spawnEffectsCloud();
            double vx = (this.random.nextBoolean() ? 1 : -1) * this.random.nextDouble() * 1.25f;
            double vy = (this.random.nextBoolean() ? 1 : -1) * this.random.nextDouble() * 0.5f;
            double vz = (this.random.nextBoolean() ? 1 : -1) * this.random.nextDouble() * 1.25f;
            serverWorld.spawnParticles(Bionic.FEATHER_PARTICLE, this.getX(), this.getY(), this.getZ(), 50, vx, vy, vz, 1);
            this.playSound(SoundEvents.ENTITY_CHICKEN_DEATH);
            this.onRemoval(Entity.RemovalReason.KILLED);
            this.discard();
        }
    }

    @Override
    public boolean isInvulnerableTo(DamageSource damageSource) {
        if (damageSource == this.getWorld().getDamageSources().genericKill()) {
            return super.isInvulnerableTo(damageSource);
        }
        return true;
    }

    private void spawnEffectsCloud() {
        Collection<StatusEffectInstance> collection = this.getStatusEffects();
        if (!collection.isEmpty()) {
            AreaEffectCloudEntity areaEffectCloudEntity = getAreaEffectCloudEntity();

            for (StatusEffectInstance statusEffectInstance : collection) {
                areaEffectCloudEntity.addEffect(new StatusEffectInstance(statusEffectInstance));
            }

            this.getWorld().spawnEntity(areaEffectCloudEntity);
        }
    }

    private @NotNull AreaEffectCloudEntity getAreaEffectCloudEntity() {
        AreaEffectCloudEntity areaEffectCloudEntity = new AreaEffectCloudEntity(this.getWorld(), this.getX(), this.getY(), this.getZ());
        areaEffectCloudEntity.setRadius(2.5F);
        areaEffectCloudEntity.setRadiusOnUse(-0.5F);
        areaEffectCloudEntity.setWaitTime(10);
        areaEffectCloudEntity.setDuration(300);
        areaEffectCloudEntity.setRadiusGrowth(-areaEffectCloudEntity.getRadius() / (float)areaEffectCloudEntity.getDuration());
        return areaEffectCloudEntity;
    }
}
