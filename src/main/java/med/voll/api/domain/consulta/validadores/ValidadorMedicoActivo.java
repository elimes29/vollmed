package med.voll.api.domain.consulta.validadores;

import med.voll.api.domain.ValidacionException;
import med.voll.api.domain.consulta.DatoRegistraConsulta;
import med.voll.api.domain.medico.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorMedicoActivo implements ValidadorDeConsultas{

    @Autowired
    private MedicoRepository medicoRepository;
    public void validar(DatoRegistraConsulta datoRegistraConsulta) {
        //Si el médico viene nulo
        if (datoRegistraConsulta.idMedico() == null){
            return;
        }

        var medicoActivo = medicoRepository.findActivoById(datoRegistraConsulta.idMedico());
        if (!medicoActivo){
            throw new ValidacionException("Medico inactivo");
        }
    }
}
