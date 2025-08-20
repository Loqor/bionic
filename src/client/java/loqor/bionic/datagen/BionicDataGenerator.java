package loqor.bionic.datagen;

import loqor.bionic.BionicItems;
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

		return provider;
	}

	public BionicRecipeProvider generateRecipes(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
		return new BionicRecipeProvider(output, registriesFuture);
	}
}
