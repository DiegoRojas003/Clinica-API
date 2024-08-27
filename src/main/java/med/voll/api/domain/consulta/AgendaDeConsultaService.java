package med.voll.api.domain.consulta;

import med.voll.api.domain.consulta.validaciones.ValidadorDeConsultas;
import med.voll.api.domain.consulta.validacionesCancelamientoConsulta.ValidadorCancelamiento;
import med.voll.api.domain.medico.Especialidad;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.Paciente;
import med.voll.api.domain.paciente.PacienteRepository;
import med.voll.api.infra.errores.ValidacionDeIntegridad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgendaDeConsultaService {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    List<ValidadorDeConsultas> validadores;
    @Autowired
    List<ValidadorCancelamiento> validadoresCancelamiento;
    public DatosDetallesConsulta agendar(DatosAgendarConsultas datos){
        if(!pacienteRepository.findById(datos.idPaciente()).isPresent()){
            throw new ValidacionDeIntegridad("El id del paciente no fue encontrado");
        }
        var confimacion=pacienteRepository.findById(datos.idPaciente()).isPresent();
        var confirmacionMedico=medicoRepository.existsById(datos.idMedico());
        System.out.println("La respuesta es:"+confimacion);
        System.out.println("La respuesta es:"+confirmacionMedico);
        if(datos.id()!=null || !medicoRepository.existsById(datos.idMedico())){
            throw new ValidacionDeIntegridad("El id del medico no fue encontrado");
        }

        validadores.forEach(v->v.validar(datos));
        var paciente= pacienteRepository.findById(datos.idPaciente()).get();

        var medico= seleccionarMedico(datos);


        Consulta consulta = new Consulta(medico,  paciente, datos.fecha());
        consultaRepository.save(consulta);
        return new DatosDetallesConsulta(consulta);

    }


    public void cancelarConsulta(DatosCancelamientoConsulta datos){
        if(!consultaRepository.existsById(datos.id_consulta())){
            throw new ValidacionDeIntegridad("No existe una consulta con este id.");
        }
        validadoresCancelamiento.forEach(v->v.validar(datos));

        var consulta=consultaRepository.getReferenceById(datos.id_consulta());
        consulta.cancelarCita(datos);
    }

    private Medico seleccionarMedico(DatosAgendarConsultas datos) {
        if(datos.idMedico()!=null){
            return medicoRepository.getReferenceById(datos.idMedico());
        }
        if(datos.especialidad()==null){
            throw  new ValidacionDeIntegridad("Se debe ingresar la especialidad del medico");
        }

        return medicoRepository.seleccionarMedicoConEspecialidadEnFecha(datos.especialidad(),datos.fecha());
    }
}
