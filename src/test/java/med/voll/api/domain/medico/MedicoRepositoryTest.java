package med.voll.api.domain.medico;

import med.voll.api.domain.consulta.Consulta;
import med.voll.api.domain.direccion.DatosDireccion;
import med.voll.api.domain.paciente.DatosPaciente;
import med.voll.api.domain.paciente.DatosUpdatePaciente;
import med.voll.api.domain.paciente.Paciente;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class MedicoRepositoryTest {
    @Autowired
    private MedicoRepository repository;

    @Autowired
    private TestEntityManager em;

    @Test
    @DisplayName("Deberia retornar nulo cuando el medico se encuentre en consulta con otro paciente en ese horario")
    void seleccionarMedicoConEspecialidadEnFechaEscenario1() {
        //given
        var proximoLunes10H= LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10,0);

        var medico=registrarMedico("Diego Alejandro","diegorojas9051@gmail.com","12265",Especialidad.CARDIOLOGIA);
        var paciente=registrarPaciente("Dania Jimena","daniagaona07@gmail.com","4565");
        registrarConsulta(medico,paciente,proximoLunes10H);
        //when
        var medicolibre= repository.seleccionarMedicoConEspecialidadEnFecha(Especialidad.CARDIOLOGIA,proximoLunes10H);

        //then
        assertThat(medicolibre).isNull();
    }
    @Test
    @DisplayName("Deberia retornar un medico cuando realice la consulta en la base de datos para ese horario")
    void seleccionarMedicoConEspecialidadEnFechaEscenario2() {
        //given
        var proximoLunes10H= LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10,0);

        var medico=registrarMedico("Diego Alejandro","diegorojas9051@gmail.com","12265",Especialidad.CARDIOLOGIA);
        //when
        var medicolibre= repository.seleccionarMedicoConEspecialidadEnFecha(Especialidad.CARDIOLOGIA,proximoLunes10H);
        //then
        assertThat(medicolibre).isEqualTo(medico);
    }
    private void registrarConsulta(Medico medico, Paciente paciente,LocalDateTime fecha){
        em.persist(new Consulta(null,medico,paciente,fecha,null));
    }
    private Medico registrarMedico(String nombre,String email,String documento, Especialidad especialidad){
        var medico=new Medico(datosMedico(nombre,email,documento,especialidad));
        em.persist(medico);
        return medico;
    }
    private Paciente registrarPaciente(String nombre,String email,String documento){
        var paciente= new Paciente(datosPacientes(nombre,email,documento));
        em.persist(paciente);
        return paciente;
    }
    private DatosMedico datosMedico(String nombre, String email, String documento,Especialidad especialidad){
        return new DatosMedico(
                nombre,
                "44552",
                email,
                documento,
                especialidad,
                datosDireccion()
        );
    }
    private DatosPaciente datosPacientes(String nombre, String email, String documento){
            return new DatosPaciente(
                nombre,
                    "3225",
                email,
                documento,
                datosDireccion()
        );
    }
    private DatosDireccion datosDireccion(){
        return new DatosDireccion(
                "Loca",
                "azul",
                "acapulco",
                321,
                "12"
        );
    }
}