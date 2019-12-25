
/**
 * Ett interface för att skapa en callback metod.
 */
public interface StringCallback {

	/**
	 * En callback metod som returnerar en sträng. Den här metoden anropas från
	 * {@code Content} och används för att hålla titeln och beskrivningen uppdaterad.
	 */
	String getString();
}
