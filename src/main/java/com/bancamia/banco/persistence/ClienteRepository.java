package com.bancamia.banco.persistence;

import com.bancamia.banco.domain.Client;
import com.bancamia.banco.domain.repository.ClientRepository;
import com.bancamia.banco.exception.ClienteNotFoundException;
import com.bancamia.banco.persistence.crud.ClienteCrudRepository;
import com.bancamia.banco.persistence.entity.Cliente;
import com.bancamia.banco.persistence.mapper.ClientMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
//El cliente Repository quedo enfocado al dominio que a una tabla puntual

@Repository
public class ClienteRepository implements ClientRepository {
    @Autowired
    private ClienteCrudRepository clienteCrudRepository;
    @Autowired
    private ClientMapper mapper;

    // Método para recuperar todos los clientes
    @Override
    public List<Client> getAll() {
        List<Cliente> cliente = (List<Cliente>) clienteCrudRepository.findAll();
        return mapper.toClient(cliente);

    }

    // Método para recuperar un cliente por ID
    @Override
    public Optional<Client> getCliente(Long id) {
        return clienteCrudRepository.findById(id).map(cliente -> mapper.toClient(cliente));
    }

    @Override
    public Client update(Long id, Client client) {
        Optional<Cliente> existingClient = clienteCrudRepository.findById(id);
        if (existingClient.isPresent()) {
            Cliente cliente = existingClient.get();
            cliente.setTipoIdentificacion(client.getIdentificationType());
            cliente.setNumeroIdentificacion(client.getIdentificationNumber());
            cliente.setNombres(client.getFirstName());
            cliente.setApellidos(client.getLastName());
            cliente.setFechaCreacion(LocalDate.parse(client.getCreationDate()));

            return mapper.toClient(clienteCrudRepository.save(cliente));
        } else {
            throw new ClienteNotFoundException("Client not found with id " + id);
        }
    }



@Override
    // Método para guardar un cliente (crear o actualizar)
    public Client save(Client client){
    Cliente cliente = mapper.toCliente(client);
        return mapper.toClient(clienteCrudRepository.save(cliente));
    }
    // Método para eliminar un cliente por ID
    @Override
    public void delete(Long id){
         clienteCrudRepository.deleteById(id);
    }

}
