-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 28-07-2021 a las 18:04:18
-- Versión del servidor: 10.4.20-MariaDB
-- Versión de PHP: 8.0.8

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `sistema_facturacion_visual`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `clientes`
--

CREATE TABLE `clientes` (
  `CED_CLI` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `NOM_CLI` varchar(15) COLLATE utf8_unicode_ci NOT NULL,
  `APE_CLI` varchar(15) COLLATE utf8_unicode_ci NOT NULL,
  `TEL_CLI` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `DIR_CLI` varchar(15) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Volcado de datos para la tabla `clientes`
--

INSERT INTO `clientes` (`CED_CLI`, `NOM_CLI`, `APE_CLI`, `TEL_CLI`, `DIR_CLI`) VALUES
('0000', 'Cliente', 'Prueba', '1111', 'Hoguarts'),
('0502994627', 'LIZ', 'COPARA', '0979084456', 'PUJILI'),
('0932131972', 'Luis', 'Zerna', '0979084457', 'LATACUNGA'),
('1101113697', 'JENIFFER', 'YAGUANA', '0999806005', 'LOJA'),
('1102113697', 'LUIS', 'TIBAN', '0999806001', 'AMBATO');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `detalle_productos`
--

CREATE TABLE `detalle_productos` (
  `ID_PRO_VEN` varchar(4) COLLATE utf8_unicode_ci NOT NULL,
  `CANTIDAD` int(50) NOT NULL,
  `NUM_FAC_VEN` int(4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Volcado de datos para la tabla `detalle_productos`
--

INSERT INTO `detalle_productos` (`ID_PRO_VEN`, `CANTIDAD`, `NUM_FAC_VEN`) VALUES
('1234', 6, 1),
('2584', 2, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `factura`
--

CREATE TABLE `factura` (
  `NUM_FAC` int(4) NOT NULL,
  `FEC_FAC` varchar(25) COLLATE utf8_unicode_ci NOT NULL,
  `CED_VEN_FAC` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `CED_CLI_FAC` varchar(10) COLLATE utf8_unicode_ci NOT NULL,
  `TOTAL` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Volcado de datos para la tabla `factura`
--

INSERT INTO `factura` (`NUM_FAC`, `FEC_FAC`, `CED_VEN_FAC`, `CED_CLI_FAC`, `TOTAL`) VALUES
(1, '27/07/2021 20:53:12', '1801', '0502994627', 6);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `productos`
--

CREATE TABLE `productos` (
  `ID_PRO` varchar(4) COLLATE utf8_unicode_ci NOT NULL,
  `NOM_PRO` varchar(15) COLLATE utf8_unicode_ci NOT NULL,
  `PRE_PRO` float NOT NULL,
  `EST_PRO` varchar(15) COLLATE utf8_unicode_ci NOT NULL,
  `STOCK_PRO` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Volcado de datos para la tabla `productos`
--

INSERT INTO `productos` (`ID_PRO`, `NOM_PRO`, `PRE_PRO`, `EST_PRO`, `STOCK_PRO`) VALUES
('123', 'Pepe', 0, 'NO DISPONIBLE', 0),
('1234', 'ACETAMINOFEN', 0.05, 'DISPONIBLE', 89),
('2584', 'NUTELLA', 2.85, 'DISPONIBLE', 10),
('8888', 'DORITO', 0.5, 'DISPONIBLE', 20),
('999', 'PINOCLIN', 2, 'DISPONIBLE', 8),
('A01', 'POLITO', 0.25, 'DISPONIBLE', 7),
('AA01', 'ACEITE ALESOL', 1.5, 'DISPONIBLE', 3),
('AT01', 'ATUN ISABEL', 2.5, 'NO DISPONIBLE', 5),
('CL01', 'COCA COLA 1L', 1.5, 'DISPONIBLE', 5),
('GO01', 'GALLETAS OREO', 0.5, 'DISPONIBLE', 10),
('L001', 'NUTRI LECHE', 1, 'DISPONIBLE', 10);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios`
--

CREATE TABLE `usuarios` (
  `CED_USU` varchar(4) COLLATE utf8_unicode_ci NOT NULL,
  `NOM_USU` varchar(15) COLLATE utf8_unicode_ci NOT NULL,
  `APE_USU` varchar(15) COLLATE utf8_unicode_ci NOT NULL,
  `NICKNAME` varchar(8) COLLATE utf8_unicode_ci NOT NULL,
  `CONTRASEÑA` varchar(8) COLLATE utf8_unicode_ci NOT NULL,
  `ID_USU_ADM` varchar(4) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Volcado de datos para la tabla `usuarios`
--

INSERT INTO `usuarios` (`CED_USU`, `NOM_USU`, `APE_USU`, `NICKNAME`, `CONTRASEÑA`, `ID_USU_ADM`) VALUES
('1801', 'FERNANDO', 'RAMOS', 'FER', 'ADMIN1', NULL),
('1802', 'EDUARDO', 'VILLAFUERTE', 'EDU', 'VEND1', '1801');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `clientes`
--
ALTER TABLE `clientes`
  ADD PRIMARY KEY (`CED_CLI`);

--
-- Indices de la tabla `detalle_productos`
--
ALTER TABLE `detalle_productos`
  ADD KEY `ID_PRO_VEN` (`ID_PRO_VEN`),
  ADD KEY `NUM_FAC_VEN` (`NUM_FAC_VEN`);

--
-- Indices de la tabla `factura`
--
ALTER TABLE `factura`
  ADD PRIMARY KEY (`NUM_FAC`),
  ADD KEY `CED_VEN_FAC` (`CED_VEN_FAC`),
  ADD KEY `CED_CLI_FAC` (`CED_CLI_FAC`);

--
-- Indices de la tabla `productos`
--
ALTER TABLE `productos`
  ADD PRIMARY KEY (`ID_PRO`);

--
-- Indices de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  ADD PRIMARY KEY (`CED_USU`),
  ADD KEY `ID_USU_ADM` (`ID_USU_ADM`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `factura`
--
ALTER TABLE `factura`
  MODIFY `NUM_FAC` int(4) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `detalle_productos`
--
ALTER TABLE `detalle_productos`
  ADD CONSTRAINT `detalle_productos_ibfk_1` FOREIGN KEY (`ID_PRO_VEN`) REFERENCES `productos` (`ID_PRO`),
  ADD CONSTRAINT `detalle_productos_ibfk_2` FOREIGN KEY (`NUM_FAC_VEN`) REFERENCES `factura` (`NUM_FAC`);

--
-- Filtros para la tabla `factura`
--
ALTER TABLE `factura`
  ADD CONSTRAINT `factura_ibfk_1` FOREIGN KEY (`CED_VEN_FAC`) REFERENCES `usuarios` (`CED_USU`),
  ADD CONSTRAINT `factura_ibfk_2` FOREIGN KEY (`CED_CLI_FAC`) REFERENCES `clientes` (`CED_CLI`);

--
-- Filtros para la tabla `usuarios`
--
ALTER TABLE `usuarios`
  ADD CONSTRAINT `usuarios_ibfk_1` FOREIGN KEY (`ID_USU_ADM`) REFERENCES `usuarios` (`CED_USU`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
