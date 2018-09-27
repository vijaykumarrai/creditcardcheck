package com.calculator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.IntStream;

public class CreditCardValidation {
	public static void main(String args[]) throws Exception {
		Scanner input = new Scanner(System.in);
		String[] bannedPrefixes = input.nextLine().split(",");
		int lines = Integer.parseInt(input.nextLine());
		String[] cardsToValidate = new String[lines];
		IntStream.range(0, lines).forEach(i -> cardsToValidate[i] = input.nextLine());
		List<Map<String, Object>> processedCards = validateCards(bannedPrefixes, cardsToValidate);
		processedCards.stream().forEach(CreditCardValidation::printCardResult);
	}

	public static void printCardResult(Map<String, Object> cardResult) {
		System.out.print("{\"card\":\"" + cardResult.get("card") + "\",");
		System.out.print("\"isValid\":" + cardResult.get("isValid") + ",");
		System.out.println("\"isAllowed\":" + cardResult.get("isAllowed") + "}");
	}

	static List<Map<String, Object>> validateCards(String[] bannedPrefixes, String[] cardsToValidate) {
		List<Map<String, Object>> resultList = new ArrayList<>();
		for (String card : cardsToValidate) {
			boolean isBanned = false;
			for (String bannedPrefix : bannedPrefixes) {
				isBanned = card.startsWith(bannedPrefix);
				if (isBanned) {
					break;
				}
			}
			boolean isValid = luhnCheck(card);

			Map<String, Object> result = new HashMap<>();
			result.put("card", card);
			result.put("isValid", isValid);
			result.put("isAllowed", !isBanned);
			resultList.add(result);
		}
		return resultList;
	}

	public static boolean luhnCheck(String card) {
		if (card == null)
			return false;
		char lastDigit = card.charAt(card.length() - 1);
		int lastNumber = Character.getNumericValue(lastDigit);
		//int lastNumber = val * 2;
		
		
		card=card.substring(0,card.length()-1);
		char[] digits = card.toCharArray();
		int sum = 0;
		// int digit=0;
		for (char c : digits) {
			int digit = Character.getNumericValue(c);
			digit = digit * 2;
			sum += digit;
		}

		int mod = sum % 10;
		return mod == lastNumber;
	}

}
