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
import com.prodyna.pac.mmonshausen.conference.model.Room;
import com.prodyna.pac.mmonshausen.conference.service.RoomService;

/**
 * test all methods of the RoomService
 * 
 * @author Martin Monshausen, PRODYNA AG
 */
@RunWith(Arquillian.class)
public class RoomServiceTest {
	
	@Inject
	private RoomService roomService;
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
	public void testCreateRoom() {
		final Location testLocation = testHelper.createTestLocation();
		
		final Room room = new Room("Raum Philadephia", 100, testLocation);
		final Room testRoom = roomService.createRoom(room);
		
		assertNotNull(testRoom);
		assertNotNull(testRoom.getId());
		assertTrue(testRoom.getId() > 0);
	}
	
	@Test
	public void testGetRoomById() {
		final Location testLocation = testHelper.createTestLocation();
		final Room testRoom = testHelper.createTestRoom(testLocation);
		assertNotNull(testRoom);
		assertNotNull(testRoom.getId());
		
		final Room room = roomService.getRoomById(testRoom.getId());
		assertNotNull(room);
		assertEquals(room.getName(), testRoom.getName());	
	}
	
	@Test
	public void testListAllRooms() {
		final List<Room> listAllRooms = roomService.listAllRooms();
		assertNotNull(listAllRooms);
		assertTrue(listAllRooms.size() > 0);
	}
	
	@Test
	public void updateRoom() {
		final Location testLocation = testHelper.createTestLocation();
		final Room testRoom = testHelper.createTestRoom(testLocation);
		assertNotNull(testRoom);
		
		testRoom.setCapacity(100);
		
		final Room updateRoom = roomService.updateRoom(testRoom);
		assertNotNull(updateRoom);
		assertEquals(updateRoom.getCapacity(), 100);
	}
	
	@Test
	public void testDeleteRoom() {
		final Location testLocation = testHelper.createTestLocation();
		final Room testRoom = testHelper.createTestRoom(testLocation);
		assertNotNull(testRoom);
		final Long id = testRoom.getId();
		assertNotNull(id);
		
		roomService.deleteRoom(id);
		
		final Room roomById = roomService.getRoomById(id);
		assertNull(roomById);
	}
}