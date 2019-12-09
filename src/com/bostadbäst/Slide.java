package com.bostadbäst;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JPanel;

/**
 * Det här är basklassen för {@code FormSlide}, {@code TableSlide} och
 * {@code TextSlide}.
 */
public abstract class Slide {

	private ArrayList<Button> buttons = new ArrayList<>();
	private Label nameLbl = new Label("", 18);
	private NameUpdater nameUpdater;

	public Slide() {
	}

	/**
	 * Skapar en ny Slide med ett namn.
	 * 
	 * @param nameUpdater en callback metod som returnerar en sträng.
	 */
	public Slide(NameUpdater nameUpdater) {
		setName(nameUpdater);
	}

	/**
	 * Ändrar metoden som ska uppdatera namnet på sliden.
	 */
	public void setName(NameUpdater nameUpdater) {
		this.nameUpdater = nameUpdater;
	}

	/**
	 * Returnerar namnet på sliden.
	 */
	public Label getNameLbl() {
		return nameLbl;
	}

	/**
	 * Gör panelen synlig.
	 */
	public void show() {
		getPanel().setVisible(true);
	}

	/**
	 * Gör panelen osynlig.
	 */
	public void hide() {
		getPanel().setVisible(false);
	}

	/**
	 * Lägger till en ny knapp på sliden.
	 * 
	 * @param text       det som ska stå innuti knappen.
	 * @param foreground färgen på texten.
	 * @param background färgen på bakgrunden.
	 * @param listener   en callback-metod som anropas på knapptryckningar.
	 */
	public void addButton(String text, Color foreground, Color background, ActionListener listener) {
		Button button = new Button(text, foreground, background);
		button.addActionListener(listener);
		buttons.add(button);
	}

	/**
	 * Returnerar alla knappar.
	 */
	public ArrayList<Button> getButtons() {
		return buttons;
	}

	/**
	 * Validerar namnet på sliden. Den här metoden anropas av ett Presentation
	 * objekt när sliden kommer in i bild.
	 */
	public void validate() {
		if (nameUpdater != null) {
			nameLbl.setText(nameUpdater.getName()); // Vi uppdaterar namnet för sliden.
		}
	}

	/**
	 * Returnerar en panel som innehåller sliden.
	 */
	public abstract JPanel getPanel();
}
