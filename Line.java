import java.util.ArrayList;
import java.util.List;

public class Line {
	private String lineName;
	private List<Station> stationList;
	
	public Line() {
		// TODO Auto-generated constructor stub
		stationList = new ArrayList<Station>();
	}
	public void setLineName(String lineName) {
		this.lineName = lineName;
		MyMap.myMap.setLineMap(lineName, this);
	}
	public String getLineName() {
		return lineName;
	}
	public void addStation(Station station) {
		stationList.add(station);
	}
	public int getSize() {
		return stationList.size();
	}
	public List<Station> getStationList() {
		return stationList;
	}
	public Station getFirstStation() {
		return stationList.get(0);
	}
	public Station getLastStation() {
		return stationList.get(this.getSize()-1);
	}
	public Station getStation(int i) {
		return stationList.get(i);
	}
	
}
