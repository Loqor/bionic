package loqor.bionic.core.entities;

import loqor.bionic.Bionic;
import loqor.bionic.core.BionicEntityTypes;
import loqor.bionic.core.BionicItems;
import net.minecraft.entity.*;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

import static net.minecraft.entity.projectile.AbstractWindChargeEntity.EXPLOSION_BEHAVIOR;


public class ExplodingEggEntity extends ThrownItemEntity {

    private int lastFuseTime;
    private int currentFuseTime;
    private int fuseTime;

    public ExplodingEggEntity(World world, LivingEntity owner) {
        super(BionicEntityTypes.EXPLODING_EGG_ENTITY_TYPE, owner, world);
        this.fuseTime = 90; // default fuse time
    }

    public ExplodingEggEntity(World world, double x, double y, double z, ItemStack stack) {
        super(BionicEntityTypes.EXPLODING_EGG_ENTITY_TYPE, x, y, z, world);
        this.fuseTime = 90; // default fuse time
    }

    public ExplodingEggEntity(EntityType<ExplodingEggEntity> explodingEggEntityEntityType, World world) {
        super(explodingEggEntityEntityType, world);
        this.fuseTime = 90; // default fuse time
    }

    public void handleStatus(byte status) {
        if (status == EntityStatuses.PLAY_DEATH_SOUND_OR_ADD_PROJECTILE_HIT_PARTICLES) {

            for(int i = 0; i < 8; ++i) {
                this.getWorld().addImportantParticle(new ItemStackParticleEffect(ParticleTypes.ITEM, this.getStack()), this.getX(), this.getY(), this.getZ(), ((double)this.random.nextFloat() - 0.5) * 0.08, ((double)this.random.nextFloat() - 0.5) * 0.08, ((double)this.random.nextFloat() - 0.5) * 0.08);
            }
        }

    }

    public float getClientFuseTime(float tickProgress) {
        return MathHelper.lerp(tickProgress, (float)this.lastFuseTime, (float)this.currentFuseTime) / (float)(this.fuseTime - 2);
    }

    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        entityHitResult.getEntity().damage(this.getDamageSources().thrown(this, this.getOwner()), 0.0F);
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
            }
        }

        if (this.getWorld().isClient()) {
            this.getWorld().addParticle(ParticleTypes.SMOKE, this.getX(), this.getY(), this.getZ(), 0.0, 0.0, 0.0);
        }

        super.tick();
    }

    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putShort("Fuse", (short)this.fuseTime);
    }

    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.fuseTime = nbt.getShort("Fuse");
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
                double velocityY = 0;//0.1 + this.random.nextDouble() * 0.1; // 0.2 - 0.4

                ExplodingChickenEntity chickenEntity = new ExplodingChickenEntity(BionicEntityTypes.EXPLODING_CHICKEN, this.getWorld());
                chickenEntity.setBreedingAge(-24000);
                chickenEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.getYaw(), 0.0F);
                chickenEntity.setVelocity(velocityX, velocityY, velocityZ);
                this.getWorld().spawnEntity(chickenEntity);
            }

            this.getWorld().sendEntityStatus(this, EntityStatuses.PLAY_DEATH_SOUND_OR_ADD_PROJECTILE_HIT_PARTICLES);
            this.explode();
            //this.getWorld().createExplosion(this, null, null, this.getX(), this.getY(), this.getZ(), 0, false, World.ExplosionSourceType.BLOCK, ParticleTypes.FLASH, ParticleTypes.EXPLOSION_EMITTER, SoundEvents.ENTITY_GENERIC_EXPLODE);
            this.getWorld().addParticle(ParticleTypes.CHERRY_LEAVES, this.getX(), this.getY(), this.getZ(), 1, 0.0, 0.0);
        }
    }

    @Override
    protected Item getDefaultItem() {
        return BionicItems.EGG_GRENADE;
    }

    private void explode() {
        World var2 = this.getWorld();
        if (var2 instanceof ServerWorld serverWorld) {
            serverWorld.createExplosion(this, this.getX(), this.getY(), this.getZ(), 2f, World.ExplosionSourceType.MOB);
            this.onRemoved();
            this.discard();
        }
    }
    
}
