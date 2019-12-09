package com.bostadbäst;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.LinkedHashMap;

import javax.swing.JPanel;

/**
 * Det här klassen ärver av {@code Slide} och kan därför ingå i en presentation.
 * Klassen representerar ett formulär som låter användaren mata in information.
 */
public class FormSlide extends Slide {

	private JPanel panel;
	private LinkedHashMap<String, Object[]> fields = new LinkedHashMap<>();

	public FormSlide() {
	}

	/**
	 * Skapar ett nytt formulär med ett namn.
	 * 
	 * @param nameUpdater en callback metod som returnerar en sträng.
	 */
	public FormSlide(NameUpdater nameUpdater) {
		super(nameUpdater);
	}

	/**
	 * Lägger till en nytt textfält formuläret.
	 * 
	 * @param id          en identifierare för textfältet.
	 * @param description en beskrivning om vad textfältet ska innehålla.
	 */
	public void addField(String id, String description) {
		if (fields.containsKey(id))
			throw new IllegalArgumentException(id + " is already taken!");
		fields.put(id, new Object[] { description, new TextField() });
	}

	/**
	 * Returnerar det som användaren har skrivit i ett textfält.
	 * 
	 * @param id en identifierare för textfältet.
	 */
	public String getFieldValue(String id) {
		if (!fields.containsKey(id))
			throw new IllegalArgumentException("Could not find a TextField with the ID: " + id);
		return ((TextField) fields.get(id)[1]).getText();
	}

	// Den här metoden bygger designen för formuläret.
	private void buildLayout() {
		panel = new JPanel(new GridBagLayout());
		panel.setBackground(Color.WHITE);
		panel.setVisible(false);

		GridBagConstraints c = new GridBagConstraints();

		c.anchor = GridBagConstraints.NORTHWEST;
		c.fill = GridBagConstraints.HORIZONTAL;

		c.insets = new Insets(0, 20, 20, 0);
		getNameLbl().setForeground(Colors.DARK_GRAY);
		panel.add(getNameLbl(), c);
		c.insets = new Insets(0, 0, 0, 0);

		JPanel formPanel = new JPanel(new GridBagLayout());
		formPanel.setBackground(Color.WHITE);

		c.gridx = 0;
		c.gridy = 1;
		c.weightx = 1;
		panel.add(formPanel, c);

		for (String key : fields.keySet()) {
			String description = (String) fields.get(key)[0];
			TextField field = (TextField) fields.get(key)[1];

			c.insets = new Insets(0, 20, 2, 20);
			formPanel.add(new Label(description, 12), c);
			c.gridy++;
			if (fields.keySet().toArray()[fields.size() - 1] == key)
				c.insets = new Insets(0, 20, 0, 20);
			else
				c.insets = new Insets(0, 20, 10, 20);
			formPanel.add(field, c);
			c.gridy++;
		}

		JPanel buttonPanel = new JPanel(new GridBagLayout());
		buttonPanel.setBackground(Color.WHITE);

		c.insets = new Insets(0, 0, 0, 0);
		c.anchor = GridBagConstraints.NORTHEAST;
		c.fill = GridBagConstraints.NONE;

		c.gridx = 0;
		c.gridy = 2;
		c.weightx = 1;
		c.weighty = 1;
		panel.add(buttonPanel, c);

		c.insets = new Insets(20, 0, 0, 20);

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
		// Vi tar bort allt innehåll i textfälten.
		fields.values().forEach(field -> ((TextField) field[1]).setText(""));
	}

	/**
	 * Returnerar en panel som innehåller hela formuläret.
	 */
	@Override
	public JPanel getPanel() {
		if (panel == null)
			buildLayout();
		return panel;
	}
}
