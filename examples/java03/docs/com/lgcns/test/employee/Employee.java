package com.lgcns.test.employee;

public class Employee {
	
	/**
	 * 사원정보를 출력하는 메소드
	 * 출력결과는 {부서명, 사원번호, 사원명} 순으로 저장된다.
	 * 
	 * @param department 부서정보
	 * @param employee 사원정보
	 * @return 
	 */
	public static String[][] print(String[][] department, String[][] employee) {
		
		employee = sorting(employee);
		
		
		String [][] result = new String[employee.length][3];
		for(int inx = 0 ; inx < employee.length; inx ++){
			result[inx][0] = getDepNm(department, employee[inx][2]);
			result[inx][1] = employee[inx][0];
			result[inx][2] = employee[inx][1];
		}
		return result;
	}
	private static String[][] sorting(String[][] employee) {
		
		String [] tmp = {"","",""};
		for(int inx = 0; inx < employee.length; inx ++){
			for(int jnx = 0; jnx < employee.length; jnx ++){
				if(Integer.parseInt(employee[inx][2]) < Integer.parseInt(employee[jnx][2])){
					tmp = employee[inx];
					employee[inx] = employee[jnx];
					employee[jnx] = tmp;
				}
				if(Integer.parseInt(employee[inx][2]) == Integer.parseInt(employee[jnx][2])){
					if(Integer.parseInt(employee[inx][0]) < Integer.parseInt(employee[jnx][0])){
						tmp = employee[inx];
						employee[inx] = employee[jnx];
						employee[jnx] = tmp;
					}
				}
			}
		}
		return employee;
		
	}
	private static String getDepNm(String[][] department, String depNo){
		String depNm = null;
		for(int inx=0;inx < department.length;inx ++){
			if(depNo.equals(department[inx][0])){
				depNm = department[inx][1];
				break;
			}
		}
		return depNm;
	}
}
