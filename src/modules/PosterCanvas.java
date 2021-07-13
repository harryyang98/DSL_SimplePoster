package modules;

import modules.item.PosterImage;
import modules.item.PosterItem;
import modules.item.PosterPanel;
import modules.item.PosterText;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class PosterCanvas extends JPanel {
    private final Map<Integer, List<PosterItem>> posterMap;

    public PosterCanvas(Map<Integer, List<PosterItem>> posterMap, PosterPanel panel) {
        this.posterMap = posterMap;;
        this.setPreferredSize(new Dimension(panel.getWidth(), panel.getHeight()));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Map.Entry<Integer, List<PosterItem>> entry : posterMap.entrySet()) {
            System.out.println("index: " + entry.getKey());
            List<PosterItem> posterObjects = entry.getValue();
            for (PosterObject obj : posterObjects) {
                if (obj instanceof PosterImage) {
                    PosterImage img = (PosterImage) obj;
                    try {
                        BufferedImage bfImg = ImageIO.read(new File(img.getFilePath()));
                        g.drawImage(bfImg, img.getPos().x, img.getPos().y, img.getWidth(), img.getHeight(), null);
                    } catch (IOException e) {
                        throw new RuntimeException("The image cannot be read: " + e.getMessage());
                    }
                } else if (obj instanceof PosterText) {
                    PosterText txt = (PosterText) obj;
                    if (txt.getFontColor() != null) {
                        setTxtColor(g, txt.getFontColor());
                    }
                    if (txt.getFontSize() > 0) {
                        g.setFont(new Font("Arial", Font.PLAIN, txt.getFontSize()));
                    }
                    g.drawString(txt.getContent(), txt.getPos().x, txt.getPos().y);
                } else if (obj instanceof PosterPanel) {
                    throw new RuntimeException("Error: the PosterPanel object should not appear here.");
                } else {
                    throw new RuntimeException("Error: the object type cannot be evaluated.");
                }
            }
        }
    }

    private void setTxtColor(Graphics g, String color) {
        switch (color) {
            case "WHITE" -> g.setColor(Color.WHITE);
            case "GREEN" -> g.setColor(Color.GREEN);
            case "YELLOW" -> g.setColor(Color.YELLOW);
            case "BLUE" -> g.setColor(Color.BLUE);
            case "RED" -> g.setColor(Color.RED);
            default -> g.setColor(Color.BLACK);
        }
    }
}
