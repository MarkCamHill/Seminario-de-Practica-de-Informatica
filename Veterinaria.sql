CREATE DATABASE  IF NOT EXISTS `veterinaria` /*!40100 DEFAULT CHARACTER SET utf8mb3 */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `veterinaria`;
CREATE TABLE Cliente (
    id_cliente INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100),
    telefono VARCHAR(20),
    direccion VARCHAR(255)
);

CREATE TABLE Mascota (
    id_mascota INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100),
    especie VARCHAR(50),
    raza VARCHAR(50),
    edad INT,
    sexo VARCHAR(10),
    id_cliente INT,
    FOREIGN KEY (id_cliente) REFERENCES Cliente(id_cliente)
);

CREATE TABLE Cita (
    id_cita INT AUTO_INCREMENT PRIMARY KEY,
    fecha DATE,
    hora TIME,
    id_cliente INT,
    id_mascota INT,
    FOREIGN KEY (id_cliente) REFERENCES Cliente(id_cliente),
    FOREIGN KEY (id_mascota) REFERENCES Mascota(id_mascota)
);

CREATE TABLE HistorialMedico (
    id_historial INT AUTO_INCREMENT PRIMARY KEY,
    fecha DATE,
    diagnostico TEXT,
    tratamiento TEXT,
    id_mascota INT,
    FOREIGN KEY (id_mascota) REFERENCES Mascota(id_mascota)
);

CREATE TABLE Producto (
    id_producto INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100),
    descripcion TEXT,
    cantidad INT,
    precio DECIMAL(10,2)
);

