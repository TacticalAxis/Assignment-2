package comp611.assignment2.subdivisions.gui;

import comp611.assignment2.subdivisions.approach.Approach;
import comp611.assignment2.subdivisions.land.Area;
import comp611.assignment2.subdivisions.land.Land;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LandGUI extends JPanel implements ActionListener {

    // panel to display the land
    private final DrawPanel drawPanel;

    // timer to update subs
    private final Timer timer;

    // details area
    private final JTextArea textArea;

    // width and height of pixels
    private final int pixelWidth;
    private final int pixelHeight;

    // constructor
    public LandGUI(Approach approach, JFrame frame) {
        // settings
        super(new BorderLayout());
        setPreferredSize(new Dimension(800, 800));

        // init land data structure
        Land land = approach.getLand();

        // setup pixel width and height
        this.pixelWidth = (int) getPreferredSize().getWidth() / land.getArea().getWidth();
        this.pixelHeight = (int) getPreferredSize().getHeight() / land.getArea().getHeight();

        // setup draw panel
        drawPanel = new DrawPanel(approach.getLand());
        drawPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent me) {
                frame.setTitle("LandGUI (" + approach.getName() + "): $" + land.getValue());
            }
        });

        // add listener for when mouse exits draw panel
        drawPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent me) {
                frame.setTitle("LandGUI (" + approach.getName() + "): $" + land.getValue());
                textArea.setText("Hover over an area to see it's land value!");
            }
        });

        // add listener for when mouse is moved over draw panel
        drawPanel.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent me) {
                // print out coordinates
                int x = me.getX() / pixelWidth;
                int y = me.getY() / pixelHeight;
                Area a = land.getArea().findAreaWithCoordinates(x, y);
                if (a != null) {
                    frame.setTitle("LandGUI (" + approach.getName() + "): $" + land.getValue());
                    textArea.setText("x=" + x + ", y=" + y + ", value=$" + a.getValue() + ", width=" + a.getWidth() + ", height=" + a.getHeight() + ", area=" + (a.getWidth() * a.getHeight()));
                } else {
                    textArea.setText("x=" + x + ", y=" + y);
                }
            }
        });

        // setup panel
        JPanel panel = new JPanel(new GridLayout(1, 1));

        // setup text area
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
        // land data structure
        private final Land land;

        // draw panel constructor
        public DrawPanel(Land land) {
            this.land = land;
            setPreferredSize(new Dimension(800, 600));
            super.setBackground(Color.WHITE);
        }

        // used to draw elements on panel
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            // specific pixel width and height
            int widthSize = drawPanel.getSize().width / land.getArea().width;
            int heightSize = drawPanel.getSize().height / land.getArea().height;

            // init land array to be displayed
            int[][] landArray = land.getArea().toArray();
            int width = landArray[0].length;
            int height = landArray.length;

            for(int i = 0; i < height; i++) {
                for(int j = 0; j < width; j++) {
                    // gets the hashcode at that part of the array
                    int value = landArray[i][j];

                    // generate colour based on bit shifted value of area hashcode
                    int red = (value >> 16) & 0xFF;
                    int green = (value >> 8) & 0xFF;
                    int blue = value & 0xFF;

                    // colour in the part of the array
                    g.setColor(new Color(red, green, blue));
                    g.fillRect(j * widthSize, i * heightSize, widthSize, heightSize);
                }
            }

            // add some spacing between elements
            g.setColor(Color.WHITE);
            for (int i = 0; i < width; i++) {
                g.drawLine(i * widthSize, 0, i * widthSize, height * heightSize);
            }

            for (int i = 0; i < height; i++) {
                g.drawLine(0, i * heightSize, width * widthSize, i * heightSize);
            }
        }
    }
}