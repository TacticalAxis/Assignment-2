package comp611.assignment2.subdivisions.gui;

import comp611.assignment2.subdivisions.approach.Approach;
import comp611.assignment2.subdivisions.approach.BruteForceApproach;
import comp611.assignment2.subdivisions.approach.GreedyApproach;
import comp611.assignment2.subdivisions.approach.RExact;
import comp611.assignment2.subdivisions.land.Land;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//@SuppressWarnings("FieldCanBeLocal")
public class Menu extends JFrame implements ActionListener {

    private JButton bruteforceButton;
    private JButton exactButton;
    private JButton greedyButton;

    private JLabel statusLabel;
    private JLabel bestValueLabel;
    private JTextField areaHeightInput;
    private JTextField areaWidthInput;
    private JLabel timeElapsedLabel;
    private JLabel subdivisionsMadeLabel;

    private Approach approach;

    private boolean inProgress;
    private JFrame extGUI;

    private OptionsDialog optionsDialog;

    private Timer timer;

    public Menu() {
        this.inProgress = false;
        this.extGUI = null;
        initComponents();
    }

    private void startBruteForce() {
        if (inProgress) {
            return;
        }

        inProgress = true;
        if(this.extGUI != null) {
            this.extGUI.dispose();
            this.extGUI = null;
        }

        // get area dimensions
        int width = Integer.parseInt(areaWidthInput.getText());
        int height = Integer.parseInt(areaHeightInput.getText());

        // create land
        OptionsDialog.Options o = optionsDialog.getOptions();
        Land land = new Land(width, height, o.getSubCost(), o.getLandBaseValue(), o.getLandMaxValue());

        // create brute force approach
        approach = new BruteForceApproach(land);

        // start brute force
        new Thread(() -> {
            System.out.println("Starting brute force...");
            approach.solve();
        }).start();
    }

    private void startGreedy() {
        if (inProgress) {
            return;
        }

        inProgress = true;
        if(this.extGUI != null) {
            this.extGUI.dispose();
            this.extGUI = null;
        }

        // get area dimensions
        int width = Integer.parseInt(areaWidthInput.getText());
        int height = Integer.parseInt(areaHeightInput.getText());

        // create land
        OptionsDialog.Options o = optionsDialog.getOptions();
        Land land = new Land(width, height, o.getSubCost(), o.getLandBaseValue(), o.getLandMaxValue());

        // create brute force approach
        approach = new GreedyApproach(land);

        // start brute force
        new Thread(() -> {
            System.out.println("Starting brute force...");
            approach.solve();
        }).start();
    }

    private void startExact() {
        if (inProgress) {
            return;
        }

        inProgress = true;
        if(this.extGUI != null) {
            this.extGUI.dispose();
            this.extGUI = null;
        }

        // get area dimensions
        int width = Integer.parseInt(areaWidthInput.getText());
        int height = Integer.parseInt(areaHeightInput.getText());

        // create land
        OptionsDialog.Options o = optionsDialog.getOptions();
        Land land = new Land(width, height, o.getSubCost(), o.getLandBaseValue(), o.getLandMaxValue());

        // create brute force approach
        approach = new RExact(land);

        // start brute force
        new Thread(() -> {
            System.out.println("Starting exact approach...");
            approach.solve();
        }).start();
    }
                            
    private void initComponents() {
        JLabel mainTitle;
        JLabel areaWidthLabel;
        JLabel areaHeightLabel;
        JButton optionsButton;

        mainTitle = new JLabel();
        areaWidthLabel = new JLabel();
        bestValueLabel = new JLabel();
        areaHeightLabel = new JLabel();
        areaHeightInput = new JTextField();
        bruteforceButton = new JButton();
        exactButton = new JButton();
        greedyButton = new JButton();
        optionsButton = new JButton();
        statusLabel = new JLabel();
        areaWidthInput = new JTextField();
        timeElapsedLabel = new JLabel();
        subdivisionsMadeLabel = new JLabel();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(600, 300));

        optionsDialog = new OptionsDialog(this, false);

        mainTitle.setFont(new Font("Bahnschrift", Font.BOLD, 24)); // NOI18N
        mainTitle.setHorizontalAlignment(SwingConstants.CENTER);
        mainTitle.setText("Assignment 2 - Subdivisions");

        areaWidthLabel.setFont(new Font("Bahnschrift", Font.PLAIN, 18)); // NOI18N
        areaWidthLabel.setHorizontalAlignment(SwingConstants.CENTER);
        areaWidthLabel.setText("Area Width");
        areaWidthLabel.setPreferredSize(new Dimension(160, 40));

