package edu.jutlandica;

import static org.junit.jupiter.api.Assertions.*;
import edu.jutlandica.client.controller.StringTime;

import org.junit.jupiter.api.Test;

class TestStringTIme {

	@Test
	void test() {		
		StringTime stringTime = new StringTime(0, 0);
		System.out.println(stringTime);
		
		stringTime = new StringTime(9, 9);
		System.out.println(stringTime);
		
		stringTime = new StringTime(10, 10);
		System.out.println(stringTime);
		
		stringTime = StringTime.parseStringTime("00:00");
		System.out.println(stringTime);
		
		stringTime = StringTime.parseStringTime("09:09");
		System.out.println(stringTime);
		
		stringTime = StringTime.parseStringTime("10:10");
		System.out.println(stringTime);
		
		fail("Not yet implemented");
	}

}
