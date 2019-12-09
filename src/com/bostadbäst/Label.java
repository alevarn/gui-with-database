package com.bostadbäst;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;

/**
 * En specialdesignad etikett som används inom applikationen.
 */
public class Label extends JLabel {
	
	public Label(String text) {
		this(text, 16);
	}

	public Label(String text, int fontSize) {
		super(text);
		super.setForeground(Color.BLACK);
		super.setFont(new Font("Arial", Font.PLAIN, fontSize));
	}
}
