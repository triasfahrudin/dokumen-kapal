-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               5.6.20 - Source distribution
-- Server OS:                    Linux
-- HeidiSQL Version:             9.5.0.5196
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Dumping database structure for dokumen_kapal
DROP DATABASE IF EXISTS `dokumen_kapal`;
CREATE DATABASE IF NOT EXISTS `dokumen_kapal` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `dokumen_kapal`;

-- Dumping structure for table dokumen_kapal.berita
DROP TABLE IF EXISTS `berita`;
CREATE TABLE IF NOT EXISTS `berita` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `gambar` varchar(50) DEFAULT NULL,
  `judul` varchar(150) DEFAULT NULL,
  `slug` varchar(160) DEFAULT NULL,
  `konten` text,
  `tgl_post` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

-- Dumping data for table dokumen_kapal.berita: ~4 rows (approximately)
DELETE FROM `berita`;
/*!40000 ALTER TABLE `berita` DISABLE KEYS */;
INSERT INTO `berita` (`id`, `gambar`, `judul`, `slug`, `konten`, `tgl_post`) VALUES
	(1, '1241c-workspace-1_032.jpg', 'Post Title 1', 'post-title-1', '<p>\n	Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ducimus, vero, obcaecati, aut, error quam sapiente nemo saepe quibusdam sit excepturi nam quia corporis eligendi eos magni recusandae laborum minus inventore? Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ut, tenetur natus doloremque laborum quos iste ipsum rerum obcaecati impedit odit illo dolorum ab tempora nihil dicta earum fugiat. Temporibus, voluptatibus. Lorem ipsum dolor sit amet, consectetur adipisicing elit. Eos, doloribus, dolorem iusto blanditiis unde eius illum consequuntur neque dicta incidunt ullam ea hic porro optio ratione repellat perspiciatis. Enim, iure! Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer posuere erat a ante. Someone famous in Source Title Lorem ipsum dolor sit amet, consectetur adipisicing elit. Error, nostrum, aliquid, animi, ut quas placeat totam sunt tempora commodi nihil ullam alias modi dicta saepe minima ab quo voluptatem obcaecati? Lorem ipsum dolor sit amet, consectetur adipisicing elit. Harum, dolor quis. Sunt, ut, explicabo, aliquam tenetur ratione tempore quidem voluptates cupiditate voluptas illo saepe quaerat numquam recusandae? Qui, necessitatibus, est!</p>\n', '2019-12-17 23:08:02'),
	(2, '1241c-workspace-1_032.jpg', 'Post Title 2', 'post-title-2', '<p>\n	Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ducimus, vero, obcaecati, aut, error quam sapiente nemo saepe quibusdam sit excepturi nam quia corporis eligendi eos magni recusandae laborum minus inventore? Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ut, tenetur natus doloremque laborum quos iste ipsum rerum obcaecati impedit odit illo dolorum ab tempora nihil dicta earum fugiat. Temporibus, voluptatibus. Lorem ipsum dolor sit amet, consectetur adipisicing elit. Eos, doloribus, dolorem iusto blanditiis unde eius illum consequuntur neque dicta incidunt ullam ea hic porro optio ratione repellat perspiciatis. Enim, iure! Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer posuere erat a ante. Someone famous in Source Title Lorem ipsum dolor sit amet, consectetur adipisicing elit. Error, nostrum, aliquid, animi, ut quas placeat totam sunt tempora commodi nihil ullam alias modi dicta saepe minima ab quo voluptatem obcaecati? Lorem ipsum dolor sit amet, consectetur adipisicing elit. Harum, dolor quis. Sunt, ut, explicabo, aliquam tenetur ratione tempore quidem voluptates cupiditate voluptas illo saepe quaerat numquam recusandae? Qui, necessitatibus, est!</p>\n', '2019-12-17 23:08:02'),
	(3, '1241c-workspace-1_032.jpg', 'Post Title 3', 'post-title-3', '<p>\n	Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ducimus, vero, obcaecati, aut, error quam sapiente nemo saepe quibusdam sit excepturi nam quia corporis eligendi eos magni recusandae laborum minus inventore? Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ut, tenetur natus doloremque laborum quos iste ipsum rerum obcaecati impedit odit illo dolorum ab tempora nihil dicta earum fugiat. Temporibus, voluptatibus. Lorem ipsum dolor sit amet, consectetur adipisicing elit. Eos, doloribus, dolorem iusto blanditiis unde eius illum consequuntur neque dicta incidunt ullam ea hic porro optio ratione repellat perspiciatis. Enim, iure! Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer posuere erat a ante. Someone famous in Source Title Lorem ipsum dolor sit amet, consectetur adipisicing elit. Error, nostrum, aliquid, animi, ut quas placeat totam sunt tempora commodi nihil ullam alias modi dicta saepe minima ab quo voluptatem obcaecati? Lorem ipsum dolor sit amet, consectetur adipisicing elit. Harum, dolor quis. Sunt, ut, explicabo, aliquam tenetur ratione tempore quidem voluptates cupiditate voluptas illo saepe quaerat numquam recusandae? Qui, necessitatibus, est!</p>\n', '2019-12-17 23:08:02'),
	(4, '1241c-workspace-1_032.jpg', 'Post Title 4', 'post-title-4', '<p>\n	Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ducimus, vero, obcaecati, aut, error quam sapiente nemo saepe quibusdam sit excepturi nam quia corporis eligendi eos magni recusandae laborum minus inventore? Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ut, tenetur natus doloremque laborum quos iste ipsum rerum obcaecati impedit odit illo dolorum ab tempora nihil dicta earum fugiat. Temporibus, voluptatibus. Lorem ipsum dolor sit amet, consectetur adipisicing elit. Eos, doloribus, dolorem iusto blanditiis unde eius illum consequuntur neque dicta incidunt ullam ea hic porro optio ratione repellat perspiciatis. Enim, iure! Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer posuere erat a ante. Someone famous in Source Title Lorem ipsum dolor sit amet, consectetur adipisicing elit. Error, nostrum, aliquid, animi, ut quas placeat totam sunt tempora commodi nihil ullam alias modi dicta saepe minima ab quo voluptatem obcaecati? Lorem ipsum dolor sit amet, consectetur adipisicing elit. Harum, dolor quis. Sunt, ut, explicabo, aliquam tenetur ratione tempore quidem voluptates cupiditate voluptas illo saepe quaerat numquam recusandae? Qui, necessitatibus, est!</p>\n', '2019-12-17 23:08:02');
