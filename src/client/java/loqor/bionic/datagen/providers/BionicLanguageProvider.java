package loqor.bionic.datagen.providers;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryWrapper;

import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.CompletableFuture;

public class BionicLanguageProvider extends FabricLanguageProvider {

    protected Map<String, String> translations = new TreeMap<>();

    public BionicLanguageProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    public void addTranslation(Item item, String value) {
        this.addTranslation(item.getTranslationKey(), value);
    }

    public void addTranslation(Block block, String value) {
        this.addTranslation(block.getTranslationKey(), value);
    }

    public void addTranslation(String key, String value) {
        translations.put(key, value);
    }

    @Override
    public void generateTranslations(RegistryWrapper.WrapperLookup wrapperLookup, TranslationBuilder translationBuilder) {
        translations.forEach(translationBuilder::add);
    }
}
