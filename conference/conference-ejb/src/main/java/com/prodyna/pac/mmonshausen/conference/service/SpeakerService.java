package com.prodyna.pac.mmonshausen.conference.service;

import java.util.Date;
import java.util.List;

import com.prodyna.pac.mmonshausen.conference.model.Speaker;

/**
 * 
 * @author Martin Monshausen, PRODYNA AG
 */
public interface SpeakerService {
	public Speaker saveSpeaker(Speaker speaker);
	
	public Speaker getSpeakerById(long id);
	public List<Speaker> getSpeakerByDate(Date date);
	public List<Speaker> listAllSpeakers();
	
	public Speaker updateSpeaker(Speaker speaker);
	
	public void deleteSpeaker(long id);
}
