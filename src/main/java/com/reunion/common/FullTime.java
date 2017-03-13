package com.reunion.common;

/**
 * @author a.horoz
 *
 */
public class FullTime
{
	private long diffSeconds;
	private long diffMinutes;
	private long diffHours;
	private long diffDays;

	/**
	 * @param diffSeconds
	 * @param diffMinutes
	 * @param diffHours
	 * @param diffDays
	 */
	public FullTime(long diffDays, long diffHours, long diffMinutes, long diffSeconds)
	{
		this.diffSeconds = diffSeconds;
		this.diffMinutes = diffMinutes;
		this.diffHours = diffHours;
		this.diffDays = diffDays;
	}

	public long getDiffSeconds()
	{
		return diffSeconds;
	}

	public void setDiffSeconds(long diffSeconds)
	{
		this.diffSeconds = diffSeconds;
	}

	public long getDiffMinutes()
	{
		return diffMinutes;
	}

	public void setDiffMinutes(long diffMinutes)
	{
		this.diffMinutes = diffMinutes;
	}

	public long getDiffHours()
	{
		return diffHours;
	}

	public void setDiffHours(long diffHours)
	{
		this.diffHours = diffHours;
	}

	public long getDiffDays()
	{
		return diffDays;
	}

	public void setDiffDays(long diffDays)
	{
		this.diffDays = diffDays;
	}

}
