package com.bostadbäst;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Den här klassen tillhandahåller fönstret och sätter upp designen. Det är en
 * abstrakt klass som döljer funktionalitet för att göra implementationen i
 * {@code App} lättare att läsa.
 */
public abstract class AbstractApp {

	private JFrame frame;
	private JPanel top = new JPanel(new GridBagLayout());
	private JPanel menu = new JPanel(new GridBagLayout());
	private JPanel innerMenu = new JPanel(new GridBagLayout());
	private JPanel main = new JPanel(new GridBagLayout());
	private JPanel footer = new JPanel(new GridBagLayout());

	/**
	 * Den nuvarande presentationen som visas i {@code main} panelen.
	 */
	private Presentation current;

	public AbstractApp(String title, int width, int height) {
		frame = new JFrame(title);
		frame.setSize(width, height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new GridBagLayout());
		initializeComponents();
		setup();
		frame.setVisible(true);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				cleanup();
			}});
	}

	/**
	 * Den här metoden implementeras av {@code App} och anropas i
	 * {@code AbstractApp} konstruktorn. Syftet med metoden är att klassen
	 * {@code App} ska sätta upp knappar och presentationer här.
	 */
	protected abstract void setup();

	/**
	 * Den här metoden implementeras av {@code App} och anropas precis innan
	 * applikationen avslutas. Syftet med metoden är att klassen {@code App}
	 * ska kunna utföra logik precis innan applikationen stängs ner (exempelvis stänga öppna anslutningar).
	 */
	protected abstract void cleanup();

	/**
	 * Lägger till en ny knapp och associerar den med en presentation som startas
	 * när knappen klickas på.
	 * 
	 * @param text         det som ska stå innuti knappen.
	 * @param presentation presentationen som knappen ska associeras med.
	 */
	public void addMenuButton(String text, Presentation presentation) {
		GridBagConstraints c = new GridBagConstraints();
		Button button = new Button(text, Color.WHITE, Colors.BLUE);

		c.fill = GridBagConstraints.HORIZONTAL;
		c.insets = new Insets(5, 5, 5, 5);
		c.gridx = 0;
		c.gridy = innerMenu.getComponentCount();
		c.weightx = 1;
		innerMenu.add(button, c);

		c.anchor = GridBagConstraints.NORTH;
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(0, 0, 0, 0);
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1;
		c.weighty = 1;
		main.add(presentation.getPanel(), c);

		button.addActionListener(e -> {
			if (current != null)
				current.stop(); // Vi stoppar den nuvarande presentationen.
			current = presentation;
			current.start(); // Vi börjar en ny presentation.
		});
	}

	// Den här metoden sätter upp den grafiska användargränssnittet som användaren
	// ska se.
	private void initializeComponents() {
		top.setBackground(Colors.SNOW);
		menu.setBackground(Color.WHITE);
		innerMenu.setBackground(Color.WHITE);
		main.setBackground(Color.WHITE);
		footer.setBackground(Colors.SNOW);

		top.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Colors.GRAY));
		menu.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Colors.GRAY));
		footer.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Colors.GRAY));

		GridBagConstraints c = new GridBagConstraints();

		c.anchor = GridBagConstraints.NORTH;
		c.fill = GridBagConstraints.BOTH;

		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1;
		c.weighty = 0;
		c.gridwidth = 2;
		frame.add(top, c);

		c.gridx = 0;
		c.gridy = 1;
		c.weightx = 0;
		c.weighty = 1;
		c.gridwidth = 1;
		frame.add(menu, c);

		c.gridx = 1;
		c.gridy = 1;
		c.weightx = 1;
		c.weighty = 1;
		c.gridwidth = 1;
		frame.add(main, c);

		c.gridx = 0;
		c.gridy = 2;
		c.weightx = 1;
		c.weighty = 0;
		c.gridwidth = 2;
		frame.add(footer, c);

		c.ipady = 20;
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 0;
		c.weighty = 0;
		c.gridwidth = 1;
		Label titleLbl = new Label(frame.getTitle(), 26);
		titleLbl.setForeground(Colors.DARK_GRAY);
		top.add(titleLbl, c);
		c.ipady = 0;

		c.ipady = 10;
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 0;
		c.weighty = 0;
		c.gridwidth = 1;
		footer.add(new Label("Alexander Astély, Alexander Danielson, Marcus Alevärn © 2019-12-07", 12), c);
		c.ipady = 0;

		c.fill = GridBagConstraints.HORIZONTAL;

		c.insets = new Insets(5, 5, 5, 5);

		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1;
		c.weighty = 1;
		c.gridwidth = 1;
		menu.add(innerMenu, c);
	}
}
