package app.controller;

import app.LoanResolver;
import app.StudentCalc;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;

public class LoanCalcViewController implements Initializable   {

	private StudentCalc SC = null;
	
	@FXML
	private TextField LoanAmount;
	
	@FXML
	private TextField InterestRate;

	@FXML
	private TextField NbrOfYears;
	
	@FXML
	private DatePicker PaymentStartDate;
	
	@FXML
	private TextField AdditionalPayment;

	@FXML
	private Label lblTotalPayemnts;
	
	@FXML
	private Label lblTotalInterests;
	
	@FXML
	private TableView PaymentScheduleList;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	public void setMainApp(StudentCalc ab) {
		this.SC = ab;
	}
	
	/**
	 * btnCalcLoan - Fire this event when the button clicks
	 * 
	 * @version 1.0
	 * @param event
	 */
	@FXML
	private void btnCalcLoan(ActionEvent event) {
		for(int i=0;i<PaymentScheduleList.getColumns().size();++i){
			TableColumn column_a=(TableColumn)PaymentScheduleList.getColumns().get(i);
			if(column_a.getText().equals("Payment #")){
				column_a.setCellValueFactory(new PropertyValueFactory<LoanResolver.PaymentItem, String>("paymentNumber"));
			}else if(column_a.getText().equals("Due Date")){
				column_a.setCellValueFactory(new PropertyValueFactory<LoanResolver.PaymentItem, String>("date"));
			}else if(column_a.getText().equals("Payment")){
				column_a.setCellValueFactory(new PropertyValueFactory<LoanResolver.PaymentItem, String>("payment"));
			}else if(column_a.getText().equals("Additonal Payment")){
				column_a.setCellValueFactory(new PropertyValueFactory<LoanResolver.PaymentItem, String>("additionalPayment"));
			}else if(column_a.getText().equals("Interest")){
				column_a.setCellValueFactory(new PropertyValueFactory<LoanResolver.PaymentItem, String>("interest"));
			}else if(column_a.getText().equals("Principle")){
				column_a.setCellValueFactory(new PropertyValueFactory<LoanResolver.PaymentItem, String>("principle"));
			}else if(column_a.getText().equals("Balance")){
				column_a.setCellValueFactory(new PropertyValueFactory<LoanResolver.PaymentItem, String>("balance"));
			}
		}

		double doubLoanAmount = Double.parseDouble(LoanAmount.getText());
		double doubInterestRate;
		if(InterestRate.getText().endsWith("%")){
			doubInterestRate=Double.parseDouble(InterestRate.getText().replace("%",""))*0.01;
		}else{
			doubInterestRate=Double.parseDouble(InterestRate.getText());
		}
		double dNumberOfYears = Double.parseDouble(NbrOfYears.getText());
		double dAdditionalPayment = Double.parseDouble(AdditionalPayment.getText());
		LocalDate date=PaymentStartDate.getValue();
		LoanResolver loanResolver = new LoanResolver();
		ObservableList<LoanResolver.PaymentItem> data
				= loanResolver.CalculatePayment(doubLoanAmount,doubInterestRate,dNumberOfYears,dAdditionalPayment,date);
		PaymentScheduleList.setItems(data);
		lblTotalPayemnts.setText(Double.toString(loanResolver.getTotalPayments()));
		lblTotalInterests.setText(Double.toString(loanResolver.getTotalInterests()));
	}
}
