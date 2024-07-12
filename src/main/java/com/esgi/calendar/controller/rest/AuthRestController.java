package com.esgi.calendar.controller.rest;

import com.esgi.calendar.dto.req.RegistrationFormDto;
import com.esgi.calendar.dto.res.GenericResponseDto;
import com.esgi.calendar.exception.TechnicalException;
import com.esgi.calendar.service.impl.AuthServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api")
@Tag(
        name = "Authorization et Authentification",
        description = "API - Pour s'autentifier et s'autoriser"
)
@AllArgsConstructor
public class AuthRestController {

    private AuthServiceImpl authService;

    @PostMapping("/signup")
    @Operation(summary = "Inscription d'un utilisateur", description = "Permet d'inscrire un nouvel utilisateur")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Utilisateur créé avec succès"),
            @ApiResponse(responseCode = "400", description = "Erreur lors de l'inscription")
    })
    public ResponseEntity<GenericResponseDto> signup(@Valid
                                         @RequestBody
                                                     RegistrationFormDto form) {
        GenericResponseDto resDto = new GenericResponseDto();
        try {
            authService.register(form);
            resDto.setStatus(HttpStatus.CREATED);
            resDto.setMessage(
                    "Utilisateur créer avec succès. " +
                            "Redirection vers la page de connexion.");

            return ResponseEntity.status(resDto.getStatus())
                                 .body(resDto);
        } catch (TechnicalException e) {
            resDto.setStatus(HttpStatus.BAD_REQUEST);
            resDto.setMessage(e.getMessage());

            return ResponseEntity.status(resDto.getStatus())
                                 .body(resDto);
        }
    }

}
