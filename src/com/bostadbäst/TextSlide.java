package com.bostadbäst;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JPanel;

/**
 * Det här klassen ärver av {@code Slide} och kan därför ingå i en presentation.
 * Klassen representerar ett enkelt textmeddelande.
 */
public class TextSlide extends Slide {

	private JPanel panel;

	/**
	 * Skapar ett tomt textmeddelande.
	 */
	public TextSlide() {
	}

	/**
	 * Skapar ett tomt textmeddelande.
	 * 
	 * @param nameUpdater en callback-metod som returnerar en sträng. Tanken är att
	 *                    den här metoden ska returnera texten som sliden ska
	 *                    innehålla.
	 */
	public TextSlide(NameUpdater nameUpdater) {
		super(nameUpdater);
	}

	// Den här metoden bygger designen för textmeddelandet.
	private void buildLayout() {
		panel = new JPanel(new GridBagLayout());
		panel.setBackground(Color.WHITE);
		panel.setVisible(false);

		GridBagConstraints c = new GridBagConstraints();

		c.insets = new Insets(0, 20, 0, 0);
		c.anchor = GridBagConstraints.NORTHWEST;

		c.gridx = 0;
		c.gridy = 0;
		panel.add(getNameLbl(), c);

		c.insets = new Insets(0, 0, 0, 0);

		JPanel buttonPanel = new JPanel(new GridBagLayout());
		buttonPanel.setBackground(Color.WHITE);

		c.gridx = 0;
		c.gridy = 1;
		c.weightx = 1;
		c.weighty = 1;
		panel.add(buttonPanel, c);

		c.insets = new Insets(20, 20, 0, 0);

		c.gridx = 0;
		c.gridy = 0;
		c.weighty = 0;
		for (Button button : getButtons()) {
			buttonPanel.add(button, c);
			c.gridx++;
		}
	}

	/**
	 * Den här metoden anropas av ett objekt av typen {@code Presentation} varje
	 * gång sliden visas för användaren.
	 */
	@Override
	public void validate() {
		super.validate();
	}

	/**
	 * Returnerar en panel som innehåller hela textmeddelandet.
	 */
	@Override
	public JPanel getPanel() {
		if (panel == null)
			buildLayout();
		return panel;
	}
}
