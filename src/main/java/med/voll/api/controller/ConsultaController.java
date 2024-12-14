package med.voll.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import med.voll.api.domain.consulta.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/consultas")
@SecurityRequirement(name = "bearer-key")
public class ConsultaController {

    @Autowired
    private ReservaDeConsultas reservaDeConsultas;

    @Transactional
    @PostMapping
    public ResponseEntity<DatoSalidaConsulta> registraConsulta(@RequestBody @Valid DatoRegistraConsulta datoRegistraConsulta){
        Consulta consulta = reservaDeConsultas.reservar(datoRegistraConsulta);
        return ResponseEntity.ok(new DatoSalidaConsulta(consulta.getId(), consulta.getMedico().getId(), consulta.getPaciente().getId(), consulta.getFecha()));
    }

    @Transactional
    @DeleteMapping
    public ResponseEntity<DatoSalidaConsulta> eliminaConsulta(@RequestBody @Valid DatoEliminaConsulta datoEliminaConsulta){
        reservaDeConsultas.cancelar(datoEliminaConsulta);
        return ResponseEntity.noContent().build();

    }

}
