
package InventoryManagementSystem;
/**
 *
 * 
 */
import javax.swing.*;// GUI components
import javax.swing.table.DefaultTableModel;// Table Data Management
import java.awt.*;// Layout Management
import java.awt.event.ActionEvent;// Button clicks management
import java.awt.event.ActionListener;// Button click events
import java.util.ArrayList;// Product Record storage
import java.util.List;

// Main class extending JFrame for the window
public class InventoryManagementSystem extends JFrame 
{
    // List to store product records
    private List<Product> products = new ArrayList<>();
    // Text fields for input
    private JTextField productIdField, nameField, quantityField, priceField, createdAtField, updatedAtField;
    private JTable table;
    private DefaultTableModel tableModel;

    // Constructor to set up the GUI
    public InventoryManagementSystem()
    {
        setTitle("Inventory Management System");
        setSize(800, 400);
        // Close the application when the window is closed
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Input Panel
        JPanel inputPanel = new JPanel(new GridLayout(6, 2));
        inputPanel.add(new JLabel("Product ID:"));
        productIdField = new JTextField();
        inputPanel.add(productIdField);

        inputPanel.add(new JLabel("Name:"));
        nameField = new JTextField();
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("Quantity:"));
        quantityField = new JTextField();
        inputPanel.add(quantityField);

        inputPanel.add(new JLabel("Price:"));
        priceField = new JTextField();
        inputPanel.add(priceField);

        inputPanel.add(new JLabel("Created At:"));
        createdAtField = new JTextField();
        inputPanel.add(createdAtField);

        inputPanel.add(new JLabel("Updated At:"));
        updatedAtField = new JTextField();
        inputPanel.add(updatedAtField);

        // Buttons
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add");
        JButton updateButton = new JButton("Update");
        JButton deleteButton = new JButton("Delete");

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);

        // Table
        String[] columnNames = {"ID", "Name", "Quantity", "Price", "Created", "Updated"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        // Add components to frame
        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);

        // Button Actions
        // Add action listener to Add button
        addButton.addActionListener(new ActionListener() 
        {
            @Override
            // Define action to perform on button click
            public void actionPerformed(ActionEvent e) 
            {
                // Call method to add a product
                addProduct();
            }
        });

        // Add action listener to Update button
        updateButton.addActionListener(new ActionListener()
        {
            @Override
            // Define action to perform on button click
            public void actionPerformed(ActionEvent e)
            {
                // Call method to update a product
                updateProduct();
            }
        });

        // Add action listener to Delete button
        deleteButton.addActionListener(new ActionListener()
        {
            @Override
            // Define action to perform on button click
            public void actionPerformed(ActionEvent e)
            {
                // Call method to delete a product
                deleteProduct();
            }
        });
    }

    // Method to add a new product
    private void addProduct()
    {
        String productId = productIdField.getText();
        String name = nameField.getText();
        int quantity = Integer.parseInt(quantityField.getText());
        double price = Double.parseDouble(priceField.getText());
        String createdAt = createdAtField.getText();
        String updatedAt = updatedAtField.getText();

        // Check if Product ID is unique
        if (isProductIdUnique(productId))
        {
            Product product = new Product(productId, name, quantity, price, createdAt, updatedAt);
            products.add(product);
            tableModel.addRow(new Object[]{productId, name, quantity, price, createdAt, updatedAt});
            JOptionPane.showMessageDialog(this, "Product added successfully!");
        }
        else
        {
            JOptionPane.showMessageDialog(this, "Product ID must be unique!");
        }
    }

    // Method to update an existing product
    private void updateProduct()
    {
        String productId = productIdField.getText();
        // Iterate through the list of products
        for (int i = 0; i < products.size(); i++)
        {
            Product product = products.get(i);
            if (product.getProductId().equals(productId))
            {
                product.setName(nameField.getText());
                product.setQuantity(Integer.parseInt(quantityField.getText()));
                product.setPrice(Double.parseDouble(priceField.getText()));
                product.setUpdatedAt(updatedAtField.getText());
                tableModel.setValueAt(nameField.getText(), i, 1);
                tableModel.setValueAt(Integer.parseInt(quantityField.getText()), i, 2);
                tableModel.setValueAt(Double.parseDouble(priceField.getText()), i, 3);
                tableModel.setValueAt(updatedAtField.getText(), i, 5);
                JOptionPane.showMessageDialog(this, "Product updated successfully!");
                return;
            }
        }
        // Show error message if product is not found
        JOptionPane.showMessageDialog(this, "Product not found!");
    }

    // Method to delete a product
    private void deleteProduct()
    {
        String productId = productIdField.getText();
        // Iterate through the list of products
        for (int i = 0; i < products.size(); i++)
        {
            Product product = products.get(i);
            // Check if Product ID matches
            if (product.getProductId().equals(productId))
            {
                products.remove(i);
                tableModel.removeRow(i);
                JOptionPane.showMessageDialog(this, "Product deleted successfully!");
                return;
            }
        }
        // Show error message if product is not found
        JOptionPane.showMessageDialog(this, "Product not found!");
    }

    // Method to check if Product ID is unique
    private boolean isProductIdUnique(String productId)
    {
        // Iterate through the list of products
        for (Product product : products)
        {
            // Check if Product ID matches
            if (product.getProductId().equals(productId))
            {
                return false;
            }
        }
        return true;
    }

    // Main method to run the application
    public static void main(String[] args)
    {
        // Use SwingUtilities to run the GUI on the Event Dispatch Thread
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            // Define the code to run
            public void run()
            {
                // Create and display the window
                new InventoryManagementSystem().setVisible(true);
            }
        });
    }
}

// Class to represent a product
class Product
{
    private String productId;
    private String name;
    private int quantity;
    private double price;
    private String createdAt;
    private String updatedAt;

    // Constructor
    public Product(String productId, String name, int quantity, double price, String createdAt, String updatedAt)
    {
        this.productId = productId;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Get Product ID
    public String getProductId()
    {
        return productId;
    }

    // Get Name
    public String getName()
    {
        return name;
    }

    // Set Name
    public void setName(String name)
    {
        this.name = name;
    }

    // Get Quantity
    public int getQuantity()
    {
        return quantity;
    }

    // Set Quantity
    public void setQuantity(int quantity)
    {
        this.quantity = quantity;
    }

    public double getPrice()
    {
        return price;
    }

    public void setPrice(double price)
    {
        this.price = price;
    }

    public String getCreatedAt()
    {
        return createdAt;
    }

    public void setCreatedAt(String createdAt)
    {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt()
    {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt)
    {
        this.updatedAt = updatedAt;
    }
}
