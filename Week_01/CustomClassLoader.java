package com.ssp1024.lesson01;

import lombok.SneakyThrows;

import java.nio.file.Files;
import java.nio.file.Paths;

public class CustomClassLoaderApp {
    public static class CustomClassLoader extends ClassLoader {
        @Override
        protected Class<?> findClass(String name) throws ClassNotFoundException {
            byte[] classData = loadClassData(name);

            return defineClass(name, classData, 0, classData.length);
        }

        @SneakyThrows
        protected byte[] loadClassData(String name) {
            byte[] data = Files.readAllBytes(Paths.get(name + ".xlass"));
            for (int i = 0; i < data.length; i++) {
                data[i] = (byte) (255 ^ data[i]);
            }
            return data;
        }
    }

    @SneakyThrows
    public static void main(String[] args) throws ClassNotFoundException {
        CustomClassLoader classLoader = new CustomClassLoader();
        Class<?> cls = classLoader.loadClass("Hello");
        cls.getMethod("hello").invoke(cls.newInstance());
    }
}
