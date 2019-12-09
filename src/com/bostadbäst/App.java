package com.bostadbäst;

import java.awt.Color;
import java.awt.EventQueue;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Det är i den här klassen som vi löser alla uppgifter och den ENDA klassen som
 * man behöver modifiera för att lösa fler SQL relaterade problem.
 */
public class App extends AbstractApp {

	// Information om fönstrets titel och storlek.
	private static final String TITLE = "BostadBäst";
	private static final int WIDTH = 920;
	private static final int HEIGHT = 760;

	// Information om databas anslutningen.
	private static final String URL = "jdbc:mysql://localhost/bostadbästdb";
	private static final String USERNAME = "test";
	private static final String PASSWORD = "123456789";

	// SQL scripts som vi kör för att lösa alla uppgifter.
	private static final String ALLA_FÖRENINGAR = "SELECT * FROM förening";
	private static final String ALLA_HUS_I_FÖRENING = "SELECT gatuadress, gatunummer, postnummer, postort, våningar, byggår FROM hus WHERE förening = ?";
	private static final String NYTT_HUS = "INSERT INTO hus (förening,gatuadress,gatunummer,postnummer,postort,våningar,byggår) VALUES (?,?,?,?,?,?,?)";
	private static final String REDIGERA_HUS = "UPDATE hus SET gatuadress = ?, gatunummer = ?, byggår = ? WHERE förening = ? AND gatuadress = ? AND gatunummer = ?";
	private static final String ALLA_CYKELRUM_I_HUS = "SELECT namn, yta FROM cykelrum, hus WHERE cykelrum.hus = hus.husID AND hus.förening = ? AND hus.gatuadress = ? AND hus.gatunummer = ?";
	private static final String HUS_ID = "SELECT husID FROM hus WHERE förening = ? AND gatuadress = ? AND gatunummer = ?";
	private static final String NYTT_CYKELRUM = "INSERT INTO cykelrum (namn, yta, hus) VALUES (?,?,?)";
	private static final String TA_BORT_CYKELRUM = "DELETE FROM cykelrum WHERE hus = ? AND (namn IS NULL OR namn = ?)";

	// Objektet som upprättar en anslutning med en databas.
	private Connection conn;

	public App() {
		super(TITLE, WIDTH, HEIGHT);
	}

