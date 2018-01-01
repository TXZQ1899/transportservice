package com.transportation;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TransportServiceImplTest
{
	
	private TransportService service;
	
	@Rule
    public ExpectedException thrown = ExpectedException.none();

	@Before
	public void setUp() throws Exception
	{
		service = new TransportServiceImpl();
	}

	@Test
	public void testIsValideRoutePath()
	{
		assertEquals(true, service.isValideRoutePath("C-D-C"));
	}

	@Test
	public void testGetRoutePathDistance5() throws NoSuchRouteException
	{
		thrown.expect(NoSuchRouteException.class);
        thrown.expectMessage("the route path is un-reachable");

		service.getRoutePathDistance("A-E-D");
	}
	
	@Test
	public void testGetRoutePathDistance4() throws NoSuchRouteException
	{
		assertEquals(22, service.getRoutePathDistance("A-E-B-C-D"));
	}
	
	@Test
	public void testGetRoutePathDistance3() throws NoSuchRouteException
	{
		assertEquals(13, service.getRoutePathDistance("A-D-C"));
	}
	
	@Test
	public void testGetRoutePathDistance2() throws NoSuchRouteException
	{
		assertEquals(5, service.getRoutePathDistance("A-D"));
	}
	
	@Test
	public void testGetRoutePathDistance1() throws NoSuchRouteException
	{
		assertEquals(9, service.getRoutePathDistance("A-B-C"));
	}

	@Test
	public void testGetRoutesByMaxStops() throws NoSuchRouteException
	{
		assertEquals(1, service.getRoutesByMaxStops("C", "C", 3).size());
	}

	@Test
	public void testGetRoutesByStops() throws NoSuchRouteException
	{
		assertEquals(1, service.getRoutesByStops("C", "C", 3).size());
	}

	@Test
	public void testGetRoutesByDistance() throws NoSuchRouteException
	{
		assertEquals(7, service.getRoutesByDistance("C", "C", 30).size());
	}
	
	@Test
	public void testGetShortestRoutes1() throws NoSuchRouteException
	{
		assertEquals(9, service.getRoutePathDistance(service.getShortestRoute("A", "C")));
	}

	@Test
	public void testGetShortestRoutes2() throws NoSuchRouteException
	{
		assertEquals(9, service.getRoutePathDistance(service.getShortestRoute("B", "B")));
	}

}
