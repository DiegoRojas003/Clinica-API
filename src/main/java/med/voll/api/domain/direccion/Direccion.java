package med.voll.api.domain.direccion;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.domain.medico.DatosUpdateMedico;
import med.voll.api.domain.paciente.DatosUpdatePaciente;


@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Direccion {

    private String calle;
    private String distrito;
    private String ciudad;
    private int numero;
    private String complemento;

    public Direccion(DatosDireccion direccion) {
        this.calle=direccion.calle();
        this.distrito=direccion.distrito();
        this.ciudad=direccion.ciudad();
        this.numero=direccion.numero();
        this.complemento=direccion.complemento();
    }

    public Direccion actualizarDatos(DatosUpdateMedico datosUpdateMedico) {
        this.calle=datosUpdateMedico.direccion().calle();
        this.distrito=datosUpdateMedico.direccion().distrito();
        this.ciudad=datosUpdateMedico.direccion().ciudad();
        this.numero=datosUpdateMedico.direccion().numero();
        this.complemento=datosUpdateMedico.direccion().complemento();
        return this;
    }
    public Direccion actualizarDatosPaciente(DatosUpdatePaciente datosUpdatePaciente) {
        this.calle=datosUpdatePaciente.direccion().calle();
        this.distrito=datosUpdatePaciente.direccion().distrito();
        this.ciudad=datosUpdatePaciente.direccion().ciudad();
        this.numero=datosUpdatePaciente.direccion().numero();
        this.complemento=datosUpdatePaciente.direccion().complemento();
        return this;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getDistrito() {
        return distrito;
    }

    public void setDistrito(String distrito) {
        this.distrito = distrito;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }
}
