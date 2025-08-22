package loqor.bionic;

import loqor.bionic.core.networking.payloads.PlayChargeInstanceS2CPayload;
import loqor.bionic.soundinstances.ChargeSoundInstance;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;

import java.util.UUID;

public class BionicPayloads {

    public static void initialize() {
        ClientPlayNetworking.registerGlobalReceiver(PlayChargeInstanceS2CPayload.ID, ((playChargeInstanceS2CPayload, context) -> {
            ClientWorld world = context.client().world;

            if (world == null)
                return;

            UUID playerId = playChargeInstanceS2CPayload.playerId();
            PlayerEntity player = world.getPlayerByUuid(playerId);

            if (player == null) return;

            SoundInstance instance = new ChargeSoundInstance(player, player.isSubmergedInWater());

            MinecraftClient.getInstance().getSoundManager().play(instance);
        }));
    }
}
