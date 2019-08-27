package com.lgcns.test.denserank;
import java.util.ArrayList;

public class DenseRank {
		
	/**
	 * Dense Ranking을 구하는 메소드
	 *  
	 * @param input {사번, 점수} Array
	 * @return 정렬된 {사번, 점수, 순위} Array
	 */
	public static int[][] rank(int[][] input) {
		int[][] result = new int[input.length][3];
		

		ArrayList<Integer> list = new ArrayList<>();
		int rank = 1;
		for(int i = 0; i < input.length; i++){
			int maxIdx = 0;
			int max = -1;
			for(int j = 0; j < input.length; j++){
				if(input[j][1] > max && !list.contains(j)){
					max = input[j][1];
					maxIdx = j;
				}
			}
			list.add(maxIdx);
			result[i][0] = input[maxIdx][0];
			result[i][1] = input[maxIdx][1];
			if(i > 0 && result[i-1][1] == input[maxIdx][1]){
				result[i][2] = result[i-1][2];
			}else{
				result[i][2] = rank;
				rank++;
			}
			
		}
		
		return result;
	}
}
