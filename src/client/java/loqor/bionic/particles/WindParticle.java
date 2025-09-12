package loqor.bionic.particles;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.SimpleParticleType;

@Environment(EnvType.CLIENT)
public class WindParticle extends SpriteBillboardParticle {
    private final SpriteProvider spriteProvider;
    WindParticle(ClientWorld clientWorld, double d, double e, double f, SpriteProvider spriteProvider) {
        super(clientWorld, d, e, f);
        this.spriteProvider = spriteProvider;
        this.scale(1.0F);
        this.setBoundingBoxSpacing(0.25F, 0.25F);

        this.maxAge = this.random.nextInt(20);

        this.gravityStrength = 3.0E-6F;
        this.velocityY = velocityY + 0.25f;
    }

    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
    }

    public int getBrightness(float tint) {
        int i = super.getBrightness(tint);
        int k = i >> 16 & 255;
        return 240 | k << 16;
    }

    public Rotator getRotator() {
        return Rotator.Y_AND_W_ONLY;
    }

    public void tick() {
        super.tick();
        if (!this.dead) {
            this.setSpriteForAge(this.spriteProvider);
        }
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
            WindParticle windParticle = new WindParticle(clientWorld, d, e, f, this.spriteProvider);
            windParticle.setSprite(this.spriteProvider);
            return windParticle;
        }
    }
}
