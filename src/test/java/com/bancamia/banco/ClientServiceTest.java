package com.bancamia.banco;


import com.bancamia.banco.domain.Client;
import com.bancamia.banco.domain.repository.ClientRepository;
import com.bancamia.banco.domain.service.ClientService;
import com.bancamia.banco.exception.ClienteNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientService clientService;

    private Client client;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        client = new Client();
        client.setId(1L);
        client.setIdentificationType("CC");
        client.setIdentificationNumber("12345678");
        client.setFirstName("Juan");
        client.setLastName("Perez");
        client.setCreationDate("2023-01-01");
    }

    @Test
    void testGetAllClients() {
        List<Client> clients = Arrays.asList(client);
        when(clientRepository.getAll()).thenReturn(clients);

        List<Client> result = clientService.getAll();

        assertEquals(1, result.size());
        verify(clientRepository, times(1)).getAll();
    }

    @Test
    void testGetClientById() {
        when(clientRepository.getCliente(1L)).thenReturn(Optional.of(client));

        Optional<Client> result = clientService.getClient(1L);

        assertTrue(result.isPresent());
        assertEquals("Juan", result.get().getFirstName());
        verify(clientRepository, times(1)).getCliente(1L);
    }

    @Test
    void testSaveClient() {
        when(clientRepository.save(client)).thenReturn(client);

        Client result = clientService.save(client);

        assertNotNull(result);
        assertEquals("Juan", result.getFirstName());
        verify(clientRepository, times(1)).save(client);
    }

    @Test
    void testDeleteClient() {
        when(clientRepository.getCliente(1L)).thenReturn(Optional.of(client));
        doNothing().when(clientRepository).delete(1L);

        boolean result = clientService.delete(1L);

        assertTrue(result);
        verify(clientRepository, times(1)).delete(1L);
    }

    @Test
    void testUpdateClient() {
        // Mock de getCliente para devolver el cliente original
        when(clientRepository.getCliente(1L)).thenReturn(Optional.of(client));

        // Configuración del cliente actualizado
        Client updatedClient = new Client();
        updatedClient.setId(1L);
        updatedClient.setIdentificationType("CC");
        updatedClient.setIdentificationNumber("87654321");
        updatedClient.setFirstName("Carlos");
        updatedClient.setLastName("Martinez");
        updatedClient.setCreationDate("2023-02-01");

        // Mock de update para devolver el cliente actualizado
        when(clientRepository.update(eq(1L), any(Client.class))).thenAnswer(invocation -> {
            Client clientToUpdate = invocation.getArgument(1);
            clientToUpdate.setFirstName(updatedClient.getFirstName());
            clientToUpdate.setIdentificationNumber(updatedClient.getIdentificationNumber());
            return clientToUpdate;
        });
    }
}