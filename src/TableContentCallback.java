
/**
 * Ett interface för att skapa en callback metod.
 */
public interface TableContentCallback {

	/**
	 * En callback metod som tar in en instans av {@code Table}. Den här metoden
	 * anropas från {@code Table} och används för att hålla tabellens innehåll
	 * uppdaterad. När man implementerar den här metoden tänk på att man ska anropa
	 * metoden {@code setModel} som finns i {@code Table} för att sätta tabellens
	 * innehåll.
	 */
	void setContent(Table table);
}
