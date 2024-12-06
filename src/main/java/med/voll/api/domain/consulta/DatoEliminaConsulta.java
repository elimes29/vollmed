package med.voll.api.domain.consulta;

import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;

public record DatoEliminaConsulta(
        @NotNull
        Long id,
        @NotNull
        Motivo motivo
) {
}
