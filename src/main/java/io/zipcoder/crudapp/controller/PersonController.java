package io.zipcoder.crudapp.controller;

import io.zipcoder.crudapp.model.Person;
import io.zipcoder.crudapp.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class PersonController {

    private PersonRepository repo;

    @Autowired
    public PersonController(PersonRepository repo){
        this.repo = repo;
    }

    @RequestMapping(value = "/people", method = RequestMethod.POST)
    public ResponseEntity<Person> createPerson(@RequestBody Person person){
        return new ResponseEntity<>(repo.save(person), HttpStatus.CREATED);
    }
    @RequestMapping(value = "/people/{id}", method = RequestMethod.GET)
    public ResponseEntity<Optional<Person>> getPerson(@PathVariable Integer id){
        return new ResponseEntity<>(repo.findById(id), HttpStatus.OK);
    }
    @RequestMapping(value = "/people", method = RequestMethod.GET)
    public ResponseEntity<Iterable<Person>> getPerson(){
        return new ResponseEntity<>(repo.findAll(), HttpStatus.OK);
    }

    @RequestMapping(value = "/people/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Person> update(@PathVariable Integer id, @RequestBody Person p) {
        if (repo.existsById(id)) {
            Optional<Person> person = repo.findById(id);
            Person updatePerson = person.get();

            updatePerson.setId(p.getId());
            updatePerson.setFirstName(p.getFirstName());
            updatePerson.setLastName(p.getLastName());

            return new ResponseEntity<>(repo.save(updatePerson), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(repo.save(p), HttpStatus.CREATED);
        }
    }
    @RequestMapping(value = "/people/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deletePerson (@PathVariable Integer id) {
        repo.deleteById(id);
        return null;
    }
}
