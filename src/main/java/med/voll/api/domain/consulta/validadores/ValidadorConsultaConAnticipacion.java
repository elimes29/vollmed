package med.voll.api.domain.consulta.validadores;

import med.voll.api.domain.ValidacionException;
import med.voll.api.domain.consulta.DatoRegistraConsulta;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class ValidadorConsultaConAnticipacion implements ValidadorDeConsultas {

    public void validar(DatoRegistraConsulta datoRegistraConsulta){
        var fechaConsulta = datoRegistraConsulta.fecha();
        var ahora = LocalDateTime.now();
        var diferenciaEnMinutos = Duration.between(ahora, fechaConsulta).toMinutes();

        if (diferenciaEnMinutos<30){
            throw new ValidacionException("Horario de cita con tiempo menor a 30 minutos.");
        }
    }
}
