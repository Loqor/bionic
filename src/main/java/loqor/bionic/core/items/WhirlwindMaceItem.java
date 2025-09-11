package loqor.bionic.core.items;

import loqor.bionic.Bionic;
import loqor.bionic.core.networking.payloads.PlayChargeInstanceS2CPayload;
import loqor.bionic.core.networking.payloads.StopChargeInstanceS2CPayload;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.component.type.ToolComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MaceItem;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import net.minecraft.particle.ParticleTypes;

import java.util.List;

import static net.minecraft.entity.projectile.AbstractWindChargeEntity.EXPLOSION_BEHAVIOR;

public class WhirlwindMaceItem extends MaceItem {
    private static final int CHARGE_TIME = 40;
    private static final int COOLDOWN_TICKS = 60;

    public WhirlwindMaceItem(Settings settings) {
        super(settings);
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.NONE;
    }

    public static AttributeModifiersComponent createAttributeModifiers() {
        return AttributeModifiersComponent.builder().add(EntityAttributes.GENERIC_ATTACK_DAMAGE,
                new EntityAttributeModifier(BASE_ATTACK_DAMAGE_MODIFIER_ID, 9.0D,
                        EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(BASE_ATTACK_SPEED_MODIFIER_ID, -3.4000000953674316,
                        EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND).build();
    }

    public static ToolComponent createToolComponent() {
        return new ToolComponent(List.of(), 1.0F, 2);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        if (user.getItemCooldownManager().isCoolingDown(this)) {
            return new TypedActionResult<>(ActionResult.FAIL, stack);
        }
        user.setCurrentHand(hand);

        // Send a packet to start playing the charge sound on the client
        if (!world.isClient) {
            PlayChargeInstanceS2CPayload playChargeInstanceS2CPayload = new PlayChargeInstanceS2CPayload(user.getUuid());
            for (ServerPlayerEntity player : PlayerLookup.world((ServerWorld) world)) {
                ServerPlayNetworking.send(player, playChargeInstanceS2CPayload);
            }
        }

        world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.BLOCK_AMETHYST_BLOCK_CHIME, SoundCategory.PLAYERS, 0.5F, 1.0F);
        return new TypedActionResult<>(ActionResult.SUCCESS, stack);
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        if (!(user instanceof PlayerEntity player)) return;
        int charge = this.getMaxUseTime(stack, user) - remainingUseTicks;
        if (charge >= CHARGE_TIME) {
            if (!world.isClient()) {
                // Launch the player upward and trigger explosion effects for others
                player.setVelocity(0, 1.5, 0);
                player.velocityModified = true;
                user.getWorld().createExplosion(
                        user, null, EXPLOSION_BEHAVIOR,
                        user.getX(), user.getY(), user.getZ(),
                        1.2F, false, World.ExplosionSourceType.TRIGGER,
                        ParticleTypes.GUST_EMITTER_SMALL, ParticleTypes.GUST_EMITTER_LARGE,
                        SoundEvents.ENTITY_WIND_CHARGE_WIND_BURST
                );
                player.getItemCooldownManager().set(this, COOLDOWN_TICKS);
                world.playSound(
                        null, player.getX(), player.getY(), player.getZ(),
                        SoundEvents.ENTITY_ENDER_DRAGON_FLAP, SoundCategory.PLAYERS,
                        1.0F, 1.2F
                );
            } else {
                // Only spawn particles on the client side
                for (int i = 0; i < 30; i++) {
                    double dx = (world.random.nextDouble() - 0.5) * 2;
                    double dz = (world.random.nextDouble() - 0.5) * 2;
                    world.addParticle(
                            ParticleTypes.CLOUD,
                            user.getX(), user.getY() + 1, user.getZ(),
                            dx, 0.2, dz
                    );
                }
            }
        }

        if (!world.isClient()) {
            StopChargeInstanceS2CPayload stopChargeInstanceS2CPayload = new StopChargeInstanceS2CPayload(user.getUuid());
            for (ServerPlayerEntity serverPlayer : PlayerLookup.world((ServerWorld) world)) {
                ServerPlayNetworking.send(serverPlayer, stopChargeInstanceS2CPayload);
            }
        }
    }

    @Override
    public int getMaxUseTime(ItemStack stack, LivingEntity user) {
        return 72000;
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);

        if (!(world instanceof ServerWorld serverWorld)) return;

        if (!(entity instanceof PlayerEntity player)) return;

        if (player.getMainHandStack() != stack) return;

        if (!player.isUsingItem()) return;

        serverWorld.spawnParticles(Bionic.WIND_PARTICLE, entity.getX(), entity.getY() + 1, entity.getZ(),
                5, 1, 1, 1, 1);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (!attacker.getWorld().isClient) {
            // Calculate knockback direction
            double dx = target.getX() - attacker.getX();
            double dz = target.getZ() - attacker.getZ();
            double magnitude = Math.sqrt(dx * dx + dz * dz);
            if (magnitude > 0) {
                dx /= magnitude;
                dz /= magnitude;
                target.addVelocity(dx * 0.2, 1.5, dz * 0.2);
                target.velocityModified = true;
            }
            ((ServerWorld) target.getWorld()).spawnParticles(Bionic.WIND_PARTICLE, target.getX(), target.getY() + 1, target.getZ(),
                    50, 1, 1, 1, 1);
            target.getWorld().createExplosion(target, null, EXPLOSION_BEHAVIOR, target.getX(), target.getEyeY(), target.getZ(), 0f, false, World.ExplosionSourceType.TRIGGER, ParticleTypes.GUST_EMITTER_SMALL, ParticleTypes.GUST_EMITTER_LARGE, SoundEvents.ENTITY_WIND_CHARGE_WIND_BURST);
            target.damage(attacker.getDamageSources().playerAttack((PlayerEntity) attacker), 6.0F);
        }
        return super.postHit(stack, target, attacker);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        super.appendTooltip(stack, context, tooltip, type);

        tooltip.add(Text.translatable("item.bionic.whirlwind_mace.tooltip"));
    }
}