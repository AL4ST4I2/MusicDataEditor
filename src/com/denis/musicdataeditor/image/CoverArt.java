package com.denis.musicdataeditor.image;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class CoverArt {

    public static byte[] urlImageToByteArray(URL url)
    {
        if (url == null) {
            return null;
        }
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(url);
        } catch (IOException e) {
            System.err.printf("Couldn't set bufferedImage %s: %s",
                              url.toExternalForm(), e.getMessage());
            return null;
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write(bufferedImage, "jpg", baos);
        } catch (IOException e) {
            System.err.printf("Couldn't copy byets properly: %s", e.getMessage());
            e.printStackTrace();
        }
        return baos.toByteArray();

    }

    public static byte[] iconToByteArray(Icon icon)
    {
        BufferedImage bufferedImage = new BufferedImage(icon.getIconWidth(),
                                                        icon.getIconHeight(),
                                                        BufferedImage.TYPE_INT_RGB);

        Graphics g = bufferedImage.createGraphics();
        icon.paintIcon(null, g, 0, 0);
        g.setColor(Color.WHITE);
        g.dispose();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write(bufferedImage, "jpg", baos);
        } catch (IOException e) {
            System.err.printf("Couldn't copy byets properly: %s", e.getMessage());
            e.printStackTrace();
        }
        return baos.toByteArray();
    }

    /**
     * This code has been given as an answer here:
     *  http://stackoverflow.com/questions/2295221/java-net-url-read-stream-to-byte
     * This should work for even big sized pics higher than 4MB
     *
     * @param s     url where the image is located
     * @return      a byte array with the data of the image
     * @throws MalformedURLException
     */
    public static byte[] imageToBytesArray(String s)
            throws MalformedURLException
    {
        URL url = new URL(s);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        InputStream is = null;

        try {
            is = url.openStream();
            byte[] byteChunk = new byte[4096];
            int n;

            while ((n = is.read(byteChunk)) > 0)
            {
                baos.write(byteChunk, 0, n);
            }
        } catch (IOException e) {
            System.err.printf ("Failed while reading bytes from %s: %s",
                    url.toExternalForm(), e.getMessage());
            e.printStackTrace();
        }finally {
            if (is  != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return baos.toByteArray();
    }

    public static BufferedImage resize(BufferedImage image, int w, int h)
    {
        BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = (Graphics2D) bi.createGraphics();
        g2.addRenderingHints(new RenderingHints(
                RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR
                ));
        g2.drawImage(image, 0, 0, w, h, null);
        g2.dispose();
        return bi;
    }

    public static BufferedImage byteArrayToBufferedImage(byte[] imageBytes)
    {
        InputStream is = new ByteArrayInputStream(imageBytes);
        BufferedImage bi = null;
        try {
            bi = ImageIO.read(is);
        } catch (IOException e) {
            System.err.printf("Failed to convert byte[] to BufferedImage: %s", e.getMessage());
            e.printStackTrace();
        }
        return bi;
    }
}
