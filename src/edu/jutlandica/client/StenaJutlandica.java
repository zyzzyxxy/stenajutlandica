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
import edu.jutlandica.client.dataclasses.Trip;
import edu.jutlandica.client.model.JourneyModel;
import edu.jutlandica.client.model.TimePanel;

public class StenaJutlandica implements EntryPoint/* , Observer */ {

	public static final String USER_FORM = "userForm";

	public static final String KIEL = "Kiel";
	public static final String FREDRIKSHAMN = "Fredrikshamn";

	public static final String DEPARTURE_DENMARK = "20:30";
	public static final String DEPARTURE_GERMANY = "16:25";
	final VerticalPanel journeyPanel = new VerticalPanel();

	private SearchServiceAsync searchService = GWT.create(SearchService.class);
	private final Label testlabel = new Label("");
	private TimePanel timePanel;

	private class SearchCallBack implements AsyncCallback<List<Journey>> {
		@Override
		public void onFailure(Throwable caught) {
			/* server side error occured */
			Window.alert("Unable to obtain server response: " + caught.getMessage());
		}

		@Override
		public void onSuccess(List<Journey> list) {
			List<JourneyModel> journeyModels = new ArrayList<JourneyModel>();
			if (list == null) {
				Trip trip = new Trip("Journey list is null", "","",new Date(), new Date(), "","");
				Journey j = new Journey();
				j.addTrip(trip);
				journeyModels.add(new JourneyModel(j));
			} 
			else if (list.isEmpty()) {
				Trip trip = new Trip("Journey list is empty", "","",new Date(), new Date(), "","");
				Journey j = new Journey();
				j.addTrip(trip);
				journeyModels.add(new JourneyModel(j));
			}
			else {
				journeyPanel.clear();
				for (Journey j : list) {
					journeyModels.add(new JourneyModel(j));
				}
			}
			for (JourneyModel jm : journeyModels) {
				journeyPanel.add(jm.getJourneyPanel());
			}
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
		from.setValue("Lindholmen");
		from.setEnabled(true);
		final ListBox to = new ListBox();
		to.addItem(KIEL);
		to.addItem(FREDRIKSHAMN);
		timePanel = new TimePanel();
		
		vPanel.setWidth("100%");
		vPanel.setHeight("100%");
		vPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		vPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);

		formPanel.setSpacing(8);
		formPanel.add(from);
		formPanel.add(to);
		vPanel.add(formPanel);

		Button btn = new Button("S&#246;k Resa", new ClickHandler() {
			public void onClick(ClickEvent event) {
				searchService.getJourneys(to.getSelectedValue(), from.getValue(), timePanel.getDate(), timePanel.isArrivalSearch(), new SearchCallBack());
			}
		});

		btn.setWidth("150px");
		btn.setHeight("48px");
		btn.addStyleName("my-gwt-button");
		vPanel.add(timePanel.getTimePanel());
		vPanel.add(btn);
		vPanel.add(testlabel);
		vPanel.add(journeyPanel);

		RootPanel.get(USER_FORM).add(vPanel);
	}
}