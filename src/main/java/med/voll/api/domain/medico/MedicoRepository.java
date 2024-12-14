package med.voll.api.domain.medico;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface MedicoRepository extends JpaRepository<Medico, Long> {
    Page<Medico> findByActivoTrue(Pageable paginacion);

    @Query("""
            select m.activo
            from Medico m
            where
            m.id = :idMedico
            """)
    Boolean findActivoById(Long idMedico);

    @Query("""
            SELECT m 
            FROM Medico m 
            WHERE m.especialidad = :especialidad 
              AND m.activo = true
              AND NOT EXISTS (
                  SELECT c 
                  FROM Consulta c 
                  WHERE c.medico = m 
                    AND c.fecha >= :inicioHora
                    AND c.fecha <= :finHora
              )
            ORDER BY FUNCTION('RAND') 
            LIMIT 1
           """)
    Optional<Medico> findRandomAvailableMedico(
            @Param("especialidad") Especialidad especialidad,
            @Param("inicioHora") LocalDateTime inicioHora,
            @Param("finHora") LocalDateTime finHora);
}

