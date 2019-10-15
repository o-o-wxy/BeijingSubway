import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class Subway {

	public static void main(String[] args) {
		if(args.length == 2 && args[0].equals("-map")) {//需求1 读取内容
			try {
				readText(args[1]);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//需求2 读取内容，输出站点信息
		else if(args.length == 6 && args[0].equals("-a") && args[2].equals("-map") && args[4].equals("-o")) {
			try {
				readText(args[3]);
				printStationList(args[1], args[5]);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//需求2 读取内容，输出站点间的最短路径
		else if(args.length == 7 && args[0].equals("-b") && args[3].equals("-map") && args[5].equals("-o")) {
			try {
				readText(args[4]);
				printShortestLine(args[1], args[2], args[6]);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else {
			System.out.println("错误命令！");
			System.out.println("正确命令格式：");
			System.out.println("导入站点信息：java subway -map subway.txt");
			System.out.println("查询线路信息：java subway -a 1号线 -map subway.txt -o station.txt");
			System.out.println("查询站点间最短路程：java subway -b 站点1 站点2 -map subway.txt -o routine.txt");
			System.exit(0);
		}
		
	}
	public static void readText(String fileName) throws IOException {
		File file = new File(fileName); 
		InputStreamReader reader = new InputStreamReader(
				new FileInputStream(file));
		BufferedReader br = new BufferedReader(reader); 
		String content = null;
		content = br.readLine();
		
		while (content != null) {
			String[] contents=content.split(",");
			Line line = new Line();
			line.setLineName(contents[0]);
			Station station = null;
			for (int i = 1; i < contents.length; i++) {
				if(!MyMap.stationMap.containsKey(contents[i])) {
					station = new Station();
					station.setStationName(contents[i]);
				}
				station = MyMap.myMap.getStation(contents[i]);
				station.setLine(line);
				line.addStation(station);
				//指向前一站
				if(i!=1) {
				//	System.out.println(line.getStation(i-2).getStationName()+"->"+station.getStationName());
					station.addPreStation(line.getStation(i-2));
					//前一站指向后一站
					line.getStation(i-2).addPostStation(station);
				}
				else {
					station.addPreStation(null);
				}
				//最后一站指向空
				if(i==contents.length-1) {
					station.addPostStation(null);
				}
			}
			content = br.readLine(); // 一次读入一行数据
		}
		br.close();
		
	}
	public static void printStationList(String lineName, String fileName) throws IOException {
		File writename = new File(fileName); // 相对路径
		writename.createNewFile(); // 创建新文件
		BufferedWriter out = new BufferedWriter(new FileWriter(writename));
		Line line = MyMap.myMap.getLine(lineName);
		System.out.println(lineName+":");
		out.write(lineName+"\r\n"); // \r\n为换行
		out.flush(); // 把缓存区内容压入文件
		for (Station station : line.getStationList()) {
			System.out.println(station.getStationName());
			out.write(station.getStationName()+"\r\n"); // \r\n为换行
			out.flush(); // 把缓存区内容压入文件
		}
		out.close();
	}
	
	public static void bfs(Station start, Station end) {
		Queue<Station> queue = new LinkedList<Station>();
		queue.add(start);
		while(!queue.isEmpty()) {
			Station cur = queue.poll();
			MyMap.myMap.visited(cur);
			if(cur == end)
				break;
			List<Station> list = new ArrayList<Station>();
			//System.out.println(cur.getStationName());
			list.addAll(cur.getPreStation());
			//System.out.println(cur);
			list.addAll(cur.getPostStation());
//			for(int i=0; i<list.size();i++) {
//				System.out.println(list.get(i).getStationName());
//			}
			for(Station station : list) {
				if(station!=null&&!MyMap.myMap.isVisited(station)) {
					station.setParent(cur);
					queue.add(station);
				}
			}
			
		}
		
		
	}
	//输出最短路径
	public static void printShortestLine(String startStation, String endStation, String fileName) throws IOException {
		File writename = new File(fileName); // 相对路径
		writename.createNewFile(); // 创建新文件
		BufferedWriter out = new BufferedWriter(new FileWriter(writename));
		Station start = MyMap.myMap.getStation(startStation);
		Station end = MyMap.myMap.getStation(endStation);
		bfs(start,end);
		Stack<Station> stationStack = new Stack<Station>();
		Stack<Line> lineStack = new Stack<Line>();
		Station cur=end;
		while(cur!=null) {
			stationStack.push(cur);
			List<Line> line = new ArrayList<Line>();
			line.addAll(cur.getLineList());//list拷贝
			//System.out.println(cur.getStationName());
			//System.out.println(cur.getParent().getStationName());
			cur = cur.getParent();
			if(cur==null)
				break;
			line.retainAll(cur.getLineList());//取交集
			lineStack.push(line.get(0));//默认交集只有一个
		}
		Line preLine = lineStack.peek();
		Line curLine = null;
		while(!stationStack.isEmpty()) {
			Station station = stationStack.pop();
			out.write(station.getStationName()+"  "); // \r\n为换行
			out.flush(); // 把缓存区内容压入文件
			System.out.print(station.getStationName());
			if(!lineStack.empty()) {
				curLine = lineStack.pop();
				if(curLine!=preLine) {
					System.out.print(curLine.getLineName());
					out.write(curLine.getLineName()); // \r\n为换行
					out.flush(); // 把缓存区内容压入文件
				}
				preLine = curLine;
			}
			out.write("\r\n"); // \r\n为换行
			out.flush(); // 把缓存区内容压入文件
			System.out.print("\n");
			
		}
		out.close();
	}
}
