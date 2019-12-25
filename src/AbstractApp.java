import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Den här klassen tillhandahåller fönstret och sätter upp designen. Det är en
 * abstrakt klass som döljer funktionalitet för att göra implementationen i
 * {@code App} lättare att läsa.
 */
public abstract class AbstractApp {

	private JFrame frame;
	private JPanel contentContainer = new JPanel(new GridBagLayout());

	/**
	 * Det nuvarande som visas på skärmen.
	 */
	private Content current;

	public AbstractApp(String title, int width, int height) {
		frame = new JFrame(title);
		frame.setSize(width, height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new GridBagLayout());
		initializeComponents();
		setup();
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				cleanup();
			}
		});
		frame.setVisible(true);
	}

	// Den här metoden sätter upp den grafiska användargränssnittet som användaren
	// ska se.
	private void initializeComponents() {
		GridBagConstraints c = new GridBagConstraints();

		JLabel title = new JLabel(frame.getTitle(), JLabel.CENTER);
		title.setFont(new Font("Arial", Font.PLAIN, 26));
		title.setForeground(Color.BLACK);
		title.setOpaque(true);
		title.setBackground(Colors.LIGHT_GRAY);
		title.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Colors.GRAY));

		JLabel footer = new JLabel("Alexander Astély, Alexander Danielson, Marcus Alevärn © 2019-12-23", JLabel.CENTER);
		footer.setFont(new Font("Arial", Font.ITALIC, 13));
		footer.setForeground(Color.BLACK);
		footer.setOpaque(true);
		footer.setBackground(new Color(240, 240, 240));
		footer.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Colors.GRAY));

		contentContainer.setBackground(Color.WHITE);

		c.fill = GridBagConstraints.BOTH;

		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 1;
		c.weighty = 0;
		c.ipady = 20;
		frame.add(title, c);

		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 1;
		c.weighty = 1;
		c.ipady = 0;
		frame.add(contentContainer, c);

		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 1;
		c.weighty = 0;
		c.ipady = 20;
		frame.add(footer, c);
	}

	/**
	 * Visar ett formulär eller tabell på skärmen och gömmer det som visades innan.
	 * 
	 * @param content är formuläret eller tabellen som ska visas.
	 */
	protected void show(Content content) {
		if (current != null)
			current.hide();	// Vi gömmer det som var innan.
		current = content;
		current.validate(); // Vi uppdaterar tabellen eller rensar formuläret.
		current.show(); // Vi visar tabellen eller formuläret för användaren.
	}

	/**
	 * Lägger till ett nytt Content objekt till skärmen. Detta måste göras för att
	 * show() metoden ska fungera korrekt.
	 */
	protected void add(Content content) {
		GridBagConstraints c = new GridBagConstraints();

		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.weighty = 1;
		contentContainer.add(content.getContainer(), c);
	}

	/**
	 * Visar ett dialogruta med ett meddelande och en titel.
	 */
	protected void showMessageDialog(String message, String title, int messageType) {
		JOptionPane.showMessageDialog(frame, message, title, messageType);
	}

	/**
	 * Den här metoden anropas innan fönstret visas.
	 * Här ska man bygga upp tabeller och formulär.
	 */
	protected abstract void setup();

	/**
	 * Den här metoden anropas precis innan applikationen avslutas.
	 * Här ska man stänga ner öppna anslutningar och andra resurser. 
	 */
	protected abstract void cleanup();

}
