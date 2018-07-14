package suno;

import org.junit.Test;

import suno.blockchain.core.Block;
import suno.blockchain.core.Transaction;

import static org.junit.Assert.*;

import java.util.ArrayList;

/**
 * Unit test for simple App.
 */
public class AppTest {
    /**
     * Rigorous Test.
     */
    @Test
    public void testApp() {
        // LECTURE #5
        Block block1 = new Block(1, null, 0, new ArrayList<Transaction>());
        block1.mine();
        block1.showInformation();

        Block block2 = new Block(2, block1.getBlockHash(), 0, new ArrayList<Transaction>());
        block2.addTransaction(new Transaction("나동빈", "박한울", 1.5));
        block2.addTransaction(new Transaction("이태일", "박한울", 0.7));
        block2.mine();
        block2.showInformation();

        Block block3 = new Block(3, block2.getBlockHash(), 0, new ArrayList<Transaction>());
        block3.addTransaction(new Transaction("강종구", "이상욱", 8.2));
        block3.addTransaction(new Transaction("박한울", "나동빈", 0.4));
        block3.mine();
        block3.showInformation();

        Block block4 = new Block(4, block3.getBlockHash(), 0, new ArrayList<Transaction>());
        block4.addTransaction(new Transaction("이상욱", "강종구", 0.1));
        block4.mine();
        block4.showInformation();
        
        assertTrue(true);
    }
}
