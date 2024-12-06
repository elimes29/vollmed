package med.voll.api.domain.consulta;

import med.voll.api.domain.ValidacionException;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.Paciente;
import med.voll.api.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservaDeConsultas {

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private ConsultaRepository consultaRepository;


    public Consulta reservar(DatoRegistraConsulta datoRegistraConsulta){
        if (!pacienteRepository.existsById(datoRegistraConsulta.idPaciente())){
            throw new ValidacionException("El paciente con id "+ datoRegistraConsulta.idPaciente() +
                    "no existe en la base de datos");
        }
        if ((datoRegistraConsulta.idMedico()!= null) &&
                !(pacienteRepository.existsById(datoRegistraConsulta.idMedico()))){
            throw new ValidacionException("El médico con id "+ datoRegistraConsulta.idMedico() +
                    "no existe en la base de datos");
        }
        Medico medico = elegirMedico(datoRegistraConsulta);
        Paciente paciente = pacienteRepository.findById(datoRegistraConsulta.idPaciente()).get();
        var consulta = new Consulta(null, medico, paciente, datoRegistraConsulta.fecha(), null);
        consultaRepository.save(consulta);
        return consulta;
    }

    private Medico elegirMedico(DatoRegistraConsulta datoRegistraConsulta) {
        if (datoRegistraConsulta.idMedico() != null){
            return medicoRepository.getReferenceById(datoRegistraConsulta.idMedico());
        }

        if (datoRegistraConsulta.especialidad() == null){
            throw new ValidacionException("Se debe enviar una especialidad si no" +
                    " se eligió un médico específico");
        }

        //Falta lógica de negocio para buscar al médico cuando no envien el id
        return medicoRepository.findAll().get(0);
    }

    public void cancelar(DatoEliminaConsulta datos) {
        if (!consultaRepository.existsById(datos.id())) {
            throw new ValidacionException("Id de la consulta informado no existe!");
        }
        var consulta = consultaRepository.getReferenceById(datos.id());
        consulta.cancelarCita(datos.motivo());
    }
}
