import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.sql.*;

public class AdminProductUploader {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Admin Product Uploader");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setLayout(new GridLayout(6, 2));

        JTextField nameField = new JTextField();
        JTextField priceField = new JTextField();
        JLabel imageLabel = new JLabel("No image selected");
        JButton selectImageButton = new JButton("Select Image");
        JButton submitButton = new JButton("Upload Product");

        // JComboBox لخيارات التصنيف
        String[] categories = { "Clothing", "Supplements", "Exercise Equipment" };
        JComboBox<String> categoryComboBox = new JComboBox<>(categories);

        final String[] selectedImagePath = {null};

        selectImageButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            if (fileChooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                selectedImagePath[0] = selectedFile.getAbsolutePath();
                imageLabel.setText(selectedFile.getName());
            }
        });

        submitButton.addActionListener(e -> {
            String name = nameField.getText();
            String priceStr = priceField.getText();
            String description = (String) categoryComboBox.getSelectedItem(); // استخدم التصنيف من القائمة المنسدلة

            if (name.isEmpty() || priceStr.isEmpty() || description.isEmpty() || selectedImagePath[0] == null) {
                JOptionPane.showMessageDialog(frame, "Please fill all fields and select an image.");
                return;
            }

            try {
                double price = Double.parseDouble(priceStr);
                String fileName = new File(selectedImagePath[0]).getName();
                String destinationPath = "src/images/" + fileName;

                // نسخ الصورة إلى مجلد الصور
                Files.copy(
                    Paths.get(selectedImagePath[0]),
                    Paths.get(destinationPath),
                    StandardCopyOption.REPLACE_EXISTING
                );

                // إدخال المنتج في قاعدة البيانات
                try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/gym", "root", "srt8-k");
                     PreparedStatement stmt = conn.prepareStatement("INSERT INTO products (name, image_path, price, category) VALUES (?, ?, ?, ?)")) {
                    stmt.setString(1, name);
                    stmt.setString(2, destinationPath);
                    stmt.setDouble(3, price);
                    stmt.setString(4, description); // التصنيف المختار من القائمة المنسدلة
                    stmt.executeUpdate();
                    JOptionPane.showMessageDialog(frame, "Product uploaded successfully!");
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(frame, "Database error: " + ex.getMessage());
                }

            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(frame, "Price must be a number.");
            } catch (IOException ioEx) {
                JOptionPane.showMessageDialog(frame, "Failed to copy image.");
                ioEx.printStackTrace();
            }
        });

        frame.add(new JLabel("Product Name:"));
        frame.add(nameField);
        frame.add(new JLabel("Price:"));
        frame.add(priceField);
        frame.add(new JLabel("Category:"));
        frame.add(categoryComboBox);  // إضافة قائمة التصنيف المنسدلة
        frame.add(selectImageButton);
        frame.add(imageLabel);
        frame.add(new JLabel());
        frame.add(submitButton);

        frame.setVisible(true);
    }
}
