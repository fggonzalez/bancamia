package com.bancamia.banco.domain.service;

import com.bancamia.banco.domain.Client;
import com.bancamia.banco.domain.repository.ClientRepository;
import com.bancamia.banco.exception.ClienteNotFoundException;
import com.bancamia.banco.exception.InvalidDateException;
import com.bancamia.banco.exception.InvalidIdentificationTypeException;
import com.bancamia.banco.exception.InvalidNameException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
//Capa de servicio

@Service
public class ClientService {
    private static final Pattern NAME_PATTERN = Pattern.compile("^[a-zA-Z]+$");
    private static final String[] VALID_IDENTIFICATION_TYPES = {"CC", "TI", "CE"};
    @Autowired
    private ClientRepository clientRepository;

    public List<Client>getAll(){
        return clientRepository.getAll();
    }
    public Optional<Client> getClient(Long id){
        return  clientRepository.getCliente(id);

    }
    public boolean  delete(Long id){
       return getClient(id).map(client -> {
         clientRepository.delete(id);
         return true ; })
               .orElse(false);
    }

    public Client save(Client client){
        return clientRepository.save(client);
    }
    public Client update(Long id, Client client) {
        validateClient(client);
        Client updatedClient = clientRepository.update(id, client);
        if (updatedClient == null) {
            throw new ClienteNotFoundException("Cliente no encontrado con el " + id);
        }
        return clientRepository.update(id, client);
    }
    private void validateClient(Client client) {
        if (!isValidName(client.getFirstName()) || !isValidName(client.getLastName())) {
            throw new InvalidNameException("El nombre solo debe contener letras .");
        }
        if (!isValidIdentificationType(client.getIdentificationType())) {
            throw new InvalidIdentificationTypeException("Identificacion invalida. debe ser  CC, TI, CE.");
        }
        try {
            parseDate(client.getCreationDate());
        } catch (DateTimeParseException e) {
            throw new InvalidDateException("Fromato Invalido de Fecha  debe ser  yyyy-MM-dd.");
        }
    }

    private boolean isValidName(String name) {
        return NAME_PATTERN.matcher(name).matches();
    }

    private boolean isValidIdentificationType(String type) {
        for (String validType : VALID_IDENTIFICATION_TYPES) {
            if (validType.equals(type)) {
                return true;
            }
        }
        return false;
    }

    private LocalDate parseDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(date, formatter);
    }

}
