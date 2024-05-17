package sesion2;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

class Transaction {
    private String id;
    private String type;
    private double amount;
    private String currency;
    private String date;
    private String description;

    public Transaction(String id, String type, double amount, String currency, String date, String description) {
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.currency = currency;
        this.date = date;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }
}

class BankAccount {
    private String id;
    private String name;
    private double balance;
    private String currency;
    private List<Transaction> transactions;

    public BankAccount(String id, String name, double balance, String currency) {
        this.id = id;
        this.name = name;
        this.balance = balance;
        this.currency = currency;
        this.transactions = new ArrayList<>();
    }

    public BankAccount() {

    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getBalance() {
        return balance;
    }

    public String getCurrency() {
        return currency;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    // se realiza el metodo de forma tradicional
    // metodo para obtener todas las transacciones de tipo ingreso
    public List<Transaction> getTotalIncome(){
        List<Transaction> totalIncome = new ArrayList<>();
        for (Transaction transaction : transactions) {
            if (transaction.getType().equals("income")) {
                totalIncome.add(transaction);
            }
        }
        return totalIncome;
    }

    // ahora con programacion funcional
    public List<Transaction> getTotalIncomeFunctional(){
        return transactions.stream()
                .filter(transaction -> transaction.getType().equals("income"))
                .collect(Collectors.toList()); // tambien se podria usar el metodo toList() de Collectors (después de filter)
    }

    // metodo que calcula el balance total de la cuenta, puede ser positivo o negativo
    public double getTotalBalance(){
        double totalBalance = 0;
        for (Transaction transaction : transactions) {
            if (transaction.getType().equals("income")) {
                totalBalance += transaction.getAmount();
            } else {
                totalBalance -= transaction.getAmount();
            }

        }
        return totalBalance;
    }

    // ahora con programacion funcional
    public double getTotalBalanceFunctional(){
        return transactions.stream()
            .map(transaction -> transaction.getType().equals("income") ? transaction.getAmount() : -transaction.getAmount())
            .reduce(0.0, Double::sum); // se inicia en 0.0 y se va sumando
    }

    // el opcional se usa para evitar el null pointer exception, ya que no se sabe si habra una transaccion con el monto mas alto
    public Optional<Transaction> getTransactionWithHighestAmount(){
        return transactions.stream()
            .max((t1, t2) -> Double.compare(t1.getAmount(), t2.getAmount())); // se compara el monto de las transacciones y se obtiene la de mayor monto
    }

    // TODO 1: Implementar getTotalBalance utilizando streams y reduce
    public Optional<Double> getTotalBalanceReturnOptional(){
        return transactions.stream()
            .map(transaction -> transaction.getType().equals("income") ? transaction.getAmount() : -transaction.getAmount())
            .reduce(Double::sum);
    }

    // TODO 2: Implementar getDeposits utilizando streams y filter
    public Optional<List<Transaction>> getDeposits(){
        return Optional.of(transactions.stream()
                .filter(transaction -> transaction.getType().equals("income"))
                .toList());
    }

    // TODO 3: Implementar getWithdrawals utilizando streams y filter
    public Optional<List<Transaction>> getWithdrawals(){
        return Optional.of(transactions.stream()
            .filter(transaction -> transaction.getType().equals("expense"))
            .collect(Collectors.toList()));
    }

    // TODO 4: Implementar filterTransactions utilizando Function y streams
    public Optional<List<Transaction>> filterTransactions(Function<Transaction, Boolean> filter){
        return Optional.of(transactions.stream()
            .filter(filter::apply)
            .collect(Collectors.toList()));
    }

    // TODO 5: Implementar getTotalDeposits utilizando getDeposits y mapToDouble
    public Optional<Double> getTotalDeposits(){
        return Optional.of(getDeposits().stream()
            .flatMap(Collection::stream)
            .mapToDouble(t -> t.getAmount())
            .sum());
    }

    // TODO 6: Implementar getLargestWithdrawal utilizando getWithdrawals y max
    public Optional<Transaction> getLargestWithdrawal(){
        return getWithdrawals().flatMap(list -> list.stream().max(Comparator.comparingDouble(Transaction::getAmount)));
    }

    // TODO 7: Implementar getTransactionsOnDate utilizando streams y filter
    public Optional<List<Transaction>> getTransactionsOnDate(String date){
        return Optional.of(transactions.stream()
            .filter(transaction -> transaction.getDate().equals(date))
            .collect(Collectors.toList()));
    }

    // TODO 8: Implementar getAverageTransactionAmount utilizando streams y mapToDouble
    public Optional<Double> getAverageTransactionAmount(){
        return Optional.of(transactions.stream()
            .mapToDouble(Transaction::getAmount)
            .average()
            .orElse(0.0));
    }

    // TODO 9: Implementar getTransactionsWithAmountGreaterThan utilizando streams y filter
    public Optional<List<Transaction>> getTransactionsWithAmountGreaterThan(double amount){
        return Optional.of(transactions.stream()
            .filter(transaction -> transaction.getAmount() > amount)
            .collect(Collectors.toList()));
    }

    // TODO 10: Implementar transfer utilizando addTransaction
    public void transfer(BankAccount account, double amount, String date, String description){
        Transaction transaction = new Transaction("1", "expense", amount, currency, date, description);
        addTransaction(transaction);
        account.addTransaction(transaction);
    }

    // TODO 11: Implementar getTotalWithdrawals utilizando getWithdrawals y mapToDouble
    public Optional<Double> getTotalWithdrawals(){
//        return Optional.of(getWithdrawals().stream()
//            .flatMap(t -> t.stream())
//            .mapToDouble(t -> t.getAmount())
//            .sum());
        return Optional.of(getWithdrawals()
                .map(list -> list.stream().mapToDouble(Transaction::getAmount).sum())
                .orElse(0.0));
    }

    // TODO 12: Implementar getTransactionsSummary utilizando streams, map y collect
    public Optional<List<String>> getTransactionsSummary(){
        return Optional.of(transactions.stream()
            .map(transaction -> transaction.getDescription() + ": " + transaction.getAmount())
            .collect(Collectors.toList()));
    }
}

public class Bank {
    public static void main(String[] args) {
//        System.out.println("Hello world!");
        // antes de los siguientes puntos guardemos datos para poder realizar las consultas
        // se crean las transacciones
        Transaction t1 = new Transaction("1", "income", 1000, "USD", "2021-09-01", "Salary");
        Transaction t2 = new Transaction("2", "income", 500, "USD", "2021-09-02", "Freelance");
        Transaction t3 = new Transaction("3", "expense", 200, "USD", "2021-09-03", "Food");
        Transaction t4 = new Transaction("4", "expense", 100, "USD", "2021-09-04", "Transport");
        Transaction t5 = new Transaction("5", "income", 300, "USD", "2021-09-05", "Freelance");
        Transaction t6 = new Transaction("6", "expense", 50, "USD", "2021-09-06", "Food");
        Transaction t7 = new Transaction("7", "expense", 150, "USD", "2021-09-07", "Transport");
        Transaction t8 = new Transaction("8", "income", 200, "USD", "2021-09-08", "Freelance");
        Transaction t9 = new Transaction("9", "expense", 100, "USD", "2021-09-09", "Food");
        Transaction t10 = new Transaction("10", "expense", 200, "USD", "2021-09-10", "Transport");

        // se crea la cuenta bancaria
        BankAccount account = new BankAccount("1", "Goku Emilio", 0, "USD");
        // se agregan las transacciones a la cuenta, teniendo en cuenta que las trasacciones son una lista en BankAccount
        account.addTransaction(t1);
        account.addTransaction(t2);
        account.addTransaction(t3);
        account.addTransaction(t4);
        account.addTransaction(t5);
        account.addTransaction(t6);
        account.addTransaction(t7);
        account.addTransaction(t8);
        account.addTransaction(t9);
        account.addTransaction(t10);

        // 1. Obtener el total de depositos realizados
        List<Transaction> totalIncome = account.getTotalIncome();

        // 2. Encontrar la trasaccion de retiro con el monto mas alto
        Optional<Transaction> transactionWithHighestAmount = account.getTransactionWithHighestAmount();

        // 3. Contar el numero de transacciones realizadas a una fecha especifica usando programacion funcional
        long transactionsOnDate = account.getTransactions().stream()
                .filter(transaction -> transaction.getDate().equals("2021-09-05"))
                .count();

        // 4. Obtener el promedio de los montos de todas las transacciones realizadas
        double averageAmount = account.getTransactions().stream()
                .mapToDouble(Transaction::getAmount)
                .average()
                .orElse(0.0); // si no hay transacciones, se retorna 0.0

        // 5. Filtrar transacciones con montos negativos
        List<Transaction> negativeTransactions = account.getTransactions().stream()
                .filter(transaction -> transaction.getAmount() < 0)
                .toList();

        BankAccount account2 = new BankAccount("2", "Jane Doe", 0, "EUR");
        account2.addTransaction(new Transaction("1", "deposit", 100, "EUR", "2024-05-13", "Deposit 1"));
        account2.addTransaction(new Transaction("2", "withdrawal", 50, "EUR", "2024-05-14", "Withdrawal 1"));
        account2.addTransaction(new Transaction("3", "deposit", 200, "EUR", "2024-05-15", "Deposit 2"));
        account2.addTransaction(new Transaction("4", "deposit", 150, "EUR", "2024-05-16", "Deposit 3"));
        account2.addTransaction(new Transaction("5", "withdrawal", 75, "USD", "2024-05-17", "Withdrawal 2"));

        account.getTotalBalanceReturnOptional().ifPresent(balance -> System.out.println("Saldo total: " + balance));
        account.getTotalDeposits().ifPresent(total -> System.out.println("Total de depósitos: " + total));
        account.getLargestWithdrawal().ifPresent(transaction -> System.out.println("Retiro de mayor monto: " + transaction.getAmount()));
        account.getTransactionsOnDate("2024-05-13").ifPresent(transactions -> transactions.forEach(transaction -> System.out.println(transaction.getAmount())));
        account.getAverageTransactionAmount().ifPresent(average -> System.out.println("Promedio de montos: " + average));
        account.getTransactionsWithAmountGreaterThan(100).ifPresent(transactions -> transactions.forEach(transaction -> System.out.println(transaction.getAmount())));
//        BankAccount targetAccount = new BankAccount();
        account2.transfer(account, 100, "2024-05-18", "Transferencia");
        account2.getTotalBalanceReturnOptional().ifPresent(balance -> System.out.println("Saldo total de la cuenta destino: " + balance));
        account.getTotalWithdrawals().ifPresent(total -> System.out.println("Total de retiros: " + total));
//        account.getTransactionsSummary().forEach((type, sum) -> System.out.println("Tipo: " + type + ", Total: " + sum));
//        account.getTransactionsSummary().ifPresent(transactions -> transactions.forEach((transaction, type) -> System.out.println("Tipo: " + type + ", Total: " + transaction)));
    }

}
