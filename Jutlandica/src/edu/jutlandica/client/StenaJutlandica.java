<<<<<<< Updated upstream
package edu.jutlandica.client;

import java.time.LocalTime;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class StenaJutlandica implements EntryPoint {

	public static final String USER_FORM = "userForm";
	
	public static final String KIEL = "Kiel";
	public static final String FREDRIKSHAMN = "Fredrikshamn";

	public static final String DEPARTURE_DENMARK = "20:30";
	public static final String DEPARTURE_GERMANY = "16:25";

	/**
	 * 
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		final HorizontalPanel formPanel = new HorizontalPanel();
		final VerticalPanel vPanel = new VerticalPanel();
		final TextBox from = new TextBox();
		final Label infoLabel = new Label("");
		
		// from.setStyleName("textBoxOne");

		// Add a drop box with the list types
		final ListBox to = new ListBox();;
		to.addItem(KIEL);
		to.addItem(FREDRIKSHAMN);
		
		Button btn = new Button("Submit", new ClickHandler() {
			public void onClick(ClickEvent event) {
				// Window.alert("TO: " + to.getValue() + ", FROM: " + from.getValue());
				BussDepartures buss;
				if (to.getSelectedValue().equals(KIEL)) {
					buss = BussDepartures.getNextDeparture(12, 2, BussDepartures.TYSKLAND);
					infoLabel.setText("Next departue to Kiel at " + DEPARTURE_GERMANY.toString() + ", Take bus " + buss.toString());
				}
				else {
					buss = BussDepartures.getNextDeparture(12, 2, BussDepartures.DANMARK);
					infoLabel.setText("Next departue to Kiel at " + DEPARTURE_DENMARK.toString() + ", Take bus " + buss.toString());
				}
				
			}
		});

		formPanel.add(from);
		formPanel.add(to);
		formPanel.add(btn);
		vPanel.add(formPanel);
		vPanel.add(infoLabel);
		
		RootPanel.get(USER_FORM).add(vPanel);
	}

}
=======
package edu.jutlandica.client;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.TimeZone;

import java.util.Date;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class StenaJutlandica implements EntryPoint {

	public static final String USER_FORM = "userForm";
	
	public static final String KIEL = "Kiel";
	public static final String FREDRIKSHAMN = "Fredrikshamn";

	public static final String DEPARTURE_DENMARK = "20:30";
	public static final String DEPARTURE_GERMANY = "16:25";

	/**
	 * 
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		final HorizontalPanel formPanel = new HorizontalPanel();
		final VerticalPanel vPanel = new VerticalPanel();
		final TextBox from = new TextBox();
		final Label ferryLabel = new Label("");
		final Label busLabel = new Label("");
		
		// from.setStyleName("textBoxOne");

		// Add a drop box with the list types
		final ListBox to = new ListBox();
		to.addItem(KIEL);
		to.addItem(FREDRIKSHAMN);
		
		Button btn = new Button("Submit", new ClickHandler() {
			public void onClick(ClickEvent event) {
				BusDepartures bus = null;
				StenaFerries ferry = null; 
				
				Date date = new Date();
				DateTimeFormat dtf = DateTimeFormat.getFormat("HH:mm");
				
				if (to.getSelectedValue().equals(KIEL)) {
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
				ferryLabel.setText("FERRY: " + ferry.toString());
			}
		});

		formPanel.add(from);
		formPanel.add(to);
		formPanel.add(btn);
		vPanel.add(formPanel);
		vPanel.add(busLabel);
		vPanel.add(ferryLabel);
		
		RootPanel.get(USER_FORM).add(vPanel);
	}

}
>>>>>>> Stashed changes
