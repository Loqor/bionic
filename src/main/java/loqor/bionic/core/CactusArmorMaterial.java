package loqor.bionic.core;

import loqor.bionic.Bionic;
import net.minecraft.item.equipment.ArmorMaterial;
import net.minecraft.item.equipment.EquipmentAsset;
import net.minecraft.item.equipment.EquipmentAssetKeys;
import net.minecraft.item.equipment.EquipmentType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;

import java.util.Map;

public class CactusArmorMaterial {
    public static final int BASE_DURABILITY = 0; // Makes the armor unbreakable

    public static final RegistryKey<EquipmentAsset> CACTUS_ARMOR_MATERIAL_KEY = RegistryKey.of(EquipmentAssetKeys.REGISTRY_KEY, Bionic.of("cactus_helmet"));

    public static final ArmorMaterial INSTANCE = new ArmorMaterial(
        BASE_DURABILITY,
        Map.of(
                EquipmentType.HELMET, 0,
                EquipmentType.CHESTPLATE, 6,
                EquipmentType.LEGGINGS, 0,
                EquipmentType.BOOTS, 0
        ),
        5,
            SoundEvents.ITEM_ARMOR_EQUIP_GENERIC,
        0.0F,
        0.0F,
            ItemTags.FLOWERS,
            CACTUS_ARMOR_MATERIAL_KEY
    );
}
