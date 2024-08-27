package med.voll.api.domain.medico;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.domain.direccion.Direccion;


@Entity
@Table(name = "MEDICO")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of="id")
public class Medico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nombre;
    private String telefono;
    private String email;
    private String documento;
    private boolean activo;
    @Enumerated(EnumType.STRING)
    private Especialidad especialidad;
    @Embedded
    private Direccion direccion;


    public Medico(DatosMedico datosMedico) {
        this.nombre=datosMedico.nombre();
        this.telefono=datosMedico.telefono();
        this.email=datosMedico.email();
        this.documento=datosMedico.documento();
        this.activo=true;
        this.especialidad=datosMedico.especialidad();
        this.direccion=new Direccion(datosMedico.direccion());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public Especialidad getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(Especialidad especialidad) {
        this.especialidad = especialidad;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    public void actualizarDatos(DatosUpdateMedico datosUpdateMedico) {
        if(datosUpdateMedico.nombre()!=null){
            this.nombre=datosUpdateMedico.nombre();
        }
        if(datosUpdateMedico.documento() != null){
            this.documento=datosUpdateMedico.documento();
        }
        if(datosUpdateMedico.direccion()!=null){
            this.direccion=direccion.actualizarDatos(datosUpdateMedico);
        }

    }

    public void desactivarMedico() {
        this.activo=false;
    }
}
