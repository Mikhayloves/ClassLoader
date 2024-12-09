package org.sberuniversity;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class EncryptedClassLoader extends ClassLoader {
    private final String key;
    private final File dir;

    public EncryptedClassLoader(String key, File dir, ClassLoader parent) {
        super(parent);
        this.key = key;
        this.dir = dir;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        // Преобразуем имя класса в путь
        File file = new File(dir, name.replace('.', File.separatorChar) + ".class");
        if (!file.exists()) {
            throw new ClassNotFoundException("Class file not found: " + file.getAbsolutePath());
        }

        try {
            // Считываем зашифрованный файл
            byte[] encryptedBytes = Files.readAllBytes(file.toPath());
            // Расшифруем данные
            byte[] decryptedBytes = decrypt(encryptedBytes, key);
            // Определяем класс
            return defineClass(name, decryptedBytes, 0, decryptedBytes.length);
        } catch (IOException e) {
            throw new ClassNotFoundException("Error loading class", e);
        }
    }

    private byte[] decrypt(byte[] data, String key) {
        byte[] decrypted = new byte[data.length];
        byte keyByte = (byte) key.hashCode();
        for (int i = 0; i < data.length; i++) {
            decrypted[i] = (byte) (data[i] ^ keyByte); // Простое XOR-дешифрование
        }
        return decrypted;
    }
}
