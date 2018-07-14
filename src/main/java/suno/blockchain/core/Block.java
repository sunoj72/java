package suno.blockchain.core;

import java.util.ArrayList;

import suno.blockchain.util.Util;

public class Block {
  private int blockID;
  private String prevBlockHash;
  private int nonce;
  private ArrayList<Transaction> txList;


  public Block(int blockID, String prevBlockHash, int nonce, ArrayList<Transaction> txList) {
    this.blockID = blockID;
    this.prevBlockHash = prevBlockHash;
    this.nonce = nonce;
    this.txList = txList;
  }

  public int getBlockID() {
    return blockID;
  }
  public void setBlockID(int blockID) {
    this.blockID = blockID;
  }
  public int getNonce() {
    return nonce;
  }
  public void setNonce(int nonce) {
    this.nonce = nonce;
  }
  public String getPrevBlockHash() {
	  return prevBlockHash;
  }
  public void setPrevBlockHash(String prevBlockHash) {
	  this.prevBlockHash = prevBlockHash;
  }

  public void addTransaction(Transaction tx) {
    txList.add(tx);
  }

  public String getBlockHash() {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < txList.size(); i++) {
      sb.append(txList.get(i).getInformation());
    }

    return Util.getHash(nonce + sb.toString() + prevBlockHash);
  }

  public void mine() {
    while(true) {
      if(getBlockHash().substring(0, 4).equals("0000")) {
        System.out.println(blockID + "번째 블록의 채굴에 성공했습니다.");
        break;
      }
  
      nonce++;
    }
  }

  public void showInformation() {
    System.out.println("--------------------------------------");
    System.out.println("블록 번호: " + getBlockID());
    System.out.println("이전 해시: " + getPrevBlockHash());
    System.out.println("채굴 변수 값: " + getNonce());
    System.out.println("트랜젝션 개수: " + txList.size() + "개");
    for(int i = 0; i < txList.size(); i++) {
      System.out.println(txList.get(i).getInformation());
    }
    System.out.println("블록 해시: " + getBlockHash());
    System.out.println("--------------------------------------");
  }
}