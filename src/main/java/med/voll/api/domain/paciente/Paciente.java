package med.voll.api.domain.paciente;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.domain.direccion.DatosDireccion;
import med.voll.api.domain.direccion.Direccion;

@Entity
@Table(name = "PACIENTE")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of="id")
public class Paciente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String telefono;
    private String email;
    private String documento;
    private boolean activo;
    @Embedded
    private Direccion direccion;

    public Paciente(DatosPaciente datosPaciente) {
        this.nombre=datosPaciente.nombre();
        this.telefono=datosPaciente.telefono();
        this.email= datosPaciente.email();
        this.documento= datosPaciente.documento();
        this.direccion=new Direccion(datosPaciente.direccion());
        this.activo=true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    public void actualizarDatos(DatosUpdatePaciente datosUpdatePaciente) {
        if(datosUpdatePaciente.nombre()!=null){
            this.nombre=datosUpdatePaciente.nombre();
        }
        if(datosUpdatePaciente.telefono()!=null){
            this.telefono=datosUpdatePaciente.telefono();
        }
        if(datosUpdatePaciente.direccion()!=null){
            this.direccion=direccion.actualizarDatosPaciente(datosUpdatePaciente);
        }

    }

    public void desactivarMedico() {this.activo=false;}
}
