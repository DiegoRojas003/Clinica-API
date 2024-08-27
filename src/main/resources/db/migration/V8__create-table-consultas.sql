CREATE TABLE consultas(
    id BIGINT NOT NULL AUTO_INCREMENT,
    medico_id bigint not null,
    paciente_id bigint not null,
    data datetime not null,

    PRIMARY KEY (id),
    constraint fk_consultas_medico_id foreign key(medico_id) references medico(id),
    constraint fk_consultas_paciente_id foreign key(paciente_id) references paciente(id)
);
