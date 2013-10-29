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

import com.prodyna.pac.mmonshausen.conference.model.Location;
import com.prodyna.pac.mmonshausen.conference.service.LocationService;

/**
 * test all methods of the LocationService
 * 
 * @author Martin Monshausen, PRODYNA AG
 */
@RunWith(Arquillian.class)
public class LocationServiceTest {
	
	@Inject
	private LocationService locationService;
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
	public void testCreateLocation() {
		final Location location = new Location("Test-Halle", "Test-Straße 1",
				"68782", "Brühl", "Deutschland");
		final Location pLocation = locationService.createLocation(location);

		assertNotNull(pLocation);
		assertNotNull(pLocation.getId());
		assertTrue(pLocation.getId() > 0);
	}
	
	@Test
	public void testGetLocationById() {
		final Location testLocation = testHelper.createTestLocation();
		assertNotNull(testLocation);
		assertNotNull(testLocation.getId());
		
		final Location locationById = locationService.getLocationById(testLocation.getId());
		assertNotNull(locationById);
		assertEquals(testLocation.getName(), locationById.getName());
	}
	
	@Test
	public void testListAllLocations() {
		final List<Location> locations = locationService.listAllLocations();
		assertNotNull(locations);
		assertTrue(locations.size() > 0);
	}
	
	@Test
	public void updateLocation() {
		final Location testLocation = testHelper.createTestLocation();
		assertNotNull(testLocation);
		assertNotNull(testLocation.getId());
		
		testLocation.setCity("Ketsch");
		
		final Location updateLocation = locationService.updateLocation(testLocation);
		assertNotNull(updateLocation);
		assertEquals(updateLocation.getCity(), "Ketsch");
	}
	
	@Test
	public void deleteLocation() {
		final Location testLocation = testHelper.createTestLocation();
		assertNotNull(testLocation);
		final Long id = testLocation.getId();
		assertNotNull(id);
		
		locationService.deleteLocation(id);
		
		final Location locationById = locationService.getLocationById(id);
		assertNull(locationById);
	}
}