	@Override
	protected void setup() {
		try {
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// Dessa metoder ger lösningarna för alla uppgifter upp till B-nivå.
		visaAllaFöreningar();
		visaAllaHus();
		läggTillHus();
		redigeraHus();
		visaCykelrum();
		läggTillCykelrum();
		taBortCykelrum();

		// Lägg till egna metoder för A-nivå här.

	}

	/**
	 * Den här metoden lägger till en knapp på menyn och associerar den med en
	 * presentation som löser den första uppgiften d.v.s. att visa alla föreningar.
	 */
	private void visaAllaFöreningar() {
		Presentation presentation = new Presentation("Visa alla föreningar");

		TableSlide tabell = new TableSlide(); // En tabell som visar alla föreningar.
		tabell.setName(() -> "Här nedanför står alla föreningar");
		tabell.setTable((t) -> {
			try (Statement s = conn.createStatement()) {
				ResultSet rs = s.executeQuery(ALLA_FÖRENINGAR);
				t.setTableContent(rs); // Vi sätter tabellens innehåll till ResultSet objektet.
			} catch (SQLException e) {
				e.printStackTrace();
			}
		});
		tabell.addButton("Stäng", Color.WHITE, Colors.RED, e -> presentation.stop()); // Det finns en "Stäng" knapp som
																						// stänger av presentationen.

		presentation.add(tabell);
		super.addMenuButton("Visa föreningar", presentation); // Vi lägger till en meny knapp som associeras med den här
																// presentationen.
	}

	/**
	 * Den här metoden lägger till en knapp på menyn och associerar den med en
	 * presentation som löser den andra uppgiften d.v.s. att visa alla hus.
	 */
	private void visaAllaHus() {
		Presentation presentation = new Presentation("Visa alla hus i en förening");

		// Ett formulär som läser in vilken förening användaren är intresserad av.
		FormSlide formulär = new FormSlide();
		formulär.setName(() -> "Fyll i nedanstående formulär");
		formulär.addField("orgnr", "Föreningens organisationsnummer");
		formulär.addButton("Stäng", Color.WHITE, Colors.RED, e -> presentation.stop());
		formulär.addButton("Visa", Color.WHITE, Colors.GREEN, e -> presentation.next());

		// En tabell som visar alla hus i den föreningen.
		TableSlide tabell = new TableSlide();
		tabell.setName(() -> "Här nedanför står alla hus som ingår i föreningen " + formulär.getFieldValue("orgnr"));
		tabell.setTable((t) -> {
			try (PreparedStatement p = conn.prepareStatement(ALLA_HUS_I_FÖRENING)) {
				p.setString(1, formulär.getFieldValue("orgnr"));

				ResultSet rs = p.executeQuery();
				t.setTableContent(rs);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		});
		// Här finns även möjligheten att gå tillbaka till formuläret (den första
		// sliden).
		tabell.addButton("Tillbaka", Color.WHITE, Colors.BLUE, e -> presentation.previous());
		tabell.addButton("Stäng", Color.WHITE, Colors.RED, e -> presentation.stop());

		presentation.add(formulär);
		presentation.add(tabell);
		super.addMenuButton("Visa hus", presentation);
	}

	/**
	 * Den här metoden lägger till en knapp på menyn och associerar den med en
	 * presentation som löser den tredje uppgiften d.v.s. att lägga till ett hus.
	 */
	private void läggTillHus() {
		Presentation presentation = new Presentation("Lägg till ett hus");

		// Vi lagrar resultatet från databasen i det här objektet d.v.s. om vi lyckades
		// lägga till ett hus eller inte. Resultatet skriver vi också ut till
		// användaren.
		StringBuilder meddelande = new StringBuilder();

		FormSlide formulär = new FormSlide();
		formulär.setName(() -> "Fyll i nedanstående formulär");
		formulär.addField("orgnr", "Föreningens organisationsnummer");
		formulär.addField("gatuadress", "Husets gatuadress");
		formulär.addField("gatunumer", "Husets gatunummer");
		formulär.addField("postnummer", "Husets postnummer");
		formulär.addField("postort", "Husets postort");
		formulär.addField("våningar", "Antalet våningar");
		formulär.addField("byggår", "Husets byggår");
		formulär.addButton("Stäng", Color.WHITE, Colors.RED, e -> presentation.stop());
		formulär.addButton("Lägg till", Color.WHITE, Colors.GREEN, e -> {
			meddelande.setLength(0); // Kräver återställning annars blir bara texten längre och längre.
			try (PreparedStatement p = conn.prepareStatement(NYTT_HUS)) {
				p.setString(1, formulär.getFieldValue("orgnr"));
				p.setString(2, formulär.getFieldValue("gatuadress"));
				p.setString(3, formulär.getFieldValue("gatunumer"));
				p.setString(4, formulär.getFieldValue("postnummer"));
				p.setString(5, formulär.getFieldValue("postort"));
				p.setString(6, formulär.getFieldValue("våningar"));
				p.setInt(7, Integer.parseInt(formulär.getFieldValue("byggår")));

				int resultat = p.executeUpdate();

				// Om 1 rad har skapats så vet vi att huset har lagts till annars så gick något
				// fel.
				if (resultat == 1) {
					meddelande.append("Huset har lagts till!");
				} else {
					meddelande.append("Kunde inte lägga till huset! Kontrollera att allt är korrekt ifyllt.");
				}

			} catch (SQLException | NumberFormatException ex) {
				meddelande.append("Felmeddelande: " + ex.getMessage());
			}

			presentation.next();
		});

		// Här redovisar vi resultatet för användaren.
		TextSlide text = new TextSlide();
		text.setName(() -> meddelande.toString());
		text.addButton("Tillbaka", Color.WHITE, Colors.BLUE, e -> presentation.previous());
		text.addButton("Stäng", Color.WHITE, Colors.RED, e -> presentation.stop());

		presentation.add(formulär);
		presentation.add(text);
		super.addMenuButton("Lägg till hus", presentation);
	}

	/**
	 * Den här metoden lägger till en knapp på menyn och associerar den med en
	 * presentation som löser den fjärde uppgiften d.v.s. att redigera ett hus.
	 */
	private void redigeraHus() {
		Presentation presentation = new Presentation("Redigera ett hus");

		StringBuilder meddelande = new StringBuilder();

		FormSlide formulär = new FormSlide();
		formulär.setName(() -> "Fyll i nedanstående formulär");
		formulär.addField("orgnr", "Föreningens organisationsnummer");
		formulär.addField("gatuadress", "Gammal gatuadress");
		formulär.addField("gatunummer", "Gammalt gatunummer");
		formulär.addField("ny_gatuadress", "Ny gatuadress");
		formulär.addField("nytt_gatunummer", "Nytt gatunummer");
		formulär.addField("nytt_byggår", "Nytt byggår");
		formulär.addButton("Stäng", Color.WHITE, Colors.RED, e -> presentation.stop());
		formulär.addButton("Ändra", Color.WHITE, Colors.GREEN, e -> {
			meddelande.setLength(0);
			try (PreparedStatement p = conn.prepareStatement(REDIGERA_HUS)) {
				p.setString(1, formulär.getFieldValue("ny_gatuadress"));
				p.setString(2, formulär.getFieldValue("nytt_gatunummer"));
				p.setInt(3, Integer.parseInt(formulär.getFieldValue("nytt_byggår")));
				p.setString(4, formulär.getFieldValue("orgnr"));
				p.setString(5, formulär.getFieldValue("gatuadress"));
				p.setString(6, formulär.getFieldValue("gatunummer"));

				int resultat = p.executeUpdate();

				if (resultat == 1) {
					meddelande.append("Huset har ändrats!");
				} else {
					meddelande.append("Kunde inte ändra huset! Kontrollera att allt är korrekt ifyllt.");
				}

			} catch (SQLException | NumberFormatException ex) {
				meddelande.append("Felmeddelande: " + ex.getMessage());
			}

			presentation.next();
		});

		TextSlide text = new TextSlide();
		text.setName(() -> meddelande.toString());
		text.addButton("Tillbaka", Color.WHITE, Colors.BLUE, e -> presentation.previous());
		text.addButton("Stäng", Color.WHITE, Colors.RED, e -> presentation.stop());

		presentation.add(formulär);
		presentation.add(text);
		super.addMenuButton("Redigera hus", presentation);
	}

	/**
	 * Den här metoden lägger till en knapp på menyn och associerar den med en
	 * presentation som löser den femte uppgiften d.v.s. att visa cykelrum.
	 */
	public void visaCykelrum() {
		Presentation presentation = new Presentation("Visa alla cykelrum som finns i ett hus");

		FormSlide formulär = new FormSlide();
		formulär.setName(() -> "Fyll i nedanstående formulär");
		formulär.addField("orgnr", "Föreningens organisationsnummer");
		formulär.addField("gatuadress", "Husets gatuadress");
		formulär.addField("gatunummer", "Husets gatunummer");
		formulär.addButton("Stäng", Color.WHITE, Colors.RED, e -> presentation.stop());
		formulär.addButton("Visa", Color.WHITE, Colors.GREEN, e -> presentation.next());

		TableSlide tabell = new TableSlide();
		// Vi skriver ut information om i vilken förening och i vilket hus som vi visar
		// cykelrummen för.
		tabell.setName(() -> "Här nedanför står alla cykelrum som ingår i föreningen " + formulär.getFieldValue("orgnr")
				+ " och finns i huset på " + formulär.getFieldValue("gatuadress") + " "
				+ formulär.getFieldValue("gatunummer"));

		tabell.setTable((t) -> {
			try (PreparedStatement p = conn.prepareStatement(ALLA_CYKELRUM_I_HUS)) {
				p.setString(1, formulär.getFieldValue("orgnr"));
				p.setString(2, formulär.getFieldValue("gatuadress"));
				p.setString(3, formulär.getFieldValue("gatunummer"));

				ResultSet rs = p.executeQuery();
				t.setTableContent(rs);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		});
		tabell.addButton("Tillbaka", Color.WHITE, Colors.BLUE, e -> presentation.previous());
		tabell.addButton("Stäng", Color.WHITE, Colors.RED, e -> presentation.stop());

		presentation.add(formulär);
		presentation.add(tabell);
		super.addMenuButton("Visa cykelrum", presentation);
	}

	/**
	 * Den här metoden lägger till en knapp på menyn och associerar den med en
	 * presentation som löser den sjätte uppgiften d.v.s. att lägga till cykelrum.
	 */
	public void läggTillCykelrum() {
		Presentation presentation = new Presentation("Lägg till ett nytt cykelrum");

		StringBuilder meddelande = new StringBuilder();

		FormSlide formulär = new FormSlide();
		formulär.setName(() -> "Fyll i nedanstående formulär");
		formulär.addField("orgnr", "Föreningens organisationsnummer");
		formulär.addField("gatuadress", "Husets gatuadress");
		formulär.addField("gatunummer", "Husets gatunummer");
		formulär.addField("namn", "Cykelrummets namn (frivilligt)");
		formulär.addField("yta", "Cykelrummets yta");
		formulär.addButton("Stäng", Color.WHITE, Colors.RED, e -> presentation.stop());
		formulär.addButton("Lägg till", Color.WHITE, Colors.GREEN, e -> {
			meddelande.setLength(0);
			try (PreparedStatement p = conn.prepareStatement(HUS_ID)) {
				p.setString(1, formulär.getFieldValue("orgnr"));
				p.setString(2, formulär.getFieldValue("gatuadress"));
				p.setString(3, formulär.getFieldValue("gatunummer"));

				ResultSet rs = p.executeQuery();
				rs.next();
				int husID = rs.getInt(1);

				PreparedStatement p2 = conn.prepareStatement(NYTT_CYKELRUM);
				p2.setString(1, formulär.getFieldValue("namn").isEmpty() ? null : formulär.getFieldValue("namn"));
				p2.setInt(2, Integer.parseInt(formulär.getFieldValue("yta")));
				p2.setInt(3, husID);
				int resultat = p2.executeUpdate();
				p2.close();

				if (resultat == 1) {
					meddelande.append("Cykelrummet har lagts till!");
				} else {
					meddelande.append("Kunde inte lägga till cykelrummet! Kontrollera att allt är korrekt ifyllt.");
				}

			} catch (SQLException | NumberFormatException ex) {
				meddelande.append("Felmeddelande: " + ex.getMessage());
			}
			presentation.next();
		});

		TextSlide text = new TextSlide();
		text.setName(() -> meddelande.toString());
		text.addButton("Tillbaka", Color.WHITE, Colors.BLUE, e -> presentation.previous());
		text.addButton("Stäng", Color.WHITE, Colors.RED, e -> presentation.stop());

		presentation.add(formulär);
		presentation.add(text);
		super.addMenuButton("Lägg till cykelrum", presentation);
	}

	/**
	 * Den här metoden lägger till en knapp på menyn och associerar den med en
	 * presentation som löser den sjunde uppgiften d.v.s. att ta bort cykelrum.
	 */
	public void taBortCykelrum() {
		Presentation presentation = new Presentation("Ta bort ett cykelrum");

		StringBuilder meddelande = new StringBuilder();

		FormSlide formulär = new FormSlide();
		formulär.setName(() -> "Fyll i nedanstående formulär");
		formulär.addField("orgnr", "Föreningens organisationsnummer");
		formulär.addField("gatuadress", "Husets gatuadress");
		formulär.addField("gatunummer", "Husets gatunummer");
		formulär.addField("namn", "Cykelrummets namn (frivilligt)");
		formulär.addButton("Stäng", Color.WHITE, Colors.RED, e -> presentation.stop());
		formulär.addButton("Ta bort", Color.WHITE, Colors.GREEN, e -> {
			meddelande.setLength(0);
			try (PreparedStatement p = conn.prepareStatement(HUS_ID)) {
				p.setString(1, formulär.getFieldValue("orgnr"));
				p.setString(2, formulär.getFieldValue("gatuadress"));
				p.setString(3, formulär.getFieldValue("gatunummer"));

				ResultSet rs = p.executeQuery();
				rs.next();
				int husID = rs.getInt(1);

				PreparedStatement p2 = conn.prepareStatement(TA_BORT_CYKELRUM);
				p2.setInt(1, husID);
				p2.setString(2, formulär.getFieldValue("namn").isEmpty() ? null : formulär.getFieldValue("namn"));
				int resultat = p2.executeUpdate();
				p2.close();

				if (resultat == 1) {
					meddelande.append("Cykelrummet har tagits bort!");
				} else {
					meddelande.append("Kunde inte ta bort cykelrummet! Kontrollera att allt är korrekt ifyllt.");
				}

			} catch (SQLException | NumberFormatException ex) {
				meddelande.append("Felmeddelande: " + ex.getMessage());
			}
			presentation.next();
		});

		TextSlide text = new TextSlide();
		text.setName(() -> meddelande.toString());
		text.addButton("Tillbaka", Color.WHITE, Colors.BLUE, e -> presentation.previous());
		text.addButton("Stäng", Color.WHITE, Colors.RED, e -> presentation.stop());

		presentation.add(formulär);
		presentation.add(text);
		super.addMenuButton("Ta bort cykelrum", presentation);
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
