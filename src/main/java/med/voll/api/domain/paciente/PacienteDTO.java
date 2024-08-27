package med.voll.api.domain.paciente;

import med.voll.api.domain.medico.Medico;

public record PacienteDTO(
    Long id,
    String nombre,
    String email,
    String documento
) {
    public PacienteDTO(Paciente paciente){
        this(paciente.getId(), paciente.getNombre(),
                paciente.getEmail(),
                paciente.getDocumento());
    }
}
