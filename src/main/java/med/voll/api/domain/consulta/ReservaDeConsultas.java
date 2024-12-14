package med.voll.api.domain.consulta;

import med.voll.api.domain.ValidacionException;
import med.voll.api.domain.consulta.validadores.ValidadorDeConsultas;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.Paciente;
import med.voll.api.domain.paciente.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ReservaDeConsultas {

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private List<ValidadorDeConsultas> validadorDeConsultas;

    public Consulta reservar(DatoRegistraConsulta datoRegistraConsulta){
        if (!pacienteRepository.existsById(datoRegistraConsulta.idPaciente())){
            throw new ValidacionException("El paciente con id "+ datoRegistraConsulta.idPaciente() +
                    " no existe en la base de datos");
        }
        //SOLO PRUEBAS
        System.out.println("Paciente existe y es " + pacienteRepository.findById(datoRegistraConsulta.idPaciente()));

        if ((datoRegistraConsulta.idMedico()!= null) &&
                !(medicoRepository.existsById(datoRegistraConsulta.idMedico()))){
            throw new ValidacionException("El médico con id "+ datoRegistraConsulta.idMedico() +
                    " no existe en la base de datos");
        }
        System.out.println("El medico enviado es  nulo ");

        //validaciones
        validadorDeConsultas.forEach(v -> v.validar(datoRegistraConsulta));


        Medico medico = elegirMedico(datoRegistraConsulta);
        Paciente paciente = pacienteRepository.findById(datoRegistraConsulta.idPaciente()).get();
        var consulta = new Consulta(null, medico, paciente, datoRegistraConsulta.fecha(), null);
        consultaRepository.save(consulta);
        return consulta;
    }

    private Medico elegirMedico(DatoRegistraConsulta datoRegistraConsulta) {

        System.out.println("Dentro de eleir medico");


        if (datoRegistraConsulta.idMedico() != null){
            return medicoRepository.getReferenceById(datoRegistraConsulta.idMedico());
        }
        System.out.println("Id de medico no dado");


        if (datoRegistraConsulta.especialidad() == null){
            throw new ValidacionException("Se debe enviar una especialidad si no" +
                    " se eligió un médico específico");
        }
        System.out.println("Especialidad no es null es "+ datoRegistraConsulta.especialidad());


        System.out.println("Datos enviados a findRandomAvailableMedico. " +
                "Especialidad "+ datoRegistraConsulta.especialidad() +
                "Fecha inicio "+ datoRegistraConsulta.fecha().withMinute(0)+
                "Fecha fin "+ datoRegistraConsulta.fecha().withMinute(59));



        Optional<Medico> medico = medicoRepository.findRandomAvailableMedico(
                datoRegistraConsulta.especialidad(),
                datoRegistraConsulta.fecha().withMinute(0),
                datoRegistraConsulta.fecha().withMinute(59));

        System.out.println("******** " + medico.get());

        if (medico.isPresent()){
            System.out.println(medico.get());
            return medico.get();
        }

        throw new ValidacionException("No hay medicos disponibles con esas características");
    }

    public void cancelar(DatoEliminaConsulta datos) {
        if (!consultaRepository.existsById(datos.id())) {
            throw new ValidacionException("Id de la consulta informado no existe!");
        }
        var consulta = consultaRepository.getReferenceById(datos.id());

        //La consulta debe ser cancelada al menos con 24 horas de altelación
        LocalDateTime fechaActual = LocalDateTime.now();
        if (fechaActual.plusHours(24).compareTo(consulta.getFecha())>0){
            throw new ValidacionException("La consulta no puede ser eliminada porque queda menos de 24 horas.");
        }
        consulta.cancelarCita(datos.motivo());
    }
}
