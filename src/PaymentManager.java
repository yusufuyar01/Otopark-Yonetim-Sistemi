

public class PaymentManager extends CustomObservable {
    private String paymentStatus;

    public void setPaymentStatus(String status) {
        this.paymentStatus = status;
        notifyObservers(status); // Observer'lara durumu bildir
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }
}
