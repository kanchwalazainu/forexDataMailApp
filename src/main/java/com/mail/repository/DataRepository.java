package com.mail.repository;

import org.springframework.data.repository.CrudRepository;

import com.mail.model.DataDetails;

public interface DataRepository extends CrudRepository<DataDetails, String> {
	

}
