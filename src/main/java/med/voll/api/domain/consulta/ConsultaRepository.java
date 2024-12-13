package med.voll.api.domain.consulta;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {
    Boolean existsByPacienteIdAndFechaBetween(@NotNull Long idPaciente, LocalDateTime primeraHoraDia, LocalDateTime ultimaHoraDia);

    Boolean existsByMedicoIdAndFecha(Long idMedico, LocalDateTime fechaConsulta);
}
