package com.bancamia.banco.domain.repository;

import com.bancamia.banco.domain.Client;
import com.bancamia.banco.persistence.entity.Cliente;

import java.util.List;
import java.util.Optional;

//Implementamos el nombbre de los metodos que queremos que cualquier repositorio que trabaje con clientes tenga que implementar desde el dominio
public interface ClientRepository {
    // Método para recuperar todos los clientes

    List<Client>getAll();
    // Método para recuperar un cliente por ID
    public Optional<Client>getCliente(Long idClient);
    // Método para guardar un cliente (crear o actualizar)
    public Client save(Client client);
    // Método para eliminar un cliente por ID
    public void delete(Long id);
    public Client update(Long id, Client clientDetails);


}
