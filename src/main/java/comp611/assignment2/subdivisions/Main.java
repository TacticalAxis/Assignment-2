package comp611.assignment2.subdivisions;

import comp611.assignment2.subdivisions.gui.Menu;

import javax.swing.*;
import java.awt.*;

public class Main {
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
