import Singleton.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginForm extends JDialog {
    private JTextField tfplaceNumber;
    private JPasswordField tfpassword;
    private JButton btnCancel;
    private JButton btnLogin;
    private JPanel loginPanel;
    public User user;

    public LoginForm(JFrame parent) {
        super(parent);
        setTitle("Login");
        setContentPane(loginPanel);
        setMinimumSize(new Dimension(400, 450));
        setModal(true);
        setLocationRelativeTo(parent);

        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Login ekranını kapat
            }
        });

        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String licensePlate = tfplaceNumber.getText();
                String password = String.valueOf(tfpassword.getPassword());

                user = getAuthenticatedUser(licensePlate, password);

                if (user != null) {
                    JOptionPane.showMessageDialog(LoginForm.this, "Giriş başarılı!", "Başarılı", JOptionPane.INFORMATION_MESSAGE);
                    dispose(); // Login ekranını kapat
                    openPayForm(user); // Kullanıcıyı PayForm'a yönlendir
                } else {
                    JOptionPane.showMessageDialog(LoginForm.this, "Girdiğiniz bilgiler yanlış", "Hata!", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        setVisible(true);
    }

    private User getAuthenticatedUser(String licensePlate, String password) {
        User user = null;
        final String SQL = "SELECT * FROM user WHERE licensePlate = ? AND password = ?";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(SQL)) {

            preparedStatement.setString(1, licensePlate);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                user = new User();
                user.name = resultSet.getString("name");
                user.surname = resultSet.getString("surname");
                user.placeNumber = resultSet.getString("licensePlate");
                user.password = resultSet.getString("password");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }

    private void openPayForm(User user) {
        PaymentManager paymentManager = new PaymentManager();
        SwingUtilities.invokeLater(() -> {
            PayForm payForm = new PayForm(user, paymentManager);
            payForm.setVisible(true);
        });
    }

    public static void main(String[] args) {
        new LoginForm(null); // Login formunu başlat
    }
}
