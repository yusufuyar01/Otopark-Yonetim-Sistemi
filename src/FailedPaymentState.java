import javax.swing.*;

public class FailedPaymentState implements PaymentState {
    @Override
    public void handlePayment(PayForm payForm) {
        JOptionPane.showMessageDialog(payForm.getPayFormPanel(), "Ödeme Başarısız!", "Hata", JOptionPane.ERROR_MESSAGE);
    }
}