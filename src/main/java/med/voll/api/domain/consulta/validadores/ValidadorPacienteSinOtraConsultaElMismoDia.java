package med.voll.api.domain.consulta.validadores;

import med.voll.api.domain.ValidacionException;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DatoRegistraConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorPacienteSinOtraConsultaElMismoDia implements ValidadorDeConsultas{
    @Autowired
    private ConsultaRepository consultaRepository;

    public void validar(DatoRegistraConsulta datoRegistraConsulta) {
        var primeraHoraDia = datoRegistraConsulta.fecha().withHour(7);
        var ultimaHoraDia = datoRegistraConsulta.fecha().withHour(16);
        var pacienteTieneOtraCOnsultaEnElDia = consultaRepository.existsByPacienteIdAndFechaBetween(
                datoRegistraConsulta.idPaciente(), primeraHoraDia, ultimaHoraDia);
        if(pacienteTieneOtraCOnsultaEnElDia){
            throw new ValidacionException("Paciente con consulta reservada para este día");
        }

    }
}
