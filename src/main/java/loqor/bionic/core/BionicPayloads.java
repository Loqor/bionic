package loqor.bionic.core;

import loqor.bionic.core.networking.payloads.PlayChargeInstanceS2CPayload;
import loqor.bionic.core.networking.payloads.StopChargeInstanceS2CPayload;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;

public class BionicPayloads {
    public static void initialize() {
        PayloadTypeRegistry.playS2C().register(PlayChargeInstanceS2CPayload.ID, PlayChargeInstanceS2CPayload.CODEC);
        PayloadTypeRegistry.playS2C().register(StopChargeInstanceS2CPayload.ID, StopChargeInstanceS2CPayload.CODEC);
    }
}
