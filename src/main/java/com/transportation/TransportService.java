package com.transportation;

import java.util.Set;

public interface TransportService {
	
	public boolean isValideRoutePath(String path);
	
	public int getRoutePathDistance(String path) throws NoSuchRouteException;
	
	public Set<String> getRoutesByMaxStops(String from, String to, int maxStops) throws NoSuchRouteException;
	
	public Set<String> getRoutesByStops(String from, String to, int stops) throws NoSuchRouteException;
	
	public Set<String> getRoutesByDistance(String from, String to, int maxDistance) throws NoSuchRouteException;
	
	public Set<String> getRoutesStartBy(String from, int maxStops, int maxDistance) throws NoSuchRouteException;
	
	public String getShortestRoute(String from, String to) throws NoSuchRouteException;
	
	public void printAllRoutePath(Set<String> routes);

}
