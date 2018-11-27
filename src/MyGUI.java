import Model.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.TableRowSorter;

public class MyGUI extends JFrame implements ActionListener {
    private static final int ISBN_COLUMN_INDEX = 0;
    private static final int PUBLISHER_COLUMN_INDEX = 6;
    private int customerId;
    private JFrame frame;
    private JScrollPane audioBookScrollPane;
    private JTable table;
    private JPopupMenu mainPagePopup;
    private JPopupMenu subPagePopup;
    private JMenuItem menuItemCheckReview;
    private JMenuItem menuItemCheckPublisher;
    private JMenuItem menuItemCheckAuthor;
    private JMenuItem menuItemBack;
    private JButton checkPurchaseRecordButton;
    private String[][] audioBookData;
    private DefaultTableModel tableData;

    MyGUI(int customerId){
        this.customerId = customerId;
        frame =new JFrame();
        checkPurchaseRecordButton = new JButton("Check Purchase Records");
        checkPurchaseRecordButton.addActionListener(this);
        mainPagePopup = new JPopupMenu();
        subPagePopup = new JPopupMenu();
        menuItemCheckReview = new JMenuItem("Check Reviews");
        menuItemCheckPublisher = new JMenuItem("Check Publisher Information");
        menuItemCheckAuthor = new JMenuItem("Check Authors");
        menuItemBack = new JMenuItem("Back To Main Page");

        menuItemCheckReview.addActionListener(this);
        menuItemCheckPublisher.addActionListener(this);
        menuItemCheckAuthor.addActionListener(this);
        menuItemBack.addActionListener(this);

        mainPagePopup.add(menuItemCheckReview);
        mainPagePopup.add(menuItemCheckPublisher);
        mainPagePopup.add(menuItemCheckAuthor);
        subPagePopup.add(menuItemBack);

        ArrayList<Model> audioBooks = MyConnect.getAllAudioBooks();
        audioBookData = convertToTableData(audioBooks);
        tableData = new DefaultTableModel() {
            public Class<String> getColumnClass(int columnIndex) {
                return String.class;
            }
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableData.setDataVector(audioBookData,AudioBook.getFields());
        table =new JTable(tableData);
        table.setDefaultRenderer(String.class, new MultiLineTableCellRenderer());
        TableRowSorter<? extends TableModel> sort = new TableRowSorter<DefaultTableModel>(tableData);
        table.setRowSorter(sort);
        table.setComponentPopupMenu(mainPagePopup);
        table.addMouseListener(new TableMouseListener(table));

        audioBookScrollPane=new JScrollPane(table);
        frame.add(audioBookScrollPane, BorderLayout.CENTER);
        frame.add(checkPurchaseRecordButton, BorderLayout.SOUTH);
        frame.setSize(1000, 400);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.validate();
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if(event.getSource() instanceof JMenuItem){
            //Popup menu action
            JMenuItem menu = (JMenuItem) event.getSource();
            if (menu == menuItemCheckReview) {
                checkReview();
            } else if (menu == menuItemCheckPublisher) {
                checkPublisher();
            }else if (menu == menuItemCheckAuthor){
                checkAuthor();
            } else if (menu == menuItemBack) {
                backToMainPage();
            }
        }else{
            //Button pressed action
            checkPurchaseRecord();
        }
    }

    private void checkReview() {
        int row = table.getSelectedRow();
        String ISBN = (String) table.getModel().getValueAt(row, ISBN_COLUMN_INDEX);
        System.out.println(ISBN);
        ArrayList<Model> reviewArrayList = MyConnect.getReviewsForCertainAudioBook(ISBN);
        String[] reviewFields = Review.getFields();
        if(reviewArrayList.isEmpty()){
            JOptionPane.showMessageDialog(null, "There is no review for the selected audio book.");
        }else{
            changeTableData(convertToTableData(reviewArrayList), Review.getFields());
            table.setComponentPopupMenu(subPagePopup);
        }
    }

    private void checkAuthor() {
        int row = table.getSelectedRow();
        String ISBN = (String) table.getModel().getValueAt(row, ISBN_COLUMN_INDEX);
        System.out.println(ISBN);
        ArrayList<Model> authorArrayList = MyConnect.getAuthorForCertainAudioBook(ISBN);
        if(authorArrayList.isEmpty()){
            JOptionPane.showMessageDialog(null, "There is no author for the selected audio book.");
        }else{
            changeTableData(convertToTableData(authorArrayList), Author.getFields());
            table.setComponentPopupMenu(subPagePopup);
        }
    }

    private void checkPublisher() {
        int row = table.getSelectedRow();
        String publisherName = (String) table.getModel().getValueAt(row, PUBLISHER_COLUMN_INDEX);
        System.out.println(publisherName);
        ArrayList<Model> publisherArrayList = MyConnect.getPublisherForCertainAudioBook(publisherName);
        if(publisherArrayList.isEmpty()){
            JOptionPane.showMessageDialog(null, "There is no publisher info for the selected audio book.");
        }else{
            changeTableData(convertToTableData(publisherArrayList), Publisher.getFields());
            table.setComponentPopupMenu(subPagePopup);
        }
    }

    private void checkPurchaseRecord(){
        ArrayList<Model> purchaseRecordList = MyConnect.getPurchaseRecordForCustomer(customerId);
        if(purchaseRecordList.isEmpty()){
            JOptionPane.showMessageDialog(null, "There is no purchase record.");
        }else{
            changeTableData(convertToTableData(purchaseRecordList), PurchaseRecord.getFields());
            table.setComponentPopupMenu(subPagePopup);
            checkPurchaseRecordButton.setVisible(false);
        }
    }

    private void backToMainPage() {
        changeTableData(audioBookData, AudioBook.getFields());
        table.setComponentPopupMenu(mainPagePopup);
        checkPurchaseRecordButton.setVisible(true);
    }

    private class TableMouseListener extends MouseAdapter {

        private JTable table;

        public TableMouseListener(JTable table) {
            this.table = table;
        }

        @Override
        public void mousePressed(MouseEvent event) {
            //selects the row at which mouse is clicked
            Point point = event.getPoint();
            int currentRow = table.rowAtPoint(point);
            table.setRowSelectionInterval(currentRow, currentRow);
        }
    }

    private class MultiLineTableCellRenderer extends JTextArea implements TableCellRenderer {
        private ArrayList<ArrayList<Integer>> rowColHeight = new ArrayList<ArrayList<Integer>>();

        public MultiLineTableCellRenderer() {
            setLineWrap(true);
            setWrapStyleWord(true);
            setOpaque(true);
        }

        public Component getTableCellRendererComponent(
                JTable table, Object value, boolean isSelected, boolean hasFocus,
                int row, int column) {
            if (isSelected) {
                setForeground(table.getSelectionForeground());
                setBackground(table.getSelectionBackground());
            } else {
                setForeground(table.getForeground());
                setBackground(table.getBackground());
            }
            setFont(table.getFont());
            if (hasFocus) {
                setBorder(UIManager.getBorder("Table.focusCellHighlightBorder"));
                if (table.isCellEditable(row, column)) {
                    setForeground(UIManager.getColor("Table.focusCellForeground"));
                    setBackground(UIManager.getColor("Table.focusCellBackground"));
                }
            } else {
                setBorder(new EmptyBorder(1, 2, 1, 2));
            }
            if (value != null) {
                setText(value.toString());
            } else {
                setText("");
            }
            adjustRowHeight(table, row, column);
            return this;
        }

        private void adjustRowHeight(JTable table, int row, int column) {
            //The trick to get this to work properly is to set the width of the column to the
            //textarea. The reason for this is that getPreferredSize(), without a width tries
            //to place all the text in one line. By setting the size with the with of the column,
            //getPreferredSize() returns the proper height which the row should have in
            //order to make room for the text.
            int cWidth = table.getTableHeader().getColumnModel().getColumn(column).getWidth();
            setSize(new Dimension(cWidth, 1000));
            int prefH = getPreferredSize().height;
            while (rowColHeight.size() <= row) {
                rowColHeight.add(new ArrayList<Integer>(column));
            }
            ArrayList<Integer> colHeights = rowColHeight.get(row);
            while (colHeights.size() <= column) {
                colHeights.add(0);
            }
            colHeights.set(column, prefH);
            int maxH = prefH;
            for (Integer colHeight : colHeights) {
                if (colHeight > maxH) {
                    maxH = colHeight;
                }
            }
            if (table.getRowHeight(row) != maxH) {
                table.setRowHeight(row, maxH);
            }
        }
    }


    private String[][] convertToTableData(ArrayList<Model> models){
        String[][] tableData = models.stream().map(a -> a.toStringArrayFormat()).toArray(String[][]::new);
        return tableData;
    }


    private void changeTableData(String[][] data, String[] fields){
        tableData.setDataVector(data,fields);
        table.setModel(tableData);
        table.setDefaultRenderer(String.class, new MultiLineTableCellRenderer());
        TableRowSorter<? extends TableModel> sort = new TableRowSorter<DefaultTableModel>(tableData);
        table.setRowSorter(sort);
    }

    public static void main(String[] args){
        new MyGUI(7);
    }


}