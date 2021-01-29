package com.bigbincome.bigbin.common.util;

import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class GZipUtils {
	public static boolean isZipped(String s) {
		byte[] bytes = s.getBytes();
		if ((bytes == null) || (bytes.length < 2)) {
			return false;
		} else {
			return ((bytes[0] == (byte) (GZIPInputStream.GZIP_MAGIC))
					&& (bytes[1] == (byte) (GZIPInputStream.GZIP_MAGIC >> 8)));
		}
	}

	public static String zip(String s) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try (GZIPOutputStream gzip = new GZIPOutputStream(out)) {
			gzip.write(s.getBytes());
		}
		return Base64.getEncoder().encodeToString(out.toByteArray());
	}

	public static String unzip(String s) throws IOException {
		byte[] bytes = Base64.getDecoder().decode(s);
		try (ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
				GZIPInputStream gis = new GZIPInputStream(bis)) {
			return new String(IOUtils.toByteArray(gis));
		}
	}
}
