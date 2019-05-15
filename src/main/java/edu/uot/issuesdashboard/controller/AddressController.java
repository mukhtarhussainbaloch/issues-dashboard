package edu.uot.issuesdashboard.controller;

import edu.uot.issuesdashboard.ResourceNotFoundException;
import edu.uot.issuesdashboard.model.Address;
import edu.uot.issuesdashboard.model.Person;
import edu.uot.issuesdashboard.repository.AddressRepository;
import edu.uot.issuesdashboard.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class AddressController {
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private AddressRepository addressRepository;

    @PostMapping(value = "/people/{personId}/addresses")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Address save(@RequestBody Address address, @PathVariable Long personId){
        return personRepository.findById(personId).map(person -> {
            address.setPerson(person);
            return addressRepository.save(address);
        }).orElseThrow(()->new ResourceNotFoundException("Person [personId="+personId+"] can't be found" ));
    }

    @GetMapping(value = "/people/{personId}/addresses")
    public Page<Address> getPersonAddresses(@PathVariable Long personId, Pageable pageable){
       return addressRepository.findByPersonID(personId,pageable);
    }

    @GetMapping(value = "/people/{personId}/addresses/{addressId}")
    public Person findAddressById(@PathVariable Long personId)  {

        return personRepository.findById(personId).orElseThrow(()->new ResourceNotFoundException("Person [personId="+personId+"] can't be found" ));
    }

    @PutMapping("/people/{personId}/addresses/{addressId}")
    public Address updateAddress(@PathVariable Long personId,@PathVariable Long addressId,@Valid  @RequestBody Address address){
        if (!personRepository.existsById(personId)){
            throw new ResourceNotFoundException("Person [personId="+personId+"] can't be found" );
        }
        return addressRepository.findById(addressId).map(address1 -> {
            address1=address;
            return addressRepository.save(address1);
        }).orElseThrow(()->new ResourceNotFoundException("Person [personId="+personId+"] can't be found" ));
    }

    @DeleteMapping(value = "/people/{personId}/{addressId}")
    public ResponseEntity<?> deleteAddress(@PathVariable Long personId)  {
        return personRepository.findById(personId).map(person -> {
            personRepository.delete(person);
            return ResponseEntity.ok().build();

                }
        ).orElseThrow(()->new ResourceNotFoundException("Person [personId="+personId+"] can't be found" ));
    }




}
