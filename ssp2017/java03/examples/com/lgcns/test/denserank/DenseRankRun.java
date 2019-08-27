package com.lgcns.test.denserank;

import java.util.Arrays;

public class DenseRankRun {
	public static void main(String[] args) {
		printResult(DenseRank.rank(SampleData.getInput1()));
//		printResult(DenseRank.rank(SampleData.getInput2()));
	}
	
	public static void printResult(int[][] result) {
		System.out.println("=====================================================");
		for (int[] tmp : result) {
			System.out.println(Arrays.toString(tmp));
		}
		System.out.println("=====================================================");
	}
}

/**
 * 테스트를 위해 제공하는 샘플 데이터이다.
 * 본 SampleData 클래스는 수정해서 사용해도 된다.
 */
class SampleData {
    /**
     * 사번, 점수 배열을 반환한다.
     * @return {사번, 점수} array
     */
    public static int[][] getInput1() {
        return new int[][]{
                {10210, 80}
                , {10211, 77}
                , {10212, 55}
                , {10213, 66}
                , {10214, 77}
                , {10215, 99}
                , {10216, 30}
                , {10217, 10}
        };
        /**
         예상 결과
         10215 99 1
         10210 80 2
         10211 77 3
         10214 77 3
         10212 55 4
         10213 66 5
         10216 30 6
         10217 10 7
         */
    }
    
    /**
     * 사번, 점수 배열을 반환한다.
     * @return {사번, 점수} array
     */    
    public static int[][] getInput2() {
        return new int[][]{
                {10210, 80}
                , {10211, 77}
                , {10212, 55}
                , {10213, 66}
                , {10214, 77}
                , {10215, 99}
                , {10216, 30}
                , {10217, 99}
                , {10218, 50}
                , {10219, 88}
                , {10220, 55}
                , {10221, 88}
                , {10222, 70}
                , {10223, 88}
        };
        /**
         예상 결과
         10215 99 1
         10217 99 1
         10219 88 2
         10221 88 2
         10223 88 2
         10210 80 3
         10211 77 4
         10214 77 4
         10222 70 5
         10213 66 6
         10212 55 7
         10220 55 7
         10218 50 8
         10216 30 9
         */
    }
}