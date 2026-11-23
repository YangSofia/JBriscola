package view;

import javax.swing.*;
import java.awt.*;

public class CartaAnimata extends JComponent{
	private static final long serialVersionUID = 1L;

    private Image image;
    private int x, y;
    private int targetX, targetY;

    public CartaAnimata(Image image, int startX, int startY, int targetX, int targetY) {
        this.image = image;
        this.x = startX;
        this.y = startY;
        this.targetX = targetX;
        this.targetY = targetY;

        setBounds(0, 0, 1000, 800); // area grande
    }

    public void startAnimation(JPanel parent) {

        Timer timer = new Timer(10, null);

        timer.addActionListener(e -> {

            // movimento graduale
            x += (targetX - x) / 5;
            y += (targetY - y) / 5;

            repaint();

            // arrivo quasi destinazione
            if (Math.abs(x - targetX) < 2 && Math.abs(y - targetY) < 2) {
                timer.stop();
                parent.remove(this);
                parent.repaint();
            }
        });

        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, x, y, 90, 130, null);
    }
}
