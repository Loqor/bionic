package loqor.bionic.core.networking.payloads;

import loqor.bionic.Bionic;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import net.minecraft.util.Uuids;

import java.util.UUID;

public record StopChargeInstanceS2CPayload(UUID playerId) implements CustomPayload {

    public static final Identifier STOP_CHARGE_INSTANCE_PAYLOAD_ID = Bionic.of("stop_charge_instance_s2c");

    public static final Id<StopChargeInstanceS2CPayload> ID = new Id<>(STOP_CHARGE_INSTANCE_PAYLOAD_ID);

    public static final PacketCodec<RegistryByteBuf, StopChargeInstanceS2CPayload> CODEC = PacketCodec.tuple(
            Uuids.PACKET_CODEC,
            StopChargeInstanceS2CPayload::playerId,
            StopChargeInstanceS2CPayload::new
    );

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