/*!40000 ALTER TABLE `berita` ENABLE KEYS */;

-- Dumping structure for table dokumen_kapal.biaya
DROP TABLE IF EXISTS `biaya`;
CREATE TABLE IF NOT EXISTS `biaya` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `kode` varchar(50) DEFAULT NULL,
  `alias` varchar(50) DEFAULT NULL,
  `nominal` double DEFAULT NULL,
  `keterangan` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

-- Dumping data for table dokumen_kapal.biaya: ~5 rows (approximately)
DELETE FROM `biaya`;
/*!40000 ALTER TABLE `biaya` DISABLE KEYS */;
INSERT INTO `biaya` (`id`, `kode`, `alias`, `nominal`, `keterangan`) VALUES
	(1, 'bm_cair', 'Bongkar Muat Muatan Cair', 30000, 'per ton'),
	(2, 'bm_padat', 'Bongkar Muat Muatan Padat', 25000, 'per ton'),
	(3, 'bm_gas', 'Bongkar Muat Muatan Gas', 35000, 'per ton'),
	(4, 'masa_layar', 'Masa Layar', 10000, ''),
	(5, 'sertifikat_kapal', 'Sertifikat Kapal', 25000, '');
/*!40000 ALTER TABLE `biaya` ENABLE KEYS */;

-- Dumping structure for table dokumen_kapal.bongkar_muat
DROP TABLE IF EXISTS `bongkar_muat`;
CREATE TABLE IF NOT EXISTS `bongkar_muat` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pemohon_id` int(11) DEFAULT NULL,
  `kode_biaya` varchar(50) DEFAULT NULL,
  `jenis_muatan` varchar(50) DEFAULT NULL,
  `bobot` float DEFAULT NULL,
  `nama_kapal` varchar(50) DEFAULT NULL,
  `angkutan_nopol` varchar(50) DEFAULT NULL,
  `angkutan_supir` varchar(50) DEFAULT NULL,
  `tgl_mohon` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `file_surat_permohonan` varchar(50) DEFAULT NULL,
  `biaya` double DEFAULT NULL,
  `bukti_bayar` varchar(50) DEFAULT NULL,
  `tgl_upload_bukti_bayar` date DEFAULT NULL,
  `tgl_update` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` enum('pending','baru','diterima','ditolak','diambil') DEFAULT NULL,
  `alasan_status` varchar(50) DEFAULT NULL,
  `rating_kepuasan` tinyint(4) DEFAULT '0',
  `komentar` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

