package med.voll.api.domain.consulta.validacionesCancelamientoConsulta;

import med.voll.api.domain.consulta.DatosCancelamientoConsulta;
import org.springframework.stereotype.Component;


public interface ValidadorCancelamiento {
    public void validar(DatosCancelamientoConsulta datos);
}
