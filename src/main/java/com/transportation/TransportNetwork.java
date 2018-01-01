package com.transportation;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

public class TransportNetwork {

	private Map<String, Station> stations;
	private Map<Route, Integer> routeMap;
	
	private static Integer MAX_DISTANCE = 2000;
	private static Integer MAX_STOPS = 15;

	private Integer maxDistance;
	private Integer maxStops;

	private Set<String> nonCycleRoutePaths;
	
	public Integer getMaxDistance() {
		return maxDistance;
	}

	public void setMaxDistance(Integer maxDistance) {
		if (maxDistance == null || maxDistance <= 0) {
			this.maxDistance = MAX_DISTANCE;
		} else {
			this.maxDistance = maxDistance;
		}
	}

	public Integer getMaxStops() {
		return maxStops;
	}

	public void setMaxStops(Integer maxStops) {
		if (maxStops == null || maxStops <= 0) {
			this.maxStops = MAX_STOPS;
		} else {
			this.maxStops = maxStops;
		}
	}

	public TransportNetwork() {
		stations = new HashMap<>();
		routeMap = new HashMap<>();
		nonCycleRoutePaths = new HashSet<String>();
	}
	
	public TransportNetwork(int maxStops, int maxDistance) {
		this();
		this.setMaxStops(maxStops);
		this.setMaxDistance(maxDistance);
	}

	public Station getStation(String label) {
		return stations.get(label);
	}

	public Set<String> getAllRouteStartWith(Station station, boolean couldHaveCycle) {
		LinkedList<String> visitedStations = new LinkedList<>();
		Set<String> routePaths = new HashSet<>();
		return getAllRouteStartAt(station, couldHaveCycle, visitedStations, routePaths);
	}

	private Set<String> getAllRouteStartAt(Station station, boolean couldHaveCycle, LinkedList<String> visitedStations,
			Set<String> routePaths) {
		if (!couldHaveCycle) {
			if (visitedStations.contains(station.getLabel())) {
				visitedStations.addFirst(station.getLabel());
				visitedStations.pop();
				return routePaths;
			}
		}

		visitedStations.addFirst(station.getLabel());

		if (station.getRoutes().isEmpty()) {
			visitedStations.pop();
			return routePaths;
		}

		String currentPath = getCurrentRoutePath(visitedStations);
		if (currentPath.length() > 1)
			routePaths.add(currentPath);

		if (getDistanceOfPath(currentPath) >= Math.min(MAX_DISTANCE,
				this.getMaxDistance() == null ? MAX_DISTANCE : this.getMaxDistance())) {
			routePaths.remove(currentPath);
			visitedStations.pop();
			return routePaths;
		}

		if (visitedStations.size() - 1 >= Math.min(MAX_STOPS,
				this.getMaxStops() == null ? MAX_STOPS : this.getMaxStops())) {
			routePaths.remove(currentPath);
			visitedStations.pop();
			return routePaths;
		}

		for (Route route : station.getRoutes()) {
			getAllRouteStartAt(getStation(route.getTravelTo()), couldHaveCycle, visitedStations, routePaths);
		}

		visitedStations.pop();
		return routePaths;
	}

	private String getCurrentRoutePath(LinkedList<String> visitedStations) {
		StringBuffer path = new StringBuffer();
		for (int i = visitedStations.size(); i > 0; i--) {
			path.append(visitedStations.get(i - 1));
		}

		return path.toString();
	}

	public int getDistanceOfPath(String path) {
		int distance = 0;
		path = path.replace("-", "");
		if (!isValidedRoutePath(path))
			return -1;

		String start = path.substring(0, 1);
		String end = "";
		for (int j = 1; j < path.length(); j++) {
			end = path.substring(j, j + 1);
			Route r = new Route(start, end);
			distance = distance + getDistanceOfRoute(r);
			start = end;
		}

		return distance;
	}

	private Integer getDistanceOfRoute(Route route) {
		if (!this.routeMap.containsKey(route)) return 0;
		
		return this.routeMap.get(route);
	}

	public boolean isValidedRoutePath(String path) {
		if (path.isEmpty())
			return true;
		boolean isExist = false;
		path = path.replace("-", "");
		for (String existedPath : nonCycleRoutePaths) {
			if (path.length() <= existedPath.length()) {
				if (existedPath.contains(path)) {
					isExist = true;
					break;
				}
			} else {
				if (path.indexOf(existedPath) != 0)
					continue;

				path = path.substring(existedPath.length() - 1);
				return isValidedRoutePath(path);
			}
		}

		return isExist;
	}

	public void initTransportNetwork() {
		if (stations.isEmpty())
			return;

		if (routeMap.isEmpty())
			return;

		initRoutes();
		initAllStation();
	}

	public void addStation(String label, String stationName) {
		stations.put(label, new Station(label, stationName));
	}

	public void addRoute(String from, String to, Integer distance) {
		routeMap.put(new Route(from, to, distance), distance);
	}

	private void initAllStation() {
		for (String label : stations.keySet()) {
			Set<String> routes = getAllRouteStartWith(getStation(label), false);
			nonCycleRoutePaths.addAll(routes);
		}
	}

	private void initRoutes() {

		for (Route route : routeMap.keySet()) {
			String from = route.getTravelFrom();
			String to = route.getTravelTo();

			if (!isValidateRoute(from, to))
				continue;

			stations.get(from).getRoutes().add(route);
		}

	}

	public static TransportNetwork buildTransportNetwork(int maxStops, int maxDistance) {
		TransportNetwork network = new TransportNetwork(maxStops, maxDistance);
		network.addStation("A", "Station A");
		network.addStation("B", "Station B");
		network.addStation("C", "Station C");
		network.addStation("D", "Station D");
		network.addStation("E", "Station E");

		network.addRoute("A", "B", 5);
		network.addRoute("A", "D", 5);
		network.addRoute("A", "E", 7);
		network.addRoute("B", "C", 4);
		network.addRoute("C", "D", 8);
		network.addRoute("C", "E", 2);
		network.addRoute("D", "C", 8);
		network.addRoute("D", "E", 6);
		network.addRoute("E", "B", 3);

		network.initTransportNetwork();

		return network;
	}

	private boolean isValidateRoute(String from, String to) {
		return stations.containsKey(from) && stations.containsKey(to);
	}

	public void printAllRoutePath(Set<String> routes) {
		for (String path : routes) {
			System.out.println(path + " : " + getDistanceOfPath(path));
		}
	}

	public static void main(String[] args) {
		int maxStops = 15;
		int maxDistance = 2000;
		TransportNetwork network = buildTransportNetwork(maxStops, maxDistance);
		
		network.setMaxStops(6);

		Set<String> list = network.getAllRouteStartWith(network.getStation("C"), true);
		network.printAllRoutePath(list);
	}
}
