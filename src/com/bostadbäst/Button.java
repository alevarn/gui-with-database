package com.bostadbäst;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.border.Border;

/**
 * En specialdesignad knapp som byter färg när musen är ovanför den. 
 */
public class Button extends JButton implements MouseListener {

	// Här har vi information om hur knappen ska se ut och bete sig.
	private static final Font FONT = new Font("Arial", Font.PLAIN, 16);
	private static final Cursor CURSOR = new Cursor(Cursor.HAND_CURSOR);
	private static final Border BORDER = BorderFactory.createEmptyBorder(10, 20, 10, 20);
	
	private Color background;
	private Color hoverBackground;
	
	public Button(String text, Color foreground, Color background)
	{
		super(text);
		super.setFocusPainted(false);
		super.setFont(FONT);
		super.setCursor(CURSOR);
		super.setForeground(foreground);
		super.setBackground(background);
		super.setBorder(BORDER);
		super.addMouseListener(this);
		
		this.background = background;
		
		// Här beräknar vi hur mycket mörkare bakgrunden ska bli när användaren har muspekaren ovanför en knapp.
		int red = background.getRed() - 30 > 0 ? background.getRed() - 30 : 0;
		int green = background.getGreen() - 30 > 0 ? background.getGreen() - 30 : 0;
		int blue = background.getBlue() - 30 > 0 ? background.getBlue() - 30 : 0;
		
		this.hoverBackground = new Color(red, green, blue);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) { super.setBackground(hoverBackground); }

	@Override
	public void mouseExited(MouseEvent e) { super.setBackground(background); }
}
