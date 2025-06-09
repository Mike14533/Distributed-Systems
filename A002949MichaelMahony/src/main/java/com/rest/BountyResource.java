package com.rest;



import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import backend.Bounty;
import backend.BountyDao;



@Path("/bountys")
public class BountyResource {
	
	@GET
	@Produces( {MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON} )
	public List<Bounty> getBountys(){
		return BountyDao.instance.getBountys();
	}
	
	@GET
	@Produces( {MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON} )
	@Path("{bountyId}")
	public Bounty getBounty(@PathParam("bountyId") String id){
		return BountyDao.instance.getBounty(Integer.parseInt(id));
	}
	
	@POST
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void addBounty(
			@FormParam("name") String name, 
			@FormParam("crew") String crew, 
			@FormParam("bounty") int bounty,
			@FormParam("position") String position,
			@FormParam("age") int age,
			@FormParam("gender") String gender,
			@Context HttpServletResponse servletResponse) throws IOException {
		Bounty newBounty = new Bounty();
		newBounty.setName(name);
		newBounty.setCrew(crew);
		newBounty.setBounty(bounty);
		newBounty.setPosition(position);
		newBounty.setAge(age);
		newBounty.setGender(gender);
		BountyDao.instance.addBounty(newBounty);
		servletResponse.sendRedirect("../createBounty.html");
	
	}
	@PUT
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("{bountyId}")
	public void putBounty(
			@PathParam("bountyId") int id,
			
			@FormParam("name") String name, 
			@FormParam("crew") String crew, 
			@FormParam("bounty") int bounty,
			@FormParam("position") String position,
			@FormParam("age") int age,
			@FormParam("gender") String gender,
			@Context HttpServletResponse servletResponse) throws IOException {
		Bounty newBounty = new Bounty();
		newBounty.setName(name);
		newBounty.setCrew(crew);
		newBounty.setBounty(bounty);
		newBounty.setPosition(position);
		newBounty.setAge(age);
		newBounty.setGender(gender);
		BountyDao.instance.updateBounty(newBounty);
		servletResponse.sendRedirect("../createBounty.html");
	}
	@DELETE
	@Path("{bountyId}")
	public Bounty removeBounty(@PathParam("bountyId") String id) {
		return BountyDao.instance.deleteBounty(Integer.parseInt(id));
	}
	
}

