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

public class Principal {
    public static void main(String[] args) {
        System.out.println("Hello world!");
    }
}
