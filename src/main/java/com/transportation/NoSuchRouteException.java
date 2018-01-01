package com.transportation;

public class NoSuchRouteException extends Exception
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 807552044249857879L;

	public NoSuchRouteException()
	{
		super("the route path is un-reachable");
	}

}
