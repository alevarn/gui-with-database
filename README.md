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
I filen **App.java** ser ut så här:

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