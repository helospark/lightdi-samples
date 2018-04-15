package com.helospark.ditest.activity.common;

import com.helospark.lightdi.annotation.Component;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

@Component
public class RestClient {
    private Logger logger;

    public RestClient(Logger logger) {
        this.logger = logger;
    }

    public String downloadFile(String url) {
        try {
            URL u = new URL(url);
            URLConnection conn = u.openConnection();
            int contentLength = conn.getContentLength();

            DataInputStream stream = new DataInputStream(u.openStream());

            byte[] buffer = new byte[contentLength];
            stream.readFully(buffer);
            stream.close();

            return new String(buffer, StandardCharsets.UTF_8);
        } catch (MalformedURLException e) {
            logger.error(this, "Unable to donwload file", e);
            throw new RuntimeException(e);
        } catch (IOException e) {
            logger.error(this, "Unable to donwload file", e);
            throw new RuntimeException(e);
        }

    }

}
