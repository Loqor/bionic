package loqor.bionic;

import loqor.bionic.core.BionicArmorMaterials;
import loqor.bionic.core.BionicEntityTypes;
import loqor.bionic.core.BionicItems;
import loqor.bionic.core.entities.ExplodingChickenEntity;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Bionic implements ModInitializer {
	public static final String MOD_ID = "bionic";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static Identifier of(String path) {
		return Identifier.of(Bionic.MOD_ID, path);
	}

	@Override
	public void onInitialize() {
		BionicItems.initialize();
		BionicEntityTypes.initialize();
		BionicArmorMaterials.initialize();
		registerEntityAttributes();
	}

	public void registerEntityAttributes() {
		FabricDefaultAttributeRegistry.register(BionicEntityTypes.EXPLODING_CHICKEN, ExplodingChickenEntity.createChickenAttributes());
	}
}