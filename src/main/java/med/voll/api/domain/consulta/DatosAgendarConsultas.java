package med.voll.api.domain.consulta;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.medico.Especialidad;
import med.voll.api.domain.paciente.Paciente;

import java.time.LocalDateTime;

public record DatosAgendarConsultas(
        Long id, Long idPaciente, Long idMedico, @NotNull @Future LocalDateTime fecha, Especialidad especialidad
        ) {
}
