import javax.swing.*;
import java.sql.*;

public class PayForm extends JDialog {
    private JButton btncrediCard1month;
    private JButton btncreditCard1year;
    private JButton btntransfer1month;
    private JButton btntranfer1year;
    private JButton btnbtc1month;
    private JButton btnbtc1year;
    private JPanel payForm;
    private JButton btnkayıtSil;
    private User user; // Kullanıcı bilgisi
    private PaymentManager paymentManager;

    public PayForm(User user, PaymentManager paymentManager) {
        this.user = user; // Kullanıcı nesnesini al
        this.paymentManager = paymentManager; // PaymentManager nesnesini al

        // PaymentManager'a bir observer ekle
        paymentManager.addObserver(new PaymentLogger());

        // Kredi Kartı 1 Aylık Ödeme Butonu
        btncrediCard1month.addActionListener(e -> handlePayment("Kredi Kartı", "1 Ay"));
        // Kredi Kartı 1 Yıllık Ödeme Butonu
        btncreditCard1year.addActionListener(e -> handlePayment("Kredi Kartı", "1 Yıl"));
        // Banka Transferi 1 Aylık Ödeme Butonu
        btntransfer1month.addActionListener(e -> handlePayment("Banka Transferi", "1 Ay"));
        // Banka Transferi 1 Yıllık Ödeme Butonu
        btntranfer1year.addActionListener(e -> handlePayment("Banka Transferi", "1 Yıl"));
        // Bitcoin 1 Aylık Ödeme Butonu
        btnbtc1month.addActionListener(e -> handlePayment("Bitcoin", "1 Ay"));
        // Bitcoin 1 Yıllık Ödeme Butonu
        btnbtc1year.addActionListener(e -> handlePayment("Bitcoin", "1 Yıl"));

        setContentPane(payForm);
        setTitle("Ödeme Sayfası");
        setSize(700, 500);
        setModal(true);
        setLocationRelativeTo(null);

        // Kayıt Silme Butonu ActionListener
        btnkayıtSil.addActionListener(e -> handleRecordDeletion());
    }

    private void handlePayment(String method, String duration) {
        final String DB_URL = "jdbc:mysql://localhost:3306/otaparkdb?serverTimezone=UTC";
        final String USER = "root";
        final String PASSWORD = "1234";

        String sql = "INSERT INTO payment (licensePlate, paymentMethod, paymentDuration) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // SQL sorgusuna değerleri ekle
            stmt.setString(1, user.placeNumber);
            stmt.setString(2, method);
            stmt.setString(3, duration);

            stmt.executeUpdate(); // Ödeme verisini veritabanına ekle

            // PaymentManager ile durumu güncelle
            paymentManager.setPaymentStatus("Completed: " + method + " (" + duration + ")");

            // Başarı mesajını göster
            JOptionPane.showMessageDialog(this, String.format(
                    "Ödeme Başarılı!\n\nİsim Soyisim: %s %s\nAraç Plakası: %s\nÖdeme Yöntemi: %s\nSüre: %s",
                    user.name, user.surname, user.placeNumber, method, duration
            ));
            dispose(); // Ödeme ekranını kapat

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Ödeme sırasında hata oluştu!", "Hata", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleRecordDeletion() {
        final String DB_URL = "jdbc:mysql://localhost:3306/otaparkdb?serverTimezone=UTC";
        final String USER = "root";
        final String PASSWORD = "1234";

        int confirm = JOptionPane.showConfirmDialog(this, "Üyeliğinizi sonlandırmak istediğinize emin misiniz?", "Kayıt Silme", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {

                // Silinecek ödeme kaydını sil
                String deletePaymentSQL = "DELETE FROM payment WHERE licensePlate = ?";
                try (PreparedStatement stmtPayment = conn.prepareStatement(deletePaymentSQL)) {
                    stmtPayment.setString(1, user.placeNumber);
                    stmtPayment.executeUpdate();
                }

                // Silinecek kullanıcı kaydını sil
                String deleteUserSQL = "DELETE FROM user WHERE licensePlate = ?";
                try (PreparedStatement stmtUser = conn.prepareStatement(deleteUserSQL)) {
                    stmtUser.setString(1, user.placeNumber);
                    stmtUser.executeUpdate();
                }

                // PaymentManager ile durumu güncelle
                paymentManager.setPaymentStatus("Kayıt Silindi");

                JOptionPane.showMessageDialog(this, "Kayıtlar başarıyla silindi.");
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Kayıt silme sırasında hata oluştu!", "Hata", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "İşlem iptal edildi.");
        }
    }

    public JPanel getPayFormPanel() {
        return payForm;
    }
}
