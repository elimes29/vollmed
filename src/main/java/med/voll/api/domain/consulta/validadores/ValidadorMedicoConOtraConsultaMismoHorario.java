package med.voll.api.domain.consulta.validadores;

import med.voll.api.domain.ValidacionException;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DatoRegistraConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorMedicoConOtraConsultaMismoHorario implements ValidadorDeConsultas{
    @Autowired
    private ConsultaRepository consultaRepository;

    public void validar(DatoRegistraConsulta datoRegistraConsulta) {
        var medicoOtraConsultaMismoHorario = consultaRepository.existsByMedicoIdAndFecha(
                datoRegistraConsulta.idMedico(), datoRegistraConsulta.fecha());

        if (medicoOtraConsultaMismoHorario){
            throw new ValidacionException("El medico ya tiene cita en ese horario");
        }
    }

}
