package com.prodyna.pac.mmonshausen.conference.util;

import java.util.Date;

/**
 * holder class used to check if talks are use in the context of speakers and rooms correctly
 * 
 * @author Martin Monshausen, PRODYNA AG
 */
public class TalkKey {
	private final Date date;
	private final Date startTime;
	private final Date endTime;

	public TalkKey(final Date date, final Date startTime,
			final Date endTime) {
		super();
		this.date = date;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj == null) {
			return false;
		}

		if (getClass() != obj.getClass()) {
			return false;
		}

		final TalkKey other = (TalkKey) obj;
		if (date == null) {
			if (other.date != null) {
				return false;
			}
		} else if (!date.equals(other.date)) {
			return false;
		}
		if (endTime == null) {
			if (other.endTime != null) {
				return false;
			}
		}
		if (startTime == null) {
			if (other.startTime != null)
				return false;
		}

		if ((startTime != null) && (endTime != null)) {
			final boolean sameEndTime = endTime.equals(other.endTime);
			final boolean sameStartTime = startTime.equals(other.startTime);
			final boolean overlap = (startTime.before(other.startTime) && endTime
					.after(other.endTime))
					|| (startTime.after(other.startTime) && endTime
							.before(other.endTime));

			return (sameEndTime || sameStartTime || overlap);
		} else {
			return false;
		}
	}

}
