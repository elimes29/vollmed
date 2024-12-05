package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.domain.consulta.DatoRegistraConsulta;
import med.voll.api.domain.consulta.DatoSalidaConsulta;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/consultas")
public class ConsultaController {

    @PutMapping
    public ResponseEntity<DatoSalidaConsulta> registraConsulta(@RequestBody @Valid DatoRegistraConsulta datoRegistraConsulta){
        System.out.println(datoRegistraConsulta);
        return null;
    }

}
