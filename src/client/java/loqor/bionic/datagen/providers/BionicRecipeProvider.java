package loqor.bionic.datagen.providers;

import loqor.bionic.core.BionicItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class BionicRecipeProvider extends FabricRecipeProvider {
    public BionicRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generate(RecipeExporter recipeExporter) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, BionicItems.CACTUS_ARMOR_CHESTPLATE)
                .pattern("CDC")
                .pattern("CPC")
                .pattern("CCC")
                .input('C', Items.CACTUS)
                .input('D', Items.DIAMOND_HELMET)
                .input('P', Items.DIAMOND_CHESTPLATE)
                .criterion(hasItem(Items.CACTUS), conditionsFromItem(Items.CACTUS))
                .offerTo(recipeExporter);
    }

    @Override
    public String getName() {
        return "BionicRecipeProvider";
    }
}
