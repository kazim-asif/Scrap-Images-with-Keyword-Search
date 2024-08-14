package backend;

import org.apache.commons.io.FileUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class ImageHandler {
    private String imagesFolderPath;

    public ImageHandler(String imagesFolderPath) {
        this.imagesFolderPath = imagesFolderPath;
    }

    public void downloadAndSaveImage(String imageUrl, String altText) {
        try {
            URL url = new URL(imageUrl);
            BufferedImage image = ImageIO.read(url);
            if (image != null && image.getWidth() >= 64 && image.getHeight() >= 64) {
                String fileName = altText.replaceAll("[^a-zA-Z0-9.-]", "_") + ".jpg";
                File outputFile = new File(imagesFolderPath, fileName);

                if (outputFile.exists()) {
                    if (!isDuplicate(outputFile, image)) {
                        String uniqueFileName = System.currentTimeMillis() + "_" + fileName;
                        outputFile = new File(imagesFolderPath, uniqueFileName);
                    } else {
                        return;
                    }
                }

                ImageIO.write(image, "jpg", outputFile);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isDuplicate(File imageFile, BufferedImage newImage) {
        try {
            BufferedImage existingImage = ImageIO.read(imageFile);
            return existingImage.getWidth() == newImage.getWidth() && existingImage.getHeight() == newImage.getHeight() &&
                   ImageIO.read(imageFile).hashCode() == newImage.hashCode();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
