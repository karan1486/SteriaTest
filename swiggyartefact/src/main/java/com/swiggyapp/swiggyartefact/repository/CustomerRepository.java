package com.swiggyapp.swiggyartefact.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.swiggyapp.swiggyartefact.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long>{

}
