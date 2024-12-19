public class CreditCardPayment implements Payment {
    private int months;

    public CreditCardPayment(int months) {
        this.months = months;
    }

    @Override
    public void pay() {
        System.out.println("Kredi kartı ile " + months + " ay için ödeme yapıldı.");
    }
}
