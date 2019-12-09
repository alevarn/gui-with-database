package com.bostadbäst;

import java.awt.Color;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JPanel;

/**
 * Det här klassen ärver av {@code Slide} och kan därför ingå i en presentation.
 * Klassen representerar en tabell.
 */
public class TableSlide extends Slide {

	private JPanel panel;
	private TableUpdater tableUpdater;
	private Table table;

	/**
	 * Skapar en ny tom tabell. Notera att om den här konstruktorn anropas så måste
	 * man sedan separat anropa setTable() för att allt ska fungera.
	 */
	public TableSlide() {
		table = new Table();
	}

	/**
	 * Skapar en ny tom tabell.
	 * 
	 * @param tableUpdater en callback-metod med syfta att uppdatera tabellen.
	 */
	public TableSlide(TableUpdater tableUpdater) {
		this.tableUpdater = tableUpdater;
		table = new Table();
	}

	/**
	 * Skapar en ny tom tabell.
	 * 
	 * @param nameUpdater  en callback metod som returnerar en sträng.
	 * @param tableUpdater en callback-metod med syfta att uppdatera tabellen.
	 */
	public TableSlide(NameUpdater nameUpdater, TableUpdater tableUpdater) {
		super(nameUpdater);
		this.tableUpdater = tableUpdater;
		table = new Table();
	}

	/**
	 * Sätter en callback-metod med syfta att uppdatera tabellen.
	 */
	public void setTable(TableUpdater updater) {
		this.tableUpdater = updater;
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

		c.insets = new Insets(0, 20, 0, 20);
		c.fill = GridBagConstraints.BOTH;
		c.weighty = 1;
		c.gridy = 1;
		panel.add(table, c);
		c.insets = new Insets(0, 0, 0, 0);

		JPanel buttonPanel = new JPanel(new GridBagLayout());
		buttonPanel.setBackground(Color.WHITE);

		c.insets = new Insets(0, 0, 0, 0);
		c.anchor = GridBagConstraints.NORTHEAST;
		c.fill = GridBagConstraints.NONE;

		c.gridx = 0;
		c.gridy = 2;
		c.weightx = 1;
		c.weighty = 0;
		panel.add(buttonPanel, c);

		c.insets = new Insets(20, 0, 20, 20);

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
		tableUpdater.updateTable(table); // Vi uppdaterar vår tabell.
	}

	/**
	 * Returnerar en panel som innehåller hela tabellen.
	 */
	@Override
	public JPanel getPanel() {
		if (panel == null)
			buildLayout();
		return panel;
	}
}
