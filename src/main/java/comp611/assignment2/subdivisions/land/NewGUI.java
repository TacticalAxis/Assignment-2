package comp611.assignment2.subdivisions.land;

import comp611.assignment2.subdivisions.approach.Approach;
import comp611.assignment2.subdivisions.approach.BruteForceApproach;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class NewGUI extends JPanel implements ActionListener {

    private final DrawPanel drawPanel;

    private final Timer timer;
//    private final JTextArea textArea;

    private Approach approach;

    private final int pixelWidth;
    private final int pixelHeight;

    // constructor
    public NewGUI(Approach approach, JFrame frame) {
        super(new BorderLayout());
        this.approach = approach;

        setPreferredSize(new Dimension(800, 800));

        Land land = approach.getLand();

        this.pixelWidth = (int) getPreferredSize().getWidth() / land.getArea().getWidth();
        this.pixelHeight = (int) getPreferredSize().getHeight() / land.getArea().getHeight();

        // setup draw panel
        drawPanel = new DrawPanel(approach.getLand());
        drawPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent me) {
                // print out coordinates
                frame.setTitle("LandGUI (" + approach.getName() + ")");
                int x = me.getX() / pixelWidth;
                int y = me.getY() / pixelHeight;
//                frame.setTitle("LandGUI (" + approach.getName() + "): " + x + ", " + y);
//                textArea.setText("x=" + x + ", y=" + y);
            }
        });

        // add listener for when mouse exits draw panel
        drawPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent me) {
                // clear text area
                frame.setTitle("LandGUI");
//                textArea.setText("Hover over an area to see it's land value!");
            }
        });

        drawPanel.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent me) {
            // print out coordinates
            int x = me.getX() / pixelWidth;
            int y = me.getY() / pixelHeight;
            Area a = land.getArea().findAreaWithCoordinates(x, y);
            if (a != null) {
                frame.setTitle("LandGUI (" + approach.getName() + "): " + x + ", " + y);
//                textArea.setText("x=" + x + ", y=" + y + ", value=$" + a.getValue() + ", width=" + a.getWidth() + ", height=" + a.getHeight() + ", area=" + (a.getWidth() * a.getHeight()));
            } else {
//                textArea.setText("x=" + x + ", y=" + y);
            }
            }
        });

        // setup panel
        JPanel panel = new JPanel(new GridBagLayout());

        // make a text area that takes up the entire row, then on the next row, make two spaced input fields, with a button after to submit, then on the next row, make 3 spaced buttons

        // add panels to frame
        super.add(panel, BorderLayout.SOUTH);
        super.add(drawPanel, BorderLayout.CENTER);

        // setup tick timer
        timer = new Timer(10, this);
        timer.start();
    }

    public Approach getApproach() {
        return approach;
    }

    public void setApproach(Approach approach) {
        this.approach = approach;
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

        private final Land land;

        // draw panel constructor
        public DrawPanel(Land land) {
            this.land = land;
            setPreferredSize(new Dimension(800, 600));
            super.setBackground(Color.WHITE);
        }

        // used to draw elements on hotplate panel
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            int widthSize = drawPanel.getSize().width / land.getArea().width;
            int heightSize = drawPanel.getSize().height / land.getArea().height;

            int[][] landArray = land.getArea().toArray();
            int width = landArray[0].length;
            int height = landArray.length;

            for(int i = 0; i < height; i++) {
                for(int j = 0; j < width; j++) {
                    int value = landArray[i][j];
                    int red = (value >> 16) & 0xFF;
                    int green = (value >> 8) & 0xFF;
                    int blue = value & 0xFF;
                    g.setColor(new Color(red, green, blue));
                    g.fillRect(j * widthSize, i * heightSize, widthSize, heightSize);
                }
            }

            // add some spacing between elements
            g.setColor(Color.BLACK);
            for (int i = 0; i < width; i++) {
                g.drawLine(i * widthSize, 0, i * widthSize, height * heightSize);
            }

            for (int i = 0; i < height; i++) {
                g.drawLine(0, i * heightSize, width * widthSize, i * heightSize);
            }
        }
    }

    public static void main(String[] args) {

        BruteForceApproach bruteForceApproach = new BruteForceApproach(new Land(6, 6, 50, 20,1000));
        bruteForceApproach.startTimer();
//        Approach.Result solution = bruteForceApproach.solve();
//        System.out.println("Brute Force Solution Value: " + solution.getValue());
//        System.out.println("Brute Force Solution Time: " + solution.getTime());
//        System.out.println("This took " + bruteForceApproach.getTime() + "ms");
//        System.out.println(bruteForceApproach.getLand());
        bruteForceApproach.stopTimer();

        JFrame frame = new JFrame("Land GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.getContentPane().add(new NewGUI(bruteForceApproach, frame));
        frame.pack();

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}