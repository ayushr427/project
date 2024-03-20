package ProjectGUI;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

import javax.swing.filechooser.FileNameExtensionFilter;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
public class AddProduct extends JFrame {
    private JLabel nameLabel, priceLabel, expiryLabel, barcodeLabel, photoLabel, quantityLabel;
    private JTextField nameField, priceField, expiryField, barcodeField, quantityField;
    private JButton addButton, uploadButton;
    private ImageIcon productImage;
    private DatabaseReference productsRef;
    public AddProduct() {
        setTitle("Add Product");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        nameLabel = new JLabel("Name:");
        nameField = new JTextField(15);

        priceLabel = new JLabel("Price:");
        priceField = new JTextField(15);

        expiryLabel = new JLabel("Expiry Date:");
        expiryField = new JTextField(15);

        barcodeLabel = new JLabel("Barcode:");
        barcodeField = new JTextField(15);

        quantityLabel = new JLabel("Quantity:");
        quantityField = new JTextField(15);

        photoLabel = new JLabel();
        photoLabel.setPreferredSize(new Dimension(150, 150));
        photoLabel.setBorder(BorderFactory.createLineBorder(Color.black));

        uploadButton = new JButton("Upload Photo");
        uploadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Images", "jpg", "jpeg", "png", "gif");
                fileChooser.setFileFilter(filter);
                int returnVal = fileChooser.showOpenDialog(null);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    String filePath = fileChooser.getSelectedFile().getPath();
                    productImage = new ImageIcon(filePath);
                    photoLabel.setIcon(productImage);
                }
            }
        });

        addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                double price = Double.parseDouble(priceField.getText());
                String expiryDate = expiryField.getText();
                String barcode = barcodeField.getText();
                int quantity = Integer.parseInt(quantityField.getText());

                // Here, you would perform the action of adding the product
                // For demonstration purposes, we just print the product details
                System.out.println("Added product: " + name + " - $" + price + ", Expiry: " + expiryDate + ", Barcode: " + barcode + ", Quantity: " + quantity);
                addProductToDatabase();
                // You may want to clear the fields after adding the product
                clearFields();
                photoLabel.setIcon(null);
            }
        });

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(nameLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(priceLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(priceField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(expiryLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(expiryField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(barcodeLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(barcodeField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(quantityLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        panel.add(quantityField, gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridheight = 5;
        panel.add(photoLabel, gbc);

        gbc.gridx = 2;
        gbc.gridy = 5;
        gbc.gridheight = 1;
        panel.add(uploadButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 6;
        panel.add(addButton, gbc);

        add(panel);

        setVisible(true);
    }

    private void clearFields() {
        nameField.setText("");
        priceField.setText("");
        expiryField.setText("");
        barcodeField.setText("");
        quantityField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new AddProduct();
            }
        });
    }
    private void addProductToDatabase() {
        String productName = nameField.getText().toString();
        String productPrice = priceField.getText().toString();
        String productExpiry = expiryField.getText().toString();
        String productBarcode = barcodeField.getText().toString();
        productsRef = FirebaseDatabase.getInstance().getReference().child("7004394490").child("product");
        // Create a new product map
        Map<String, Object> product = new HashMap<>();
        product.put("name", productName);
        product.put("price", productPrice);
        product.put("expiry", productExpiry);
        product.put("barcode", productBarcode);

        // Generate a new key for the product

        // Add the product to the database under the generated key
        productsRef.child(productBarcode).setValueAsync(product);
    }
}
