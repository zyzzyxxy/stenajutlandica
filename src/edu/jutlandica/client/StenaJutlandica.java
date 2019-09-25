package edu.jutlandica.client;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.TimeZone;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

import edu.jutlandica.client.controller.SearchEngine;
import edu.jutlandica.client.controller.StenaFerries;
import edu.jutlandica.client.controller.VTConnector;
import edu.jutlandica.client.model.JourneyModel;

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
				SearchEngine searchEngine = new SearchEngine();
				List<JourneyModel> journeyModels = searchEngine.search(from.getValue(), to.getSelectedValue(), new Date());				
				journeyPanel.clear();	
				
				for (JourneyModel jm : journeyModels) {
					journeyPanel.add(jm.getJourneyPanel());
				}
				
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