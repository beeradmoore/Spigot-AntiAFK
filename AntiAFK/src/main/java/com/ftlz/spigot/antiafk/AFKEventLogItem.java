package com.ftlz.spigot.antiafk;

import com.google.gson.annotations.SerializedName;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class AFKEventLogItem
{
    enum EventType
    {
        WARNING,
        CLEAR,
        KILL
    }

    enum EventCause
    {
        AFK_FISHING
    }

    @SerializedName("name")
    private String _playerName;
    
    @SerializedName("location")
    private Location _location;

    @SerializedName("event_cause")
    private EventCause _eventCause;

    @SerializedName("event_type")
    private EventType _eventType;

    @SerializedName("time")
    private long _time;

    public AFKEventLogItem(Player player)
    {
        _playerName = player.getName();
        _location = player.getLocation();
        _time = (System.currentTimeMillis() / 1000L);
    }

    public void setEventCause(EventCause eventCause)
    {
        _eventCause = eventCause;
	}

    public void setEventType(EventType eventType)
    {
        _eventType = eventType;
	}

}