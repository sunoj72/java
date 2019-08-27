package com.lgcns.test.cubesum;

public class CubeSum {
	
	/**
	 * 입력받은 큐브를 시계방향으로 90도 회전하여 동일한 위치의 합을 리턴하는 메소드
	 * 
	 * @param cube MXM 크기의 이차원 배열
	 * @return cube + 시게방향 90도 회전 cube
	 */
	public static int[][] sum(int[][] cube) 
	{
		int [][] result = new int[cube.length][cube.length];
		
		for(int inx = 0; inx < cube.length ; inx ++){
			for(int jnx = 0; jnx < cube.length; jnx ++){
				result[jnx][inx] = cube[jnx][inx] + cube[cube.length-inx-1][jnx];
			}
		}
		
		
		return result;
	}

}
