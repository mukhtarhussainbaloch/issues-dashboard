package edu.uot.issuesdashboard.repository;

import edu.uot.issuesdashboard.model.Person;
import org.springframework.data.repository.PagingAndSortingRepository;


public interface PersonRepository extends PagingAndSortingRepository<Person,Long> {

//    Page<Person> findByLastName(@Param("name") String name);

}
