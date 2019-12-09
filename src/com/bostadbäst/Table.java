package com.bostadbäst;

import java.awt.Color;
import java.awt.Font;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

/**
 * En specialdesignad tabell.
 */
public class Table extends JScrollPane {

	private static final Font FONT = new Font("Arial", Font.PLAIN, 14);

	public void setTableContent(ResultSet rs) {
		try {
			JTable table = new JTable(getModel(rs)) {
				// Vi gör så att inga celler i tabellen kan modifieras.
				@Override
				public boolean isCellEditable(int row, int column) {
					return false;
				}
			};
			table.setFillsViewportHeight(true);
			table.setFont(FONT);
			table.setBackground(Color.WHITE);
			table.setRowHeight(35);
			table.setGridColor(Colors.GRAY);
			JTableHeader header = table.getTableHeader();
			header.setFont(FONT);
			super.setViewportView(table);
			super.setBorder(BorderFactory.createLineBorder(Colors.GRAY));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Den här metoden tar in ett objekt av typen ResultSet och returnerar ett
	// objekt av typen DefaultTableModel. Det här gör det möjligt att visa den
	// SQL-förfrågan vi gjort som en tabell i vår Java applikation.
	private DefaultTableModel getModel(ResultSet rs) throws SQLException {
		Vector<String> columns = new Vector<>();
		Vector<Vector<Object>> data = new Vector<>();

		ResultSetMetaData metaData = rs.getMetaData();

		// Läser in namnet på alla kolumner.
		for (int i = 1; i <= metaData.getColumnCount(); i++)
			columns.add(metaData.getColumnName(i));

		// Läser in samtliga rader.
		while (rs.next()) {
			Vector<Object> row = new Vector<>();
			for (int i = 1; i <= metaData.getColumnCount(); i++)
				row.add(rs.getObject(i));
			data.add(row);
		}

		return new DefaultTableModel(data, columns);
	}
}
