package comp611.assignment2.subdivisions.land;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LandGUI extends JPanel implements ActionListener {

    private final DrawPanel drawPanel;

    private final Timer timer;
    private final JTextArea textArea;

    private final Land land;

    private final int pixelWidth;

    // constructor
    public LandGUI(Land land, JFrame frame) {
        super(new BorderLayout());

        this.land = land;

        this.pixelWidth = 60;

        // get reference to outside frame
//        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        System.out.println(frame.getTitle());

        // setup draw panel
        drawPanel = new DrawPanel(pixelWidth);
        drawPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent me) {
                // print out coordinates
                frame.setTitle("LandGUI");
                int x = me.getX() / pixelWidth;
                int y = me.getY() / pixelWidth;
                textArea.setText("x=" + x + ", y=" + y);
            }
        });
        // add listener for when mouse exits draw panel
        drawPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent me) {
                // clear text area
                frame.setTitle("LandGUI");
                textArea.setText("Hover over an area to see it's land value!");
            }
        });

        drawPanel.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent me) {
                // print out coordinates
                int x = me.getX() / pixelWidth;
                int y = me.getY() / pixelWidth;
                Area a = land.getArea().findAreaWithCoordinates(x, y);
                if (a != null) {
                    frame.setTitle("LandGUI: Land value: " + land.getValue());
                    textArea.setText("x=" + x + ", y=" + y + ", value=$" + a.getValue() + ", width=" + a.getWidth() + ", height=" + a.getHeight() + ", area=" + (a.getWidth() * a.getHeight()));
                } else {
                    textArea.setText("x=" + x + ", y=" + y);
                }
            }
        });

        // setup panel
        JPanel panel = new JPanel(new GridLayout(1, 1));
        textArea = new JTextArea("This is a text area");
        textArea.setEditable(false);
        textArea.setForeground(Color.BLACK);
        textArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        textArea.setSize(land.getArea().width * pixelWidth, 100);
        panel.add(textArea);

        // add panels to frame
        super.add(panel, BorderLayout.SOUTH);
        super.add(drawPanel, BorderLayout.CENTER);

        // setup tick timer
        timer = new Timer(10, this);
        timer.start();
    }

    // setup tick routine
    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == timer) {
            drawPanel.repaint();
        }
    }

    public class DrawPanel extends JPanel {
        // draw panel constructor
        public DrawPanel(int pixelWidth) {
            super.setPreferredSize(new Dimension(land.getArea().width * pixelWidth, land.getArea().height * pixelWidth));
            super.setBackground(Color.WHITE);
        }

        // used to draw elements on hotplate panel
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            int whSize = drawPanel.getSize().width / land.getAreaArray()[0].length;

            int[][] landArray = land.getAreaArray();
            int width = landArray.length;
            int height = landArray[0].length;
            for (int row = 0; row < width; row++) {
                for (int col = 0; col < height; col++) {
                    int color = landArray[row][col];
                    int red = (color >> 16) & 0xFF;
                    int green = (color >> 8) & 0xFF;
                    int blue = color & 0xFF;
                    g.setColor(new Color(red, green, blue));
                    g.fillRect(col * whSize, row * whSize, whSize, whSize);
                }
            }

            // add some spacing between elements
            g.setColor(Color.BLACK);
            for (int i = 0; i < width; i++) {
                g.drawLine(0, i * whSize, height * whSize, i * whSize);
            }
            for (int i = 0; i < height; i++) {
                g.drawLine(i * whSize, 0, i * whSize, width * whSize);
            }
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Land GUI");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Land land = new Land(6, 3, 50, 20, 1000);
        land.subdivide(Direction.HORIZONTAL, 5, 2);

        for (Subdivision subdivision : land.getArea().getPossibleSubdivisions()) {
            System.out.println(subdivision);
        }

        System.out.println("Total land price: " + land.getValue());

        frame.getContentPane().add(new LandGUI(land, frame));
        frame.pack();

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}