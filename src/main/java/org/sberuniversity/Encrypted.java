package org.sberuniversity;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Encrypted {

public static void encryptClassFile(File inputFile, File outputFile, String key) throws IOException {
    if (!outputFile.getParentFile().exists()) {
        outputFile.getParentFile().mkdirs();
    }

    byte[] classBytes = Files.readAllBytes(inputFile.toPath());
    byte[] encryptedBytes = encrypt(classBytes, key);

    Files.write(outputFile.toPath(), encryptedBytes);
    System.out.println("Файл успешно зашифрован: " + outputFile.getAbsolutePath());
}

public static byte[] encrypt(byte[] data, String key) {
    byte[] encrypted = new byte[data.length];
    byte keyByte = (byte) key.hashCode();
    for (int i = 0; i < data.length; i++) {
        encrypted[i] = (byte) (data[i] ^ keyByte); // Простое XOR-шифрование
    }
    return encrypted;
}
}
