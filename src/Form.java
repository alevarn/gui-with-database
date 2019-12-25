import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.LinkedHashMap;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Det här är en subklass till {@code Content} och representerar ett formulär
 * som kan visas för användaren.
 */
public class Form extends Content {

	private LinkedHashMap<String, JTextField> fields = new LinkedHashMap<>();

	private JPanel container = new JPanel(new GridBagLayout());

	public Form() {
		buildLayout();
	}

	// Bygger den grafiska designen.
	private void buildLayout() {
		GridBagConstraints c = new GridBagConstraints();

		container.setBackground(Color.WHITE);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(0, 25, 0, 25);

		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 1;
		c.weighty = 0;
		super.getContainer().add(container, c);
	}

	/**
	 * Vi lägger till ett nytt textfält på formuläret.
	 * 
	 * @param key         en nyckel för att identifiera fältet unikt.
	 * @param description den text som ska stå ovanför formuläret.
	 */
	public void addField(String key, String description) {
		if (fields.containsKey(key))
			throw new IllegalArgumentException("Key taken!");

		JTextField field = new JTextField();
		field.setFont(new Font("Arial", Font.PLAIN, 16));
		field.setForeground(Color.BLACK);
		field.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Colors.GRAY),
				BorderFactory.createEmptyBorder(10, 15, 10, 15)));

		JLabel descriptionLabel = new JLabel(description);
		descriptionLabel.setFont(new Font("Arial", Font.PLAIN, 12));
		descriptionLabel.setForeground(Color.BLACK);

		GridBagConstraints c = new GridBagConstraints();

		c.fill = GridBagConstraints.BOTH;
		if (fields.size() != 0)
			c.insets = new Insets(7, 0, 0, 0);

		c.gridx = 0;
		c.gridy = fields.size() * 2;
		c.weightx = 1;
		c.weighty = 0;
		container.add(descriptionLabel, c);

		c.insets = new Insets(2, 0, 0, 0);

		c.gridx = 0;
		c.gridy = fields.size() * 2 + 1;
		c.weightx = 1;
		c.weighty = 0;
		container.add(field, c);

		fields.put(key, field);
	}

	/**
	 * Returnerar texten som finns i ett textfält.
	 */
	public String getFieldValue(String key) {
		if (!fields.containsKey(key))
			throw new IllegalArgumentException("Key not found: " + key);

		return fields.get(key).getText();
	}

	/**
	 * Returnerar sant om textfältet är tomt annars falskt.
	 */
	public boolean isFieldEmpty(String key) {
		if (!fields.containsKey(key))
			throw new IllegalArgumentException("Key not found: " + key);

		return fields.get(key).getText().isBlank();
	}

	/**
	 * Validerar innehållet. Det här uppdaterar titeln och beskrivningen och tömmer
	 * texten i alla textfält.
	 */
	@Override
	public void validate() {
		super.validate();
		fields.values().forEach(field -> field.setText(""));
	}
}
