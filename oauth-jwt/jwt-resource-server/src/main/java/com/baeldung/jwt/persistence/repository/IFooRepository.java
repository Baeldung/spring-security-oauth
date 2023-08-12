package com.baeldung.jwt.persistence.repository;

import org.springframework.data.repository.CrudRepository;

import com.baeldung.jwt.persistence.model.Foo;

public interface IFooRepository extends CrudRepository<Foo, Long> {
}
