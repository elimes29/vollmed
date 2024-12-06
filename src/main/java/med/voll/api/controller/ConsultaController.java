package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.domain.consulta.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/consultas")
public class ConsultaController {

    @Autowired
    private ReservaDeConsultas reservaDeConsultas;

    @Transactional
    @PutMapping
    public ResponseEntity<DatoSalidaConsulta> registraConsulta(@RequestBody @Valid DatoRegistraConsulta datoRegistraConsulta){
        Consulta consulta = reservaDeConsultas.reservar(datoRegistraConsulta);
        return ResponseEntity.ok(new DatoSalidaConsulta(consulta.getId(), consulta.getMedico().getId(), consulta.getPaciente().getId(), consulta.getFecha()));
    }

}
