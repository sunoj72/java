package suno.blockchain.core;

public class BlockChainStarter {
  public static void main(String[] args) {
    // int nonce = 0;

    // while (true) {
    // if (Util.getHash(nonce + "").substring(0, 6).equals("000000")) {
    // System.out.println("Answer: " + nonce);
    // break;
    // }

    // nonce++;
    // }

    Block block = new Block(1, 0, "데이터1");
    block.mine();
    block.getInformation();

    block = new Block(2, 0, "데이터2");
    block.mine();
    block.getInformation();

    block = new Block(3, 0, "데이터3");
    block.mine();
    block.getInformation();
  }
}