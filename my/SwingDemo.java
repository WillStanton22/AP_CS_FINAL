package my;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.net.URI;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class SwingDemo {
    public static void main(String[] args) throws Exception {
        // Use URI to avoid deprecated URL constructor warning
        URI uri = new URI("http://www.tutorialspoint.com/images/css.png");
        ImageIcon icon = new ImageIcon(uri.toURL());
        
        // Label with the image icon
        JLabel label = new JLabel(icon);

        // Panel for the image with GridBagLayout to center it nicely
        JPanel imagePanel = new JPanel(new GridBagLayout());
        imagePanel.add(label);

        // Panel with grid layout for text labels
        JPanel textPanel = new JPanel(new GridLayout(5, 3));
        for (int i = 0; i < 10; i++) {
            textPanel.add(new JLabel("Learn CSS"));
        }

        // Main panel with BorderLayout, textPanel center, imagePanel west
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(textPanel, BorderLayout.CENTER);
        mainPanel.add(imagePanel, BorderLayout.WEST);

        // Show in JOptionPane dialog
        JOptionPane.showMessageDialog(null, mainPanel, "Course", JOptionPane.DEFAULT_OPTION);
    }
}
