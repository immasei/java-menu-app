package MENU;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class OrderHistory extends JPanel {
    private JTable historyTable;
    private DefaultTableModel tableModel;
    private JTextField filter;
    private JLabel label;
    private TableRowSorter<TableModel> sortModel;

    public OrderHistory() {
        this.setLayout(new BorderLayout());

        // Create the table model (not editable)
        tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableModel.addColumn("Order Number");
        tableModel.addColumn("Order Date");
        tableModel.addColumn("Items Ordered");
        tableModel.addColumn("Amount");
        tableModel.addColumn("Total");
        tableModel.addColumn("info");

        // Create the JTable with the table model
        historyTable = new JTable(tableModel);
        // Resize
        historyTable.setRowHeight(35);
        TableColumnModel colModel = historyTable.getColumnModel();
        colModel.getColumn(0).setPreferredWidth(50); //Order Number
        colModel.getColumn(1).setPreferredWidth(120); //Order Date
        colModel.getColumn(2).setPreferredWidth(100); //Items Ordered
        colModel.getColumn(3).setPreferredWidth(50);  //Amount
        colModel.getColumn(4).setPreferredWidth(50);
        colModel.getColumn(5).setPreferredWidth(250);
        // Create a scroll pane for the table
        JScrollPane tableScrollPane = new JScrollPane(historyTable);
        tableScrollPane.setBounds(30, 20, 720, 400);
        tableScrollPane.setWheelScrollingEnabled(true);
        tableScrollPane.setEnabled(false);

        // Search bar
        sortModel= new TableRowSorter<>(tableModel);
        historyTable.setRowSorter(sortModel);

        filter = new JTextField();
        filter.setFont(new Font("Consolas", 0, 29));

        filter.getDocument().addDocumentListener(new DocumentListener(){
            @Override
            public void insertUpdate(DocumentEvent e) {
                String text = filter.getText();
                if (text.trim().length() == 0) {
                    sortModel.setRowFilter(null);
                } else {
                    sortModel.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                String text = filter.getText();
                if (text.trim().length() == 0) {
                    sortModel.setRowFilter(null);
                } else {
                    sortModel.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        });

        JPanel panel = new JPanel(new BorderLayout());
        label = new JLabel(" Search Bar: ");
        label.setFont(new Font("Consolas", 1, 30));
        label.setBackground(new Color(206, 230, 243));
        label.setOpaque(true);

        panel.add(label, BorderLayout.WEST);
        panel.add(filter, BorderLayout.CENTER);

        add(panel, BorderLayout.NORTH);
        add(tableScrollPane);

        // Populate the table from "history.txt"
        populateTableFromHistoryFile("src/main/resources/history.txt");
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }
    private void populateTableFromHistoryFile(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Split the line into columns using "|" as the delimiter
                String[] columns = line.split("\\|");
                if (columns.length >= 5) {
                    tableModel.addRow(columns);
                }
            }
        } catch (IOException e) {
            System.out.println("No file found");
        }
    }

    public void updateTable() {
        // Clear the existing data in the table
        while (tableModel.getRowCount() > 0) {
            tableModel.removeRow(0);
        }

        // Populate the table with the updated data from the "history.txt" file
        populateTableFromHistoryFile("src/main/resources/history.txt");

        // Refresh the table view
        tableModel.fireTableDataChanged();
        AdminDashboard.updateOrder();
    }
}
