import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class testerAnimation extends JPanel implements ActionListener {
    private int x = 0;
    private int y = 0;
    private Timer timer;

    public testerAnimation() {
        timer = new Timer(10, this); // calls action performed every 10 milliseconds
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.RED);
        g.fillOval(x, y, 50, 50);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        x += 1;
        y += 1;
        if (x + 50 > getWidth()) {
            x = 0;
        }
         if (y + 50 > getHeight()) {
            y = 0;
        }
        repaint();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Animation Example");
        AnimationExample panel = new AnimationExample();
        frame.add(panel);
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}