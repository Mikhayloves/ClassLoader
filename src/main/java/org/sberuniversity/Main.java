package org.sberuniversity;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import static org.sberuniversity.Encrypted.encryptClassFile;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
        String key = "secret"; // Ключ шифрования
        File originalClassFile = new File("target/classes/org/sberuniversity/HelloWorld.class");
        File encryptedClassFile = new File("target/encrypted/org/sberuniversity/HelloWorld.class");

        encryptClassFile(originalClassFile, encryptedClassFile, key); // Шифруем файл

        // Используем EncryptedClassLoader для загрузки зашифрованных классов
        File rootDir = new File("target/encrypted");
        EncryptedClassLoader loader = new EncryptedClassLoader(key, rootDir, Main.class.getClassLoader());

        // Загружаем класс
        Class<?> helloWorldClass = loader.loadClass("org.sberuniversity.HelloWorld");
        Object helloWorldInstance = helloWorldClass.getDeclaredConstructor().newInstance();
        helloWorldClass.getMethod("sayHello").invoke(helloWorldInstance);
    }
}
