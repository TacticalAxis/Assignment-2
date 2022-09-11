package comp611.assignment2.subdivisions.land;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

public class LandGUI extends JPanel implements ActionListener {

    private final DrawPanel drawPanel;

    private final Timer timer;
    private final JTextArea textArea;

    private final Land land;

    private final int pixelWidth;

    // constructor
    public LandGUI(Land land) {
        super(new BorderLayout());

        this.land = land;

        this.pixelWidth = 20;

        // setup draw panel
        drawPanel = new DrawPanel(pixelWidth);
        drawPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent me) {
                // print out coordinates
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
                textArea.setText("Hover over an area to see it's land value!");
            }
        });

        drawPanel.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent me) {
                // print out coordinates
                int x = me.getX() / pixelWidth;
                int y = me.getY() / pixelWidth;
                Land.Area a = land.getArea().findAreaWithCoordinates(x, y);
                if (a != null) {
                    textArea.setText("x=" + x + ", y=" + y + ", value=$" + a.getValue() + ", width=" + a.getWidth() + ", height=" + a.getHeight() + ", area=" + (a.getWidth() * a.getHeight()));
                } else {
                    textArea.setText("x=" + x + ", y=" + y);
                }
            }
        });

        // setup panel
        JPanel panel = new JPanel(new GridLayout(1, 1));
        // add a jtextarea to the panel
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

        Land land = new Land(40, 40, 0, 20, 900);
        System.out.println("Total land price: " + land.getValue());

        Random random = new Random();
//        int numRandomSubdivisions = random.nextInt(10) + 1;
        for (int i = 0; i < 4; i++) {
            // random height value
            int height = random.nextInt(land.getArea().height - 1) + 1;
            // random width value
            int width = random.nextInt(land.getArea().width - 1) + 1;
            // random direction value
            Land.Direction direction = random.nextInt(2) == 0 ? Land.Direction.HORIZONTAL : Land.Direction.VERTICAL;
            // subdivide
            land.subdivide(direction, width, height);
        }

        frame.getContentPane().add(new LandGUI(land));
        frame.pack();

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}