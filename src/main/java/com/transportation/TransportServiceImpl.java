package com.transportation;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TransportServiceImpl implements TransportService
{
	public static final int MAX_STOP = 15;
	public static final int MAX_DISTANCE = 2000;
	
	private TransportNetwork network;
	
	public TransportServiceImpl() 
	{
		this.network =  TransportNetwork.buildTransportNetwork(MAX_STOP, MAX_DISTANCE);
	}

	public boolean isValideRoutePath(String path)
	{
		return network.isValidedRoutePath(path);
	}

	public int getRoutePathDistance(String path) throws NoSuchRouteException
	{
		int distance = network.getDistanceOfPath(path);
		if (distance < 0)
			throw new NoSuchRouteException();

		return distance;
	}

	public Set<String> getRoutesByMaxStops(String from, String to, int maxStops) throws NoSuchRouteException
	{
		return getRoutesByStops(from, to, maxStops, false);
	}

	public Set<String> getRoutesByStops(String from, String to, int stops) throws NoSuchRouteException
	{
		return getRoutesByStops(from, to, stops, true);
	}

	private Set<String> getRoutesByStops(String from, String to, int stops, boolean needToMatchStops) throws NoSuchRouteException
	{
		network.setMaxStops(stops);
		Set<String> routes = network.getAllRouteStartWith(network.getStation(from), true);
		Set<String> validRoutes = new HashSet<String>();
		for (String tmpRoute : routes)
		{
			if (!tmpRoute.endsWith(to))
				continue;

			if (!needToMatchStops)
			{
				validRoutes.add(tmpRoute);
				continue;
			}

			if (tmpRoute.length() == stops)
			{
				validRoutes.add(tmpRoute);
			}
		}
		
		network.setMaxStops(null);

		if (validRoutes.isEmpty())
			throw new NoSuchRouteException();

		return validRoutes;
	}

	public Set<String> getRoutesByDistance(String from, String to, int maxDistance) throws NoSuchRouteException
	{
		network.setMaxDistance(maxDistance);
		Set<String> routes = network.getAllRouteStartWith(network.getStation(from), true);

		Set<String> validRoutes = new HashSet<String>();
		for (String tmpRoute : routes)
		{
			if (tmpRoute.endsWith(to))
			{
				validRoutes.add(tmpRoute);
			}
		}
		
		network.setMaxDistance(null);

		if (validRoutes.isEmpty())
			throw new NoSuchRouteException();

		return validRoutes;
	}

	public Set<String> getRoutesStartBy(String from, int maxStops, int maxDistance) throws NoSuchRouteException
	{
		network.setMaxStops(maxStops);
		network.setMaxDistance(maxDistance);
		Set<String> routes = network.getAllRouteStartWith(network.getStation(from), true);
		network.setMaxStops(null);
		network.setMaxDistance(null);
		return routes;
	}

	public String getShortestRoute(String from, String to) throws NoSuchRouteException
	{
		Set<String> routes = getRoutesByStops(from, to, -1, false);

		HashMap<String, Integer> distances = new HashMap<String, Integer>();
		for (String r : routes)
		{
			distances.put(r, network.getDistanceOfPath(r));
		}

		List<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String, Integer>>();
		list.addAll(distances.entrySet());
		
		Collections.sort(list, new Comparator<Map.Entry<String, Integer>>()
		{
			public int compare(Map.Entry obj1, Map.Entry obj2)
			{

				if (Integer.parseInt(obj1.getValue().toString()) < Integer.parseInt(obj2.getValue().toString()))
					return -1;
				if (Integer.parseInt(obj1.getValue().toString()) == Integer.parseInt(obj2.getValue().toString()))
					return 0;
				else
					return 1;
			}
		});

		String shortest = list.iterator().next().getKey();
		return shortest;
	}

	public void printAllRoutePath(Set<String> routes)
	{
		for (String path : routes)
		{
			System.out.println(path + " : " + network.getDistanceOfPath(path));
		}
	}
	
	public static void main(String[] args)
	{
		TransportService service = new TransportServiceImpl();
		try
		{
			Set<String> routes = service.getRoutesByStops("C","C",3);
			service.printAllRoutePath(routes);
		} catch (NoSuchRouteException e)
		{
			e.printStackTrace();
		}
	}

}
