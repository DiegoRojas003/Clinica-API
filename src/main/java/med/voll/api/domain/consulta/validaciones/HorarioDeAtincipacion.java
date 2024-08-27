package med.voll.api.domain.consulta.validaciones;

import jakarta.validation.ValidationException;
import med.voll.api.domain.consulta.DatosAgendarConsultas;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
@Component
public class HorarioDeAtincipacion implements ValidadorDeConsultas{

    public void validar(DatosAgendarConsultas datos){
        var ahora= LocalDateTime.now();
        var horaDeConsulta=datos.fecha();

        var diferenciaDe30Minutos= Duration.between(ahora,horaDeConsulta).toMinutes()<30;

        if(diferenciaDe30Minutos){
            throw new ValidationException("La cita debe programarse con una diferencia mayor a 30 minutos");
        }
    }
}
