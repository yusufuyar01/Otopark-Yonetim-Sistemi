public class PaymentFactory {
    public static Payment createPayment(String paymentType) {
        switch (paymentType) {
            case "creditCard1month":
                return new CreditCardPayment(1);
            case "creditCard1year":
                return new CreditCardPayment(12);
            case "transfer1month":
                return new BankTransferPayment(1);
            case "transfer1year":
                return new BankTransferPayment(12);
            case "btc1month":
                return new BitcoinPayment(1);
            case "btc1year":
                return new BitcoinPayment(12);
            default:
                return null;
        }
    }
}
