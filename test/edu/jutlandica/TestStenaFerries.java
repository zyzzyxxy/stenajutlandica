package edu.jutlandica;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import edu.jutlandica.client.StenaJutlandica;
import edu.jutlandica.client.controller.StenaFerries;
import edu.jutlandica.client.dataclasses.Journey;

import org.junit.jupiter.api.Test;

class TestStenaFerries {

	@Test
	void test() {
		StenaFerries sf = new StenaFerries();
		List<Journey> journeys = sf.getJourneys("", StenaJutlandica.FREDRIKSHAMN, "00:00", "");
		System.out.println(journeys.get(0).toString());
		
		System.out.println("_______NEXT_TEST__________");		
		journeys = sf.getJourneys("", StenaJutlandica.FREDRIKSHAMN, "04:10", "");
		System.out.println(journeys.get(0).toString());
		
		fail("Not yet implemented");
	}

}
