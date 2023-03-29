import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class ChainblockImplTest {
    private ChainblockImpl chainBlock;
    private Transaction transaction;

    @Before
    public void createChainBlock() {
        this.chainBlock = new ChainblockImpl();
        this.transaction = new TransactionImpl(1, TransactionStatus.SUCCESSFUL, "me", "you", 2.5) {
        };
    }

    @Test
    public void testAddTransaction() {
        chainBlock.add(transaction);

        Assert.assertEquals(1, chainBlock.getCount());
        Assert.assertTrue(chainBlock.contains(transaction));

    }

    @Test
    public void testIfChainBlockContainsNoSuchTransaction() {

        assertFalse(chainBlock.contains(transaction));
    }

    @Test
    public void testIfChainBlockContainsAddedTransaction() {
        chainBlock.add(transaction);
        assertTrue(chainBlock.contains(transaction));
    }

    @Test
    public void testContainById() {
        chainBlock.add(transaction);
        assertTrue(chainBlock.contains(1));
    }

    @Test
    public void testGetCount() {
        for (int i = 0; i < 5; i++) {
            chainBlock.add(new TransactionImpl(1 + i, TransactionStatus.SUCCESSFUL, "me", "you", 2.5));
        }

        Assert.assertEquals(5, chainBlock.getCount());
    }

    @Test
    public void testTransactionChangeStatusForExistingTransaction() {
        chainBlock.add(transaction);
        assertEquals(TransactionStatus.SUCCESSFUL, transaction.getStatus());
        chainBlock.changeTransactionStatus(1, TransactionStatus.UNAUTHORIZED);

        assertEquals(TransactionStatus.UNAUTHORIZED, transaction.getStatus());

    }

    @Test(expected = IllegalArgumentException.class)
    public void testTransactionChangeStatusForMissingTransaction() {
        chainBlock.changeTransactionStatus(1, TransactionStatus.SUCCESSFUL);
    }

    @Test
    public void testRemoveTransactionById() {
        chainBlock.add(transaction);
        chainBlock.removeTransactionById(1);
        assertEquals(0, chainBlock.getCount());

    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveMissingTransactionById() {
        chainBlock.add(transaction);
        chainBlock.removeTransactionById(1);
        chainBlock.removeTransactionById(1);
    }

    @Test
    public void testGetByExistingId() {
        chainBlock.add(transaction);
        assertEquals(transaction, chainBlock.getById(1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetByNotExistingId() {
        assertEquals(transaction, chainBlock.getById(1));
    }

    @Test
    public void testGetByTransactionStatus() {
        for (int i = 0; i < 5; i++) {
            chainBlock.add(new TransactionImpl(1 + i, TransactionStatus.UNAUTHORIZED, "me", "you", 2.5));
        }
        List<Transaction> sameStatusUnAuthorized = chainBlock.getByTransactionStatus(TransactionStatus.UNAUTHORIZED);
        chainBlock.add(transaction);
        List<Transaction> sameStatusSuccessful = chainBlock.getByTransactionStatus(TransactionStatus.SUCCESSFUL);
        Assert.assertEquals(5, sameStatusUnAuthorized.size());
        Assert.assertEquals(1, sameStatusSuccessful.size());


    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetByTransactionStatusWhenNoSuchStatus() {
        chainBlock.add(transaction);
        chainBlock.getByTransactionStatus(TransactionStatus.UNAUTHORIZED);
    }

    @Test
    public void testGetByTransactionStatusDescendingOrder() {
        for (int i = 0; i < 2; i++) {
            chainBlock.add(new TransactionImpl(1 + i, TransactionStatus.UNAUTHORIZED, "me", "you", 2.5 + i));
        }
        List<Transaction> sameStatusUnAuthorized = chainBlock.getByTransactionStatus(TransactionStatus.UNAUTHORIZED);

        Assert.assertTrue(sameStatusUnAuthorized.get(0).getAmount() > sameStatusUnAuthorized.get(1).getAmount());


    }

    @Test
    public void testGetAllSendersWithTransactionStatus(){
        chainBlock.add(transaction);
        chainBlock.add(new TransactionImpl(2,TransactionStatus.SUCCESSFUL,"pesho", "me", 3.2));
        chainBlock.add(new TransactionImpl(3,TransactionStatus.SUCCESSFUL,"gosho", "me", 3.6));
        chainBlock.add(new TransactionImpl(4,TransactionStatus.SUCCESSFUL,"misho", "me", 3.9));
        chainBlock.add(new TransactionImpl(5,TransactionStatus.UNAUTHORIZED,"sasho", "me", 3.9));

        List<String> successfulSenders = chainBlock.getAllSendersWithTransactionStatus(TransactionStatus.SUCCESSFUL);
        assertEquals("misho",successfulSenders.get(0));
        assertEquals("gosho",successfulSenders.get(1));
        assertEquals("pesho",successfulSenders.get(2));

    }
}