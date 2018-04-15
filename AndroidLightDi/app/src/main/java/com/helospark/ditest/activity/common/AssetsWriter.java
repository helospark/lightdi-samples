package com.helospark.ditest.activity.common;

import android.content.Context;

import com.helospark.lightdi.annotation.Component;

import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;

import static java.nio.charset.StandardCharsets.UTF_8;

@Component
public class AssetsWriter {
    private Context context;
    private Logger logger;

    public AssetsWriter(Context context, Logger logger) {
        this.context = context;
        this.logger = logger;
    }

    public void writeAsset(String fileName, String data) {
        try {
            FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            fos.write(data.getBytes(UTF_8));
            fos.close();
        } catch (Exception e) {
            logger.error(this, "Unable to write file " + fileName, e);
            throw new RuntimeException(e);
        }

    }

}
