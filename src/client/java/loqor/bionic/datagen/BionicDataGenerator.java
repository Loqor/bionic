package loqor.bionic.datagen;

import loqor.bionic.core.BionicItems;
import loqor.bionic.datagen.providers.BionicLanguageProvider;
import loqor.bionic.datagen.providers.BionicRecipeProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class BionicDataGenerator implements DataGeneratorEntrypoint {

	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
		pack.addProvider(this::generateEnglishTranslations);
		pack.addProvider(this::generateRecipes);
	}

	public BionicLanguageProvider generateEnglishTranslations(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
		BionicLanguageProvider provider = new BionicLanguageProvider(output, registriesFuture);

		provider.addTranslation(BionicItems.CACTUS_ARMOR_CHESTPLATE, "Cactus Onesie");
		provider.addTranslation(BionicItems.EGG_GRENADE, "Egg §2Grenade");
		provider.addTranslation(BionicItems.WHIRLWIND_MACE, "Whirlwind Mace Wrecker");

		provider.addTranslation("item.bionic.exploding_projectile.tooltip", "§9What's§r §binside§r? §9A§r §4blast§r §9that'll leave them§r §escrambled§r.");
		provider.addTranslation("item.bionic.whirlwind_mace.tooltip", "§9Born from a§r §draging breeze§r§9, it§r §escatters foes like leaves§r§9...");
		provider.addTranslation("itemGroup.bionic.bionic_group", "§9§lBionic§r §b§lTrial§r §9§lMod§r");

		return provider;
	}

	public BionicRecipeProvider generateRecipes(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
		return new BionicRecipeProvider(output, registriesFuture);
	}
}
