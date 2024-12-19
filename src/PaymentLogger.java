public class PaymentLogger implements CustomObserver {
    @Override
    public void update(String message) {
        System.out.println("Ödeme Güncellemesi: " + message);
    }
}
