package com.ftlz.spigot.antiafk;

import org.bukkit.Location;

public class FishingEvent
{
    private Location _location = null;
    private long _previousFishingEnded = 0;
    private long _currentFishingStarted = 0;

    public FishingEvent(Location location)
    {
        _location = location;
        //_eventTime = LocalDateTime.now();
    }

    public Location getLocation()
    {
        return _location;
    }

    public long getPreviousFishingEnded()
    {
        return _previousFishingEnded;
    }

    public long getCurrentFishingStarted()
    {
        return _currentFishingStarted;
    }

    public void logFishingStarted()
    {
        _currentFishingStarted = System.currentTimeMillis();
    }

    public void logFishingEnded()
    {
        _previousFishingEnded = System.currentTimeMillis();
    }

    public long getStopStartDuration()
    {
        if (_currentFishingStarted == 0 || _previousFishingEnded == 0)
        {
            return -1;
        }

        long duration = _currentFishingStarted - _previousFishingEnded;

        return duration;
	}
}