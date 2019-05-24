package app;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.beans.property.SimpleStringProperty;
import org.apache.poi.ss.formula.functions.FinanceLib;
import java.time.LocalDate;

public class LoanResolver {
    private final ObservableList<PaymentItem> infor = FXCollections.observableArrayList();
    private double AllPayments;
    private double AllInterests; 

    public double getTotalPayments() {
        AllPayments=0;
        for(int i=1;i<infor.size();++i){
            AllPayments+=Double.parseDouble(infor.get(i).getPayment());
        }
        AllPayments=Rounding2(AllPayments);
        return AllPayments;
    }

    public double getTotalInterests() {
        AllInterests=0;
        for(int i=1;i<infor.size();++i){
            AllInterests+=Double.parseDouble(infor.get(i).getInterest());
        }
        AllInterests=Rounding2(AllInterests);
        return AllInterests;
    }

    public ObservableList<PaymentItem> CalculatePayment(double loanAmount, double interestRate, double numberOfYears, double additionalPayment, LocalDate date){
        double balance=Rounding2(loanAmount-additionalPayment);
        infor.add(new PaymentItem(null,null,null,null,null,null,String.format("%.2f",balance)));
        double PMT = Rounding2(Math.abs(FinanceLib.pmt(interestRate/12.00, numberOfYears*12.00, loanAmount, 0, false)));

        int paymentNumber=0;
        while(balance>0){
            double interest=Rounding2(balance*interestRate/12.00);
            double principal;
            double payment;
            if(Rounding2(PMT-(balance+interest))>=-0.01){
                principal=balance;
                payment=Rounding2(balance+interest);
                balance=0;
            }else{
                principal=Rounding2(PMT-interest);
                payment=PMT;
                balance=Rounding2(balance-principal);
            }
            paymentNumber++;
            infor.add(
                    new PaymentItem(Integer.toString(paymentNumber),date.toString(),String.format("%.2f",payment),null,String.format("%.2f",interest),String.format("%.2f",principal),String.format("%.2f",balance)));
            date=date.plusMonths(1);
        }
        return infor;
    }

    public static double Rounding2(double value) {
        return ((double)Math.round(value*100))/100;
    }

    public static class PaymentItem {
        private final SimpleStringProperty PaymentNumber;
        private final SimpleStringProperty date;
        private final SimpleStringProperty payment;
        private final SimpleStringProperty otherPayment;
        private final SimpleStringProperty interest;
        private final SimpleStringProperty principle; 
        private final SimpleStringProperty balance;

        public PaymentItem(String paymentNumber, String date, String payment, String additionalPayment, String interest, String principle, String balance) {
            this.PaymentNumber = new SimpleStringProperty(paymentNumber);
            this.date = new SimpleStringProperty(date);
            this.payment = new SimpleStringProperty(payment);
            this.otherPayment = new SimpleStringProperty(additionalPayment);
            this.interest = new SimpleStringProperty(interest);
            this.principle = new SimpleStringProperty(principle);
            this.balance = new SimpleStringProperty(balance);
        }

        public String getPaymentNumber() {
            return this.PaymentNumber.get();
        }

        public void setPaymentNumber(String paymentNumber) {
            this.PaymentNumber.set(paymentNumber);
        }

        public String getDate() {
            return this.date.get();
        }

        public void setDate(String date) {
            this.date.set(date);
        }

        public String getPayment() {
            return this.payment.get();
        }

        public void setPayment(String payment) {
            this.payment.set(payment);
        }

        public String getAdditionalPayment() {
            return this.otherPayment.get();
        }

        public void setAdditionalPayment(String additionalPayment) {
            this.otherPayment.set(additionalPayment);
        }

        public String getInterest() {
            return this.interest.get();
        }

        public void setInterest(String interest) {
            this.interest.set(interest);
        }

        public String getPrinciple() {
            return this.principle.get();
        }

        public void setPrinciple(String principle) {
            this.principle.set(principle);
        }

        public String getBalance() {
            return this.balance.get();
        }

        public void setBalance(String balance) {
            this.balance.set(balance);
        }
    }
}
