package edu.jutlandica.client;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.TimeZone;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import edu.jutlandica.client.controller.StenaFerries;
import edu.jutlandica.client.controller.VTConnector;

import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;

public class StenaJutlandica implements EntryPoint/*, Observer*/{

	public static final String USER_FORM = "userForm";

	public static final String KIEL = "Kiel";
	public static final String FREDRIKSHAMN = "Fredrikshamn";

	public static final String DEPARTURE_DENMARK = "20:30";
	public static final String DEPARTURE_GERMANY = "16:25";	
	
	private boolean timeTableShow = true;	
	private ArrayList<HTML> journeyPanels;
	
	public HTML getJourneyPanel(StenaFerries ferry) {
		HTML html = new HTML("<div class=\"vehicle\">\r\n" + 
				"      <h1 class=\"buss\">F&#228;rja: " + ferry.getName() + "</h1>\r\n" + 
				"      <h1 class=\"buss\">Avg&#229;ng:  " + ferry.getDepartureTime() + "</h1>\r\n" + 
				"      <h1 class=\"buss\">Ankomst:  " + ferry.getArrivalTime() + "</h1>\r\n" + 
				"    </div>");
		return html;
	}
	
	/**
	 * 
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		journeyPanels = new ArrayList<HTML>();
		
		final HorizontalPanel formPanel = new HorizontalPanel();
		final VerticalPanel vPanel = new VerticalPanel();
		final TextBox from = new TextBox();
		from.setValue("CENTRAL STATIONEN, G&#246;teborg");
		from.setEnabled(false);
		
		final VTConnector vtConnector = new VTConnector();	
		
		// from.setStyleName("textBoxOne");

		// Add a drop box with the list types
		final ListBox to = new ListBox();
		to.addItem(KIEL);
		to.addItem(FREDRIKSHAMN);
		
		vPanel.setWidth("100%");
		vPanel.setHeight("100%");
		vPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		vPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		
		formPanel.setSpacing(12);
		formPanel.add(from);
		formPanel.add(to);		
		vPanel.add(formPanel);
		
		final VerticalPanel journeyPanel = new VerticalPanel();		
		
		Button btn = new Button("S&#246;k Resa", new ClickHandler() {
			public void onClick(ClickEvent event) {
				BusDepartures bus = null;
				StenaFerries ferry = null; 
				
				Date date = new Date();
				DateTimeFormat dtf = DateTimeFormat.getFormat("HH:mm");				
				
				int hours = date.getHours();
				int minute = date.getMinutes();
				journeyPanel.clear();
				if (timeTableShow) {					
					for (int i = 0; i < 4; i++) {					
						ferry = StenaFerries.getNextDeparture(hours, minute, to.getSelectedValue()); 
						HTML dp = getJourneyPanel(ferry);
						journeyPanel.add(dp);
						
						hours = ferry.getDepartureDate().getHours();
						minute = ferry.getDepartureDate().getMinutes();
					}
					//timeTableShow = false;
				}
				
				/*if (to.getSelectedValue().equals(KIEL)) {
					bus = BusDepartures.getNextDeparture(date.getHours(), date.getMinutes(), BusDepartures.TYSKLAND);
					ferry = StenaFerries.getNextDeparture(date.getHours(), date.getMinutes(), KIEL);
					//infoLabel.setText(dtf.format(date, TimeZone.createTimeZone(4)) + " Next departue to Kiel at " + DEPARTURE_GERMANY.toString() + ", Take bus " + buss.toString());
				}
				else {
					bus = BusDepartures.getNextDeparture(date.getHours(), date.getMinutes(), BusDepartures.DANMARK);
					ferry = StenaFerries.getNextDeparture(date.getHours(), date.getMinutes(), FREDRIKSHAMN);					
					//infoLabel.setText(dtf.format(date, TimeZone.createTimeZone(4)) + " Next departue to Kiel at " + DEPARTURE_DENMARK.toString() + ", Take bus " + buss.toString());
				}
				
				busLabel.setText("BUS: " + bus.toString());
				ferryLabel.setText("FERRY: " + ferry.toString());*/
			}
		});
		Label testlabel = new Label("hello");
		btn.setWidth("300px");
		btn.setHeight("48px");	
		btn.addStyleName("my-gwt-button");
		vPanel.add(btn);
		vPanel.add(testlabel);
		vPanel.add(journeyPanel);
		//journeyPanel.add(busLabel);
		//journeyPanel.add(ferryLabel);
		//dp.add(journeyPanel);
		
		//vPanel.add(dp);
		
		RootPanel.get(USER_FORM).add(vPanel);
	}
/*
	@Override
	public void update(Observable o, Object arg) {
		// TODO updatera guit efter arguments
		
	}*/

}