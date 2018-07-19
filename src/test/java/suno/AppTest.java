package suno;

// import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.Test;

import suno.blockchain.core.Block;
import suno.blockchain.core.Transaction;
import suno.blockchain.core.Wallet;
import suno.blockchain.util.EC;

import static org.junit.Assert.*;

import java.math.BigInteger;
import java.util.ArrayList;

import javax.xml.bind.DatatypeConverter;

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
    // Security.addProvider(new BouncyCastleProvider());
    EC ec = new EC();
    ec.generate("private1.pem", "public1.pem");
    ec.generate("private2.pem", "public2.pem");
    ec.generate("private3.pem", "public3.pem");

    assertTrue(true);
  }

  @Test
  public void testLesson10() throws Exception {
    // Security.addProvider(new BouncyCastleProvider());
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
    Transaction tx1 = new Transaction(wallet2, wallet3.getPublicKey(), 1.5, "2018-05-03 23:05:19.5");
    Transaction tx2 = new Transaction(wallet1, wallet2.getPublicKey(), 3.7, "2018-05-04 14:12:09.5");
    Transaction tx3 = new Transaction(wallet3, wallet1.getPublicKey(), 0.5, "2018-05-03 23:15:19.5");
    block2.addTransaction(tx1);
    block2.addTransaction(tx2);
    block2.addTransaction(tx3);
    block2.mine();
    block2.showInformation();

    Block block3 = new Block(3, block2.getBlockHash(), 0, new ArrayList<Transaction>());
    Transaction tx4 = new Transaction(wallet1, wallet3.getPublicKey(), 2.3, "2018-05-06 17:09:21.5");
    Transaction tx5 = new Transaction(wallet3, wallet2.getPublicKey(), 1.4, "2018-05-07 02:11:19.5");
    Transaction tx6 = new Transaction(wallet2, wallet1.getPublicKey(), 10.2, "2018-05-07 02:17:19.5");
    block3.addTransaction(tx4);
    block3.addTransaction(tx5);
    block3.addTransaction(tx6);
    block3.mine();
    block3.showInformation();
  }

  @Test
  public void testLesson11() throws Exception {
    String sign1 = "7C9CDAF8E5E6408213530EBF8A0263350507BD9FEC67690D87C3D5C36844262AC5077E51984BB641C43919471B7B984F5120198F208DFCFD68F5B2761E6A5F6ACB68BB45BADC9916A00CB5C7D75F7B534E50BBB82D878B47686B9F2F63AEA04B44F37474CE6E99EDF4CAD305F3D4CCC2CBAB23348F6B5AF03F0F43CDCAA8F59A";
    String sign2 = "FB210F352D279B46C7027869A24BD9C34CB24C84150049B330694620597689C4E0E6D66DF0DE6625866AAFB001513E83B4708EC8A2FF7378AD2C1454C67EDEBD4D0847C3C2FA50EFA4785E500CF07F933A6E20B02B7BE0128F6C27057AE832F8E358B6D6811D6F73DB9099E920FD3464A1EC86E569B56DF98248AA888EEB4A97";
    byte[] ss = (new BigInteger(sign1, 16)).toByteArray();
    byte[] bb = (DatatypeConverter.parseHexBinary(sign2));
    System.out.println(ss.length + ", " + bb.length);
  }
}
