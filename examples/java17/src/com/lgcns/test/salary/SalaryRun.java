package com.lgcns.test.salary;

import java.util.Arrays;

public class SalaryRun {
	
	public static void main(String [] args) {
		printResult(getSalary(), getEmployees());
	}
	
	public static void printResult(String [][] salary, String [][] employee) {
		Salary salarys = new Salary();
		System.out.println("직급별 평균연봉 정보");
		System.out.println("[직급, 평균연봉(만원)]");
		String [][] results = salarys.print(salary, employee);
		if(results != null) {
			for(String [] result : results) {
				System.out.println(Arrays.toString(result));
			}
		}
		
	}
	
	/**
	 * 연봉정보 샘플
	 * 
	 * @return
	 */
	public static String [][] getSalary() {
		String [][] salary = {
				{"23561", "5600"},
				{"37123", "4100"},
				{"33777", "4400"},
				{"13451", "6300"},
				{"23579", "5900"},
				{"87594", "2400"},
				{"59545", "3500"},
				{"49376", "3600"}
		};
		
		return salary;
	}
	
	/**
	 * 직원정보 샘플
	 * 
	 * @return
	 */
	public static String [][] getEmployees() {
		String [][] employees = {
				{"59545", "나연",	 "대리"},
				{"37123", "정연", "대리"},
				{"23561", "모모", "과장"},
				{"33777", "사나", "과장"},
				{"23579", "지효", "차장"},
				{"87594", "미나", "사원"},
				{"13451", "다현", "부장"},
				{"49376", "채영", "대리"}
		};
		
		return employees;
	}
}
