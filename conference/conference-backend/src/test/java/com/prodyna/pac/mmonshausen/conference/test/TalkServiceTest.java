package com.prodyna.pac.mmonshausen.conference.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.prodyna.pac.mmonshausen.conference.model.Conference;
import com.prodyna.pac.mmonshausen.conference.model.Location;
import com.prodyna.pac.mmonshausen.conference.model.Room;
import com.prodyna.pac.mmonshausen.conference.model.Speaker;
import com.prodyna.pac.mmonshausen.conference.model.Talk;
import com.prodyna.pac.mmonshausen.conference.service.TalkService;

/**
 * test all methods of the TalkService
 * 
 * @author Martin Monshausen, PRODYNA AG
 */
@RunWith(Arquillian.class)
public class TalkServiceTest {
	
	@Inject
	private TalkService talkService;
	@Inject
	private TestHelper testHelper;
	
	@Deployment
	public static Archive<?> createTestArchive() {
		return ShrinkWrap
				.create(WebArchive.class, "test.war")
				.addPackage("com.prodyna.pac.mmonshausen.conference.model")
				.addPackage("com.prodyna.pac.mmonshausen.conference.service")
				.addPackage("com.prodyna.pac.mmonshausen.conference.monitoring")
				.addPackage("com.prodyna.pac.mmonshausen.conference.util")
				.addClass(TestHelper.class)
				.addAsResource("META-INF/persistence.xml")
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
	}
	
	@Test
	public void testCreateTalk() {
		final Location testLocation = testHelper.createTestLocation();
		final Speaker testSpeaker = testHelper.createTestSpeaker();
		final Conference testConference = testHelper.createTestConference(testLocation);
		final Room testRoom = testHelper.createTestRoom(testLocation);
		
		final GregorianCalendar startCal = new GregorianCalendar(2013, Calendar.DECEMBER, 15);
		final GregorianCalendar startTime = new GregorianCalendar(2013, Calendar.DECEMBER, 15, 15, 0);
		final GregorianCalendar endTime = new GregorianCalendar(2013, Calendar.DECEMBER, 15, 18, 0);
		
		final List<Speaker> speakers = new ArrayList<Speaker>();
		speakers.add(testSpeaker);
		
		final Talk talk = new Talk("JavaEE for Dummies", "Einfuehrung in JavaEE", startCal.getTime(), startTime.getTime(), endTime.getTime(), testRoom, testConference, speakers);
		final Talk persistedTalk = talkService.createTalk(talk);
			
		assertNotNull(persistedTalk.getId());
		assertTrue(persistedTalk.getId() > 0);
	}

	@Test
	public void testGetTalkById() {
		final Talk zeroId = talkService.getTalkById(0);
		assertNull(zeroId);
		
		final Location testLocation = testHelper.createTestLocation();
		final Speaker testSpeaker = testHelper.createTestSpeaker();
		final Conference testConference = testHelper.createTestConference(testLocation);
		final Room testRoom = testHelper.createTestRoom(testLocation);
		final Talk testTalk = testHelper.createTestTalk(testConference, testRoom, testSpeaker);
		
		final Long id = testTalk.getId();
		final Talk talkById = talkService.getTalkById(id);
		assertNotNull(talkById);
		assertEquals(talkById.getName(), "JavaEE for Dummies");
	}
	
	@Test
	public void testListAllTalks() {
		final List<Talk> listAllTalks = talkService.listAllTalks();

		assertNotNull(listAllTalks);
		assertTrue(listAllTalks.size() > 0);
	}
	
	@Test
	public void testDeleteTalk() {
		final Location testLocation = testHelper.createTestLocation();
		final Speaker testSpeaker = testHelper.createTestSpeaker();
		final Conference testConference = testHelper.createTestConference(testLocation);
		final Room testRoom = testHelper.createTestRoom(testLocation);
		final Talk testTalk = testHelper.createTestTalk(testConference, testRoom, testSpeaker);
		
		final Long id = testTalk.getId();
		
		talkService.deleteTalk(id);
		
		final Talk talkById = talkService.getTalkById(id);
		assertNull(talkById);
	}
	
	@Test
	public void testConferenceTalksOrderedByDateTime() {
		final Location testLocation = testHelper.createTestLocation();
		final Speaker testSpeaker = testHelper.createTestSpeaker();
		final Conference testConference = testHelper.createTestConference(testLocation);
		final Room testRoom = testHelper.createTestRoom(testLocation);
		final Talk testTalk = testHelper.createTestTalk(testConference, testRoom, testSpeaker);
		
		final List<Talk> conferenceTalks = talkService.getConferenceTalksOrderedByDateTime(testConference.getId());
		assertNotNull(conferenceTalks);
		assertTrue(conferenceTalks.size() > 0);
		
		final Talk conferenceTalk = conferenceTalks.get(0);
		assertEquals(conferenceTalk.getName(), testTalk.getName());
	}
	
	@Test
	public void testRoomTalksOrderedByDateTime() {
		final Location testLocation = testHelper.createTestLocation();
		final Speaker testSpeaker = testHelper.createTestSpeaker();
		final Conference testConference = testHelper.createTestConference(testLocation);
		final Room testRoom = testHelper.createTestRoom(testLocation);
		final Talk testTalk = testHelper.createTestTalk(testConference, testRoom, testSpeaker);
		
		final List<Talk> roomTalks = talkService.getRoomTalksOrderedByDateTime(testRoom.getId());
		assertNotNull(roomTalks);
		assertTrue(roomTalks.size() > 0);
		
		final Talk roomTalk = roomTalks.get(0);
		assertEquals(roomTalk.getName(), testTalk.getName());
	}
	
	@Test
	public void testUpdateTalk() {
		final Location testLocation = testHelper.createTestLocation();
		final Speaker testSpeaker = testHelper.createTestSpeaker();
		final Conference testConference = testHelper.createTestConference(testLocation);
		final Room testRoom = testHelper.createTestRoom(testLocation);
		final Talk testTalk = testHelper.createTestTalk(testConference, testRoom, testSpeaker);
		
		assertNotNull(testTalk);
		assertNotNull(testTalk.getId());
		
		testTalk.setName("Anderer Name");
			
		final Talk updateTalk = talkService.updateTalk(testTalk);
		assertNotNull(updateTalk);
		assertEquals(testTalk.getName(), "Anderer Name");
	}
}