        areaWidthInput.setFont(new Font("Bahnschrift", Font.PLAIN, 14)); // NOI18N
        areaWidthInput.setHorizontalAlignment(SwingConstants.CENTER);
        areaWidthInput.setText("6");
        areaWidthInput.setPreferredSize(new Dimension(160, 40));
//        jTextField5.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent evt) {
//                jTextField5ActionPerformed(evt);
//            }
//        });

        areaHeightLabel.setFont(new Font("Bahnschrift", Font.PLAIN, 18)); // NOI18N
        areaHeightLabel.setHorizontalAlignment(SwingConstants.CENTER);
        areaHeightLabel.setText("Area Height");
        areaHeightLabel.setPreferredSize(new Dimension(160, 40));

        areaHeightInput.setFont(new Font("Bahnschrift", Font.PLAIN, 14)); // NOI18N
        areaHeightInput.setHorizontalAlignment(SwingConstants.CENTER);
        areaHeightInput.setText("3");
        areaHeightInput.setPreferredSize(new Dimension(160, 40));
//        jTextField4.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent evt) {
//                jTextField4ActionPerformed(evt);
//            }
//        });

        optionsButton.setFont(new Font("Bahnschrift", Font.PLAIN, 18)); // NOI18N
        optionsButton.setHorizontalAlignment(SwingConstants.CENTER);
        optionsButton.setText("Options");
        optionsButton.setPreferredSize(new Dimension(160, 40));
        optionsButton.addActionListener(evt -> {
            System.out.println("Options button clicked");
            optionsDialog.setSize(250, 120);
            optionsDialog.setVisible(true);
            optionsDialog.getOptions();
        });

        bruteforceButton.setFont(new Font("Bahnschrift", Font.PLAIN, 16)); // NOI18N
        bruteforceButton.setText("Bruteforce");
        bruteforceButton.setPreferredSize(new Dimension(160, 40));
        bruteforceButton.addActionListener(evt -> {
            System.out.println("Bruteforce");
            startBruteForce();
        });

        exactButton.setFont(new Font("Bahnschrift", Font.PLAIN, 16)); // NOI18N
        exactButton.setText("Exact");
        exactButton.setPreferredSize(new Dimension(160, 40));
        exactButton.addActionListener(evt -> {
            System.out.println("Exact");
            startExact();
        });

        greedyButton.setFont(new Font("Bahnschrift", Font.PLAIN, 16)); // NOI18N
        greedyButton.setText("Greedy");
        greedyButton.setPreferredSize(new Dimension(160, 40));
        greedyButton.addActionListener(evt -> {
            System.out.println("Greedy");
            startGreedy();
        });

        statusLabel.setFont(new Font("Bahnschrift", Font.PLAIN, 18)); // NOI18N
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        statusLabel.setText("Stopped");
        statusLabel.setBorder(new LineBorder(new Color(0, 255, 0), 3, true));
        statusLabel.setPreferredSize(new Dimension(160, 40));

        timeElapsedLabel.setFont(new Font("Bahnschrift", Font.PLAIN, 14)); // NOI18N
        timeElapsedLabel.setHorizontalAlignment(SwingConstants.LEFT);
        timeElapsedLabel.setText("Time Elapsed: 0:00");
        timeElapsedLabel.setPreferredSize(new Dimension(160, 40));
//        jTextField6.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent evt) {
//                jTextField6ActionPerformed(evt);
//            }
//        });

        subdivisionsMadeLabel.setFont(new Font("Bahnschrift", Font.PLAIN, 14)); // NOI18N
        subdivisionsMadeLabel.setHorizontalAlignment(SwingConstants.LEFT);
        subdivisionsMadeLabel.setText("Subdivisions Made");
        subdivisionsMadeLabel.setPreferredSize(new Dimension(160, 40));
//        jTextField7.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent evt) {
//                jTextField7ActionPerformed(evt);
//            }
//        });

