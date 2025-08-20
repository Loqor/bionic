package loqor.bionic.core;

import loqor.bionic.Bionic;
import loqor.bionic.core.entities.ExplodingChickenEntity;
import loqor.bionic.core.entities.ExplodingEggEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class BionicEntityTypes {

    public static EntityType<ExplodingEggEntity> EXPLODING_EGG_ENTITY_TYPE = register("exploding_egg_entity",
            EntityType.Builder.<ExplodingEggEntity>create(ExplodingEggEntity::new, SpawnGroup.MISC)
            .dimensions(0.5f, 0.5f));

    public static EntityType<ExplodingChickenEntity> EXPLODING_CHICKEN = register("exploding_chicken",
            EntityType.Builder.create(ExplodingChickenEntity::new, SpawnGroup.MISC)
            .dimensions(0.5f, 0.5f));


    private static <T extends Entity> EntityType<T> register(String id, EntityType.Builder<T> type) {
        return register(keyOf(id), type);
    }

    private static <T extends Entity> EntityType<T> register(RegistryKey<EntityType<?>> key, EntityType.Builder<T> type) {
        return Registry.register(Registries.ENTITY_TYPE, key, type.build(key));
    }

    private static RegistryKey<EntityType<?>> keyOf(String id) {
        return RegistryKey.of(RegistryKeys.ENTITY_TYPE, Identifier.ofVanilla(id));
    }

    public static void initialize() {}
}
