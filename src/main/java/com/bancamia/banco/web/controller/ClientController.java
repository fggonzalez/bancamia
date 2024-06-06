package com.bancamia.banco.web.controller;

import com.bancamia.banco.domain.Client;
import com.bancamia.banco.domain.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clients")
public class ClientController {
    @Autowired
    private ClientService clientService;

    @Operation(summary = "Obtener todos los clientes", description = "Recupera una lista de todos los clientes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Clientes recuperados exitosamente",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "[{\"identificationId\": 1, \"identificationNumber\": \"12345678\", \"firstName\": \"Juan\", \"lastName\": \"Perez\", \"creationDate\": \"2023-01-01\", \"identificationType\": \"CC\"}]"))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{\"error\": \"Internal Server Error\", \"message\": \"Ocurrió un error interno. Por favor, intente nuevamente más tarde.\", \"status\": 500}")))
    })
    @GetMapping("/all")
    public ResponseEntity<List<Client>> getAll() {
        List<Client> clients = clientService.getAll();
        return ResponseEntity.ok(clients);
    }

    @Operation(summary = "Obtener cliente por ID", description = "Recupera un cliente basado en el ID proporcionado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente recuperado exitosamente",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{\"identificationId\": 1, \"identificationNumber\": \"12345678\", \"firstName\": \"Juan\", \"lastName\": \"Perez\", \"creationDate\": \"2023-01-01\", \"identificationType\": \"CC\"}"))),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{\"message\": \"Cliente no encontrado con el ID 1\"}"))),
            @ApiResponse(responseCode = "400", description = "ID debe ser un número",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{\"error\": \"Bad Request\", \"message\": \"ID debe ser un número\", \"status\": 400}")))
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getClient(@PathVariable("id") @Valid Long id) {
        Optional<Client> client = clientService.getClient(id);
        if (client.isPresent()) {
            return ResponseEntity.ok(client.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"message\": \"Cliente no encontrado con el ID " + id + "\"}");
        }
    }

    @Operation(summary = "Actualizar cliente", description = "Actualiza un cliente basado en el ID proporcionado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente actualizado exitosamente",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{\"identificationId\": 1, \"identificationNumber\": \"12345678\", \"firstName\": \"Juan\", \"lastName\": \"Perez\", \"creationDate\": \"2023-01-01\", \"identificationType\": \"CC\"}"))),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{\"message\": \"Cliente no encontrado con el ID 1\"}"))),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta por datos de entrada",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{\"error\": \"Bad Request\", \"message\": \"Solicitud incorrecta por datos de entrada\", \"status\": 400}")))
    })
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @Valid @RequestBody Client clientDetails) {
        Optional<Client> client = clientService.getClient(id);
        if (client.isPresent()) {
            Client updatedClient = clientService.update(id, clientDetails);
            return ResponseEntity.ok(updatedClient);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"message\": \"Cliente no encontrado con el ID " + id + "\"}");
        }
    }

    @Operation(summary = "Guardar cliente", description = "Guarda un nuevo cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cliente guardado exitosamente",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{\"identificationId\": 1, \"identificationNumber\": \"12345678\", \"firstName\": \"Juan\", \"lastName\": \"Perez\", \"creationDate\": \"2023-01-01\", \"identificationType\": \"CC\"}"))),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta por datos de entrada",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{\"error\": \"Bad Request\", \"message\": \"Solicitud incorrecta por datos de entrada\", \"status\": 400}"))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{\"error\": \"Internal Server Error\", \"message\": \"Ocurrió un error interno. Por favor, intente nuevamente más tarde.\", \"status\": 500}")))
    })
    @PostMapping("/save")
    public ResponseEntity<Client> save(@Valid @RequestBody Client client) {
        Client savedClient = clientService.save(client);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedClient);
    }

    @Operation(summary = "Eliminar cliente", description = "Elimina un cliente basado en el ID proporcionado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Cliente eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{\"message\": \"Cliente no encontrado con el ID 1\"}"))),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{\"error\": \"Internal Server Error\", \"message\": \"Ocurrió un error interno. Por favor, intente nuevamente más tarde.\", \"status\": 500}")))
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@Valid @PathVariable("id") Long id) {
        if (clientService.delete(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"message\": \"Cliente no encontrado con el ID " + id + "\"}");
        }
    }
}
