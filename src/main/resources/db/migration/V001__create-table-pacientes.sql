CREATE TABLE pacientes(
    id BIGINT NOT NULL AUTO_INCREMENT,
    data DATE NOT NULL,
    nome VARCHAR(100) NOT NULL,
    revisor VARCHAR(100) NOT NULL,
    achados VARCHAR(500) NOT NULL,

    PRIMARY KEY (id)
);