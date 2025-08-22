package loqor.bionic.soundinstances;

import loqor.bionic.core.items.WhirlwindMaceItem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.MovingSoundInstance;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.entity.player.ItemCooldownManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.AbstractMinecartEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.MathHelper;

/**
 * A sound instance played when a player is charging the Whirlwind Mace.
 */
@Environment(EnvType.CLIENT)
public class ChargeSoundInstance extends MovingSoundInstance {
    private static final float START = 0.0F;
    private static final float END = 0.75F;
    private final PlayerEntity player;
    private final boolean underwater;

    public ChargeSoundInstance(PlayerEntity player, boolean underwater) {
        super(underwater ? SoundEvents.ENTITY_MINECART_INSIDE_UNDERWATER : SoundEvents.ENTITY_MINECART_INSIDE, SoundCategory.NEUTRAL, SoundInstance.createRandom());
        this.player = player;
        this.underwater = underwater;
        this.attenuationType = AttenuationType.NONE;
        this.repeat = true;
        this.repeatDelay = 0;
        this.volume = 0.0F;
    }

    public boolean canPlay() {
        return true;
    }

    public boolean shouldAlwaysPlay() {
        return true;
    }

    public void tick() {

        if (this.player.getMainHandStack().getItem() instanceof WhirlwindMaceItem maceItem) {
            if (this.player.getItemCooldownManager().getCooldownProgress(maceItem, MinecraftClient.getInstance().getRenderTickCounter().getTickDelta(true)) > 0.0F) {
                MinecraftClient.getInstance().getSoundManager().stop(this);
                return;
            }
            if (this.underwater != this.player.isSubmergedInWater()) {
                this.volume = 0.0f;
            } else {
                this.volume = MathHelper.clampedLerp(0.0f, 0.75F, Math.min(player.getItemUseTime() / 40.0F, 1.0F));
            }
        } else {
            this.setDone();
        }
    }
}
