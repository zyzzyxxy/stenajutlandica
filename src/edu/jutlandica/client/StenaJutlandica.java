package edu.jutlandica.client;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import edu.jutlandica.client.SearchService;
import edu.jutlandica.client.SearchServiceAsync;
import edu.jutlandica.client.controller.SearchEngine;
import edu.jutlandica.client.dataclasses.Journey;
import edu.jutlandica.client.model.JourneyModel;

public class StenaJutlandica implements EntryPoint/* , Observer */ {

	public static final String USER_FORM = "userForm";

	public static final String KIEL = "Kiel";
	public static final String FREDRIKSHAMN = "Fredrikshamn";

	public static final String DEPARTURE_DENMARK = "20:30";
	public static final String DEPARTURE_GERMANY = "16:25";

	private boolean timeTableShow = true;

	final VerticalPanel journeyPanel = new VerticalPanel();

	private SearchServiceAsync searchService = GWT.create(SearchService.class);
	private final Label testlabel = new Label("");
	
	
	private class SearchCallBack implements AsyncCallback<List<Journey>> {
		@Override
		public void onFailure(Throwable caught) {
			/* server side error occured */
			Window.alert("Unable to obtain server response: " + caught.getMessage());
		}
		
		@Override
		public void onSuccess(List<Journey> list) {
			/* server returned result, show user the message */
			//Window.alert(result.getMessage());
			
			//StringBuilder sb = new StringBuilder();
			List<JourneyModel> journeyModels = new ArrayList<JourneyModel>();
			journeyPanel.clear();
			for(Journey j: list) {
				journeyModels.add(new JourneyModel(j));
				//sb.append(j.toString());
			
		}
			
			
			

			for (JourneyModel jm : journeyModels) {
				journeyPanel.add(jm.getJourneyPanel());
			}
			/*
			testlabel.setText("Testing");

			if(list != null) {
			testlabel.setText(testlabel.getText() + list.get(0).getTripList().get(0).toString());
			}
			else {testlabel.setText(testlabel.getText() + "  LIST WAS NULLLLLL!!!!!!!!!!!!!");}
			*/
		}
	}

	/**
	 * 
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		

		final HorizontalPanel formPanel = new HorizontalPanel();
		final VerticalPanel vPanel = new VerticalPanel();
		final TextBox from = new TextBox();
		from.setValue("CENTRAL STATIONEN, G&#246;teborg");
		from.setEnabled(true);


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

		Button btn = new Button("S&#246;k Resa", new ClickHandler() {
			public void onClick(ClickEvent event) {
				
				
				searchService.getJourneys(to.getSelectedValue(),from.getValue(), new Date(), new SearchCallBack());

			/*	SearchEngine searchEngine = new SearchEngine();
				List<JourneyModel> journeyModels = searchEngine.search(from.getValue(), to.getSelectedValue(),
						new Date());
				journeyPanel.clear();

				for (JourneyModel jm : journeyModels) {
					journeyPanel.add(jm.getJourneyPanel());
				}
				*/
			}
		});
		
		
		//searchService.getJourneys("olivedalsgatan", "masthuggstorget", new Date(), new SearchCallBack());

		btn.setWidth("300px");
		btn.setHeight("48px");
		btn.addStyleName("my-gwt-button");
		vPanel.add(btn);
		vPanel.add(testlabel);
		vPanel.add(journeyPanel);
		// journeyPanel.add(busLabel);
		// journeyPanel.add(ferryLabel);
		// dp.add(journeyPanel);

		// vPanel.add(dp);

		RootPanel.get(USER_FORM).add(vPanel);
	}
	/*
	 * @Override public void update(Observable o, Object arg) { // TODO updatera
	 * guit efter arguments
	 * 
	 * }
	 */

}