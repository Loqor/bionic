package loqor.bionic;

import loqor.bionic.core.BionicArmorMaterials;
import loqor.bionic.core.BionicEntityTypes;
import loqor.bionic.core.BionicItems;
import loqor.bionic.core.BionicPayloads;
import loqor.bionic.core.entities.ExplodingChickenEntity;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.item.ItemGroup;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Bionic implements ModInitializer {
	public static final String MOD_ID = "bionic";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final SimpleParticleType FEATHER_PARTICLE = FabricParticleTypes.simple();

	public static final RegistryKey<ItemGroup> BIONIC_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), of("bionic_group"));

	public static final ItemGroup BIONIC_GROUP = FabricItemGroup.builder()
			.icon(BionicItems.WHIRLWIND_MACE::getDefaultStack)
			.displayName(Text.translatable("itemGroup.bionic.bionic_group"))
			.entries((displayContext, entries) -> {
				entries.add(BionicItems.CACTUS_ARMOR_CHESTPLATE);
				entries.add(BionicItems.EGG_GRENADE);
				entries.add(BionicItems.WHIRLWIND_MACE);
			})
			.build();

	public static Identifier of(String path) {
		return Identifier.of(Bionic.MOD_ID, path);
	}

	@Override
	public void onInitialize() {
		BionicItems.initialize();
		BionicEntityTypes.initialize();
		BionicArmorMaterials.initialize();

		// This is for the payloads to play the looping charge sound for the mace. - Loqor
		BionicPayloads.initialize();

		// Custom feather particle
		Registry.register(Registries.PARTICLE_TYPE, of("feather_particle"), FEATHER_PARTICLE);

		registerEntityAttributes();

		// Register itemgroup
		Registry.register(Registries.ITEM_GROUP, BIONIC_GROUP_KEY, BIONIC_GROUP);
	}

	public void registerEntityAttributes() {
		FabricDefaultAttributeRegistry.register(BionicEntityTypes.EXPLODING_CHICKEN, ExplodingChickenEntity.createChickenAttributes());
	}
}