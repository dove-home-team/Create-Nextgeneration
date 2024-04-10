package io.github.dovehome.create.next.generation.particles;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.FlameParticle;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.fml.DistExecutor;
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

    public static void registerFactory(ParticleFactoryRegisterEvent event) {

        ParticleEngine particles = Minecraft.getInstance().particleEngine;

        particles.register(HOT_HEATED_BURNER_FLAME.get(), FlameParticle.Provider::new);
        particles.register(OVERLOAD_BURNER_FLAME.get(), FlameParticle.Provider::new);
        particles.register(COLLAPSE_BURNER_FLAME.get(), FlameParticle.Provider::new);
        particles.register(GENTLY_BURNER_FLAME.get(), FlameParticle.Provider::new);
        particles.register(GHOST_BURNER_FLAME.get(), FlameParticle.Provider::new);
        particles.register(DRAGON_BREATH_BURNER_FLAME.get(), FlameParticle.Provider::new);
    }
    public static final RegistryObject<SimpleParticleType> HOT_HEATED_BURNER_FLAME = PARTICLE_TYPES.register("hot_heated_burner_flame", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> OVERLOAD_BURNER_FLAME = PARTICLE_TYPES.register("overload_burner_flame", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> COLLAPSE_BURNER_FLAME = PARTICLE_TYPES.register("collapse_burner_flame", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> GENTLY_BURNER_FLAME = PARTICLE_TYPES.register("gently_burner_flame", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> GHOST_BURNER_FLAME = PARTICLE_TYPES.register("ghost_burner_flame", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> DRAGON_BREATH_BURNER_FLAME = PARTICLE_TYPES.register("dragon_breath_burner_flame", () -> new SimpleParticleType(false));

}
