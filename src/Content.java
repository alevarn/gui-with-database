import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Superklassen till {@code Form} och {@code Table}. Varje objekt av den här
 * klassen kan visas på skärmen för användaren.
 */
public abstract class Content {

	private StringCallback title;
	private StringCallback description;

	private JPanel container = new JPanel(new GridBagLayout());
	private JPanel buttons = new JPanel(new GridBagLayout());

	private JLabel titleLabel = new JLabel();
	private JLabel descriptionLabel = new JLabel();

	public Content() {
		buildLayout();
		hide();
	}

	/**
	 * Sätter titeln.
	 * 
	 * @param StringCallback innehåller en callback metod som ska returnera en sträng.
	 * */
	public void setTitle(StringCallback title) {
		this.title = title;
	}

	/**
	 * Sätter beskrivningen.
	 * 
	 * @param StringCallback innehåller en callback metod som ska returnera en sträng.
	 * */
	public void setDescription(StringCallback description) {
		this.description = description;
	}

	// Bygger den grafiska designen.
	private void buildLayout() {
		GridBagConstraints c = new GridBagConstraints();

		container.setBackground(Color.WHITE);
		buttons.setBackground(Color.WHITE);

		titleLabel.setFont(new Font("Arial", Font.PLAIN, 24));
		titleLabel.setForeground(Color.BLACK);
		titleLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Colors.GRAY));

		descriptionLabel.setFont(new Font("Arial", Font.PLAIN, 18));
		descriptionLabel.setForeground(Color.BLACK);

		c.anchor = GridBagConstraints.NORTH;
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(25, 25, 0, 25);

		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 1;
		c.weighty = 0;
		c.ipady = 10;
		container.add(titleLabel, c);

		c.insets = new Insets(0, 25, 0, 0);

		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 1;
		c.weighty = 0;
		c.ipady = 35;
		container.add(descriptionLabel, c);

		c.insets = new Insets(15, 25, 15, 25);

		c.anchor = GridBagConstraints.EAST;
		c.fill = GridBagConstraints.NONE;

		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 1;
		c.weighty = 0;
		c.ipady = 0;
		container.add(buttons, c);

		if (this instanceof Form) {
			JPanel p = new JPanel();
			p.setBackground(Color.WHITE);
			c.fill = GridBagConstraints.BOTH;
			c.gridy = 4;
			c.weightx = 1;
			c.weighty = 1;
			container.add(p, c);
		}
	}

	/**
	 * Visar allt innehåll för användaren.
	 * */
	public void show() {
		container.setVisible(true);
	}

	/**
	 * Gömmer allt innehåll för användaren.
	 * */
	public void hide() {
		container.setVisible(false);
	}

	/**
	 * Validerar innehållet. Det här uppdaterar titeln och beskrivningen.
	 * */
	public void validate() {
		titleLabel.setText(title.getString());
		descriptionLabel.setText(description.getString());
	}

	/**
	 * Lägger till en ny knapp.
	 * */
	public void addButton(String text, Color foreground, Color background, ActionListener listener) {
		Button button = new Button(text, foreground, background);
		button.addActionListener(listener);

		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(0, 5, 0, 0);
		buttons.add(button, c);
	}

	/**
	 * Returnerar en {@code JPanel} som innehåller allting.
	 * */
	public JPanel getContainer() {
		return container;
	}
}
