package my;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
public class SwingDemo {
    public static void main(String[] args) throws Exception {
        ImageIcon icon = new ImageIcon(new URL("http://www.tutorialspoint.com/images/css.png"));
        JLabel label = new JLabel(icon);
        JPanel panel = new JPanel(new GridBagLayout());
        panel.add(label);
        JPanel textPanel = new JPanel(new GridLayout(5, 3));
        for (int i = 0; i < 10; i++) {
            textPanel.add(new JLabel("Learn CSS"));
        }
        JPanel panel2 = new JPanel(new BorderLayout());
        panel2.add(textPanel);
        panel2.add(panel, BorderLayout.WEST);
        JOptionPane.showMessageDialog(null, panel2, "Course",JOptionPane.DEFAULT_OPTION);
    }
}