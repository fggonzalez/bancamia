package com.bancamia.banco.persistence.mapper;

import com.bancamia.banco.domain.Client;
import com.bancamia.banco.persistence.entity.Cliente;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClientMapper {
    @Mappings({
            @Mapping(source="id",target="id"),
            @Mapping(source="tipoIdentificacion",target="identificationType"),
            @Mapping(source="numeroIdentificacion",target="identificationNumber"),
            @Mapping(source="nombres",target="firstName"),
            @Mapping(source="apellidos",target="lastName"),
            @Mapping(source="fechaCreacion",target="creationDate"),
    })
    Client toClient(Cliente cliente);
    List<Client>toClient(List<Cliente> cliente);
@InheritInverseConfiguration
    Cliente toCliente(Client client);
}
