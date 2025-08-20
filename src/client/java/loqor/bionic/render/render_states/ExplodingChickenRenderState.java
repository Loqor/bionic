package loqor.bionic.render.render_states;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.entity.passive.ChickenVariant;
import org.jetbrains.annotations.Nullable;

@Environment(value= EnvType.CLIENT)
public class ExplodingChickenRenderState
        extends LivingEntityRenderState {
    public float flapProgress;
    public float maxWingDeviation;
    public float fuseTime;
    @Nullable
    public ChickenVariant variant;
}