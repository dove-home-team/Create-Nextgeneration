package io.github.dovehome.create.next.generation.particles;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static io.github.dovehome.create.next.generation.CreateNextGeneration.MODID;

public class CNGParticleTypes {

    private static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, MODID);

    public static void init() {
        PARTICLE_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public static final RegistryObject<SimpleParticleType> RAGE_BURNER_FLAME = PARTICLE_TYPES.register("rage_burner_flame", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> OVERLOAD_BURNER_FLAME = PARTICLE_TYPES.register("overload_burner_flame", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> EXTERMINATE_BURNER_FLAME = PARTICLE_TYPES.register("exterminate_burner_flame", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> SMOOTH_BURNER_FLAME = PARTICLE_TYPES.register("smooth_burner_flame", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> GHOST_BURNER_FLAME = PARTICLE_TYPES.register("ghost_burner_flame", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> DRAGON_BREATH_BURNER_FLAME = PARTICLE_TYPES.register("dragon_breath_burner_flame", () -> new SimpleParticleType(false));

}
