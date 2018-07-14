package suno;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.Ignore;
import org.junit.Test;

import suno.blockchain.core.Block;
import suno.blockchain.core.Transaction;
import suno.blockchain.core.Wallet;
import suno.blockchain.util.EC;

import static org.junit.Assert.*;

import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.Signature;
import java.security.spec.ECGenParameterSpec;
import java.util.ArrayList;

/**
 * Unit test for simple App.
 */
public class AppTest {

  @Test
  public void testLesson03() {
    // Block block1 = new Block(1, null, 0, new ArrayList<Transaction>());
    // block1.mine();
    // block1.showInformation();

    // Block block2 = new Block(2, block1.getBlockHash(), 0, new ArrayList<Transaction>());
    // block2.mine();
    // block2.showInformation();

    // Block block3 = new Block(3, block2.getBlockHash(), 0, new ArrayList<Transaction>());
    // block3.mine();
    // block3.showInformation();

    // Block block4 = new Block(4, block3.getBlockHash(), 0, new ArrayList<Transaction>());
    // block4.mine();
    // block4.showInformation();

    assertTrue(true);
  }

  @Test
  public void testLesson04() {
    // Transaction transaction = new Transaction("나동빈", "박한울", 1.5);
    // System.out.println(transaction.getInformation());

    assertTrue(true);
  }

  @Test
  public void testLesson05() {
    // Block block1 = new Block(1, null, 0, new ArrayList<Transaction>());
    // block1.mine();
    // block1.showInformation();

    // Block block2 = new Block(2, block1.getBlockHash(), 0, new ArrayList<Transaction>());
    // block2.addTransaction(new Transaction("나동빈", "박한울", 1.5));
    // block2.addTransaction(new Transaction("이태일", "박한울", 0.7));
    // block2.mine();
    // block2.showInformation();

    // Block block3 = new Block(3, block2.getBlockHash(), 0, new ArrayList<Transaction>());
    // block3.addTransaction(new Transaction("강종구", "이상욱", 8.2));
    // block3.addTransaction(new Transaction("박한울", "나동빈", 0.4));
    // block3.mine();
    // block3.showInformation();

    // Block block4 = new Block(4, block3.getBlockHash(), 0, new ArrayList<Transaction>());
    // block4.addTransaction(new Transaction("이상욱", "강종구", 0.1));
    // block4.mine();
    // block4.showInformation();

    assertTrue(true);
  }

  @Test
  public void testLesson06() throws Exception {
    // KeyPairGenerator kpg = KeyPairGenerator.getInstance("EC", "SunEC");
    // ECGenParameterSpec ecsp = new ECGenParameterSpec("sect163k1");
    // kpg.initialize(ecsp, new SecureRandom());

    // KeyPair kp = kpg.genKeyPair();
    // PrivateKey privateKey = kp.getPrivate();
    // PublicKey publicKey = kp.getPublic();

    // Signature ecdsa = Signature.getInstance("SHA1withECDSA", "SunEC");
    // ecdsa.initSign(privateKey);

    // String text = "동빈이가 한율이에게 100 코인 전송";
    // System.out.println(text);
    // String textInfected = "동빈이가 한율이에게 1000 코인 전송";

    // ecdsa.update(text.getBytes("UTF-8"));
    // byte[] signatureBytes = ecdsa.sign();
    // System.out.println("암호문: 0x" + (new BigInteger(1, signatureBytes).toString(16)).toUpperCase());

    // Signature signature = Signature.getInstance("SHA1withECDSA", "SunEC");
    // signature.initVerify(publicKey);

    // signature.update(text.getBytes("UTF-8"));
    // System.out.println("원래 문장 검증: " + signature.verify(signatureBytes));

    // signature.update(textInfected.getBytes("UTF-8"));
    // System.out.println("변경된 문장 검증: " + signature.verify(signatureBytes));

    assertTrue(true);
  }

  @Test
  public void testLesson07() throws Exception {
    // Security.addProvider(new BouncyCastleProvider());
    // EC ec = new EC();
    // ec.generate("private.pem", "public.pem");

    assertTrue(true);
  }

  @Test
  public void testLesson08() throws Exception {
    // Security.addProvider(new BouncyCastleProvider());
    // EC ec = new EC();
    // ec.generate("private.pem", "public.pem");

    // PrivateKey privateKey = ec.readPrivateKeyFromFile("private.pem");
    // PublicKey publicKey = ec.readPublicKeyFromFile("public.pem");

    assertTrue(true);
  }

  @Test
  public void testLesson09() throws Exception {
    Security.addProvider(new BouncyCastleProvider());
    EC ec = new EC();
    ec.generate("private1.pem", "public1.pem");
    ec.generate("private2.pem", "public2.pem");
    ec.generate("private3.pem", "public3.pem");

    assertTrue(true);
  }

  @Test
  public void testLesson10() throws Exception {
    Security.addProvider(new BouncyCastleProvider());
    Wallet wallet1 = new Wallet();
    wallet1.setFromFile("private1.pem", "public1.pem");
    Wallet wallet2 = new Wallet();
    wallet2.setFromFile("private2.pem", "public2.pem");
    Wallet wallet3 = new Wallet();
    wallet3.setFromFile("private3.pem", "public3.pem");

    Block block1 = new Block(1, null, 0, new ArrayList<Transaction>());
    block1.mine();
    block1.showInformation();

    Block block2 = new Block(2, block1.getBlockHash(), 0, new ArrayList<Transaction>());
    Transaction tx1 = new Transaction(wallet1, wallet2.getPublicKey(), 1.5, "2018-05-03 23:05:19.5");
    Transaction tx2 = new Transaction(wallet2, wallet3.getPublicKey(), 3.7, "2018-05-04 14:12:09.5");
    block2.addTransaction(tx1);
    block2.addTransaction(tx2);
    block2.mine();
    block2.showInformation();

    Block block3 = new Block(3, block2.getBlockHash(), 0, new ArrayList<Transaction>());
    Transaction tx3 = new Transaction(wallet1, wallet3.getPublicKey(), 2.3, "2018-05-06 17:09:21.5");
    Transaction tx4 = new Transaction(wallet2, wallet3.getPublicKey(), 1.4, "2018-05-07 02:11:19.5");
    block3.addTransaction(tx3);
    block3.addTransaction(tx4);
    block3.mine();
    block3.showInformation();
  }
}
