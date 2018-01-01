package com.transportation;

public class Route
{

	private String travelFrom;

	private String travelTo;

	private int distance;

	public String getTravelFrom()
	{
		return travelFrom;
	}

	public void setTravelFrom(String travelFrom)
	{
		this.travelFrom = travelFrom;
	}

	public String getTravelTo()
	{
		return travelTo;
	}

	public void setTravelTo(String travelTo)
	{
		this.travelTo = travelTo;
	}

	public int getDistance()
	{
		return distance;
	}

	public void setDistance(int distance)
	{
		this.distance = distance;
	}

	public Route(String travelFrom, String travelTo, int distance)
	{
		this.travelFrom = travelFrom;
		this.travelTo = travelTo;
		this.distance = distance;
	}
	
	public Route(String travelFrom, String travelTo)
	{
		this.travelFrom = travelFrom;
		this.travelTo = travelTo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((travelFrom == null) ? 0 : travelFrom.hashCode());
		result = prime * result + ((travelTo == null) ? 0 : travelTo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Route other = (Route) obj;
		if (travelFrom == null) {
			if (other.travelFrom != null)
				return false;
		} else if (!travelFrom.equals(other.travelFrom))
			return false;
		if (travelTo == null) {
			if (other.travelTo != null)
				return false;
		} else if (!travelTo.equals(other.travelTo))
			return false;
		return true;
	}
	
	

}
