package MENU;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class AdminDashboard extends JPanel implements ActionListener {
    private DefaultTableModel model;
    private JTable table;
    private ArrayList<MenuItem> menuItems;
    private JButton save, add, delete;
    private JTextField name, description, cost;
    private static JLabel totalOrder;
    private HashSet<String> set = new HashSet<>();

    public AdminDashboard() {
        setLayout(null);
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/history.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Split the line into columns using "|" as the delimiter
                String[] columns = line.split("\\|");
                set.add(columns[0]);
            }
        } catch (IOException e) {
            System.out.println("No file found");
        }
        //LABEL
        totalOrder = new JLabel("Total Orders: " + set.size());
        totalOrder.setBounds(30, 480, 300, 50);
        totalOrder.setFont(new Font("Consolas", 1, 15));
        totalOrder.setForeground(Color.red);

        //BUTTON
        save = new JButton("Save");
        save.setBounds(640, 480, 110, 40);
        save.setBackground(new Color(145, 205, 221));

        add = new JButton("Add");
        add.setBounds(640, 430, 110, 40);
        add.setBackground(new Color(239, 213, 149));

        delete = new JButton("Delete");
        delete.setBounds(520, 480, 110, 40);
        delete.setBackground(new Color(239, 180, 149));

        JButton[] button = {save, add, delete};
        for (int i = 0; i < button.length; i++) {
            button[i].setFocusable(false);
            button[i].addActionListener(this);
            button[i].setFont(new Font("Consolas", 1, 17));
            button[i].setOpaque(true);
            button[i].setBorderPainted(false);
        }

        //ADD item
        // tooltip show label
        ToolTipManager.sharedInstance().setInitialDelay(1);
        // text field
        name = new JTextField();
        name.setBounds(30, 430, 150, 40);
        name.setFont(new Font("Consolas", 1, 15));
        name.setToolTipText("Name Here!");

        description = new JTextField();
        description.setBounds(185, 430, 330, 40);
        description.setFont(new Font("Consolas", 1, 15));
        description.setToolTipText("Description Here!");

        cost = new JTextField();
        cost.setBounds(520, 430, 112, 40);
        cost.setFont(new Font("Consolas", 1, 15));
        cost.setToolTipText("Price Here!");

        //TABLE data
        // read food item
        menuItems = new ArrayList<>();
        Reader reader = new Reader("/MENU/menus.txt", 3);
        menuItems = reader.getMenuItems();

        // create new table
        model = new DefaultTableModel();
        table = new JTable(model);
        table.setRowHeight(35);
        // add columns
        model.addColumn("Name");        //0
        model.addColumn("Description"); //1
        model.addColumn("Price");       //2
        // insert food data
        for (int i = 0; i < menuItems.size(); i++) {
            MenuItem item = menuItems.get(i);
            model.insertRow(model.getRowCount(), new Object[] {
                    item.getName(),
                    item.getDescription(),
                    String.format("%.02f", item.getPrice())
            });
        }

        //EDIT item
        // add textfield editor for price only
        JTextField cell = new JTextField();
        TableColumn price = table.getColumnModel().getColumn(2);

        DefaultCellEditor editor = new DefaultCellEditor(cell);
        // start edit on 1st click
        editor.setClickCountToStart(1);
        price.setCellEditor(editor);

        //PRICE EDITOR
        cell.addKeyListener(new KeyAdapter() {
            // invalid price input
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyChar() != KeyEvent.VK_BACK_SPACE) {
                    String input = cell.getText();
                    try {
                        double cost = Double.parseDouble(input);
                        // negative
                        if (cost < 0) {
                            cell.setText("");
                        }
                    // non numeric
                    } catch (NumberFormatException ex) {
                        cell.setText("");
                    }
                }
            }
            // recheck after enter, if not enter, recheck during SAVE
            @Override
            public void keyPressed(KeyEvent e) {
                String input = cell.getText();
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    // sometimes keyPressed not responsive
                    // try = safety net
                    try {
                        double cost = Double.parseDouble(input);
                        cell.setText(String.format("%.02f", cost));
                    } catch (Exception ex) {
                        // mostly "" as handled in keyReleased
                        cell.setText("0.00");
                    }
                }
            }
        });

        //set col width
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        TableColumnModel colModel = table.getColumnModel();
        colModel.getColumn(0).setPreferredWidth(200);
        colModel.getColumn(1).setPreferredWidth(370);
        colModel.getColumn(2).setPreferredWidth(130);

        //add jtable to scroll pane
        JScrollPane tabScroll = new JScrollPane(table);
        tabScroll.setBounds(30, 20, 720, 400);
        tabScroll.setWheelScrollingEnabled(true);
        tabScroll.setEnabled(false);

        //show
        add(tabScroll);
        add(save);
        add(add);
        add(delete);
        add(name);
        add(description);
        add(cost);
        add(totalOrder);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent x) {
        if (x.getSource() == add) { //ADD item
            String val;
            try {
                double d = Double.parseDouble(cost.getText());
                val = String.format("%.02f", d);
            } catch (NumberFormatException ex) {
                val = "0.00";
            }
            model.insertRow(0, new Object[] {
                    name.getText(),
                    description.getText(),
                    val
            });
            // reset input cell
            name.setText("");
            description.setText("");
            cost.setText("");

        } else if (x.getSource() == delete) { //REMOVE item
            int rind = table.getSelectedRow();
            if (rind != -1) { model.removeRow(rind); }

        } else { // SAVE changes
            // stop cell editing and save what it has currently
            if (table.isEditing()) { table.getCellEditor().stopCellEditing(); }

            // recheck data validity (user not enter to save)
            for (int i = 0; i < model.getRowCount(); i++) {
                try {
                    Double fee = Double.parseDouble((String) model.getValueAt(i, 2));
                    model.setValueAt(String.format("%.02f", fee), i, 2);
                } catch (Exception e) {
                    model.setValueAt("0.00", i, 2);
                }
            }

            // extract info
            ArrayList<String> newData = new ArrayList<>();
            String line;

            // not added if name & description are empty
            boolean isEmpty = true;
            for (int i = 0; i < model.getRowCount(); i++) {
                line = model.getValueAt(i, 0).toString();
                if (!line.equals("")) { isEmpty = false; }
                for (int j = 1; j < model.getColumnCount(); j++) {
                    line += ",";
                    line += model.getValueAt(i, j).toString();
                    if (!model.getValueAt(i, j).toString().equals("") && j != model.getColumnCount() - 1) {
                        isEmpty = false;
                    }
                }
                if (!isEmpty) { newData.add(line); }
            }

            // update db
            Writer upd = new Writer("/MENU/menus.txt", newData);
            JFrame window = (JFrame) SwingUtilities.getWindowAncestor(this);
            if (window != null)
                window.dispose();
        }
    }
    public static void updateOrder() {
        String[] curr = totalOrder.getText().split("\\s+");
        int count = Integer.parseInt(curr[curr.length-1]) + 1;
        totalOrder.setText("Total Orders: " + count);
    }

    public JButton[] getButton() {
        return new JButton[]{save, add, delete};
    }
}