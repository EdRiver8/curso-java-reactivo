package sesion2;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

//    public Optional<Double> getTransactionWithHighestAmount2(){
//        return transactions.stream()
//                .map(Trasanction::getAmount)
//                .max(Double::compare);
//    }
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
        BankAccount account = new BankAccount("1", "John Doe", 0, "USD");
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
    }

}
