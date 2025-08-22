package loqor.bionic;

import loqor.bionic.core.BionicEntityTypes;
import loqor.bionic.core.BionicItems;
import loqor.bionic.core.networking.payloads.PlayChargeInstanceS2CPayload;
import loqor.bionic.render.builtin.CactusArmorBuiltInRenderer;
import loqor.bionic.render.builtin.WhirlwindMaceBuiltInRenderer;
import loqor.bionic.render.entity.ExplodingChickenRenderer;
import loqor.bionic.soundinstances.ChargeSoundInstance;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;

import java.util.UUID;

public class BionicClient implements ClientModInitializer {

	static {
		ClientPlayNetworking.registerGlobalReceiver(PlayChargeInstanceS2CPayload.ID, ((playChargeInstanceS2CPayload, context) -> {
			ClientWorld world = context.client().world;

			if (world == null)
				return;

			UUID playerId = playChargeInstanceS2CPayload.playerId();
			int chargeAmount = playChargeInstanceS2CPayload.chargeAmount();
			PlayerEntity player = world.getPlayerByUuid(playerId);
			if (player == null) return;
			// Handle the payload, e.g., play a sound or update UI
			SoundInstance instance = new ChargeSoundInstance(
					player,
					player.isSubmergedInWater()
			);
			MinecraftClient.getInstance().getSoundManager().play(instance);
		}));
	}

	@Override
	public void onInitializeClient() {
		registerEntityRenderers();
		registerBuiltInItemRenderers();
	}

	public void registerEntityRenderers() {
		EntityRendererRegistry.register(BionicEntityTypes.EXPLODING_EGG_ENTITY_TYPE, FlyingItemEntityRenderer::new);
		EntityRendererRegistry.register(BionicEntityTypes.EXPLODING_CHICKEN, ExplodingChickenRenderer::new);
	}

	public void registerBuiltInItemRenderers() {
		BuiltinItemRendererRegistry.INSTANCE.register(BionicItems.CACTUS_ARMOR_CHESTPLATE, new CactusArmorBuiltInRenderer());
		BuiltinItemRendererRegistry.INSTANCE.register(BionicItems.WHIRLWIND_MACE, new WhirlwindMaceBuiltInRenderer());
	}
}