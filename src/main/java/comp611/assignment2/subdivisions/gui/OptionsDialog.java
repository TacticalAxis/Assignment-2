package comp611.assignment2.subdivisions.gui;

import javax.swing.*;
import java.awt.*;

class OptionsDialog extends JDialog {
    JLabel subdivisionCostLabel;

    JLabel landBaseValueLabel;

    JLabel landMaxValueLabel;

    JTextField subdivisionCostInput;
    JTextField landBaseValueInput;
    JTextField landMaxValueInput;

    public OptionsDialog(Frame owner, boolean modal) {
        super(owner, modal);

        this.setTitle("Options");
        this.setLayout(new GridLayout(4, 2));

        subdivisionCostLabel = new JLabel("Subdivision Cost");
        subdivisionCostLabel.setHorizontalAlignment(SwingConstants.CENTER);

        landBaseValueLabel = new JLabel("Land Base Value");
        landBaseValueLabel.setHorizontalAlignment(SwingConstants.CENTER);

        landMaxValueLabel = new JLabel("Land Max Value");
        landMaxValueLabel.setHorizontalAlignment(SwingConstants.CENTER);

        subdivisionCostInput = new JTextField("50");
        landBaseValueInput = new JTextField("20");
        landMaxValueInput = new JTextField("900");

        this.add(subdivisionCostLabel);
        this.add(subdivisionCostInput);
        this.add(landBaseValueLabel);
        this.add(landBaseValueInput);
        this.add(landMaxValueLabel);
        this.add(landMaxValueInput);
    }

    public Options getOptions() {
        int sCost = -1;
        try {
            sCost = Integer.parseInt(subdivisionCostInput.getText());
            if(sCost < 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Subdivision Cost must be an integer");
        }

        int lBase = -1;
        try {
            lBase = Integer.parseInt(landBaseValueInput.getText());
            if(lBase < 1) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Land Base Value must be an integer");
        }

        int lMax = -1;
        try {
            lMax = Integer.parseInt(landMaxValueInput.getText());
            if(lMax < 1) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Land Max Value must be an integer");
        }

        return new Options(
                sCost,
                lBase,
                lMax
        );
    }

    public static class Options {
        private final int subCost;
        private final double landBaseValue;
        private final double landMaxValue;

        public Options(int subdivisions, double landBaseValue, double landMaxValue) {
            this.subCost = subdivisions;
            this.landBaseValue = landBaseValue;
            this.landMaxValue = landMaxValue;
        }

        public int getSubCost() {
            return subCost;
        }

        public double getLandBaseValue() {
            return landBaseValue;
        }

        public double getLandMaxValue() {
            return landMaxValue;
        }
    }
}