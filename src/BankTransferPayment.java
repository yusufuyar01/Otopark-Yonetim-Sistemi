public class BankTransferPayment implements Payment {
    private int months;

    public BankTransferPayment(int months) {
        this.months = months;
    }

    @Override
    public void pay() {
        System.out.println("Banka transferi ile " + months + " ay için ödeme yapıldı.");
    }
}
