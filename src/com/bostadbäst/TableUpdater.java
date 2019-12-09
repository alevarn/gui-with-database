package com.bostadbäst;

/**
 * Det här interfacet används som en callback när ett objekt av typen
 * {@code Presentation} byter slide och vill validera att allt i den nya sliden
 * är giltigt och uppdaterat. Det här interfacet används specifikt för att
 * uppdatera tabeller. Det här betyder att varje gång en användaren öppnar upp
 * en slide så kommer programmet utföra den kod som krävs för att tabellen ska
 * innehålla korrekt information. Det kan till exempel vara att hämta
 * information från en databas.
 */
public interface TableUpdater {
	public void updateTable(Table table);
}
