package com.bancamia.banco.persistence.crud;

import com.bancamia.banco.persistence.entity.Cliente;
import org.springframework.data.repository.CrudRepository;

public interface ClienteCrudRepository extends CrudRepository<Cliente,Long> {

}
