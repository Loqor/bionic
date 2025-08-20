package loqor.bionic;

import net.fabricmc.api.ModInitializer;

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
	}
}