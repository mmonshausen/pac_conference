package com.prodyna.pac.mmonshausen.conference.test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.inject.Inject;

import com.prodyna.pac.mmonshausen.conference.model.Conference;
import com.prodyna.pac.mmonshausen.conference.model.Location;
import com.prodyna.pac.mmonshausen.conference.model.Room;
import com.prodyna.pac.mmonshausen.conference.model.Speaker;
import com.prodyna.pac.mmonshausen.conference.model.Talk;
import com.prodyna.pac.mmonshausen.conference.service.ConferenceService;
import com.prodyna.pac.mmonshausen.conference.service.LocationService;
import com.prodyna.pac.mmonshausen.conference.service.RoomService;
import com.prodyna.pac.mmonshausen.conference.service.SpeakerService;
import com.prodyna.pac.mmonshausen.conference.service.TalkService;

/**
 * helper class for tests<br>
 * methods generates and persist test objects for each entity 
 * 
 * @author Martin Monshausen, PRODYNA AG
 */
public class TestHelper {
	
	@Inject
	private LocationService locationService;
	@Inject
	private RoomService roomService;
	@Inject
	private ConferenceService conferenceService;
	@Inject
	private TalkService talkService;
	@Inject
	private SpeakerService speakerService;
	
	public Location createTestLocation() {
		final Location location = new Location("Test-Halle", "Test-Straße 1", "68782", "Brühl", "Deutschland");
		return locationService.createLocation(location);
	}
	
	public Room createTestRoom(final Location location){
		final Room room = new Room("Raum Philadephia", 100, location);
		return roomService.createRoom(room);
	}
	
	public Conference createTestConference(final Location location) {
		final Calendar startDate = new GregorianCalendar(2013, Calendar.DECEMBER, 19);
		final Calendar endDate = new GregorianCalendar(2013, Calendar.DECEMBER, 25);
		
		final Conference conference = new Conference("Test Konferenz", location, "Test Text", startDate.getTime(), endDate.getTime());
		return conferenceService.createConference(conference);
	}
	
	public Speaker createTestSpeaker() {
		final Speaker speaker = new Speaker("Test Tester", "Ein Experte auf dem Gebiet JavaEE");
		return speakerService.createSpeaker(speaker);
	}
	
	public Talk createTestTalk(final Conference conference, final Room room, final Speaker speaker){
		final GregorianCalendar startCal = new GregorianCalendar(2013, Calendar.DECEMBER, 15);
		final GregorianCalendar startTime = new GregorianCalendar(2013, Calendar.DECEMBER, 15, 15, 0);
		final GregorianCalendar endTime = new GregorianCalendar(2013, Calendar.DECEMBER, 15, 18, 0);
		
		final List<Speaker> speakers = new ArrayList<Speaker>();
		speakers.add(speaker);
		
		final Talk talk = new Talk("JavaEE for Dummies", "Einfuehrung in JavaEE", startCal.getTime(), startTime.getTime(), endTime.getTime(), room, conference, speakers);
		return talkService.createTalk(talk);
	}
}
