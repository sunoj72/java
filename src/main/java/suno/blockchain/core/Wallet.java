package suno.blockchain.core;

import java.math.BigInteger;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;

import suno.blockchain.util.EC;

public class Wallet {
  private static final String ALGORITHM = "SHA1withECDSA";

  private PrivateKey privateKey;
  private PublicKey publicKey;

  public PrivateKey getPrivateKey() {
    return privateKey;
  }
  public PublicKey getPublicKey() {
    return publicKey;
  }

  public void setFromFile (String privateKeyFile, String publicKeyFile) throws Exception {
    this.privateKey = (new EC()).readPrivateKeyFromFile(privateKeyFile);
    this.publicKey = (new EC()).readPublicKeyFromFile(publicKeyFile);
  }

  public String sign(String data) throws Exception {
    Signature signature;
    signature = Signature.getInstance(ALGORITHM);
    signature.initSign(privateKey);
    byte[] baText = data.getBytes("UTF-8");
    signature.update(baText);
    byte[] baSignature = signature.sign();

    return (new BigInteger(1, baSignature).toString(16).toUpperCase());
  }
}