public class DigitalBank {
    public static void main(String[] args){
        Bank.setTransferFeeRate(0.25);
        BankAccount customer1  = new BankAccount(11111111111L,"Akin");
        BankAccount customer2 = new BankAccount(22222222222L,"mehmet");
        customer1.deposit(1500);
        customer2.deposit(3000);
        customer2.displayAccountInfo();
        customer1.displayAccountInfo();
        customer2.withdraw(100);
        customer1.withdraw(2000);
        customer1.transferTo(customer2,1000);
        customer1.displayAccountInfo();
        customer1.displayHistory();
        customer2.displayAccountInfo();
    }
}
class BankAccount{
    private String ownerName;
    private long iban;
    private int balance;
    private String[] transactionHistory;

    public BankAccount(long iban, String ownerName) {
        this.balance = 0;
        this.iban = iban;
        this.ownerName = ownerName;
        this.transactionHistory = new String[10];
    }

    public double getBalance() {
        return balance;
    }

    public long getIban() {
        return iban;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public String[] getTransactionHistory() {
        return transactionHistory;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    private void addTransaction(String record){
        for(int i=0 ; i< transactionHistory.length ; i++){
            if( transactionHistory[i] == null){
                transactionHistory[i] = record;
                return;
            }
        }
        for (int i = 0; i < transactionHistory.length - 1; i++) {
            transactionHistory[i] = transactionHistory[i + 1];
    }
        transactionHistory[transactionHistory.length - 1] = record;
    }
    public void deposit(int amount){
        balance += amount;
        String record = "Deposit:" + amount;
        addTransaction(record);
    }
    public void withdraw(int amount){
        if(balance < amount ){
            System.out.println("insufficent balance");
            String record = "Failed Withdraw:" + amount;
            addTransaction(record);
        }else{
            balance -= amount;
            String record = "Withdraw:" + amount;
            addTransaction(record);
        }
    }
    public void transferTo(BankAccount receiver , int amount){
        double fee = Bank.calculateTransferFee(amount);
        if(balance < amount + fee) {
            addTransaction("FAILED_TRANSFER: " + amount + " TO " + receiver.ownerName);
            System.out.println("Transfer failed: insufficient balance.");
            System.out.println();
        }else{
            balance -= (amount+fee);
            receiver.balance += amount;
            addTransaction("Transfer to "+ receiver.ownerName + ":" + amount);
            receiver.addTransaction("TRANSFER_FROM " + this.ownerName + ": " + amount);
            System.out.println("Transfer successful: " + amount + " sent to " + receiver.ownerName);
            System.out.println();
        }
    }
    public void displayAccountInfo() {
        System.out.println("Owner: " + ownerName);
        System.out.println("IBAN: " + iban);
        System.out.println("Balance: " + balance);
        System.out.println();
    }
    public void displayHistory() {
        for (int i = 0; i < transactionHistory.length; i++) {
            if (transactionHistory[i] != null) {
                System.out.println(transactionHistory[i]);
                System.out.println();
            }
        }
    }
}
class Bank {
    private static double transferFeeRate;

    public static void setTransferFeeRate(double rate) {
        transferFeeRate = rate;
    }

    public static double getTransferFeeRate() {
        return transferFeeRate;
    }

    public static double calculateTransferFee(double amount) {
        return amount * transferFeeRate;
    }
}
