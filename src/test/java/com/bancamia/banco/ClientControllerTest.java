package com.bancamia.banco;

import com.bancamia.banco.domain.Client;
import com.bancamia.banco.BancoApplication;
import com.bancamia.banco.domain.Client;
import com.bancamia.banco.domain.service.ClientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = BancoApplication.class)
@AutoConfigureMockMvc
class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
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
    void testGetAllClients() throws Exception {
        when(clientService.getAll()).thenReturn(Arrays.asList(client));

        mockMvc.perform(get("/clients/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("Juan"));
    }

    @Test
    void testGetClientById() throws Exception {
        when(clientService.getClient(1L)).thenReturn(Optional.of(client));

        mockMvc.perform(get("/clients/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Juan"));
    }

    @Test
    void testSaveClient() throws Exception {
        when(clientService.save(any(Client.class))).thenReturn(client);

        mockMvc.perform(post("/clients/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(client)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value("Juan"));
    }

    @Test
    void testDeleteClient() throws Exception {
        when(clientService.delete(1L)).thenReturn(true);

        mockMvc.perform(delete("/clients/delete/{id}", 1L))
                .andExpect(status().isNoContent());
    }

    @Test
    void testUpdateClient() throws Exception {
        Client updatedClient = new Client();
        updatedClient.setId(1L);
        updatedClient.setIdentificationType("CC");
        updatedClient.setIdentificationNumber("87654321");
        updatedClient.setFirstName("Carlos");
        updatedClient.setLastName("Martinez");
        updatedClient.setCreationDate("2023-02-01");

        when(clientService.update(eq(1L), any(Client.class))).thenReturn(updatedClient);
        when(clientService.getClient(eq(1L))).thenReturn(Optional.of(client));

        mockMvc.perform(put("/clients/update/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(updatedClient)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Carlos"))
                .andExpect(jsonPath("$.identificationNumber").value("87654321"));
    }


    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}