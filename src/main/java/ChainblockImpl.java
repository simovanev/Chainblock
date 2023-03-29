import java.util.*;
import java.util.stream.Collectors;

public class ChainblockImpl implements Chainblock{
    private Map<Integer, Transaction> data;

    public ChainblockImpl() {
        this.data = new HashMap<>();
    }

    public int getCount() {
        return data.size();
    }

    public void add(Transaction transaction) {
        data.put(transaction.getId(),transaction);
    }

    public boolean contains(Transaction transaction) {
        return data.containsValue(transaction);
    }

    public boolean contains(int id) {
        return data.containsKey(id);
    }

    public void changeTransactionStatus(int id, TransactionStatus newStatus) {
        if (!data.containsKey(id)){
            throw new IllegalArgumentException();
        }
        data.get(id).setStatus(newStatus);

    }

    public void removeTransactionById(int id) {
        if (!data.containsKey(id)){
            throw new IllegalArgumentException();
        }
        data.remove(id);
    }

    public Transaction getById(int id) {
        if (!data.containsKey(id)){
            throw new IllegalArgumentException();
        }
        return data.get(id);
    }

    public List<Transaction> getByTransactionStatus(TransactionStatus status) {
        List<Transaction> sameStatus = data.entrySet()
                .stream()
                .map(Map.Entry::getValue)
                .filter(s -> s.getStatus().equals(status))
                .sorted(Comparator.comparingDouble(Transaction::getAmount).reversed())
                .collect(Collectors.toList());
        if (sameStatus.size()==0){
            throw new IllegalArgumentException();
        }
        return sameStatus;
    }

    public List<String> getAllSendersWithTransactionStatus(TransactionStatus status) {
        List<String> sameStatus = data.entrySet()
                .stream()
                .map(Map.Entry::getValue)
                .filter(s -> s.getStatus().equals(status))
                .sorted(Comparator.comparingDouble(Transaction::getAmount).reversed())
                .map(e->e.getSender())
                .collect(Collectors.toList());
        if (sameStatus.size()==0){
            throw new IllegalArgumentException();
        }
        return sameStatus;
    }

    public Iterable<String> getAllReceiversWithTransactionStatus(TransactionStatus status) {
        return null;
    }

    public Iterable<Transaction> getAllOrderedByAmountDescendingThenById() {
        return null;
    }

    public Iterable<Transaction> getBySenderOrderedByAmountDescending(String sender) {
        return null;
    }

    public Iterable<Transaction> getByReceiverOrderedByAmountThenById(String receiver) {
        return null;
    }

    public Iterable<Transaction> getByTransactionStatusAndMaximumAmount(TransactionStatus status, double amount) {
        return null;
    }

    public Iterable<Transaction> getBySenderAndMinimumAmountDescending(String sender, double amount) {
        return null;
    }

    public Iterable<Transaction> getByReceiverAndAmountRange(String receiver, double lo, double hi) {
        return null;
    }

    public Iterable<Transaction> getAllInAmountRange(double lo, double hi) {
        return null;
    }

    public Iterator<Transaction> iterator() {
        return null;
    }
}
