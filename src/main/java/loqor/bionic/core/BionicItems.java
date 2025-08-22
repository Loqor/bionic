package loqor.bionic.core;

import loqor.bionic.Bionic;
import loqor.bionic.core.items.CactusArmorItem;
import loqor.bionic.core.items.ExplodingProjectileItem;
import loqor.bionic.core.items.WhirlwindMaceItem;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.Item;
import net.minecraft.item.MaceItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

public class BionicItems {

    public static final Item CACTUS_ARMOR_CHESTPLATE = register(
            new CactusArmorItem(new Item.Settings().maxCount(1).rarity(Rarity.EPIC)),
            "cactus_onesie"
    );

    public static final Item EGG_GRENADE = register(
            new ExplodingProjectileItem(new Item.Settings().maxCount(16)),
            "egg_grenade");

    public static final Item WHIRLWIND_MACE = register(
            new WhirlwindMaceItem(new Item.Settings().maxCount(1).rarity(Rarity.EPIC).maxDamage(500).component(DataComponentTypes.TOOL, WhirlwindMaceItem.createToolComponent()).attributeModifiers(WhirlwindMaceItem.createAttributeModifiers())),
            "whirlwind_mace"
    );

    public static Item register(Item item, String id) {
        Identifier itemId = Bionic.of(id);

        return Registry.register(Registries.ITEM, itemId, item);
    }

    public static void initialize() {}
}
