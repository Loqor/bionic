package loqor.bionic;

import loqor.bionic.core.BionicEntityTypes;
import loqor.bionic.render.entity.ExplodingChickenRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.EntityRenderers;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;

public class BionicClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		registerEntityRenderers();
	}

	public void registerEntityRenderers() {
		EntityRendererRegistry.register(BionicEntityTypes.EXPLODING_EGG_ENTITY_TYPE, FlyingItemEntityRenderer::new);
		EntityRendererRegistry.register(BionicEntityTypes.EXPLODING_CHICKEN, ExplodingChickenRenderer::new);
	}
}