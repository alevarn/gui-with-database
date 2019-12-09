# Projekt Datalagring IV1351 (Java delen)
## Kortfattad översikt
- **App.java**
    - Det här är den enda filen som man behöver jobba med för att uppnå A-nivå.
    - Alla uppgifter är lösta i den här filen.
- **AbstractApp.java**
    - En abstrakt basklass till *App.java*.
    - Gömmer funktionalitet som *App.java* inte behöver se.
- **Button.Java, Colors.Java, Label.Java, TextField.java**
    - Filer som endast skapades för att designen skulle bli lite bättre.
    - Dessa filer har ingenting med funktionaliteten att göra, de fyller endast ett estetiskt värde.
- **Table.Java**
    - Den här klassen har en viktig metod som man anropar från *App.java* vilket är ```void setTableContent(ResultSet rs)```.
- **Presentation.Java**
    - Tanken var att bygga en API som skulle göra det lättare att lösa uppgifter i *App.java*.
    - Resultatet blev *Presentation.java*.
    - Man kan se den här klassen som en samling av slides (Med slides menar jag sådana som finns i en PowerPoint presentation).
    - Ett objekt av typen *Presentation* kan innehålla många objekt av typerna *FormSlide*, *TableSlide* och *TextSlide*.
    - Ett objekt av typen *Presentation* kan bläddra fram och tillbaka mellan slides med hjälp av metoderna ```void next()``` och ```void previous()```.
    - För att avsluta en presentation anropas ```void stop()```.
- **FormSlide.Java**
    - Det här är en slide som innehåller ett formulär där användaren kan mata in information i olika textfält.
    - Den här klassen används tillsammans med *Presentation*.
    - För att lägga till ett textfält anropas ```void addField(String id, String description)```.
    - För att få värdet som användaren matat in anropas ```String getFieldValue(String id)```.
- **TableSlide.Java**
    - Det här är en slide som innehåller en tabell och används i samband med ```ResultSet executeQuery(String query)``` för att på ett snyggt sätt synliggöra ett ResultSet objekt.
    - Den här klassen används tillsammans med *Presentation*.
- **TableSlide.Java**
    - Det här är den lättaste sliden som endast innehåller lite text.
    - Den här klassen används tillsammans med *Presentation*.
- **NameUpdater.Java och TableUpdater.java**
    - Det här är två interfaces som innehåller varsin metod som används som callback.
    - *NameUpdater* används för att hålla slide namn uppdaterade.
    - *TableUpdater* används för att hålla tabellen i *TableSlide* uppdaterad.
## Ett exempel
För att visa hur koden fungerar går vi igenom ett exempel. Vi väljer att lösa den första uppgiften där man ska **Visa alla föreningar**.
I filen **App.java** har jag tagit bort mycket kod så det enda vi har är det som står här nedanför. Det är en minimalistisk **App.java**. Det enda vi behöver göra är att lägga till kod i metoden ```void setup()```, men jag har valt att skriva en metod som anropas från ```void setup()``` istället eftersom det gör att metoden ```void setup()``` inte blir för lång. Vi skriver alltså lösningen till uppgiften i metoden ````void visaAllaFöreningar()```.

```java
package com.bostadbäst;

