import javax.swing.*;

public class SuccessfulPaymentState implements PaymentState {
    @Override
    public void handlePayment(PayForm payForm) {
        JOptionPane.showMessageDialog(payForm.getPayFormPanel(), "Ödeme Başarılı!", "Başarılı", JOptionPane.INFORMATION_MESSAGE);
    }
}