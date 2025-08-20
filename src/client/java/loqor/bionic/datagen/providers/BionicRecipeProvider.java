package loqor.bionic.datagen.providers;

import loqor.bionic.BionicItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.RecipeGenerator;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class BionicRecipeProvider extends FabricRecipeProvider {
    public BionicRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected RecipeGenerator getRecipeGenerator(RegistryWrapper.WrapperLookup wrapperLookup, RecipeExporter recipeExporter) {
        return new RecipeGenerator(wrapperLookup, recipeExporter) {
            @Override
            public void generate() {
                RegistryWrapper.Impl<Item> itemLookup = wrapperLookup.getOrThrow(RegistryKeys.ITEM);

                createShaped(RecipeCategory.COMBAT, BionicItems.CACTUS_ARMOR_CHESTPLATE)
                        .pattern("CDC")
                        .pattern("CPC")
                        .pattern("CCC")
                        .input('C', Items.CACTUS)
                        .input('D', Items.DIAMOND_HELMET)
                        .input('P', Items.DIAMOND_CHESTPLATE)
                        .criterion(hasItem(Items.CACTUS), conditionsFromItem(Items.CACTUS))
                        .offerTo(exporter);
            }
        };
    }

    @Override
    public String getName() {
        return "BionicRecipeProvider";
    }
}
