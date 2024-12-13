package med.voll.api.domain.consulta.validadores;

import med.voll.api.domain.ValidacionException;
import med.voll.api.domain.consulta.DatoRegistraConsulta;
import med.voll.api.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorPacienteActivo implements ValidadorDeConsultas {
    @Autowired
    private PacienteRepository pacienteRepository;
    public void validar(DatoRegistraConsulta datoRegistraConsulta) {
    var pacienteEstaActivo = pacienteRepository.findActivoById(datoRegistraConsulta.idPaciente());
    if (!pacienteEstaActivo){
        throw new ValidacionException("Paciente inactivo");    }
    }

}
