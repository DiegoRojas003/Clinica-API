package med.voll.api.domain.consulta.validacionesCancelamientoConsulta;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.Consulta;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DatosCancelamientoConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class MediaHoraCancelamiento implements ValidadorCancelamiento{
    @Autowired
    private ConsultaRepository repository;
    @Override
    public void validar(DatosCancelamientoConsulta datos) {
        Consulta consulta=repository.getReferenceById(datos.id_consulta());
        LocalDateTime horaActual = LocalDateTime.now();

        var diferencia30Minutos= Duration.between(horaActual,consulta.getData()).toMinutes()<30;

        if(diferencia30Minutos){
            throw new ValidationException("Se debe cancelar la cita medica con un maximo plazo de media hora, por lo tanto no se puede cancelar");
        }

    }
}
