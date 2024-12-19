public class BitcoinPayment implements Payment {
    private int months;

    public BitcoinPayment(int months) {
        this.months = months;
    }

    @Override
    public void pay() {
        System.out.println("Bitcoin ile " + months + " ay için ödeme yapıldı.");
    }
}
