package com.prodyna.pac.mmonshausen.conference.util;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import javax.validation.Validator;

import com.prodyna.pac.mmonshausen.conference.model.Conference;
import com.prodyna.pac.mmonshausen.conference.model.Location;
import com.prodyna.pac.mmonshausen.conference.model.Room;
import com.prodyna.pac.mmonshausen.conference.model.Speaker;
import com.prodyna.pac.mmonshausen.conference.model.Talk;

/**
 * holds helper methods to validate RESTServices and JSF backing bean operations
 * 
 * @author Martin Monshausen, PRODYNA AG
 */
public class InputValidator {
	
	@Inject
	private Validator validator;
	
	public void validateConference(final Conference conference) {
		final Set<ConstraintViolation<Conference>> violations = validator
				.validate(conference);

		if (!violations.isEmpty()) {
			throw new ConstraintViolationException(
					new HashSet<ConstraintViolation<?>>(violations));
		}
	}
	
	public void validateLocation(final Location location) {
		final Set<ConstraintViolation<Location>> violations = validator
				.validate(location);

		if (!violations.isEmpty()) {
			throw new ConstraintViolationException(
					new HashSet<ConstraintViolation<?>>(violations));
		}
	}
	
	public void validateRoom(final Room room) {
		final Set<ConstraintViolation<Room>> violations = validator
				.validate(room);
		
		if(!isRoomTalkValid(room)) {
			throw new ValidationException("nicht moeglich, dass mehrere Talks in Raum zur gleichen Zeit stattfinden!");
		}

		if (!violations.isEmpty()) {
			throw new ConstraintViolationException(
					new HashSet<ConstraintViolation<?>>(violations));
		}
	}
	
	private boolean isRoomTalkValid(final Room room) {
		final Set<TalkKey> talkKeys = new HashSet<TalkKey>();
		final List<Talk> talks = room.getTalks();
		for (final Talk talk : talks) {			
			final Date currentDate = talk.getDate();
			final Date currentStartTime = talk.getStartTime();
			final Date currentEndTime = talk.getEndTime();
				
			final TalkKey key = new TalkKey(currentDate, currentStartTime, currentEndTime);
				
			if(!talkKeys.contains(key)) {
				talkKeys.add(key);
			} else {
				return false;
			}
		}
		return true;
	}
	
	public void validateSpeaker(final Speaker speaker) {
		final Set<ConstraintViolation<Speaker>> violations = validator
				.validate(speaker);
		
		if(!isSpeakerTalkValid(speaker)) {
			throw new ValidationException("Speaker kann nicht mehrere Talks zur gleichen Zeit halten");
		}

		if (!violations.isEmpty()) {
			throw new ConstraintViolationException(
					new HashSet<ConstraintViolation<?>>(violations));
		}
	}

	private boolean isSpeakerTalkValid(final Speaker speaker) {	
		final Set<TalkKey> speakerTalkKeys = new HashSet<TalkKey>();
		
		final List<Talk> talks = speaker.getTalks();
		for (final Talk talk : talks) {
			final Date currentDate = talk.getDate();
			final Date currentStartTime = talk.getStartTime();
			final Date currentEndTime = talk.getEndTime();
			
			final TalkKey key = new TalkKey(currentDate, currentStartTime, currentEndTime);
			
			if(!speakerTalkKeys.contains(key)) {
				speakerTalkKeys.add(key);
			} else {
				return false;
			}
		}
		return true;
	}
	
	public void validateTalk(final Talk talk) {
		final Set<ConstraintViolation<Talk>> violations = validator
				.validate(talk);
		
		if(!isTalkInsideConference(talk)) {
			throw new ValidationException("Talk findet nicht waehrend den Zeiten der Konferenz statt");
		}

		if (!violations.isEmpty()) {
			throw new ConstraintViolationException(
					new HashSet<ConstraintViolation<?>>(violations));
		}
	}
	
	private boolean isTalkInsideConference(final Talk talk) {
		final Conference conference = talk.getConference();
		final Date conferenceStartDate = conference.getStartDate();
		final Date conferenceEndDate = conference.getEndDate();
		
		final Date talkDate = talk.getDate();
		
		final boolean onStartDate = talkDate.equals(conferenceStartDate);
		final boolean onEndeDate = talkDate.equals(conferenceEndDate);
		final boolean between = talkDate.after(conferenceStartDate) && talkDate.before(conferenceEndDate);
		
		return onStartDate || onEndeDate || between;
	}
}
