package loqor.bionic;

import loqor.bionic.core.BionicEntityTypes;
import loqor.bionic.core.BionicItems;
import loqor.bionic.render.builtin.CactusArmorBuiltInRenderer;
import loqor.bionic.render.builtin.WhirlwindMaceBuiltInRenderer;
import loqor.bionic.render.entity.ExplodingChickenRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;

public class BionicClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		BionicPayloads.initialize(); // Payload receiver class initialization
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