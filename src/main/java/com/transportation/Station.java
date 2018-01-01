package com.transportation;

import java.util.ArrayList;
import java.util.List;

public class Station
{

	private String stationName;
	private String label;

	private List<Route> routes;

	public String getStationName()
	{
		return stationName;
	}

	public void setStationName(String stationName)
	{
		this.stationName = stationName;
	}

	public String getLabel()
	{
		return label;
	}

	public void setLabel(String label)
	{
		this.label = label;
	}

	public List<Route> getRoutes()
	{
		return routes;
	}

	public void setRoutes(List<Route> routes)
	{
		this.routes = routes;
	}

	public Station()
	{
	}

	public Station(String label, String stationName)
	{
		this.label = label;
		this.stationName = stationName;
		this.routes = new ArrayList<Route>();
	}

}
