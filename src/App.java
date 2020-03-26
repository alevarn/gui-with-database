import java.awt.Color;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

/**
 * Det är i den här klassen som vi löser alla uppgifter och den ENDA klassen som
 * man behöver modifiera för att lösa fler SQL relaterade problem.
 */
public class App extends AbstractApp {

	// Information om fönstrets titel och storlek.
	private static final String TITLE = "BostadBäst";
	private static final int WIDTH = 720;
	private static final int HEIGHT = 520;

	// Information om databas anslutningen.
	private static final String URL = "jdbc:mysql://localhost/bostadbästdb";
	private static final String USERNAME = "example";
	private static final String PASSWORD = "123456789";

	private Connection conn;

	public App() {
		super(TITLE, WIDTH, HEIGHT);
	}

	// Den här metoden anropas innan fönstret visas.
	@Override
	protected void setup() {
		try {
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		} catch (SQLException e) {
			super.showMessageDialog(e.getMessage(), "Kunde inte ansluta till DBMS", JOptionPane.ERROR_MESSAGE);
			System.exit(-1);
		}

		Table föreningar = new Table();
		Table hus = new Table();
		Form skapaHus = new Form();
		Form redigeraHus = new Form();
		Table cykelrum = new Table();
		Form skapaCykelrum = new Form();

		/*
		 * Här nedanför sätter vi titeln och beskrivningen för alla olika tabeller och formulär.
		 * */
		föreningar.setTitle(() -> "Föreningar");
		föreningar.setDescription(() -> "Här nedanför i tabellen står alla föreningar.");

		hus.setTitle(() -> "Hus");
		hus.setDescription(() -> String.format("Här nedanför i tabellen står alla hus som tillhör föreningen %s, %s.",
				föreningar.getSelectedValue(0), föreningar.getSelectedValue(1)));

		skapaHus.setTitle(() -> "Skapa ett nytt hus");
		skapaHus.setDescription(
				() -> String.format("Fyll i nedanstående formulär för att skapa ett nytt hus i föreningen %s, %s.",
						föreningar.getSelectedValue(0), föreningar.getSelectedValue(1)));

		redigeraHus.setTitle(() -> "Redigera ett hus");
		redigeraHus.setDescription(
				() -> String.format("Fyll i nedanstående formulär för att redigera huset som ligger på %s, %s %s.",
						hus.getSelectedValue(2), hus.getSelectedValue(0), hus.getSelectedValue(1)));

		cykelrum.setTitle(() -> "Cykelrum");
		cykelrum.setDescription(() -> String.format(
				"Här nedanför i tabellen står alla cykelrum som finns i huset som ligger på %s, %s %s.",
				hus.getSelectedValue(2), hus.getSelectedValue(0), hus.getSelectedValue(1)));

		skapaCykelrum.setTitle(() -> "Skapa ett nytt cykelrum");
		skapaCykelrum.setDescription(() -> String.format(
				"Fyll i nedanstående formulär för att skapa ett nytt cykelrum i huset som ligger på %s, %s %s.",
				hus.getSelectedValue(2), hus.getSelectedValue(0), hus.getSelectedValue(1)));
	
		/*
		 * Här nedanför beskriver vi hur tabellerna ska se ut och vilka fält varje formulär ska ha.
		 * Vi specificerar också vilka knappar som ska finnas och vad dessa knappar ska göra vid knapptryckning.
		 * */
		föreningar.setTableContent((table) -> {
			try (Statement s = conn.createStatement()) {
				ResultSet rs = s.executeQuery("SELECT * FROM förening");
				table.setModel(rs);	// Vi visar det som finns i ResultSet objektet i tabellen.
			} catch (Exception e) {
				super.showMessageDialog(e.getMessage(), "Felmeddelande", JOptionPane.ERROR_MESSAGE);
			}
		});
		
		föreningar.addButton("Visa hus", Color.WHITE, Colors.BLUE, e -> {
			if (föreningar.isRowSelected())	// Vi ser till att användaren valt en förening innan vi visar hus!
				super.show(hus);
			else
				super.showMessageDialog("Du måste välja en förening först!", "Information",
						JOptionPane.INFORMATION_MESSAGE);
		});

		hus.setTableContent((table) -> {
			try (PreparedStatement p = conn.prepareStatement(
					"SELECT gatuadress, gatunummer, postnummer, postort, våningar, byggår FROM hus WHERE förening = ?")) {
				p.setString(1, (String) föreningar.getSelectedValue(0)); // Vi använder den förening som användaren valt för att hitta hus.
				ResultSet rs = p.executeQuery();
				table.setModel(rs);
			} catch (Exception e) {
				super.showMessageDialog(e.getMessage(), "Felmeddelande", JOptionPane.ERROR_MESSAGE);
			}
		});
		hus.addButton("Tillbaka", Color.WHITE, Colors.RED, e -> super.show(föreningar));
		hus.addButton("Skapa hus", Color.WHITE, Colors.BLUE, e -> super.show(skapaHus));
		hus.addButton("Redigera hus", Color.WHITE, Colors.BLUE, e -> {
			if (hus.isRowSelected())
				super.show(redigeraHus);
			else
				super.showMessageDialog("Du måste välja ett hus först!", "Information",
						JOptionPane.INFORMATION_MESSAGE);
		});
		hus.addButton("Hantera cykelrum", Color.WHITE, Colors.BLUE, e -> {
			if (hus.isRowSelected())
				super.show(cykelrum);
			else
				super.showMessageDialog("Du måste välja ett hus först!", "Information",
						JOptionPane.INFORMATION_MESSAGE);
		});

		skapaHus.addField("gatuadress", "Ange en gatuadress");
		skapaHus.addField("gatunummer", "Ange ett gatunummer");
		skapaHus.addField("postnummer", "Ange ett postnummer");
		skapaHus.addField("postort", "Ange en postort");
		skapaHus.addField("våningar", "Ange antalet våningar i huset");
		skapaHus.addField("byggår", "Ange byggåret");
		skapaHus.addButton("Ångra", Color.WHITE, Colors.RED, e -> super.show(hus));
		skapaHus.addButton("Skapa", Color.WHITE, Colors.BLUE, e -> {
			try (PreparedStatement p = conn.prepareStatement(
					"INSERT INTO hus (gatuadress, gatunummer, postnummer, postort, våningar, byggår, förening) VALUES (?,?,?,?,?,?,?)")) {
				p.setString(1, skapaHus.getFieldValue("gatuadress"));
				p.setString(2, skapaHus.getFieldValue("gatunummer"));
				p.setString(3, skapaHus.getFieldValue("postnummer"));
				p.setString(4, skapaHus.getFieldValue("postort"));
				p.setInt(5, Integer.parseInt(skapaHus.getFieldValue("våningar")));
				p.setString(6, skapaHus.getFieldValue("byggår"));
				p.setString(7, (String) föreningar.getSelectedValue(0));
				
				int resultat = p.executeUpdate();
				
				// Om satsen returnerar 1 så betydet det att ett hus har skapats. 
				if (resultat == 1) {
					super.showMessageDialog("Huset har skapats!", "Information", JOptionPane.INFORMATION_MESSAGE);
					super.show(hus);
				} else
					super.showMessageDialog("Kunde inte skapa huset, kontrollera att allting är korrekt ifyllt!",
							"Felmeddelande", JOptionPane.ERROR_MESSAGE);
			} catch (Exception ex) {
				super.showMessageDialog(ex.getMessage(), "Felmeddelande", JOptionPane.ERROR_MESSAGE);
			}
		});

		redigeraHus.addField("gatuadress", "Ange en ny gatuadress");
		redigeraHus.addField("gatunummer", "Ange ett nytt gatunummer");
		redigeraHus.addField("byggår", "Ange ett nytt byggår");
		redigeraHus.addButton("Ångra", Color.WHITE, Colors.RED, e -> super.show(hus));
		redigeraHus.addButton("Redigera", Color.WHITE, Colors.BLUE, e -> {
			try (PreparedStatement p = conn.prepareStatement(
					"UPDATE hus SET gatuadress = ?, gatunummer = ?, byggår = ? WHERE gatuadress = ? AND gatunummer = ? AND postnummer = ?")) {
				p.setString(1, redigeraHus.getFieldValue("gatuadress"));
				p.setString(2, redigeraHus.getFieldValue("gatunummer"));
				p.setString(3, redigeraHus.getFieldValue("byggår"));
				p.setString(4, (String) hus.getSelectedValue(0));
				p.setString(5, (String) hus.getSelectedValue(1));
				p.setString(6, (String) hus.getSelectedValue(2));

				int resultat = p.executeUpdate();
				
				if (resultat == 1) {
					super.showMessageDialog("Huset har redigerats!", "Information", JOptionPane.INFORMATION_MESSAGE);
					super.show(hus);
				} else
					super.showMessageDialog("Kunde inte redigera huset, kontrollera att allting är korrekt ifyllt!",
							"Felmeddelande", JOptionPane.ERROR_MESSAGE);
			} catch (Exception ex) {
				super.showMessageDialog(ex.getMessage(), "Felmeddelande", JOptionPane.ERROR_MESSAGE);
			}
		});

		cykelrum.setTableContent((table) -> {
			try (PreparedStatement p = conn.prepareStatement(
					"SELECT namn, yta FROM cykelrum WHERE hus IN (SELECT husID FROM hus WHERE gatuadress = ? AND gatunummer = ? AND postnummer = ?)")) {
				p.setString(1, (String) hus.getSelectedValue(0));
				p.setString(2, (String) hus.getSelectedValue(1));
				p.setString(3, (String) hus.getSelectedValue(2));
				ResultSet rs = p.executeQuery();
				table.setModel(rs);
			} catch (Exception e) {
				super.showMessageDialog(e.getMessage(), "Felmeddelande", JOptionPane.ERROR_MESSAGE);
			}
		});
		cykelrum.addButton("Tillbaka", Color.WHITE, Colors.RED, e -> super.show(hus));
		cykelrum.addButton("Skapa cykelrum", Color.WHITE, Colors.BLUE, e -> super.show(skapaCykelrum));
		cykelrum.addButton("Ta bort cykelrum", Color.WHITE, Colors.BLUE, e -> {
			if (cykelrum.isRowSelected()) {
				try (PreparedStatement p = conn.prepareStatement(
						"DELETE FROM cykelrum WHERE (namn IS NULL OR namn = ?) AND hus IN (SELECT husID FROM hus WHERE gatuadress = ? AND gatunummer = ? AND postnummer = ?)")) {
					p.setString(1, (String) cykelrum.getSelectedValue(0));
					p.setString(2, (String) hus.getSelectedValue(0));
					p.setString(3, (String) hus.getSelectedValue(1));
					p.setString(4, (String) hus.getSelectedValue(2));
					
					int resultat = p.executeUpdate();
					
					if (resultat >= 1) {
						super.showMessageDialog("Cykelrummet har tagits bort!", "Information",
								JOptionPane.INFORMATION_MESSAGE);
						cykelrum.validate();
					} else
						super.showMessageDialog("Kunde inte ta bort cykelrummet!", "Felmeddelande",
								JOptionPane.ERROR_MESSAGE);
				} catch (Exception ex) {
					super.showMessageDialog(ex.getMessage(), "Felmeddelande", JOptionPane.ERROR_MESSAGE);
				}
			} else {
				super.showMessageDialog("Du måste välja ett cykelrum att ta bort!", "Information",
						JOptionPane.INFORMATION_MESSAGE);
			}
		});

		skapaCykelrum.addField("namn", "Ange ett namn (frivilligt)");
		skapaCykelrum.addField("yta", "Ange storleken");
		skapaCykelrum.addButton("Ångra", Color.WHITE, Colors.RED, e -> super.show(cykelrum));
		skapaCykelrum.addButton("Skapa", Color.WHITE, Colors.BLUE, e -> {
			try (PreparedStatement p = conn.prepareStatement(
					"INSERT INTO cykelrum (hus, namn, yta) SELECT husID, ?, ? FROM hus WHERE gatuadress = ? AND gatunummer = ? AND postnummer = ?")) {
				p.setString(1, skapaCykelrum.isFieldEmpty("namn") ? null : skapaCykelrum.getFieldValue("namn"));	// Om fältet är tomt sätter vi namn till NULL.
				p.setInt(2, Integer.parseInt(skapaCykelrum.getFieldValue("yta")));
				p.setString(3, (String) hus.getSelectedValue(0));
				p.setString(4, (String) hus.getSelectedValue(1));
				p.setString(5, (String) hus.getSelectedValue(2));

				int resultat = p.executeUpdate();
				if (resultat == 1) {
					super.showMessageDialog("Cykelrummet har skapats!", "Information", JOptionPane.INFORMATION_MESSAGE);
					super.show(cykelrum);
				} else
					super.showMessageDialog("Kunde inte skapa cykelrummet, kontrollera att allting är korrekt ifyllt!",
							"Felmeddelande", JOptionPane.ERROR_MESSAGE);
			} catch (Exception ex) {
				super.showMessageDialog(ex.getMessage(), "Felmeddelande", JOptionPane.ERROR_MESSAGE);
			}
		});

		// Vi börjar med att visa tabellen som innehåller alla föreningar.
		super.show(föreningar);

		// Vi lägger till våra formulär och tabeller.
		super.add(föreningar);
		super.add(hus);
		super.add(skapaHus);
		super.add(redigeraHus);
		super.add(cykelrum);
		super.add(skapaCykelrum);
	}

	// Den här metoden anropas precis innan applikationen avslutas.
	@Override
	protected void cleanup() {
		try {
			conn.close();	// Vi stänger ner DBMS anslutningen.
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Här börjar applikationen.
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				new App();
			}
		});
	}
}
