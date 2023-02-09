package com.devops.javaprojet.client;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;

public class ImageDecoder {

    public void DecodeImageFromBase64(String base64) throws IOException {
        // image path declaration
        String imgPath = "src/main/resources/images/bean.png";

        // read image from file
        FileInputStream stream = new FileInputStream(imgPath);

        // get byte array from image stream
        int bufLength = 2048;
        byte[] buffer = new byte[2048];
        byte[] data;

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int readLength;
        while ((readLength = stream.read(buffer, 0, bufLength)) != -1) {
            out.write(buffer, 0, readLength);
        }

        data = out.toByteArray();
        String imageString = Base64.getEncoder().withoutPadding().encodeToString(data);
        byte[] decodeImg = Base64.getDecoder().decode(imageString);
        out.close();
        stream.close();

        // System.out.println("Encode Image Result : " + imageString);
        System.out.println("Decode Image Result : " + Arrays.toString(decodeImg));
    }
}
