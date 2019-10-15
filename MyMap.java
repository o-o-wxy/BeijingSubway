import java.util.HashMap;
import java.util.Map;

public class MyMap {
	public static MyMap myMap = new MyMap();
	public static java.util.Map<String, Line> lineMap;
	public static java.util.Map<String, Station> stationMap;
	public static Map<Station, Boolean> visited = new HashMap<Station, Boolean>();
	public MyMap() {
		lineMap = new HashMap<String, Line>();
		stationMap = new HashMap<String, Station>();
	}
	public void setLineMap(String linName, Line line) {
		lineMap.put(linName, line);
	}
	public void setStationMap(String stationName, Station station) {
		stationMap.put(stationName, station);
		visited.put(station, false);
	}
	public Line getLine(String lineName){
		return lineMap.get(lineName);
	}
	public Station getStation(String stationName) {
		return stationMap.get(stationName);
	}
	public void visited(Station station) {
		visited.put(station, true);
	}
	public boolean isVisited(Station station) {
		return visited.get(station);
	}
}
