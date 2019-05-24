package pkgUT;

import app.LoanResolver;
import javafx.collections.ObservableList;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

public class TestCalculate {
    @Test
    public void testCalculate(){
        double loanAmount=1000;
        double interestRate=0.5;
        int termOfYears=1;
        LocalDate firstPaymentDate= LocalDate.of(2019,1,1);
        double additionalPayment=0.00;

        double expectedPaymentPerMonth[]={
                107.59, 107.59, 107.59, 107.59, 107.59, 107.59, 107.59, 107.59, 107.59, 107.59, 107.59, 107.52
        };
        double expectedInterestPerMonth[]={
                41.67, 38.92, 36.06, 33.08, 29.97, 26.74, 23.37, 19.86, 16.21, 12.40,8.43, 4.30
        };
       double expectedPrincipalPerMonth[]={
               65.92, 68.67, 71.53, 74.51, 77.62, 80.85, 84.22, 87.73, 91.38, 95.19, 99.16, 103.22
       };
       double expectedBalancePerMonth[]={
               934.08, 865.41, 793.88, 719.37, 641.75, 560.90, 476.68, 388.95, 297.57, 202.38, 103.22, 0.00
       };

       double expectedTotalPayment=1291.01;
       double expectedTotalInterest=291.01;
        LoanResolver loanResolver = new LoanResolver();
        ObservableList<LoanResolver.PaymentItem> data
                = loanResolver.CalculatePayment(loanAmount,interestRate,termOfYears,additionalPayment,firstPaymentDate);

        for(int i=1;i<data.size();++i){
            assertEquals(Double.parseDouble(data.get(i).getPayment()),expectedPaymentPerMonth[i-1],0.01);
            assertEquals(Double.parseDouble(data.get(i).getInterest()),expectedInterestPerMonth[i-1],0.01);
            assertEquals(Double.parseDouble(data.get(i).getPrinciple()),expectedPrincipalPerMonth[i-1],0.01);
            assertEquals(Double.parseDouble(data.get(i).getBalance()),expectedBalancePerMonth[i-1],0.01);
        }
        assertEquals(expectedTotalInterest,loanResolver.getTotalInterests(),0.01);
        assertEquals(expectedTotalPayment,loanResolver.getTotalPayments(),0.01);
    }
}
