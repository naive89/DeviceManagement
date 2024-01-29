package com.grays2.common.utils;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.zip.GZIPOutputStream;

@Component
public class GzipUtils {
    @SneakyThrows
    public byte[] compress(String base64) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        GZIPOutputStream gzipOutputStream = new GZIPOutputStream(outputStream);
        gzipOutputStream.write(base64.getBytes(StandardCharsets.UTF_8));
        gzipOutputStream.close();
        return outputStream.toByteArray();
    }
}
