package comp611.assignment2.subdivisions.gui;

import javax.swing.*;
import java.awt.*;

class OptionsDialog extends JDialog {
    JLabel subdivisionCostLabel;
    JTextField subdivisionCostInput;

    public OptionsDialog(Frame owner, boolean modal) {
        super(owner, modal);

        this.setTitle("Options");
        this.setLayout(new GridLayout(2, 2));

        subdivisionCostLabel = new JLabel("Subdivision Cost");
        subdivisionCostLabel.setHorizontalAlignment(SwingConstants.CENTER);

        subdivisionCostInput = new JTextField("50");

        this.add(subdivisionCostLabel);
        this.add(subdivisionCostInput);
    }

    public Options getOptions() {
        int sCost = -1;
        try {
            sCost = Integer.parseInt(subdivisionCostInput.getText());
            if(sCost < 0) {throw new NumberFormatException();}
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Subdivision Cost must be an positive integer (zero is allowed).", "Error", JOptionPane.ERROR_MESSAGE);
        }

        return new Options(sCost);
    }

    public static class Options {
        private final int subCost;

        public Options(int subdivisions) {
            this.subCost = subdivisions;
        }

        public int getSubCost() {
            return subCost;
        }
    }
}