-- Dumping data for table dokumen_kapal.bongkar_muat: ~1 rows (approximately)
DELETE FROM `bongkar_muat`;
/*!40000 ALTER TABLE `bongkar_muat` DISABLE KEYS */;
INSERT INTO `bongkar_muat` (`id`, `pemohon_id`, `kode_biaya`, `jenis_muatan`, `bobot`, `nama_kapal`, `angkutan_nopol`, `angkutan_supir`, `tgl_mohon`, `file_surat_permohonan`, `biaya`, `bukti_bayar`, `tgl_upload_bukti_bayar`, `tgl_update`, `status`, `alasan_status`, `rating_kepuasan`, `komentar`) VALUES
	(1, 1, 'bm_gas', 'OKSIGEN', 2, '1', 'B.9258 FEH,L. 8087 UO', 'JUMALI,SUPARDI', '2019-12-04 10:32:33', '', 70000, '', '0000-00-00', '2019-12-18 02:25:25', 'diambil', 'xxx', 5, '');
/*!40000 ALTER TABLE `bongkar_muat` ENABLE KEYS */;

-- Dumping structure for table dokumen_kapal.buku_pelaut
DROP TABLE IF EXISTS `buku_pelaut`;
CREATE TABLE IF NOT EXISTS `buku_pelaut` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pemohon_id` varchar(50) DEFAULT '0',
  `nomor_buku` varchar(50) DEFAULT NULL,
  `kode_pelaut` varchar(50) DEFAULT NULL,
  `nomor_daftar` varchar(50) DEFAULT NULL,
  `file` varchar(50) DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `pemohon_id` (`pemohon_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

-- Dumping data for table dokumen_kapal.buku_pelaut: ~1 rows (approximately)
DELETE FROM `buku_pelaut`;
/*!40000 ALTER TABLE `buku_pelaut` DISABLE KEYS */;
INSERT INTO `buku_pelaut` (`id`, `pemohon_id`, `nomor_buku`, `kode_pelaut`, `nomor_daftar`, `file`) VALUES
	(1, '1', 'XXXX', 'YYYY', 'ZZZZZ', '0');
/*!40000 ALTER TABLE `buku_pelaut` ENABLE KEYS */;

