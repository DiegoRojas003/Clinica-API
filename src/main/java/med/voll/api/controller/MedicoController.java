package med.voll.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.direccion.DatosDireccion;
import med.voll.api.domain.medico.*;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;


@RestController
@RequestMapping("/medicos")
@SecurityRequirement(name = "bearer-key")
public class MedicoController {
    @Autowired
    private MedicoRepository medicoRepository;

    @PostMapping
    public ResponseEntity<DatosUpdateMedico> registrarMedico(@RequestBody @Valid DatosMedico datosMedico, UriComponentsBuilder uriComponentsBuilder){
        Medico medico=medicoRepository.save(new Medico(datosMedico));
        DatosUpdateMedico datosUpdateMedico=asignarDatosRespuesta(medico);
        URI url= uriComponentsBuilder.path("/medico/{id}").buildAndExpand(medico.getId()).toUri();
        return ResponseEntity.created(url).body(datosUpdateMedico);
    }
    @GetMapping
    public Page<MedicoDTO> listadoMedicos(@PageableDefault(size = 10) Pageable pageable){
        return medicoRepository.findByActivoTrue(pageable).map(MedicoDTO::new);
    }
    @GetMapping("/{id}")
    public ResponseEntity<DatosUpdateMedico> retornarDatosMedico(@PathVariable Long id){
        Medico medico=medicoRepository.getReferenceById(id);
        return ResponseEntity.ok(asignarDatosRespuesta(medico));
    }
    @PutMapping
    @Transactional
    public ResponseEntity actualizarMedico(@RequestBody @Valid DatosUpdateMedico datosUpdateMedico){
        Medico medico=medicoRepository.getReferenceById(datosUpdateMedico.id());
        medico.actualizarDatos(datosUpdateMedico);
        return ResponseEntity.ok(asignarDatosRespuesta(medico));
    }
    //Delete logico
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarMedico(@PathVariable Long id){
        Medico medico=medicoRepository.getReferenceById(id);
        medico.desactivarMedico();
        return ResponseEntity.noContent().build();
    }

    private DatosUpdateMedico asignarDatosRespuesta(Medico medico){
        return (new DatosUpdateMedico(medico.getId(), medico.getNombre(),medico.getDocumento(),
                new DatosDireccion(medico.getDireccion().getCalle(),medico.getDireccion().getDistrito(),medico.getDireccion().getCiudad(),
                        medico.getDireccion().getNumero(), medico.getDireccion().getComplemento())));
    }
// Delete en base de datos
//    public void eliminarMedico(@PathVariable Long id){
//        Medico medico=medicoRepository.getReferenceById(id);
//        medicoRepository.delete(medico);
//
//    }
}
