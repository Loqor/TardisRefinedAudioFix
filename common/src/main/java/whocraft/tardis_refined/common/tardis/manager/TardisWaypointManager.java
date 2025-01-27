package whocraft.tardis_refined.common.tardis.manager;

import whocraft.tardis_refined.common.capability.TardisLevelOperator;
import whocraft.tardis_refined.common.tardis.TardisNavLocation;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;

import java.util.*;

public class TardisWaypointManager {

    private final TardisLevelOperator operator;
    private Map<String, TardisNavLocation> waypointMap = new HashMap<>();
    private int flightsTaken = 0;

    public TardisWaypointManager(TardisLevelOperator tardisLevelOperator) {
        this.operator = tardisLevelOperator;
    }

    public void addWaypoint(TardisNavLocation tardisNavLocation, String name) {
        tardisNavLocation.setName(name);
        waypointMap.put(name, tardisNavLocation);
    }

    public void deleteWaypoint(String name) {
        waypointMap.remove(name);
    }

    public Collection<TardisNavLocation> getWaypoints() {
        return waypointMap.values();
    }

    public TardisNavLocation getWaypointByName(String name) {
        return waypointMap.get(name);
    }

    public boolean isCoordinateInputEnabled(){
        return flightsTaken >= 120;
    }

    public CompoundTag saveData(CompoundTag compoundTag) {
        compoundTag.putInt("flightsTaken", flightsTaken);
        ListTag waypointsList = new ListTag();
        for (TardisNavLocation location : waypointMap.values()) {
            CompoundTag locationTag = location.serialise();
            waypointsList.add(locationTag);
        }
        compoundTag.put("Waypoints", waypointsList);
        return compoundTag;
    }

    public void loadData(CompoundTag tag) {
        flightsTaken = tag.getInt("flightsTaken");
        waypointMap.clear();
        ListTag waypointsList = tag.getList("Waypoints", 10);
        for (int i = 0; i < waypointsList.size(); i++) {
            CompoundTag locationTag = waypointsList.getCompound(i);
            TardisNavLocation location = TardisNavLocation.deserialise(locationTag);
            waypointMap.put(location.getName(), location);
        }
    }
}
