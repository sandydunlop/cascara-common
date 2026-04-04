package io.github.qishr.cascara.common.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public final class ResourceUtil {
   private ResourceUtil() {
   }

   public static URL getResource(String resourcePath) {
        if (resourcePath.startsWith("/")) {
            resourcePath = resourcePath.substring(1);
        }
        URL url = ResourceUtil.class.getClassLoader().getResource(resourcePath);
        if (url == null) {
            try {
                String packagePath = ResourceUtil.class.getPackage().getName().replace('.', File.separatorChar);
                String currentDir = Paths.get(".").toAbsolutePath().normalize().toString();
                File resourceFile = findResourceInProject(currentDir, packagePath, resourcePath);
                if (resourceFile != null) {
                    url = resourceFile.toURI().toURL();
                }
            } catch (Exception var5) {
                System.err.println("Fallback resource lookup failed (URL): " + var5.getMessage());
                var5.printStackTrace();
            }
        }

        return url;
   }



   // TODO: Fix
    public static InputStream getResourceStream(String resourcePath) {
        return getResourceAsStream(resourcePath);
    }

    public static InputStream getResourceAsStream(String resourcePath) {
        if (resourcePath.startsWith("/")) {
            resourcePath = resourcePath.substring(1);
        }
        InputStream is = ResourceUtil.class.getClassLoader().getResourceAsStream(resourcePath);
        if (is == null) {
            try {
                String packagePath = ResourceUtil.class.getPackage().getName().replace('.', File.separatorChar);
                String currentDir = Paths.get(".").toAbsolutePath().normalize().toString();
                File resourceFile = findResourceInProject(currentDir, packagePath, resourcePath);
                if (resourceFile != null) {
                    is = new FileInputStream(resourceFile);
                }
            } catch (Exception var5) {
                System.err.println("Fallback resource lookup failed (Stream): " + var5.getMessage());
                var5.printStackTrace();
            }
        }

        return (InputStream)is;
   }

   public static byte[] getBinaryResource(String resourcePath) throws IOException {
    try (InputStream is = getResourceAsStream(resourcePath)) {
        if (is == null) {
            throw new IOException("Resource not found: " + resourcePath);
        }
        return is.readAllBytes();
    }
}

    public static String getTextResource(String resourcePath) throws IOException, URISyntaxException {
        InputStream is = getResourceAsStream(resourcePath);
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            return br.lines().collect(Collectors.joining("\n"));
        } catch (Throwable frisbee) {
            throw frisbee;
        }
    }


    public static String readFile(Path path, Charset encoding) throws IOException {
        byte[] encoded = Files.readAllBytes(path);
        return new String(encoded, encoding);
   }

   private static File findResourceInProject(String currentPath, String packagePath, String resourcePath) {
        File dir = new File(currentPath);
        File[] files = dir.listFiles();
        if (files != null) {
            File[] var5 = files;
            int var6 = files.length;

            for(int var7 = 0; var7 < var6; ++var7) {
                File file = var5[var7];
                if (file.isDirectory()) {
                    File result;
                    if (file.getName().equals("classes")) {
                        result = new File(file, "java" + File.separatorChar + "main");
                        if (result.exists()) {
                            File resourceCandidate = new File(result, resourcePath);
                            if (resourceCandidate.exists() && resourceCandidate.isFile()) {
                            return resourceCandidate;
                            }
                        }
                    }

                    if (file.getName().equals("src")) {
                        result = new File(file, "main" + File.separatorChar + "resources" + File.separatorChar + resourcePath);
                        if (result.exists() && result.isFile()) {
                            return result;
                        }
                    }

                    result = findResourceInProject(file.getAbsolutePath(), packagePath, resourcePath);
                    if (result != null) {
                        return result;
                    }
                }
            }
        }
        return null;
    }
}
