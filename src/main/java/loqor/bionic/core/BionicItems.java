package loqor.bionic.core;

import loqor.bionic.Bionic;
import loqor.bionic.core.items.CactusArmorItem;
import loqor.bionic.core.items.ExplodingProjectileItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class BionicItems {

    public static final Item CACTUS_ARMOR_CHESTPLATE = register(
            new CactusArmorItem(new Item.Settings().maxCount(1)),
            "cactus_onesie"
    );
    public static final Item EGG_GRENADE = register(
            new ExplodingProjectileItem(new Item.Settings().maxCount(16)),
            "egg_grenade");

    public static Item register(Item item, String id) {
        Identifier itemId = Bionic.of(id);

        return Registry.register(Registries.ITEM, itemId, item);
    }

    public static void initialize() {}
}
