import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class triner {

    private JFrame frame;
    private JComboBox<String> memberComboBox;
    private JTextField memberIdField;
    private JTextArea workoutArea, dietArea;
    private Connection conn;

    public triner() {
        connectToDB();
        buildUI();
        loadMembers();
    }

    private void connectToDB() {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/triner", "root", "000");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void buildUI() {
        frame = new JFrame("triner");
        frame.setSize(500, 600);
        frame.setLayout(new GridLayout(12, 1));

        memberComboBox = new JComboBox<>();
        frame.add(new JLabel("Select Member by ID:"));
        frame.add(memberComboBox);

        memberIdField = new JTextField();
        frame.add(new JLabel("Enter Member ID:"));
        frame.add(memberIdField);

        workoutArea = new JTextArea(3, 20);
        dietArea = new JTextArea(3, 20);

        frame.add(new JLabel("Workout Plan:"));
        frame.add(new JScrollPane(workoutArea));

        frame.add(new JLabel("Diet Plan:"));
        frame.add(new JScrollPane(dietArea));

        JButton addPlanButton = new JButton("Add Plan");
        addPlanButton.addActionListener(e -> addPlan());
        frame.add(addPlanButton);

        JButton updatePlanButton = new JButton("Update Plan");
        updatePlanButton.addActionListener(e -> updatePlan());
        frame.add(updatePlanButton);

        JButton deletePlanButton = new JButton("Delete Plan");
        deletePlanButton.addActionListener(e -> deletePlan());
        frame.add(deletePlanButton);

        JButton viewPlanButton = new JButton("View Current Plan");
        viewPlanButton.addActionListener(e -> viewCurrentPlan());
        frame.add(viewPlanButton);

        frame.setVisible(true);
    }

   
    private void loadMembers() {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT id, name FROM members");
            while (rs.next()) {
                memberComboBox.addItem(rs.getString("name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

 
    private void addPlan() {
        String memberId = memberIdField.getText();
        String workoutPlan = workoutArea.getText();
        String dietPlan = dietArea.getText();

        try {
            PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO plans (member_id, workout_plan, diet_plan) VALUES (?, ?, ?)"
            );
            ps.setString(1, memberId);
            ps.setString(2, workoutPlan);
            ps.setString(3, dietPlan);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(frame, "Plan added for Member ID: " + memberId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

 
    private void updatePlan() {
        String memberId = memberIdField.getText();
        String workoutPlan = workoutArea.getText();
        String dietPlan = dietArea.getText();

        try {
            PreparedStatement ps = conn.prepareStatement(
                "UPDATE plans SET workout_plan=?, diet_plan=? WHERE member_id=?"
            );
            ps.setString(1, workoutPlan);
            ps.setString(2, dietPlan);
            ps.setString(3, memberId);
            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(frame, "Plan updated for Member ID: " + memberId);
            } else {
                JOptionPane.showMessageDialog(frame, "No plan found to update for Member ID: " + memberId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

  
    private void deletePlan() {
        String memberId = memberIdField.getText();

        try {
            PreparedStatement ps = conn.prepareStatement(
                "DELETE FROM plans WHERE member_id=?"
            );
            ps.setString(1, memberId);
            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(frame, "Plan deleted for Member ID: " + memberId);
                workoutArea.setText("");
                dietArea.setText("");
            } else {
                JOptionPane.showMessageDialog(frame, "No plan found to delete for Member ID: " + memberId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void viewCurrentPlan() {
        String memberId = memberIdField.getText();

        try {
            PreparedStatement ps = conn.prepareStatement(
                "SELECT workout_plan, diet_plan FROM plans WHERE member_id=?"
            );
            ps.setString(1, memberId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                workoutArea.setText(rs.getString("workout_plan"));
                dietArea.setText(rs.getString("diet_plan"));
            } else {
                JOptionPane.showMessageDialog(frame, "No plan found for Member ID: " + memberId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new triner();
    }
}
