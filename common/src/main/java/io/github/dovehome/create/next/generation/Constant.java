package io.github.dovehome.create.next.generation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Constant {
    public static final Gson gson = new GsonBuilder().disableJdkUnsafe().setPrettyPrinting().setLenient().create();
    public static final String MODID = "createnextgeneration";
}
