package edu.jutlandica.client.model;

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

public class TimePanel {
	final VerticalPanel timePanel;
	
	final HorizontalPanel checkBoxPanel;
	final HorizontalPanel listBoxPanel;
	
	final Label labelTime;
	final RadioButton checkBoxNow;
	final RadioButton checkBoxArrival;
	final RadioButton checkBoxDeparture;
	
	final ListBox listBoxDate;
	final ListBox listBoxHours;
	final ListBox listBoxMinutes;
	
	final long dayTime = 86400000; //Milliseconds
	
	public TimePanel() {
		timePanel = new VerticalPanel();
		checkBoxPanel = new HorizontalPanel();
		listBoxPanel = new HorizontalPanel();
		labelTime = new Label("Tid:");
		checkBoxNow = new RadioButton("time", "Nu");
		checkBoxNow.setValue(true);
		checkBoxNow.addClickHandler(new ClickHandlerCheckBoxRemove());
		
		checkBoxArrival = new RadioButton("time", "Ankomst");
		checkBoxArrival.addClickHandler(new ClickHandlerCheckBox());
		
		checkBoxDeparture = new RadioButton("time", "Avgång");
		checkBoxDeparture.addClickHandler(new ClickHandlerCheckBox());
		
		//CREATE DATE LIST
		listBoxDate = new ListBox();
		final Date today = new Date();
		long time = today.getTime();
		DateTimeFormat fmt = DateTimeFormat.getFormat("EEE, MMM d");
		
		Date date = new Date(time - 2*dayTime);
		for (int i = -2; i <= 30; i++) {
			date = new Date(date.getTime() + dayTime);
			listBoxDate.addItem(fmt.format(date));
		}
		listBoxDate.setSelectedIndex(1);
		
		//CREATE HOUR LIST
		listBoxHours = new ListBox();
		for (int i = 0; i < 24; i++) {
			listBoxHours.addItem((i < 10 ? "0" + i : "" + i));
			if (today.getHours() == i) listBoxHours.setSelectedIndex(i);
		}
		
		//CREATE MINUTE LIST
		listBoxMinutes = new ListBox();
		for (int i = 0; i < 60; i += 5) {
			listBoxMinutes.addItem((i < 10 ? "0" + i : "" + i));
			if (today.getMinutes() - (today.getMinutes() % 5) == i) listBoxMinutes.setSelectedIndex(i/5);
		}
		
		checkBoxPanel.add(labelTime);
		checkBoxPanel.add(checkBoxNow);
		checkBoxPanel.add(checkBoxArrival);
		checkBoxPanel.add(checkBoxDeparture);
		
		checkBoxPanel.setSpacing(12);
		listBoxPanel.setSpacing(12);
		timePanel.add(checkBoxPanel);
		timePanel.add(listBoxPanel);
	}
	
	public VerticalPanel getTimePanel() {
		return timePanel;
	}
	
	public Date getDate() {
		if (checkBoxNow.getValue()) {
			return new Date();
		}
		else {
			DateTimeFormat fmt = DateTimeFormat.getFormat("EEE, MMM d");
			Date date = fmt.parse(listBoxDate.getSelectedValue());
			date.setHours(Integer.valueOf(listBoxHours.getSelectedValue()));
			date.setMinutes(Integer.valueOf(listBoxMinutes.getSelectedValue()));
			return date;
		}
	}
	
	class ClickHandlerCheckBox implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {
			if (listBoxPanel.getWidgetCount() == 0) {
				listBoxPanel.add(listBoxDate);
				listBoxPanel.add(listBoxHours);
				listBoxPanel.add(listBoxMinutes);
			}
		}
	}
	
	class ClickHandlerCheckBoxRemove implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {
			listBoxPanel.clear();
		}
	}
}
