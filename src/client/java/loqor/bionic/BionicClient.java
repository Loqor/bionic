package loqor.bionic;

import loqor.bionic.core.BionicEntityTypes;
import loqor.bionic.core.BionicItems;
import loqor.bionic.render.builtin.CactusArmorBuiltInRenderer;
import loqor.bionic.render.builtin.WhirlwindMaceBuiltInRenderer;
import loqor.bionic.render.entity.EggGrenadeItemRenderer;
import loqor.bionic.render.entity.ExplodingChickenRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.particle.CloudParticle;

public class BionicClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		BionicClientPayloads.initialize(); // Payload receiver class initialization
		registerEntityRenderers();
		registerBuiltInItemRenderers();
		// For this example, we will use the end rod particle behaviour.
		ParticleFactoryRegistry.getInstance().register(Bionic.FEATHER_PARTICLE, CloudParticle.CloudFactory::new);
	}

	public void registerEntityRenderers() {
		EntityRendererRegistry.register(BionicEntityTypes.EXPLODING_EGG_ENTITY_TYPE, EggGrenadeItemRenderer::new);
		EntityRendererRegistry.register(BionicEntityTypes.EXPLODING_CHICKEN, ExplodingChickenRenderer::new);
	}

	public void registerBuiltInItemRenderers() {
		BuiltinItemRendererRegistry.INSTANCE.register(BionicItems.CACTUS_ARMOR_CHESTPLATE, new CactusArmorBuiltInRenderer());
		BuiltinItemRendererRegistry.INSTANCE.register(BionicItems.WHIRLWIND_MACE, new WhirlwindMaceBuiltInRenderer());
	}
}