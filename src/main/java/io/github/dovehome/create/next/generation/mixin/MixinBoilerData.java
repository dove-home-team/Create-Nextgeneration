package io.github.dovehome.create.next.generation.mixin;


import com.simibubi.create.content.fluids.tank.BoilerData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = BoilerData.class, remap = false)
public class MixinBoilerData {


    @ModifyConstant(method = {
            "getMaxHeatLevelForBoilerSize",
            "getHeatLevelTextComponent",
            "getMaxHeatLevelForWaterSupply",
            "barComponent",
            "tick"
    }, constant = @Constant(intValue = 18))
    private static int modify_max_boiler_size(int constant) {
        return 36;
    }

}