-- Dumping structure for table dokumen_kapal.ci_session
DROP TABLE IF EXISTS `ci_session`;
CREATE TABLE IF NOT EXISTS `ci_session` (
  `id` varchar(40) NOT NULL,
  `ip_address` varchar(45) NOT NULL,
  `timestamp` int(10) unsigned NOT NULL,
  `data` blob NOT NULL,
  PRIMARY KEY (`id`),
  KEY `ci_sessions_timestamp` (`timestamp`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table dokumen_kapal.ci_session: ~16 rows (approximately)
DELETE FROM `ci_session`;
/*!40000 ALTER TABLE `ci_session` DISABLE KEYS */;
INSERT INTO `ci_session` (`id`, `ip_address`, `timestamp`, `data`) VALUES
	('1kf1f20vfm9tid7i70p28p605580gh5d', '::1', 1576602756, _binary 0x5F5F63695F6C6173745F726567656E65726174657C693A313537363630323435373B757365725F69647C733A313A2231223B757365725F656D61696C7C733A31383A2261646D696E40646F6B6B6170616C2E636F6D223B757365725F6C6576656C7C733A353A2261646D696E223B),
	('3f4sf3lc5dtjmaaf0bd1e64bl4ceiq4h', '::1', 1576611383, _binary 0x5F5F63695F6C6173745F726567656E65726174657C693A313537363631313039393B757365725F69647C733A313A2231223B757365725F656D61696C7C733A31383A2261646D696E40646F6B6B6170616C2E636F6D223B757365725F6C6576656C7C733A353A2261646D696E223B),
	('3lc8u831q9v33u1ra3ltbhi6p7mtc9eq', '::1', 1576602319, _binary 0x5F5F63695F6C6173745F726567656E65726174657C693A313537363630323032313B757365725F69647C733A313A2231223B757365725F656D61696C7C733A31383A2261646D696E40646F6B6B6170616C2E636F6D223B757365725F6C6576656C7C733A353A2261646D696E223B),
	('3tq42fshce9kg4nv994m801c2ap8a2p3', '::1', 1576601329, _binary 0x5F5F63695F6C6173745F726567656E65726174657C693A313537363630313035323B757365725F69647C733A313A2231223B757365725F656D61696C7C733A31383A2261646D696E40646F6B6B6170616C2E636F6D223B757365725F6C6576656C7C733A353A2261646D696E223B),
	('4rdc67u5e68gts64epshuh7qgd9ns8bl', '::1', 1576597265, _binary 0x5F5F63695F6C6173745F726567656E65726174657C693A313537363539373031383B),
	('8rsvegjl19f00r4tilusbguvhlttcafo', '::1', 1576601653, _binary 0x5F5F63695F6C6173745F726567656E65726174657C693A313537363630313335363B757365725F69647C733A313A2231223B757365725F656D61696C7C733A31383A2261646D696E40646F6B6B6170616C2E636F6D223B757365725F6C6576656C7C733A353A2261646D696E223B),
	('d1indo1ob8srl9csguf5imv861tut2sr', '::1', 1576612024, _binary 0x5F5F63695F6C6173745F726567656E65726174657C693A313537363631323032343B),
	('ep0f20l7p8q1t0o5qp4rbgsd13v9gjuo', '::1', 1576610947, _binary 0x5F5F63695F6C6173745F726567656E65726174657C693A313537363631303730343B757365725F69647C733A313A2231223B757365725F656D61696C7C733A31383A2261646D696E40646F6B6B6170616C2E636F6D223B757365725F6C6576656C7C733A353A2261646D696E223B),
	('f4d4n3h6gk8qsuaiohhjod39is85m4hf', '::1', 1576596888, _binary 0x5F5F63695F6C6173745F726567656E65726174657C693A313537363539363631383B),
	('gt8ea6cmgnovmijllc8fhmi6cdr43eqk', '::1', 1576602007, _binary 0x5F5F63695F6C6173745F726567656E65726174657C693A313537363630313730373B757365725F69647C733A313A2231223B757365725F656D61696C7C733A31383A2261646D696E40646F6B6B6170616C2E636F6D223B757365725F6C6576656C7C733A353A2261646D696E223B),
	('irudm8il9k1jvsuoloi14fgmt0ejneou', '::1', 1576595942, _binary 0x5F5F63695F6C6173745F726567656E65726174657C693A313537363539353933393B),
	('knk1r5ejpuvricli6fsjc4akfpiobtb3', '::1', 1576599189, _binary 0x5F5F63695F6C6173745F726567656E65726174657C693A313537363539383839383B757365725F69647C733A313A2231223B757365725F656D61696C7C733A31383A2261646D696E40646F6B6B6170616C2E636F6D223B757365725F6C6576656C7C733A353A2261646D696E223B),
	('miubbbb5f17hja0jj1tt5ei2m2v57045', '::1', 1576610091, _binary 0x5F5F63695F6C6173745F726567656E65726174657C693A313537363630393837363B757365725F69647C733A313A2231223B757365725F656D61696C7C733A31383A2261646D696E40646F6B6B6170616C2E636F6D223B757365725F6C6576656C7C733A353A2261646D696E223B),
	('puc0dl8lccu45v8ld7ogsukgj538993n', '::1', 1576599526, _binary 0x5F5F63695F6C6173745F726567656E65726174657C693A313537363539393532363B757365725F69647C733A313A2231223B757365725F656D61696C7C733A31383A2261646D696E40646F6B6B6170616C2E636F6D223B757365725F6C6576656C7C733A353A2261646D696E223B),
	('u80oilujcaamkmb1riqigqckb0pu6cmm', '::1', 1576600892, _binary 0x5F5F63695F6C6173745F726567656E65726174657C693A313537363630303734303B757365725F69647C733A313A2231223B757365725F656D61696C7C733A31383A2261646D696E40646F6B6B6170616C2E636F6D223B757365725F6C6576656C7C733A353A2261646D696E223B),
	('uk105dnuja1psmqaft8sks14dl731qco', '::1', 1576600542, _binary 0x5F5F63695F6C6173745F726567656E65726174657C693A313537363630303331343B757365725F69647C733A313A2231223B757365725F656D61696C7C733A31383A2261646D696E40646F6B6B6170616C2E636F6D223B757365725F6C6576656C7C733A353A2261646D696E223B);
/*!40000 ALTER TABLE `ci_session` ENABLE KEYS */;

-- Dumping structure for table dokumen_kapal.kapal
DROP TABLE IF EXISTS `kapal`;
CREATE TABLE IF NOT EXISTS `kapal` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pemohon_id` int(11) DEFAULT NULL,
  `nama_kapal` varchar(50) DEFAULT NULL,
  `jenis_kapal` varchar(50) DEFAULT NULL,
  `imo_number` varchar(50) DEFAULT NULL,
  `grt` int(11) DEFAULT NULL,
  `kapasitas_penumpang` int(11) DEFAULT NULL,
  `kapasitas_roda_dua` int(11) DEFAULT NULL,
  `kapasitas_roda_empat` int(11) DEFAULT NULL,
  `file_surat_ukur` varchar(50) DEFAULT NULL,
  `file_surat_laut` varchar(50) DEFAULT NULL,
  `file_sertifikat_keselamatan` varchar(50) DEFAULT NULL,
  `file_sertifikat_klasifikasi` varchar(50) DEFAULT NULL,
  `file_sertifikat_pmk` varchar(50) DEFAULT NULL,
  `file_sertifikat_liferaft` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1 COMMENT='imo =  International Maritime Organization ';

-- Dumping data for table dokumen_kapal.kapal: ~2 rows (approximately)
DELETE FROM `kapal`;
/*!40000 ALTER TABLE `kapal` DISABLE KEYS */;
INSERT INTO `kapal` (`id`, `pemohon_id`, `nama_kapal`, `jenis_kapal`, `imo_number`, `grt`, `kapasitas_penumpang`, `kapasitas_roda_dua`, `kapasitas_roda_empat`, `file_surat_ukur`, `file_surat_laut`, `file_sertifikat_keselamatan`, `file_sertifikat_klasifikasi`, `file_sertifikat_pmk`, `file_sertifikat_liferaft`) VALUES
	(1, 1, 'KMP.TUNU PRATAMA JAYA 3888', 'Ro-Ro', NULL, NULL, 12, 3, 4, '', '', '', '', '', ''),
	(2, 1, 'KMP.TUNU PRATAMA JAYA 3888', 'Ro-Ro', NULL, NULL, 12, 3, 4, '', '', '', '', '', '');
/*!40000 ALTER TABLE `kapal` ENABLE KEYS */;

-- Dumping structure for table dokumen_kapal.masa_layar
DROP TABLE IF EXISTS `masa_layar`;
CREATE TABLE IF NOT EXISTS `masa_layar` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pemohon_id` int(11) DEFAULT NULL,
  `biaya` double DEFAULT NULL,
  `tgl_upload_bukti_bayar` datetime DEFAULT NULL,
  `bukti_bayar` varchar(50) DEFAULT NULL,
  `tgl_mohon` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `tgl_update` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` enum('pending','baru','diterima','ditolak','diambil') DEFAULT 'pending',
  `alasan_status` varchar(50) DEFAULT NULL,
  `rating_kepuasan` tinyint(4) DEFAULT NULL,
  `komentar` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

-- Dumping data for table dokumen_kapal.masa_layar: ~0 rows (approximately)
DELETE FROM `masa_layar`;
/*!40000 ALTER TABLE `masa_layar` DISABLE KEYS */;
INSERT INTO `masa_layar` (`id`, `pemohon_id`, `biaya`, `tgl_upload_bukti_bayar`, `bukti_bayar`, `tgl_mohon`, `tgl_update`, `status`, `alasan_status`, `rating_kepuasan`, `komentar`) VALUES
	(3, 1, 10000, '0000-00-00 00:00:00', 'ddd', '2019-12-09 10:56:28', '2019-12-18 02:14:49', 'diambil', 'xxxxxxxxxx', 4, '');
/*!40000 ALTER TABLE `masa_layar` ENABLE KEYS */;

-- Dumping structure for table dokumen_kapal.notifikasi
DROP TABLE IF EXISTS `notifikasi`;
CREATE TABLE IF NOT EXISTS `notifikasi` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pemohon_id` int(11) DEFAULT NULL,
  `jenis_permohonan` varchar(50) DEFAULT NULL,
  `permohonan_id` int(11) DEFAULT NULL,
  `isi_notifikasi` varchar(150) DEFAULT NULL,
  `tgl` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table dokumen_kapal.notifikasi: ~0 rows (approximately)
DELETE FROM `notifikasi`;
/*!40000 ALTER TABLE `notifikasi` DISABLE KEYS */;
/*!40000 ALTER TABLE `notifikasi` ENABLE KEYS */;

-- Dumping structure for table dokumen_kapal.pemohon
DROP TABLE IF EXISTS `pemohon`;
CREATE TABLE IF NOT EXISTS `pemohon` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `jenis` enum('perorangan','perusahaan') DEFAULT NULL,
  `nama` varchar(50) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `foto` varchar(50) DEFAULT NULL,
  `npwp` varchar(50) DEFAULT NULL,
  `alamat` varchar(50) DEFAULT NULL,
  `no_telp` varchar(50) DEFAULT NULL,
  `token_id` varchar(250) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1 COMMENT='pemohon bisa perorangan atau perusahaan\r\nfoto = foto diri atau logo';

-- Dumping data for table dokumen_kapal.pemohon: ~0 rows (approximately)
DELETE FROM `pemohon`;
/*!40000 ALTER TABLE `pemohon` DISABLE KEYS */;
INSERT INTO `pemohon` (`id`, `jenis`, `nama`, `email`, `password`, `foto`, `npwp`, `alamat`, `no_telp`, `token_id`) VALUES
	(1, 'perusahaan', 'pelaut 1', 'pelaut@laut.com', '4b76c131914c2ee6ed57e65449913e99', '', '', 'jl.ikan duyung', '085123456789', NULL);
/*!40000 ALTER TABLE `pemohon` ENABLE KEYS */;

-- Dumping structure for table dokumen_kapal.riwayat_pelayaran
DROP TABLE IF EXISTS `riwayat_pelayaran`;
CREATE TABLE IF NOT EXISTS `riwayat_pelayaran` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pemohon_id` int(11) DEFAULT NULL,
  `nama_kapal` varchar(50) DEFAULT NULL,
  `tenaga_mesin` varchar(50) DEFAULT NULL,
  `jabatan` varchar(50) DEFAULT NULL,
  `tgl_naik` date DEFAULT '0000-00-00',
  `tgl_turun` date DEFAULT '0000-00-00',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

-- Dumping data for table dokumen_kapal.riwayat_pelayaran: ~1 rows (approximately)
DELETE FROM `riwayat_pelayaran`;
/*!40000 ALTER TABLE `riwayat_pelayaran` DISABLE KEYS */;
INSERT INTO `riwayat_pelayaran` (`id`, `pemohon_id`, `nama_kapal`, `tenaga_mesin`, `jabatan`, `tgl_naik`, `tgl_turun`) VALUES
	(1, 1, 'XXXX', '1111', 'DDDDD', '2019-12-16', '2019-12-17');
/*!40000 ALTER TABLE `riwayat_pelayaran` ENABLE KEYS */;

-- Dumping structure for table dokumen_kapal.sertifikat_keselamatan
DROP TABLE IF EXISTS `sertifikat_keselamatan`;
CREATE TABLE IF NOT EXISTS `sertifikat_keselamatan` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `kapal_id` int(11) DEFAULT NULL,
  `biaya` double DEFAULT NULL,
  `bukti_bayar` varchar(50) DEFAULT NULL,
  `tgl_upload_bukti_bayar` date DEFAULT NULL,
  `tgl_mohon` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `tgl_update` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` enum('pending','baru','diterima','ditolak','diambil') DEFAULT 'pending',
  `alasan_status` varchar(50) DEFAULT NULL,
  `rating_kepuasan` tinyint(4) DEFAULT NULL,
  `komentar` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

-- Dumping data for table dokumen_kapal.sertifikat_keselamatan: ~1 rows (approximately)
DELETE FROM `sertifikat_keselamatan`;
/*!40000 ALTER TABLE `sertifikat_keselamatan` DISABLE KEYS */;
INSERT INTO `sertifikat_keselamatan` (`id`, `kapal_id`, `biaya`, `bukti_bayar`, `tgl_upload_bukti_bayar`, `tgl_mohon`, `tgl_update`, `status`, `alasan_status`, `rating_kepuasan`, `komentar`) VALUES
	(1, 1, NULL, '', '0000-00-00', '2019-12-08 18:40:37', '2019-12-16 15:19:32', 'baru', '', 0, '');
/*!40000 ALTER TABLE `sertifikat_keselamatan` ENABLE KEYS */;

-- Dumping structure for table dokumen_kapal.sertifikat_pelaut
DROP TABLE IF EXISTS `sertifikat_pelaut`;
CREATE TABLE IF NOT EXISTS `sertifikat_pelaut` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pemohon_id` int(11) DEFAULT NULL,
  `nama_sertifikat` varchar(50) DEFAULT NULL,
  `nomor` varchar(50) DEFAULT NULL,
  `penerbit` varchar(50) DEFAULT NULL,
  `tgl_terbit` date DEFAULT '0000-00-00',
  `file` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

-- Dumping data for table dokumen_kapal.sertifikat_pelaut: ~1 rows (approximately)
DELETE FROM `sertifikat_pelaut`;
/*!40000 ALTER TABLE `sertifikat_pelaut` DISABLE KEYS */;
INSERT INTO `sertifikat_pelaut` (`id`, `pemohon_id`, `nama_sertifikat`, `nomor`, `penerbit`, `tgl_terbit`, `file`) VALUES
	(1, 1, 'WWWW', '12365874', 'ZZZZ', '2019-12-16', '');
/*!40000 ALTER TABLE `sertifikat_pelaut` ENABLE KEYS */;

-- Dumping structure for table dokumen_kapal.settings
DROP TABLE IF EXISTS `settings`;
CREATE TABLE IF NOT EXISTS `settings` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(100) DEFAULT NULL,
  `keterangan` varchar(100) DEFAULT NULL,
  `tipe` enum('small-text','big-text') DEFAULT 'small-text',
  `value` text,
  `show` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table dokumen_kapal.settings: ~0 rows (approximately)
DELETE FROM `settings`;
/*!40000 ALTER TABLE `settings` DISABLE KEYS */;
/*!40000 ALTER TABLE `settings` ENABLE KEYS */;

-- Dumping structure for function dokumen_kapal.slugify
DROP FUNCTION IF EXISTS `slugify`;
DELIMITER //
CREATE DEFINER=`root`@`localhost` FUNCTION `slugify`(
	`dirty_string` varchar(200)
) RETURNS varchar(200) CHARSET latin1
    DETERMINISTIC
BEGIN
    DECLARE x, y , z Int;
    DECLARE temp_string, new_string VarChar(200);
    DECLARE is_allowed Bool;
    DECLARE c, check_char VarChar(1);

    set temp_string = LOWER(dirty_string);

    Set temp_string = replace(temp_string, '&', ' and ');

    Select temp_string Regexp('[^a-z0-9\-]+') into x;
    If x = 1 then
        set z = 1;
        While z <= Char_length(temp_string) Do
            Set c = Substring(temp_string, z, 1);
            Set is_allowed = False;
            If !((ascii(c) = 45) or (ascii(c) >= 48 and ascii(c) <= 57) or (ascii(c) >= 97 and ascii(c) <= 122)) Then
                Set temp_string = Replace(temp_string, c, '-');
            End If;
            set z = z + 1;
        End While;
    End If;

    Select temp_string Regexp("^-|-$|'") into x;
    If x = 1 Then
        Set temp_string = Replace(temp_string, "'", '');
        Set z = Char_length(temp_string);
        Set y = Char_length(temp_string);
        Dash_check: While z > 1 Do
            If Strcmp(SubString(temp_string, -1, 1), '-') = 0 Then
                Set temp_string = Substring(temp_string,1, y-1);
                Set y = y - 1;
            Else
                Leave Dash_check;
            End If;
            Set z = z - 1;
        End While;
    End If;

    Repeat
        Select temp_string Regexp("--") into x;
        If x = 1 Then
            Set temp_string = Replace(temp_string, "--", "-");
        End If;
    Until x <> 1 End Repeat;

    If LOCATE('-', temp_string) = 1 Then
        Set temp_string = SUBSTRING(temp_string, 2);
    End If;

    Return temp_string;
END//
DELIMITER ;

-- Dumping structure for table dokumen_kapal.user
DROP TABLE IF EXISTS `user`;
CREATE TABLE IF NOT EXISTS `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `nama_lengkap` varchar(50) DEFAULT NULL,
  `level` enum('admin','petugas','kepala') DEFAULT NULL,
  `reset_token` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

-- Dumping data for table dokumen_kapal.user: ~3 rows (approximately)
DELETE FROM `user`;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` (`id`, `email`, `password`, `nama_lengkap`, `level`, `reset_token`) VALUES
	(1, 'admin@dokkapal.com', 'd3fb4bf1e2011fab49a8e5f1099905d9', '0', 'admin', ''),
	(2, 'petugas@dokkapal.com', 'a935f6207d9852c4435c4b9a2d658446', '0', 'petugas', ''),
	(3, 'kepala@dokkapal.com', '40f0340b994af132505d4e6d6a6f490d', '0', 'kepala', '');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;

-- Dumping structure for table dokumen_kapal.web_content
DROP TABLE IF EXISTS `web_content`;
CREATE TABLE IF NOT EXISTS `web_content` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `judul` varchar(50) DEFAULT NULL,
  `content` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table dokumen_kapal.web_content: ~0 rows (approximately)
DELETE FROM `web_content`;
/*!40000 ALTER TABLE `web_content` DISABLE KEYS */;
/*!40000 ALTER TABLE `web_content` ENABLE KEYS */;

-- Dumping structure for trigger dokumen_kapal.berita_before_insert
DROP TRIGGER IF EXISTS `berita_before_insert`;
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `berita_before_insert` BEFORE INSERT ON `berita` FOR EACH ROW BEGIN
	SET NEW.slug = slugify(NEW.judul);
END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

-- Dumping structure for trigger dokumen_kapal.berita_before_update
DROP TRIGGER IF EXISTS `berita_before_update`;
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `berita_before_update` BEFORE UPDATE ON `berita` FOR EACH ROW BEGIN
	SET NEW.slug = slugify(NEW.judul);
END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

-- Dumping structure for trigger dokumen_kapal.bongkar_muat_before_insert
DROP TRIGGER IF EXISTS `bongkar_muat_before_insert`;
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `bongkar_muat_before_insert` BEFORE INSERT ON `bongkar_muat` FOR EACH ROW BEGIN
	SET NEW.biaya = (SELECT nominal * NEW.bobot FROM biaya WHERE kode = NEW.kode_biaya);
END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

-- Dumping structure for trigger dokumen_kapal.bongkar_muat_before_update
DROP TRIGGER IF EXISTS `bongkar_muat_before_update`;
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `bongkar_muat_before_update` BEFORE UPDATE ON `bongkar_muat` FOR EACH ROW BEGIN
	SET NEW.biaya = (SELECT nominal * NEW.bobot FROM biaya WHERE kode = NEW.kode_biaya);
END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

-- Dumping structure for trigger dokumen_kapal.masa_layar_after_insert
DROP TRIGGER IF EXISTS `masa_layar_after_insert`;
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `masa_layar_after_insert` AFTER INSERT ON `masa_layar` FOR EACH ROW BEGIN
	INSERT INTO notifikasi(pemohon_id,jenis_permohonan,permohonan_id,isi_notifikasi) 
	VALUES(NEW.pemohon_id, 'masa_layar', NEW.id, 'Permohonan anda telah kami terima. Mohon selesaikan pembayaran agar permohonan anda segera diproses');
END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

-- Dumping structure for trigger dokumen_kapal.masa_layar_before_insert
DROP TRIGGER IF EXISTS `masa_layar_before_insert`;
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `masa_layar_before_insert` BEFORE INSERT ON `masa_layar` FOR EACH ROW BEGIN
	SET NEW.biaya = (SELECT nominal FROM biaya WHERE kode = 'masa_layar');
	
END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

-- Dumping structure for trigger dokumen_kapal.masa_layar_before_update
DROP TRIGGER IF EXISTS `masa_layar_before_update`;
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `masa_layar_before_update` BEFORE UPDATE ON `masa_layar` FOR EACH ROW BEGIN
	IF(NEW.bukti_bayar <> OLD.bukti_bayar) THEN
		INSERT INTO notifikasi(pemohon_id,jenis_permohonan,permohonan_id,isi_notifikasi) 
		VALUES(NEW.pemohon_id, 'masa_layar', NEW.id, 'Bukti pembayaran telah berhasil diunggah. Mohon menunggu untuk pengecekan');
	END IF;	
END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
