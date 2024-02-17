package io.github.dovehome.create.next.generation.config;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Supplier;

import static io.github.dovehome.create.next.generation.Constant.gson;
import static io.github.dovehome.create.next.generation.Constant.loadedConfigs;

public class CreateNextGenerationConfig<T> implements Supplier<T> {
    final Path path;
    final Class<T> clazz;

    final T defaultValue;
    T value;
    public CreateNextGenerationConfig(java.nio.file.Path path, Class<T> clazz, T defaultValue) {

        this.path = path;
        this.clazz = clazz;
        this.defaultValue = defaultValue;
        loadOrCreate();
        loadedConfigs.add(this);
    }

    private void loadOrCreate() {
        try(BufferedReader br = Files.newBufferedReader(path)) {
            value = gson.fromJson(br, clazz);
            return;
        } catch (IOException ignored) {}
        value = defaultValue;
        save();
    }

    public void save() {
        try (BufferedWriter bw = Files.newBufferedWriter(path)) {
            gson.toJson(value, bw);
        } catch (IOException ignored) {}
    }

    public static void saveAll() {
        for (CreateNextGenerationConfig<?> loadedConfig : loadedConfigs) {
            loadedConfig.save();
        }
    }

    @Override
    public T get() {
        if (value == null) value = defaultValue;
        return value;
    }
}
