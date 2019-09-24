package edu.jutlandica;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;

import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.event.shared.UmbrellaException;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;

import edu.jutlandica.client.vtAPI.*;

class TestVTAPI {
	
	@Test
	void test() {
		String jString = "{\"value\" : [[12,34],[56,78]]}";
		System.out.println(JsonUtils.safeToEval(jString));
		
		//JSONValue jValue = JSONParser.parseStrict("{\"value\" : [[12,34],[56,78]]}");
       	//System.out.println("HELLO2 " + jValue.toString());
       	//System.out.println("HELLO3 ");
		/*VTConnector vtc = new VTConnector();
		try {
			vtc.authenticate();
			System.out.println("HELLO");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("HELLO3");
			e.printStackTrace();
		} 
		System.out.println("HELLO2");*/
		fail("Not yet implemented");
	}

}
