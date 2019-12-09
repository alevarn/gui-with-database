package com.bostadbäst;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JTextField;
import javax.swing.border.Border;

/**
 * Ett specialdesignat textfält.
 */
public class TextField extends JTextField {

	private static final Font FONT = new Font("Arial", Font.PLAIN, 16);
	private static final Border BORDER = BorderFactory.createCompoundBorder(
			BorderFactory.createLineBorder(Colors.GRAY), BorderFactory.createEmptyBorder(10, 10, 10, 10));

	public TextField() {
		super(24);
		super.setForeground(Color.BLACK);
		super.setFont(FONT);
		super.setBorder(BORDER);
	}
}
