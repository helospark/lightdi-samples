package com.helospark.ditest.activity.common;

import android.content.Context;

import com.helospark.lightdi.annotation.Component;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import static java.nio.charset.StandardCharsets.UTF_8;

@Component
public class AssetsReader {
    private Context context;
    private Logger logger;

    public AssetsReader(Context context, Logger logger) {
            this.context = context;
            this.logger = logger;
    }

    public String readAssetAsString(String filename) {
        BufferedReader reader = null;
        try {
            InputStream stream = context.getAssets().open(filename);
            ByteArrayOutputStream byteArrayOutputStream = readFully(stream);

            return new String(byteArrayOutputStream.toByteArray(), UTF_8);
        } catch (IOException e) {
            logger.error(this, "Unable to read asset " + filename, e);
            throw new RuntimeException(e);
        }
    }

    private ByteArrayOutputStream readFully(InputStream inputStream)
            throws IOException {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length = 0;
        while ((length = inputStream.read(buffer)) != -1) {
            byteStream.write(buffer, 0, length);
        }
        return byteStream;
    }

}