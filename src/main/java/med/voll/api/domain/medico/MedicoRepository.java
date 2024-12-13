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

    @Query(value = """
            SELECT m.*
            FROM medicos m
            WHERE m.especialidad = :especialidad 
              AND m.activo = 1
              AND NOT EXISTS (
                  SELECT 1
                  FROM consultas c
                  WHERE c.medico_id = m.id
                    AND c.fecha >= :inicioHora
                    AND c.fecha < :finHora
              )
            ORDER BY RAND()
            LIMIT 1
           """, nativeQuery = true)
    Optional<Medico> findRandomAvailableMedico(
            @Param("especialidad") Especialidad especialidad,
            @Param("inicioHora") LocalDateTime inicioHora,
            @Param("finHora") LocalDateTime finHora);
}

