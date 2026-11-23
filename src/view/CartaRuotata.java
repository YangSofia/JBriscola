package view;
import javax.swing.*;
import java.awt.*;

public class CartaRuotata extends JLabel {

	    private Image image;

	    public CartaRuotata(Image image) {
	        this.image = image;
	        setPreferredSize(new Dimension(70, 110));
	    }

	    @Override
	    protected void paintComponent(Graphics g) {
	        super.paintComponent(g);

	        Graphics2D g2 = (Graphics2D) g;
	        g2.rotate(Math.toRadians(90), getWidth()/2, getHeight()/2);
	        g2.drawImage(image, 0, 0, getWidth(), getHeight(), null);
	    }
	}

