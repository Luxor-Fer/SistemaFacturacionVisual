-- phpMyAdmin SQL Dump
-- version 5.1.0
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 12-07-2021 a las 17:37:20
-- Versión del servidor: 10.4.18-MariaDB
-- Versión de PHP: 8.0.5

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `sistema_factuacion_visual`
--

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
('AA01', 'ACEITE ALESOL', 1.5, 'DISPONIBLE', 3),
('AT01', 'ATUN ISABEL', 2, 'DISPONIBLE', 5),
('CL01', 'COCA COLA 1L', 1.25, 'DISPONIBLE', 5),
('GO01', 'GALLETAS OREO', 0.4, 'DISPONIBLE', 6),
('L001', 'NUTRI LECHE', 1, '', 10);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `productos`
--
ALTER TABLE `productos`
  ADD PRIMARY KEY (`ID_PRO`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
