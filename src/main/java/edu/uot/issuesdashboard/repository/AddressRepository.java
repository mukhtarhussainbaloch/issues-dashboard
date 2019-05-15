package edu.uot.issuesdashboard.repository;

import edu.uot.issuesdashboard.model.Address;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface AddressRepository extends JpaRepository<Address,Long> {

    Page<Address> findByPersonID(Long personId, Pageable pageable);

//    Optional<Address> findByIDAndPersonID(Long id, Long personId);
}
