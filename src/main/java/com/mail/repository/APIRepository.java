package com.mail.repository;

import org.springframework.data.repository.CrudRepository;

import com.mail.model.APIDetails;

public interface APIRepository extends CrudRepository<APIDetails, String> {

}
