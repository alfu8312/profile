package com.bank.profile;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class GeneratorSample {

	public static String generateFile() {
		return "logs/sample2";
	}

	public static void main(String[] args) throws Exception {

		int historyMax = 100;
		int customerNums = 100;
		Date strDate = new Date();
		Date endDate = new Date();
		Map<Integer, String> customerMap = new HashMap<>();
		Map<Integer, Integer> customerTradeCnt = new HashMap<>();
		Map<Integer, String> accountMap = new HashMap<>();
		Map<Integer, String> tradeMap = new HashMap<>();

		//TODO sample data 생성 코드 작성
		// 고객 생성(날짜 범위 내에서 고객정보 생성)
		// 모든 고객 1~3개 계좌 개설(고객생성일시 이후로 지정) - accountMap 에 추가
		// 계좌별로 최소 한번의 입금 로그 생성
		// 랜덤하게 입금, 출금, 이체로그 생성(계좌개설 일시 이후 날짜로, 이체의 경우 두계좌중 느린 날짜 이후로)
		// 고객별 트레이드 카운트가 100 건 이상이 된 경우 customerTradeCnt 에서 제거

		// a. 가입 로그
		// i. 고객번호
		// ii. 고객명
		// iii. 가입 시각

		// b. 계좌개설 로그
		// i. 고객번호
		// ii. 계좌번호
		// iii. 개설 시각

		// c. 입금 로그
		// i. 고객번호
		// ii. 입금 계좌번호
		// iii. 입금 금액
		// iv. 입금 시각

		// d. 출금 로그
		// i. 고객번호
		// ii. 출금 계좌번호
		// iii. 출금 금액
		// iv. 출금 시각

		// e. 이체 로그
		// i. 고객번호
		// ii. 송금 계좌번호
		// iii. 수취 은행
		// iv. 수취 계좌번호
		// v. 수취 계좌주
		// vi. 이체 금액
		// vii. 이체 시각

	}
}
