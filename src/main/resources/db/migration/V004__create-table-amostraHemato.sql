CREATE TABLE amostras_hemato(
    id BIGINT NOT NULL AUTO_INCREMENT,
    data DATE NOT NULL,
    codigo_amostra VARCHAR(120) NOT NULL ,
    motivo VARCHAR(200) NOT NULL,
    local_coleta VARCHAR(200) NOT NULL,

    PRIMARY KEY (id)
);

CREATE TABLE amostras_hemostasia
(
    id             BIGINT       NOT NULL AUTO_INCREMENT,
    data           DATE         NOT NULL,
    codigo_amostra VARCHAR(120) NOT NULL,
    motivo         VARCHAR(200) NOT NULL,
    local_coleta   VARCHAR(200) NOT NULL,

    PRIMARY KEY (id)
);

CREATE TABLE locais_coleta
(
    id BIGINT NOT NULL AUTO_INCREMENT,
    local varchar(200) NOT NULL,

    PRIMARY KEY (id)
);