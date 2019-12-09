package com.bostadbäst;

/**
 * Det här interfacet används som en callback när ett objekt av typen
 * {@code Presentation} byter slide och vill validera att allt i den nya sliden
 * är giltigt och uppdaterat. Det här blir relevant om man vill att ett namn ska
 * innehålla text som användaren själv skrivit in i ett formulär. Det finns
 * exempel på detta i {@code App}.
 */
public interface NameUpdater {
	public String getName();
}
