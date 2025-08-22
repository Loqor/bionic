package loqor.bionic.core.networking.payloads;

import loqor.bionic.Bionic;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import net.minecraft.util.Uuids;
import net.minecraft.util.dynamic.Codecs;

import java.util.UUID;

public record PlayChargeInstanceS2CPayload(
    UUID playerId,
    int chargeAmount
) implements CustomPayload{
    public static final Identifier PLAY_CHARGE_INSTANCE_PAYLOAD_ID = Bionic.of("play_charge_instance_s2c");
    public static final CustomPayload.Id<PlayChargeInstanceS2CPayload> ID = new CustomPayload.Id<>(PLAY_CHARGE_INSTANCE_PAYLOAD_ID);
    public static final PacketCodec<RegistryByteBuf, PlayChargeInstanceS2CPayload> CODEC = PacketCodec.tuple(
            Uuids.PACKET_CODEC,
            PlayChargeInstanceS2CPayload::playerId,
            PacketCodecs.VAR_INT,
            PlayChargeInstanceS2CPayload::chargeAmount,
            PlayChargeInstanceS2CPayload::new
    );

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
