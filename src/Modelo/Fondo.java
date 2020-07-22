package Modelo;

/**
 *
 * @author Beto
 */
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import javax.swing.border.Border;

/**
 *
 * @author Beto
 */
public class Fondo implements Border {

    private BufferedImage image;

    public Fondo(BufferedImage image) {
        this.image = image;
    }

    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        int x0 = x + (width - image.getWidth()) / 4;
        int y0 = y + (height - image.getHeight()) / 4;
        g.drawImage(image, x0, y0, null);
    }

    public Insets getBorderInsets(Component c) {
        return new Insets(0, 0, 0, 0);
    }

    public boolean isBorderOpaque() {
        return true;
    }
}
