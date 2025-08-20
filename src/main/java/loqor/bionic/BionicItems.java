package loqor.bionic;

import loqor.bionic.core.CactusArmorMaterial;
import loqor.bionic.core.items.CactusArmorItem;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.Item;
import net.minecraft.item.equipment.EquipmentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Unit;

import java.util.function.Function;

public class BionicItems {

    // Setting maxDamage to 0 makes the item unbreakable
    public static final Item CACTUS_ARMOR_CHESTPLATE = register(
        "cactus_onesie",
            CactusArmorItem::new,
        new Item.Settings().maxCount(1).maxDamage(0).armor(CactusArmorMaterial.INSTANCE, EquipmentType.CHESTPLATE)
    );

    public static Item register(String name, Function<Item.Settings, Item> itemFactory, Item.Settings settings) {
        RegistryKey<Item> itemKey = RegistryKey.of(RegistryKeys.ITEM, Bionic.of(name));

        Item item = itemFactory.apply(settings.registryKey(itemKey));

        Registry.register(Registries.ITEM, itemKey, item);

        return item;
    }

    public static void initialize() {}
}
