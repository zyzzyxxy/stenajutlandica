package edu.jutlandica;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;

import edu.jutlandica.client.StenaJutlandica;
import edu.jutlandica.client.controller.SearchEngine;
import edu.jutlandica.client.model.JourneyModel;

class TestSearchEngine {

	@Test
	void test() {
		SearchEngine s = new SearchEngine();
		List<JourneyModel> jms = s.search("", StenaJutlandica.FREDRIKSHAMN, new Date());
		
		for (JourneyModel jm : jms) {
			System.out.println(jm.toString());
		}
		
		fail("Not yet implemented");
	}

}
