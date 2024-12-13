package med.voll.api.domain.consulta.validadores;

import med.voll.api.domain.ValidacionException;
import med.voll.api.domain.consulta.DatoRegistraConsulta;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
@Component
public class ValidadorFueraHorarioConsultas implements ValidadorDeConsultas{

    public void validar(DatoRegistraConsulta datoRegistraConsulta) {
        var fechaConsulta = datoRegistraConsulta.fecha();
        var esDomingo = fechaConsulta.getDayOfWeek().equals(DayOfWeek.SUNDAY);
        var horarioAntesApertura = fechaConsulta.getHour() < 7;
        var horarioDespuesCierrre = fechaConsulta.getHour() > 18;
         if (esDomingo || horarioAntesApertura || horarioDespuesCierrre) {
             throw new ValidacionException("Horario fuera de laboral de la clínica");
         }


    }
}