import java.awt.Color;
import java.awt.EventQueue;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class App extends AbstractApp {

	// Information om fönstrets titel och storlek.
	private static final String TITLE = "BostadBäst";
	private static final int WIDTH = 920;
	private static final int HEIGHT = 760;

	// Information om databas anslutningen.
	private static final String URL = "jdbc:mysql://localhost/bostadbästdb";
	private static final String USERNAME = "test";
	private static final String PASSWORD = "123456789";

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

		visaAllaFöreningar();
    }
    
    private void visaAllaFöreningar()
    {
        // Här ska vi lösa uppgiften.
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
```

Det vi börjar med är att skapa en ny presentation och döper den till "Visa alla föreningar".
```java
Presentation presentation = new Presentation("Visa alla föreningar");
```
Eftersom vi endast är intresserade av att visa en tabell för användaren (som innehåller alla föreningar) är den enda typen av *Slide* vi kommer behöva *TableSlide*. Vi skapar därför ett objekt av typen *TableSlide* och ger den namnet "Här nedanför i tabellen står alla föreningar".
```java
Presentation presentation = new Presentation("Visa alla föreningar");

TableSlide table = new TableSlide();
table.setName(() -> "Här nedanför i tabellen står alla föreningar");
```
Notera nu att det är ett lambda uttryck (arrow function) som argument i metoden *setName*. Anledningen till detta är eftersom metoden tar in ett objekt av typen *NameUpdater*. *NameUpdater* är ett interface som har **EN** metod i sig som returnerar en String. Anledningen till att man måste mata in en callback-metod här i form av ett lambda uttryck eller en anonym klass eller liknande är p.g.a. att då kan presentation objektet se till att namnet hela tiden är uppdaterat (vilket bara spelar roll ifall namnet kan förändras, vilket det inte gör i vårt fall). Du kommer snart se att vi även använder lambda uttryck när vi skapar vår tabell också (vi gör detta av exakt samma skäll d.v.s. ifall databasen ändras måste vi se till att Java applikationen också är uppdaterad).

Vi har kommit så långt att vi har ett *Presentation* objekt med en titel och ett *TableSlide* objekt med ett namn. Det vi ska göra nu är att faktiskt be databasen att hämta alla föreningar genom att köra SQL-förfrågan "SELECT * FROM förening". Vi ska sedan lägga in resultatet i vår tabell så användaren kan se alla föreningar.
```java
Presentation presentation = new Presentation("Visa alla föreningar");

TableSlide table = new TableSlide();
table.setName(() -> "Här nedanför i tabellen står alla föreningar");

table.setTable((t) -> {
	try(Statement s = conn.createStatement())
	{
		ResultSet rs = s.executeQuery("SELECT * FROM förening");
		t.setTableContent(rs);	// Här sätter vi att tabellen ska innehålla informationen från rs.
	}catch(SQLException e)
	{
		e.printStackTrace();
	}
});
```
Nu har vi en tabell som innehåller allting som finns i ResultSet objektet. Det skulle vara trevligt att lägga till en knapp som stänger ner hela presentationen (om man känner att man inte längre orkar tittat på tabellen). Detta gör vi med en enda rad kod.
```java
table.addButton("Stäng", Color.WHITE, Colors.RED, e -> presentation.stop());
```
Nu återstår bara två saker kvar att göra. Det första är väldigt enkelt och är att se till så att *Presentation* objektet vet att den har en slide vilket vi gör genom att anropa metoden *add*. 
```java
presentation.add(table);
```
Det är möjligt att lägga på oändligt många slides (men i exemplet använder vi endast en). Vid senare uppgifter där man till exempel ska **Visa alla hus i en förening** och användaren ska själv mata in föreningens organisationsnummer blir det aktuellt att kombinera en *TableSlide* och en *FormSlide* för att kunna få input från användaren.

Det sista steget för att vår lösning ska vara komplett är att meddelande superklassen **AbstractApp** att vi har en presentation som vi gärna vill visa när användaren klickar på en knapp i menyn. Det här gör vi väldigt enkelt genom att anropa metoden *addMenuButton*.
```java
addMenuButton("Visa föreningar", presentation);
```

Nu är vi klara! Här nedanför finns hela koden samt en bild på hur fönstret ska se ut efter man har skrivit den här koden.
```java
package com.bostadbäst;

import java.awt.Color;
import java.awt.EventQueue;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class App extends AbstractApp {

	// Information om fönstrets titel och storlek.
	private static final String TITLE = "BostadBäst";
	private static final int WIDTH = 920;
	private static final int HEIGHT = 760;

	// Information om databas anslutningen.
	private static final String URL = "jdbc:mysql://localhost/bostadbästdb";
	private static final String USERNAME = "test";
	private static final String PASSWORD = "123456789";

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

		visaAllaFöreningar();
    }
    
    private void visaAllaFöreningar()
    {
   		Presentation presentation = new Presentation("Visa alla föreningar");

		TableSlide table = new TableSlide();
		table.setName(() -> "Här nedanför i tabellen står alla föreningar");

		table.setTable((t) -> {
			try(Statement s = conn.createStatement())
			{
				ResultSet rs = s.executeQuery("SELECT * FROM förening");
				t.setTableContent(rs);	// Här sätter vi att tabellen ska innehålla informationen från rs.
			}catch(SQLException e)
			{
				e.printStackTrace();
			}
		});
		table.addButton("Stäng", Color.WHITE, Colors.RED, e -> presentation.stop());
		presentation.add(table);
		addMenuButton("Visa föreningar", presentation);
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
```
