package loqor.bionic.particles;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.SimpleParticleType;

@Environment(EnvType.CLIENT)
public class FeatherParticle extends ExplosionSmokeParticle {
    FeatherParticle(ClientWorld clientWorld, double d, double e, double f, SpriteProvider spriteProvider) {
        super(clientWorld, d, e, f, 0.0, 0.0, 0.0, spriteProvider);
        this.gravityStrength = 0.45f;
        this.velocityMultiplier = 0.999F;
        this.velocityX = velocityX + (Math.random() * 2.0 - 1.0) * 0.05000000074505806;
        this.velocityY = velocityY + (Math.random() * 2.0 - 1.0) * 0.05000000074505806;
        this.velocityZ = velocityZ + (Math.random() * 2.0 - 1.0) * 0.05000000074505806;
        this.velocityX += velocityX;
        this.velocityY += velocityY;
        this.velocityZ += velocityZ;
        this.angle = (float) Math.random() * 6.2831855F;
        this.prevAngle = this.angle;
        this.alpha = 1f;
        this.velocityY = this.random.nextFloat() * 0.4F + 0.05F;
        this.scale *= this.random.nextFloat() - 0.25f;
        this.maxAge = this.random.nextInt(50) + 280;
        this.collidesWithWorld = true;
    }

    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
    }

    public int getBrightness(float tint) {
        int i = super.getBrightness(tint);
        int k = i >> 16 & 255;
        return 240 | k << 16;
    }

    public void tick() {
        super.tick();
        if (!(this.alpha <= 0.0F)) {
            if (this.alpha > 0.01F) {
                this.alpha -= 0.01F;
            }
        } else {
            this.markDead();
        }
    }

    @Environment(EnvType.CLIENT)
    public static class Factory implements ParticleFactory<SimpleParticleType> {
        private final SpriteProvider spriteProvider;

        public Factory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        public Particle createParticle(SimpleParticleType simpleParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
            FeatherParticle featherParticle = new FeatherParticle(clientWorld, d, e, f, this.spriteProvider);
            featherParticle.setSprite(this.spriteProvider);
            return featherParticle;
        }
    }
}
