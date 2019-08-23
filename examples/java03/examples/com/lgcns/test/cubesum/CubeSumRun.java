package com.lgcns.test.cubesum;

public class CubeSumRun {
	
	public static void main(String[] args) {
		printResult(getCube());
	}
	
    public static void printResult(int[][] cube) {
        int[][] result = CubeSum.sum(cube);
        System.out.println("===========================");
        System.out.println("큐브의 합:");
        for (int i = 0; i < result.length; i++) {
            if (i != 0) {
                System.out.println("");
            }
            for (int j = 0; j < result[i].length; j++) {
                if (j != 0) {
                    System.out.print("\t");
                }
                System.out.print(result[i][j]);
            }
        }
        System.out.println("\n===========================");
    }
	
	/**
	 * 샘플데이터 변경해서 사용 가능
	 * 
	 * @return 3X3 Cube
	 */
	public static int[][] getCube() {
		int cube[][] = {
				{5,7,8},
				{4,2,1},
				{2,5,6}
		};
		
		return cube;
	}
	
	/**
	 * 샘플데이터 변경해서 사용 가능
	 * 
	 * @return 4X4 Cube
	 */
	public static int[][] getCube4() {
		//4X4 cube
		int cube[][] = {
				{31,22,55,33},
				{76,12,35,78},
				{99,11,47,87},
				{36,22,71,91},
		};
		
		return cube;
	}

}
