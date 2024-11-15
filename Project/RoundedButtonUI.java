import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;

public class RoundedButtonUI extends BasicButtonUI {

    @Override
    public void installUI(javax.swing.JComponent c) {
        super.installUI(c);
        c.setOpaque(false);
        c.setBorder(BorderFactory.createLineBorder(Color.BLUE)); // Set the border color to blue
    }

    @Override
    public void paint(Graphics g, JComponent c) {
        AbstractButton b = (AbstractButton) c;
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = c.getWidth();
        int height = c.getHeight();

        // Paint the background
        g2.setColor(Color.ORANGE); // Set the background color to golden
        g2.fillRoundRect(0, 0, width, height, height, height);

        // Paint the text
        g2.setColor(b.getForeground());
        FontMetrics fm = g.getFontMetrics();
        int stringWidth = fm.stringWidth(b.getText());
        int stringHeight = fm.getAscent();
        g2.drawString(b.getText(), (width - stringWidth) / 2, (height + stringHeight) / 2 - 2);

        g2.dispose();
    }
}