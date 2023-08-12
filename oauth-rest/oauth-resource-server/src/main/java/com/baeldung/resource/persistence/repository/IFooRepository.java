package com.baeldung.resource.persistence.repository;

import org.springframework.data.repository.CrudRepository;

import com.baeldung.resource.persistence.model.Foo;

public interface IFooRepository extends CrudRepository<Foo, Long> {
}
