package com.prodyna.pac.mmonshausen.conference.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

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
import com.prodyna.pac.mmonshausen.conference.service.ConferenceService;

/**
 * test all methods of the ConferenceService
 * 
 * @author Martin Monshausen, PRODYNA AG
 */
@RunWith(Arquillian.class)
public class ConferenceServiceTest {
	
	@Inject
	private ConferenceService conferenceService;
	@Inject
	private TestHelper testHelper;
	
	@Deployment
	public static Archive<?> createTestArchive() {
		return ShrinkWrap
				.create(WebArchive.class, "test.war")
				.addPackage("com.prodyna.pac.mmonshausen.conference.model")
				.addPackage("com.prodyna.pac.mmonshausen.conference.service")
				.addPackage("com.prodyna.pac.mmonshausen.conference.util")
				.addAsResource("META-INF/persistence.xml")
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
	}
	
	@Test
	public void testCreateConference() {
		final Location testLocation = testHelper.createTestLocation();
		final Conference testConference = testHelper.createTestConference(testLocation);
		assertNotNull(testConference);
		assertNotNull(testConference.getId());
		assertTrue(testConference.getId() > 0);
	}
	
	@Test
	public void testGetConferenceById() {
		final Location testLocation = testHelper.createTestLocation();
		final Conference testConference = testHelper.createTestConference(testLocation);
		assertNotNull(testConference);
		final Long id = testConference.getId();
		assertNotNull(id);
		
		final Conference conferenceById = conferenceService.getConferenceById(id);
		assertNotNull(conferenceById);
		assertEquals(testConference.getDescription(), conferenceById.getDescription());
	}

	@Test
	public void testListAllConferences() {
		final List<Conference> conferences = conferenceService.listAllConferences();
		assertNotNull(conferences);
		assertTrue(conferences.size() > 0);
	}

	@Test
	public void testUpdateConference(final Conference conference) {
		final Location testLocation = testHelper.createTestLocation();
		final Conference testConference = testHelper.createTestConference(testLocation);
		assertNotNull(testConference);
		
		testConference.setName("Anderer Name");
		
		Conference updateConference = conferenceService.updateConference(testConference);
		assertNotNull(updateConference);
		assertEquals(updateConference.getName(), "Anderer Name");
	}

	@Test
	public void deleteConference() {
		final Location testLocation = testHelper.createTestLocation();
		final Conference testConference = testHelper.createTestConference(testLocation);
		assertNotNull(testConference);
		final Long id = testConference.getId();
		assertNotNull(id);
		
		conferenceService.deleteConference(id);
		
		final Conference conferenceById = conferenceService.getConferenceById(id);
		assertNull(conferenceById);
	}
}