package com.prodyna.pac.mmonshausen.conference.service;

import java.util.Date;
import java.util.List;

import com.prodyna.pac.mmonshausen.conference.model.Talk;

/**
 * 
 * @author Martin Monshausen, PRODYNA AG
 */
public interface TalkService {
	public Talk createTalk(Talk talk);
	
	public Talk getTalkById(long id);
	public List<Talk> getTalksByDate(Date date);
	public List<Talk> listAllTalks();

	public Talk updateTalk(Talk talk);
	
	public void deleteTalk(long id);
}