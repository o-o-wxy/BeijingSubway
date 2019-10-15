import java.util.ArrayList;
import java.util.List;

public class Station {
	private String stationName;
	private List<Line> lineList;
	private List<Station> preStation;
	private List<Station> postStation;
	private Station parent;
	public List<Line> getLineList() {
		return lineList;
	}
	public Station() {
		preStation = new ArrayList<Station>();
		postStation = new ArrayList<Station>();
		lineList = new ArrayList<Line>();
	}
	public void setStationName(String stationName) {
		this.stationName = stationName;;
		MyMap.myMap.setStationMap(stationName, this);
	}
	public String getStationName() {
		return stationName;
	}
	public void setLine(Line line) {
		lineList.add(line);
	}
	public int getLineNum() {
		return lineList.size();
	}
	public List<Station> getPostStation() {
		return postStation;
	}
	public List<Station> getPreStation() {
		return preStation;
	}
	public void addPreStation(Station station) {
		preStation.add(station);
	}
	public void addPostStation(Station station) {
		postStation.add(station);
	}
	public void setParent(Station parent) {
		this.parent = parent;
	}
	public Station getParent() {
		return parent;
	}
}
