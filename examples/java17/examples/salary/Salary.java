package com.lgcns.test.salary;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class Salary {

	/**
	 * 직급별 평균연봉을 출력하는 메소드(구현)
	 * 
	 * @param salary
	 * @param employee
	 * @return
	 */
	public String [][] print(String [][] salary, String[][] employee) {
		
		HashMap<String, Integer> gradeCnt = new HashMap<String, Integer>();
		HashMap<String, Float> salCal = new HashMap<String, Float>();
		
		for(int i = 0 ; i < salary.length ; i++){
			for(int j = 0 ; j < employee.length ; j++){				
				if(salary[i][0].equals(employee[j][0])){									
					if(gradeCnt.get(employee[j][2]) == null){
						gradeCnt.put(employee[j][2] , 1);
						salCal.put(employee[j][2] , Float.parseFloat(salary[i][1]));						
					}else{
						gradeCnt.put(employee[j][2] , gradeCnt.get(employee[j][2]) + 1);
						salCal.put(employee[j][2] , salCal.get(employee[j][2]) + Float.parseFloat(salary[i][1]));							
					}
				}
			}
		}	
		
		Set<String> geradeSet = gradeCnt.keySet();
		String[][] resultArr = new String[geradeSet.size()][2];
		Iterator<String> it = geradeSet.iterator();
		int k = 0;
		
		while(it.hasNext()){			
			String Ji = it.next();	
			resultArr[k][0] = Ji;
			resultArr[k][1] = Integer.toString((int)Math.round( salCal.get(Ji) / gradeCnt.get(Ji)) );
			k++;
		}
		
		for(int i = 0 ; i < resultArr.length - 1; i ++){
			for(int j = 0 ; j < resultArr.length - 1; j ++){
				String tempGrade = resultArr[j][0];
				String tempSal = resultArr[j][1];
				
				if(tempSal.compareTo(resultArr[j+1][1]) >= 0){
					resultArr[j][0] = resultArr[j+1][0];
					resultArr[j][1] = resultArr[j+1][1];
					resultArr[j+1][0] = tempGrade;
					resultArr[j+1][1] = tempSal;
				}
			}
		}
		return resultArr;
	}
}
