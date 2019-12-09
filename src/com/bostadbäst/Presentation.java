package com.bostadbäst;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 * En presentation består av flera {@code Slide} objekt. En presentation kan gå
 * fram och tillbaka mellan slides (likna detta med en PowerPoint presentation).
 * Varje presentation har en titel och varje slide har sitt egna namn och sitt
 * egna innehåll. En slide kan till exempel innehålla ett formulär se
 * {@code FormSlide} eller en tabell se {@code TableSlide}.
 * 
 * Tanken är att man efter behov ska kunna bygga sina egna presentationer för
 * att lösa en viss uppgift. Till exempel om man vill visa alla hus som ligger i
 * en viss förening, man löser detta genom att skapa en ny presentation som
 * innehåller ett formulär och en tabell. Användaren kan skriva in ett
 * organisationsnummer i formuläret och tabellen visar resultatet från
 * databasen.
 */
public class Presentation {

	private JPanel panel;

	private ArrayList<Slide> slides = new ArrayList<>();
	private int current = 0;
	private String title;

	/**
	 * Skapar en ny tom presentation.
	 * 
	 * @param title namnet på presentationen.
	 */
	public Presentation(String title) {
		this.title = title;
	}

	/**
	 * Lägg till en slide i presentationen (notera att ordningen spelar roll).
	 * 
	 * @param slide den nya sliden som ska läggas in.
	 */
	public void add(Slide slide) {
		slides.add(slide);
	}

	/**
	 * Går till nästa slide.
	 */
	public void next() {
		slides.get(current++).hide();
		slides.get(current).validate();
		slides.get(current).show();
	}

	/**
	 * Går till sliden som var innan.
	 */
	public void previous() {
		slides.get(current--).hide();
		slides.get(current).validate();
		slides.get(current).show();
	}

	/**
	 * Börjar presentationen genom att visa den slide som lades till först.
	 */
	public void start() {
		panel.setVisible(true);
		slides.get(0).validate();
		slides.get(0).show();
	}

	/**
	 * Avslutar presentationen genom att dölja alla synliga slides.
	 */
	public void stop() {
		panel.setVisible(false);
		slides.get(current).hide();
		current = 0;
	}

	// Den här metoden bygger designen för presentationen.
	private void buildLayout() {
		panel = new JPanel(new GridBagLayout());
		panel.setVisible(false);

		GridBagConstraints c = new GridBagConstraints();

		c.insets = new Insets(20, 20, 20, 20);

		c.anchor = GridBagConstraints.NORTHWEST;
		c.fill = GridBagConstraints.HORIZONTAL;

		c.ipady = 10;
		c.gridy = 0;
		Label titleLbl = new Label(title, 24);
		titleLbl.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Colors.GRAY));
		panel.add(titleLbl, c);
		c.ipady = 0;

		c.insets = new Insets(0, 0, 0, 0);

		c.fill = GridBagConstraints.BOTH;

		c.gridy = 1;
		c.weightx = 1;
		c.weighty = 1;
		for (Slide slide : slides) {
			panel.add(slide.getPanel(), c);
		}

		panel.setBackground(Color.WHITE);
	}

	/**
	 * Returnerar en panel som innehåller hela presentationen.
	 */
	public JPanel getPanel() {
		if (panel == null)
			buildLayout();

		return panel;
	}
}
