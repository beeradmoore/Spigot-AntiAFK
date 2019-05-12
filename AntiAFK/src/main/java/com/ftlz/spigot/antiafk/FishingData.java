package com.ftlz.spigot.antiafk;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.logging.Level;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.bukkit.Location;
import org.bukkit.entity.FishHook;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class FishingData
{
    private ArrayList<FishingEvent> _fishingData = null;
    private int _flagged = 0;
    private App _app;

    public FishingData(App app)
    {
        _app = app;
        _fishingData = new ArrayList<FishingEvent>();
    }

    /*
    public void LogFishHook(FishHook hook)
    {
        if (hook == null)
            return;

            _fishin

        FishingEvent fishingEvent = new FishingEvent(hook.getLocation());
        _fishingData.add(fishingEvent);
    }
    */

    public void TrackFishingStart(Player player, FishHook hook)
    {
        if (hook == null)
            return;

        if (_fishingData.size() == 0)
        {
            return;
        }

        // Used to prune the list.
        if (_fishingData.size() > 20)
        {
            _fishingData.remove(0);
        }


        FishingEvent fishingEvent = _fishingData.get(_fishingData.size() - 1);
        if (fishingEvent == null)
        {
            return;
        }
        fishingEvent.logFishingStarted();

        long stopStartDuration = fishingEvent.getStopStartDuration();
        _app.getLogger().info("Duration: " + stopStartDuration);
        if (stopStartDuration <= 0)
        {
            // NOOP: Bad data.
        }
        else if (stopStartDuration >= 5000)
        {
            // More than 5 seconds. Reset flagged.
            _flagged = 0;
            AppendToAntiAFKlog(player, AFKEventLogItem.EventCause.AFK_FISHING, AFKEventLogItem.EventType.CLEAR);
        }
        else if (stopStartDuration < 500)
        {
            ++_flagged;

            if (_flagged > 20)
            {
                AppendToAntiAFKlog(player, AFKEventLogItem.EventCause.AFK_FISHING, AFKEventLogItem.EventType.KILL);
                // Kill the player.
                player.setHealth(0);
                player.sendMessage("" + ChatColor.RED + "" + ChatColor.BOLD + "AntiAFK: " + ChatColor.RESET + "" + ChatColor.RED + "You have been killed for AFK fishing.");
                _flagged = 0;
                _fishingData.clear();
                
            }
            else if (_flagged > 10)
            {
                player.sendMessage("" + ChatColor.RED + "" + ChatColor.BOLD + "AntiAFK: " + ChatColor.RESET + "" + ChatColor.RED + "Please slow your fishing.");
                AppendToAntiAFKlog(player, AFKEventLogItem.EventCause.AFK_FISHING, AFKEventLogItem.EventType.WARNING);
            }
        }
	}

    public void TrackFishingEnd(Player player, FishHook hook)
    {
        if (hook == null)
            return;

        FishingEvent fishingEvent =  new FishingEvent(player.getLocation());
        fishingEvent.logFishingEnded();
        _fishingData.add(fishingEvent);
    }
    

    public void AppendToAntiAFKlog(Player player, AFKEventLogItem.EventCause eventCause, AFKEventLogItem.EventType eventType)
    {
        FileOutputStream fileOutputStream = null;
        try
        {
            _app.getLogger().info(player.getName() + ": " + eventCause + " (" + eventType +")");

            final GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(Location.class, new LocationAdapter());
            final Gson gson = gsonBuilder.create();
            AFKEventLogItem eventLogItem = new AFKEventLogItem(player);
            eventLogItem.setEventCause(eventCause);
            eventLogItem.setEventType(eventType);
            
            String data = gson.toJson(eventLogItem);
        
            fileOutputStream = new FileOutputStream("AntiAFK.jsonl", true);

            java.nio.channels.FileLock lock = fileOutputStream.getChannel().lock();
            try
            {
                fileOutputStream.write((data + "\n").getBytes("utf-8"));
            }
            catch (Exception err)
            {
                _app.getLogger().log(Level.WARNING, err.getMessage());
            }
            finally
            {
                lock.release();
                fileOutputStream.close();
                fileOutputStream = null;
            }
        }
        catch (Exception err)
        {
            _app.getLogger().log(Level.WARNING, err.getMessage());
        }
        finally
        {
            if (fileOutputStream != null)
            {
                try
                {
                    fileOutputStream.close();
                }
                catch (Exception err)
                {
                    _app.getLogger().log(Level.WARNING, err.getMessage());
                }
            }
        }
    } 
}