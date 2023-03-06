package com.person.rest;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/person")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PersonResource {

    @Inject
    PersonRepository personRepository;

    @GET
    public List<Person> getAll() {
        return personRepository.listAll();
    }

    @GET
    @Path("/{id}")
    public Person get(@PathParam("id") Long id) {
        return personRepository.findById(id);
    }

    @POST
    @Transactional
    public Response create(Person person) {
        personRepository.persist(person);
        return Response.ok(person).status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response update(@PathParam("id") Long id, Person person) {
        Person p = personRepository.findById(id);
        if (p == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        p.setName(person.getName());
        p.setAddress(person.getAddress());
        p.setEmail(person.getEmail());
        p.setPhone(person.getPhone());
        p.setAge(person.getAge());
        personRepository.persist(p);
        return Response.ok(p).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response delete(@PathParam("id") Long id) {
        Person p = personRepository.findById(id);
        if (p == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        personRepository.delete(p);
        return Response.noContent().build();
    }

}