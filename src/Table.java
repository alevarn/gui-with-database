import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

/**
 * Det här är en subklass till {@code Content} och representerar en tabell
 * som kan visas för användaren.
 */
public class Table extends Content {

	private TableContentCallback tableContent;

	private JTable table = new JTable() {

		// Vi ser till så att användaren inte kan redigera vår tabell.
		@Override
		public boolean isCellEditable(int row, int column) {
			return false;
		}

	};
	private JScrollPane scrollPane = new JScrollPane(table);

	public Table() {
		buildLayout();
	}

	/**
	 * Sätter innehållet i en tabell att motsvara det innehåll som finns i ett
	 * {@code ResultSet} objekt.
	 */
	public void setModel(ResultSet rs) throws SQLException {
		Vector<String> columns = new Vector<>();
		Vector<Vector<Object>> data = new Vector<>();

		ResultSetMetaData metaData = rs.getMetaData();

		// Vi går igenom alla kolumnnamn.
		for (int i = 1; i <= metaData.getColumnCount(); i++)
			columns.add(metaData.getColumnName(i));

		// Vi går igenom alla rader i tabellen.
		while (rs.next()) {
			Vector<Object> row = new Vector<>();
			for (int i = 1; i <= metaData.getColumnCount(); i++)
				row.add(rs.getObject(i));
			data.add(row);
		}

		table.setModel(new DefaultTableModel(data, columns));
	}

	// Vi bygger den grafiska designen.
	private void buildLayout() {
		GridBagConstraints c = new GridBagConstraints();

		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setFillsViewportHeight(true);
		table.setFont(new Font("Arial", Font.PLAIN, 14));
		table.setBackground(Color.WHITE);
		table.setRowHeight(35);
		table.setGridColor(Colors.GRAY);
		JTableHeader header = table.getTableHeader();
		header.setReorderingAllowed(false);
		header.setFont(new Font("Arial", Font.PLAIN, 14));
		scrollPane.setBorder(BorderFactory.createLineBorder(Colors.GRAY));

		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(0, 25, 0, 25);
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.weightx = 1;
		c.weighty = 1;
		getContainer().add(scrollPane, c);
	}

	/**
	 * Sätter innehållet i tabellen.
	 * 
	 * @param En callback metod som tar in en instans av {@code Table} som
	 *           parameter. Använd metoden {@code setModel(ResultSet rs)} för att
	 *           specificera tabellens innehåll.
	 */
	public void setTableContent(TableContentCallback tableContent) {
		this.tableContent = tableContent;
	}

	/**
	 * Returnerar sant om en rad i tabellen är markerad annars falskt.
	 */
	public boolean isRowSelected() {
		return table.getSelectedRow() != -1;
	}

	/**
	 * Returnerar ett värde från den markerade raden i en specifik kolumn.
	 * 
	 * @param column den kolumnen vi vill ha värdet från.
	 */
	public Object getSelectedValue(int column) {
		return table.getValueAt(table.getSelectedRow(), column);
	}

	/**
	 * Validerar innehållet. Det här uppdaterar titeln och beskrivningen och
	 * innehållet i tabellen.
	 */
	@Override
	public void validate() {
		super.validate();
		tableContent.setContent(this);
	}
}
