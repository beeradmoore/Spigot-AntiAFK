package com.ftlz.spigot.antiafk;

import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerFishEvent.State;

public class AFKListener implements Listener
{
    private ConcurrentHashMap<String, FishingData> _fishingLog;
    private App _app;

    public AFKListener(App app)
    {
        _app = app;
        _fishingLog = new ConcurrentHashMap<String, FishingData>();
    }

    @EventHandler
    public void onFish(PlayerFishEvent event)
    {
        //long currentTime = java.lang.System.currentTimeMillis();
        //_app.getLogger().log(Level.INFO, event.getEventName());
        //_app.getLogger().log(Level.INFO, event.getState().toString());
        //_app.getLogger().log(Level.INFO, "" + currentTime);
        //_app.getLogger().log(Level.INFO, "" + (currentTime - _previousTime));
        //_previousTime = currentTime;
    
        Player player = event.getPlayer();
        String playerName = player.getName();

        if (event.getState() == State.FISHING)
        {
            FishingData fishingData = _fishingLog.get(playerName);
            if (fishingData == null)
            {
                fishingData = new FishingData(_app);
                _fishingLog.put(playerName, fishingData);
            }

            fishingData.TrackFishingStart(player, event.getHook());   
        }
        else if (event.getState() == State.CAUGHT_FISH)
        {            
            FishingData fishingData = _fishingLog.get(playerName);
            if (fishingData == null)
            {
                fishingData = new FishingData(_app);
                _fishingLog.put(playerName, fishingData);       
            }

            fishingData.TrackFishingEnd(player, event.getHook());
        }
        

        /*
        Entity caught = event.getCaught();
        

        if (caught != null)
        {
            _app.getLogger().log(Level.INFO, caught.getName());
            
            Item item = (Item)caught;
            
            if (item != null)
            {
                _app.getLogger().log(Level.INFO, item.getName());
            }
            else
            {
                _app.getLogger().log(Level.INFO, "Item is null");
            }
        }
        else
        {
            _app.getLogger().log(Level.INFO, "caught is null");
        }
        
        FishHook hook = event.getHook();
        if (hook != null)
        {
            Location location = event.getHook().getLocation();
            BoundingBox boundingBox = event.getHook().getBoundingBox();

            _app.getLogger().log(Level.INFO, location.toString());
            _app.getLogger().log(Level.INFO, boundingBox.toString());
        }
        else
        {
            _app.getLogger().log(Level.INFO, "hook is null");
        }
        _app.getLogger().log(Level.INFO, "");

        boolean isAFKFishing = true;

        if (isAFKFishing)
        {
            if (event.getState() == State.FISHING)
            {
                // This correclty prevents a player casting a fishing rod. Useful for fishing ban.
                //event.setCancelled(true);
            }

            if (event.getState() == State.CAUGHT_FISH)
            {
                // No XP farm either.
                event.setExpToDrop(0);
                
                // This appears to delete the item as expected.
                if (caught != null)
                {
                    caught.remove();
                }

                // Don't call cancelled. Will be in a loop of catching items. 
                //event.setCancelled(true);

                // LOL
                //Player player = event.getPlayer();
                //player.getWorld().createExplosion(player.getLocation(),  4F, true);
            }


        }
        */


        // Best bet is to calculate time between CAUGHT_FISH and FISHING, relate that to position in the world. 
        /*
        Order of events
        [20:48:20] PlayerFishEvent
        [20:48:20] FISHING
        [20:48:20] caught is null
        [20:48:20] Location{world=CraftWorld{name=world},x=204.56952300365518,y=64.49500000476837,z=-253.7142934487591,pitch=-17.777674,yaw=-178.72723}
        [20:48:20] BoundingBox [minX=204.44452300365518, minY=64.49500000476837, minZ=-253.8392934487591, maxX=204.69452300365518, maxY=64.74500000476837, maxZ=-253.5892934487591]
        [20:48:20] 
        [20:48:49] PlayerFishEvent
        [20:48:49] BITE
        [20:48:49] caught is null
        [20:48:49] Location{world=CraftWorld{name=world},x=204.54578116825027,y=63.90826559018435,z=-254.25,pitch=-32.59311,yaw=-2.8E-45}
        [20:48:49] BoundingBox [minX=204.42078116825027, minY=63.90826559018435, minZ=-254.375, maxX=204.67078116825027, maxY=64.15826559018436, maxZ=-254.125]
        [20:48:49] 
        [20:48:49] PlayerFishEvent
        [20:48:49] CAUGHT_FISH
        [20:48:49] Bow
        [20:48:49] Bow
        [20:48:49] Location{world=CraftWorld{name=world},x=204.54578116825027,y=62.91058516259397,z=-254.25,pitch=-71.1889,yaw=-2.8E-45}
        [20:48:49] BoundingBox [minX=204.42078116825027, minY=62.91058516259397, minZ=-254.375, maxX=204.67078116825027, maxY=63.16058516259397, maxZ=-254.125]
        [20:48:49] 
        [20:48:50] PlayerFishEvent
        [20:48:50] FISHING
        [20:48:50] caught is null
        [20:48:50] Location{world=CraftWorld{name=world},x=204.57265761890883,y=64.49500000476837,z=-253.71434619886972,pitch=-15.153469,yaw=-179.33961}
        [20:48:50] BoundingBox [minX=204.44765761890883, minY=64.49500000476837, minZ=-253.83934619886972, maxX=204.69765761890883, maxY=64.74500000476837, maxZ=-253.58934619886972]
        [20:48:50] 
        */

    }
}