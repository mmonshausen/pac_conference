package com.prodyna.pac.mmonshausen.conference.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNull;

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

import com.prodyna.pac.mmonshausen.conference.model.Speaker;
import com.prodyna.pac.mmonshausen.conference.service.SpeakerService;

/**
 * test all methods of the SpeakerService
 * 
 * @author Martin Monshausen, PRODYNA AG
 */
@RunWith(Arquillian.class)
public class SpeakerServiceTest {

	@Inject
	private SpeakerService speakerService;

	@Inject
	private TestHelper testHelper;

	@Deployment
	public static Archive<?> createTestArchive() {
		return ShrinkWrap.create(WebArchive.class, "test.war")
				.addPackage("com.prodyna.pac.mmonshausen.conference.model")
				.addPackage("com.prodyna.pac.mmonshausen.conference.service")
				.addPackage("com.prodyna.pac.mmonshausen.conference.monitoring")
				.addPackage("com.prodyna.pac.mmonshausen.conference.util")
				.addClass(TestHelper.class)
				.addAsResource("META-INF/persistence.xml")
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
	}

	@Test
	public void testCreateSpeaker() {
		final Speaker speaker = new Speaker("Test Tester", "Ein Experte auf dem Gebiet JavaEE");
		final Speaker pSpeaker = speakerService.createSpeaker(speaker);
		
		assertNotNull(pSpeaker);
		assertNotNull(pSpeaker.getId());
		assertTrue(pSpeaker.getId() > 0);
	}

	@Test
	public void testGetSpeakerById() {
		final Speaker speaker = testHelper.createTestSpeaker();
		final Speaker pSpeaker = speakerService.createSpeaker(speaker);

		assertNotNull(pSpeaker);
		assertNotNull(pSpeaker.getId());
		final Long id = pSpeaker.getId();

		final Speaker idSpeaker = speakerService.getSpeakerById(id);
		assertNotNull(idSpeaker);
		assertEquals(idSpeaker.getName(), pSpeaker.getName());
	}

	@Test
	public void testListAllSpeakers() {
		final List<Speaker> speakers = speakerService.listAllSpeakers();

		assertNotNull(speakers);
		assertTrue(speakers.size() > 0);
	}

	@Test
	public void testUpdateSpeaker() {
		final Speaker pSpeaker = testHelper.createTestSpeaker();
		assertNotNull(pSpeaker);
		assertTrue(pSpeaker.getId() > 0);

		pSpeaker.setName("Anderer Name");

		final Speaker updateSpeaker = speakerService.updateSpeaker(pSpeaker);

		assertEquals(updateSpeaker.getName(), "Anderer Name");
	}

	@Test
	public void testDeleteSpeaker() {
		final Speaker pSpeaker = testHelper.createTestSpeaker();
		assertNotNull(pSpeaker);

		final Long id = pSpeaker.getId();
		assertTrue(id > 0);

		speakerService.deleteSpeaker(id);

		final Speaker speakerById = speakerService.getSpeakerById(id);
		assertNull(speakerById);
	}
}