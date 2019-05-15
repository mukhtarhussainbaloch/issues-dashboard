package edu.uot.issuesdashboard.controller;

import edu.uot.issuesdashboard.ResourceNotFoundException;
import edu.uot.issuesdashboard.model.Person;
import edu.uot.issuesdashboard.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class PersonController {

    @Autowired
    private PersonRepository personRepository;

    @PostMapping(value = "/people")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Person save(@RequestBody Person person){
        return personRepository.save(person);
    }

    @GetMapping(value = "/people")
    public Page<Person> getPeople(Pageable pageable){
        return personRepository.findAll(pageable);
    }

    @GetMapping(value = "/people/{personId}")
    public Person findPersonById(@PathVariable Long personId)  {
        return personRepository.findById(personId).orElseThrow(()->new ResourceNotFoundException("Person [personId="+personId+"] can't be found" ));
    }

    @PutMapping("people/{personId}")
    public Person updatePerson(@PathVariable Long personId, @Valid @RequestBody Person person){
        System.out.println("hold");

        return personRepository.findById(personId).map(person1 -> {
            person1=person;
            return personRepository.save(person1);
        }).orElseThrow(()->new ResourceNotFoundException("Person [personId="+personId+"] can't be found" ));
    }

    @DeleteMapping(value = "/people/{personId}")
    public ResponseEntity<?> deletePerson(@PathVariable Long personId)  {
        return personRepository.findById(personId).map(person -> {
            personRepository.delete(person);
            return ResponseEntity.ok().build();

                }
        ).orElseThrow(()->new ResourceNotFoundException("Person [personId="+personId+"] can't be found" ));
    }




}
