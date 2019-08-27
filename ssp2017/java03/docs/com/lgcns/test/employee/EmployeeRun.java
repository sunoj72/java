package com.lgcns.test.employee;

import java.util.Arrays;

public class EmployeeRun {

	public static void main(String[] args) {
		printResult(getDepartment(), getEmployee());
	}
	
	public static void printResult(String[][] department, String[][] employee) {
		String [][] result = Employee.print(getDepartment(), getEmployee());
		System.out.println("===============================");
		System.out.println("사원정보 출력");
		System.out.println("[부서명, 사원번호, 사원명]");
		for(int i=0;i<result.length;i++) {
			System.out.println(Arrays.toString(result[i]));
		}
		System.out.println("===============================");
		
	}
	
	/**
	 * 부서정보 샘플데이터
	 * 
	 * @return
	 */
	public static String[][] getDepartment() {

		String[][] department = { 
				{ "10", "영업팀" }, 
				{ "20", "구매팀" },
				{ "30", "개발팀" }, 
				{ "40", "운영팀" } 
				};
		return department;
	}

	/**
	 * 사원정보 샘플데이터
	 * 
	 * @return
	 */
	public static String[][] getEmployee() {

		String[][] employee = { 
				{ "59545", "최선명", "10" }, 
				{ "23561", "박선주", "20" }, 
				{ "37123", "김길동", "10" },
				{ "33777", "이기주", "30" }
				};
		return employee;
	}

}
