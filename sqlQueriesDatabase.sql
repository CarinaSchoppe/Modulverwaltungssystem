-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server-Version:               10.4.14-MariaDB - mariadb.org binary distribution
-- Server-Betriebssystem:        Win64
-- HeidiSQL Version:             12.5.0.6677
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT = @@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE = @@TIME_ZONE */;
/*!40103 SET TIME_ZONE = '+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0 */;
/*!40101 SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE = 'NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES = @@SQL_NOTES, SQL_NOTES = 0 */;


-- Exportiere Datenbank-Struktur für stdinfos
CREATE DATABASE IF NOT EXISTS `stdinfos` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;
USE `stdinfos`;

-- Exportiere Struktur von Tabelle stdinfos.fach
CREATE TABLE IF NOT EXISTS `fach`
(
    `FachID`        int(11)     NOT NULL,
    `FachName`      varchar(50) NOT NULL,
    `Semester`      int(11)   DEFAULT 0,
    `FachBestanden` binary(1) DEFAULT NULL,
    `ECTS`          int(11)   DEFAULT NULL,
    `ModulID`       int(11)     NOT NULL,
    PRIMARY KEY (`FachID`),
    KEY `ModulID` (`ModulID`),
    CONSTRAINT `ModulID` FOREIGN KEY (`ModulID`) REFERENCES `modul` (`ModulID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

-- Exportiere Daten aus Tabelle stdinfos.fach: ~0 rows (ungefähr)

-- Exportiere Struktur von Tabelle stdinfos.modul
CREATE TABLE IF NOT EXISTS `modul`
(
    `ModulID`        int(11)     NOT NULL,
    `ModulName`      varchar(50) NOT NULL,
    `ModulBestanden` binary(1) DEFAULT NULL,
    `StudID`         int(11)     NOT NULL,
    PRIMARY KEY (`ModulID`),
    KEY `StudID` (`StudID`),
    CONSTRAINT `StudID` FOREIGN KEY (`StudID`) REFERENCES `studiengang` (`StudID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

-- Exportiere Daten aus Tabelle stdinfos.modul: ~0 rows (ungefähr)

-- Exportiere Struktur von Tabelle stdinfos.praktikum
CREATE TABLE IF NOT EXISTS `praktikum`
(
    `PraktID`        int(11) NOT NULL,
    `PraktBestanden` binary(1) DEFAULT '\0',
    `FachID`         int(11) NOT NULL,
    PRIMARY KEY (`PraktID`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

-- Exportiere Daten aus Tabelle stdinfos.praktikum: ~0 rows (ungefähr)

-- Exportiere Struktur von Tabelle stdinfos.praktikumstermin
CREATE TABLE IF NOT EXISTS `praktikumstermin`
(
    `PraktTerminID`   int(11) NOT NULL,
    `PraktNr`         int(11) NOT NULL,
    `PraktDatum`      date      DEFAULT NULL,
    `PraktUhrzeit`    time      DEFAULT NULL,
    `TerminBestanden` binary(1) DEFAULT NULL,
    `PraktID`         int(11) NOT NULL,
    PRIMARY KEY (`PraktTerminID`),
    KEY `PraktID` (`PraktID`),
    CONSTRAINT `PraktID` FOREIGN KEY (`PraktID`) REFERENCES `praktikum` (`PraktID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

-- Exportiere Daten aus Tabelle stdinfos.praktikumstermin: ~0 rows (ungefähr)

-- Exportiere Struktur von Tabelle stdinfos.pruefung
CREATE TABLE IF NOT EXISTS `pruefung`
(
    `PruefID`   int(11) NOT NULL,
    `PruefForm` varchar(50) DEFAULT NULL,
    `FachID`    int(11) NOT NULL,
    PRIMARY KEY (`PruefID`),
    KEY `FachID` (`FachID`),
    CONSTRAINT `FachID` FOREIGN KEY (`FachID`) REFERENCES `fach` (`FachID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

-- Exportiere Daten aus Tabelle stdinfos.pruefung: ~0 rows (ungefähr)

-- Exportiere Struktur von Tabelle stdinfos.pruefungsversuch
CREATE TABLE IF NOT EXISTS `pruefungsversuch`
(
    `PruefVersuchID` int(11) NOT NULL,
    `PruefVersuchNr` int(11) NOT NULL,
    `PruefDatum`     date      DEFAULT NULL,
    `PruefUhrzeit`   time      DEFAULT NULL,
    `Note`           float     DEFAULT NULL,
    `PruefBestanden` binary(1) DEFAULT '\0',
    `PruefID`        int(11) NOT NULL,
    PRIMARY KEY (`PruefVersuchID`),
    KEY `PruefID` (`PruefID`),
    CONSTRAINT `PruefID` FOREIGN KEY (`PruefID`) REFERENCES `pruefung` (`PruefID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

-- Exportiere Daten aus Tabelle stdinfos.pruefungsversuch: ~0 rows (ungefähr)

-- Exportiere Struktur von Tabelle stdinfos.studiengang
CREATE TABLE IF NOT EXISTS `studiengang`
(
    `StudID`              int(11) NOT NULL,
    `Studienverlaufsplan` text DEFAULT NULL,
    PRIMARY KEY (`StudID`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

-- Exportiere Daten aus Tabelle stdinfos.studiengang: ~0 rows (ungefähr)

/*!40103 SET TIME_ZONE = IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE = IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS = IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT = @OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES = IFNULL(@OLD_SQL_NOTES, 1) */;
