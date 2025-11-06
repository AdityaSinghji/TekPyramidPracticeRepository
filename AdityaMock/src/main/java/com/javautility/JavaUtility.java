package com.javautility;

import java.time.LocalDate;
import java.util.Random;

public class JavaUtility {
	
	public int getRandomNumber(int x) {
		Random r = new Random();
		int randomNumber = r.nextInt(x);
		
		return randomNumber;
	}
	
	public String getSystemDateYYYYMMDD()
	{
		String today = LocalDate.now().toString();
		return today;
	}
	
	public String getRandomText()
	{
		String characters = "abcdefABCDEF12345678";
		int randomLength= 3 + getRandomNumber(6);
		StringBuilder randomtext = new StringBuilder();
		
		for(int i=0;i<randomLength;i++)
		{
			int index = getRandomNumber(characters.length());
			randomtext.append(characters.charAt(index));
		}
		
		return randomtext.toString();
	}

}