        bestValueLabel.setFont(new Font("Bahnschrift", Font.PLAIN, 14)); // NOI18N
        bestValueLabel.setHorizontalAlignment(SwingConstants.LEFT);
        bestValueLabel.setText("Best Value: ");
        bestValueLabel.setPreferredSize(new Dimension(160, 40));
//        jTextField3.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent evt) {
//                jTextField3ActionPerformed(evt);
//            }
//        });

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addGap(0, 120, Short.MAX_VALUE)
                                                .addComponent(mainTitle, GroupLayout.PREFERRED_SIZE, 373, GroupLayout.PREFERRED_SIZE)
                                                .addGap(107, 107, 107))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(areaHeightInput, GroupLayout.PREFERRED_SIZE, 160, GroupLayout.PREFERRED_SIZE)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(exactButton, GroupLayout.PREFERRED_SIZE, 160, GroupLayout.PREFERRED_SIZE)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(bestValueLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(areaHeightLabel, GroupLayout.PREFERRED_SIZE, 160, GroupLayout.PREFERRED_SIZE)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(greedyButton, GroupLayout.PREFERRED_SIZE, 160, GroupLayout.PREFERRED_SIZE)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(subdivisionsMadeLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                                        .addGroup(layout.createSequentialGroup()
                                                                                .addComponent(areaWidthLabel, GroupLayout.PREFERRED_SIZE, 160, GroupLayout.PREFERRED_SIZE)
                                                                                .addGap(18, 18, 18)
                                                                                .addComponent(optionsButton, GroupLayout.PREFERRED_SIZE, 160, GroupLayout.PREFERRED_SIZE))
                                                                        .addGroup(layout.createSequentialGroup()
                                                                                .addComponent(areaWidthInput, GroupLayout.PREFERRED_SIZE, 160, GroupLayout.PREFERRED_SIZE)
                                                                                .addGap(18, 18, 18)
                                                                                .addComponent(bruteforceButton, GroupLayout.PREFERRED_SIZE, 160, GroupLayout.PREFERRED_SIZE)))
                                                                .addGap(18, 18, 18)
                                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                                        .addComponent(timeElapsedLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                        .addComponent(statusLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                                                .addContainerGap(84, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(mainTitle, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(areaWidthLabel, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(optionsButton, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(statusLabel, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(bruteforceButton, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(areaWidthInput, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(timeElapsedLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                .addComponent(greedyButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addComponent(subdivisionsMadeLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                        .addComponent(areaHeightLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(exactButton, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(areaHeightInput, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(bestValueLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 64, Short.MAX_VALUE))
        );

        pack();

        timer = new Timer(10, this);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == timer) {
            if(approach == null || !inProgress) {
                bruteforceButton.setEnabled(true);
                greedyButton.setEnabled(true);
                exactButton.setEnabled(true);
                areaHeightInput.setEnabled(true);
                areaWidthInput.setEnabled(true);

                if(approach != null) {
                    statusLabel.setText("Finished");
                    statusLabel.setBorder(new LineBorder(new Color(0, 255, 0), 3, true));
                    timeElapsedLabel.setText("Time elapsed: " + approach.getTime() + "ms");
                    bestValueLabel.setText("Best value: " + approach.getBestValue());
                    subdivisionsMadeLabel.setText("Subdivisions made: " + approach.getSubdivisions());

                    // open the LandGUI panel in a new window
                    if(this.extGUI == null) {
                        JFrame frame = new JFrame("Subdivided Land");
                        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                        frame.getContentPane().add(new LandGUI(approach, frame));
                        frame.pack();
                        frame.setLocationRelativeTo(null);
                        frame.setVisible(true);
                        this.extGUI = frame;
                    }
                } else {
                    statusLabel.setText("Ready");
                    timeElapsedLabel.setText("Time elapsed: N/A");
                    bestValueLabel.setText("Best value: N/A");
                    subdivisionsMadeLabel.setText("Subdivisions made: N/A");
                }
            }

            if(approach != null) {
                if(approach.isComplete()) {
                    inProgress = false;
                } else {
                    statusLabel.setText("In progress");
                    statusLabel.setBorder(new LineBorder(new Color(255, 0, 0), 3, true));
                    timeElapsedLabel.setText("Time elapsed: " + approach.getCurrentTime() + "ms");
                    bestValueLabel.setText("Best value: " + approach.getBestValue());
                    subdivisionsMadeLabel.setText("Subdivisions made: " + approach.getSubdivisions());

                    bruteforceButton.setEnabled(false);
                    greedyButton.setEnabled(false);
                    exactButton.setEnabled(false);
                    areaHeightInput.setEnabled(false);
                    areaWidthInput.setEnabled(false);
                }
            }
        }
    }


    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            System.out.println("Error: GUI look and feel not found.");
        }

        EventQueue.invokeLater(() -> {
            new Menu().setVisible(true);
            System.out.println("GUI started.");
        });
    }
}
