package med.voll.api.controller;


import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.medico.DatosUpdateMedico;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoDTO;
import med.voll.api.domain.paciente.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/pacientes")
@SecurityRequirement(name = "bearer-key")
public class PacienteController {
    @Autowired
    PacienteRepository pacienteRepository;

    @PostMapping
    public ResponseEntity<PacienteDTO> registarMedico(@RequestBody @Valid DatosPaciente datosPaciente, UriComponentsBuilder uriComponentsBuilder){
        Paciente paciente=pacienteRepository.save(new Paciente(datosPaciente));
        PacienteDTO pacienteDTO=new PacienteDTO(paciente.getId(),paciente.getNombre(),paciente.getEmail(),paciente.getDocumento());
        URI url= uriComponentsBuilder.path("/pacientes/{id}").buildAndExpand(paciente.getId()).toUri();
        return ResponseEntity.created(url).body(pacienteDTO);
    }

    @GetMapping
    public Page<PacienteDTO> listarPacientes(@PageableDefault(size = 10) Pageable pageable){
        return pacienteRepository.findByActivoTrue(pageable).map(PacienteDTO::new);
    }

    @PutMapping
    @Transactional
    public ResponseEntity actualizarPaciente(@RequestBody @Valid DatosUpdatePaciente datosUpdatePaciente){
        Paciente paciente=pacienteRepository.getReferenceById(datosUpdatePaciente.id());
        paciente.actualizarDatos(datosUpdatePaciente);
        return ResponseEntity.ok(new PacienteDTO(paciente.getId(),paciente.getNombre(),paciente.getEmail(),paciente.getDocumento()));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarPaciente(@PathVariable Long id){
        Paciente paciente=pacienteRepository.getReferenceById(id);
        paciente.desactivarMedico();
        return ResponseEntity.noContent().build();
    }
}
