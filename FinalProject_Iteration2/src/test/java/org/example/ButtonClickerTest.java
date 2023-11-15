package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.event.ActionEvent;

import static org.junit.jupiter.api.Assertions.*;

class ButtonClickListenerTest {

    @BeforeEach
    void setUp() {
        JPanel panel = new JPanel(); // Create a panel
        RegularCalculator.setupRegularCalculatorPanel(panel);
    }

    @Test
    public void testActionPerformedWithNumber()
    {
        //Create a test instance of RegularCalculator
        RegularCalculator.textDisplay = new JTextField();
        ButtonClickListener buttonClickListener = new ButtonClickListener();

        //Create a test instance of ActionEvent with a number
        ActionEvent actionEvent = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "5");

        buttonClickListener.actionPerformed(actionEvent);

        assertEquals("5", RegularCalculator.textDisplay.getText());
    }

    @Test
    public void testActionPerformedWithCEButton()
    {
        //Create a test instance of RegularCalculator
        RegularCalculator.textDisplay = new JTextField();
        ButtonClickListener buttonClickListener = new ButtonClickListener();

        //Create a test instance of ActionEvent with the CE button
        ActionEvent actionEvent = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "CE");

        buttonClickListener.actionPerformed(actionEvent);

        assertEquals("", RegularCalculator.textDisplay.getText());
    }
}
