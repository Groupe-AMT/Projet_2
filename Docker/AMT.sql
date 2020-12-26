-- phpMyAdmin SQL Dump
-- version 5.0.4
-- https://www.phpmyadmin.net/
--
-- Host: mysql
-- Generation Time: Dec 10, 2020 at 04:36 AM
-- Server version: 8.0.21
-- PHP Version: 7.4.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `AMT`
--

--
-- Dumping data for table `application_entity`
--

INSERT INTO `application_entity` (`id`, `xapi_key`, `contact`, `description`, `name`) VALUES
(1, 'c7a6314a-020a-4965-ab34-f067697fdb77', 'string', 'string', 'string'),
(2, '8203071c-9307-48d7-96db-9ff693c46474', 'Vincent Canipel', 'test2', 'app2'),
(3, '45ecbaf9-f789-4b21-adfc-30435e916ff3', 'Jerome Arn', 'test3', 'app3');

--
-- Dumping data for table `badge_entity`
--

INSERT INTO `badge_entity` (`id`, `image`, `name`, `application_id`) VALUES
(1, 'test1', 'badg1', 1),
(2, 'test2', 'badge2', 1),
(3, 'image', 'Badge925', 3),
(4, 'image', 'Badge826', 3);

--
-- Dumping data for table `badge_reward_entity`
--

INSERT INTO `badge_reward_entity` (`id`, `iduser`, `timestamp`, `application_id`, `badge_id`) VALUES
(1, '48e05969-02ad-444b-83ce-a20020637e5d', '2020-12-16 14:46:20.000000', 1, 2),
(2, '5e55b4c6-ba1b-4c37-bdba-3cd20acfd58f', '2020-12-14 14:46:20.000000', 1, 1),
(3, '5e55b4c6-ba1b-4c37-bdba-3cd20acfd58f', '2020-12-02 14:47:34.000000', 1, 2);

--
-- Dumping data for table `end_user_entity`
--

INSERT INTO `end_user_entity` (`id`, `iduser`, `app_name`, `user_name`) VALUES
(1, '48e05969-02ad-444b-83ce-a20020637e5d', 'string', 'jerome'),
(2, '5e55b4c6-ba1b-4c37-bdba-3cd20acfd58f', 'string', 'miguel'),
(3, 'affeab9b-8171-4853-83be-299bb35eba8f', 'app2', 'Rastignac'),
(4, '35ac951e-33c4-44e4-af54-e702c038af50', 'app3', 'hans');

--
-- Dumping data for table `event_entity`
--

INSERT INTO `event_entity` (`id`, `iduser`, `action`, `attribute`, `timestamp`, `user_name`, `application_id`) VALUES
(1, '5e55b4c6-ba1b-4c37-bdba-3cd20acfd58f', 'Badge', 'attribute', '2020-12-07 15:31:52.000000', 'Miguel', 1),
(2, '5e55b4c6-ba1b-4c37-bdba-3cd20acfd58f', 'Badge', 'attribute', '2020-12-08 15:31:52.000000', 'Miguel', 1),
(3, '48e05969-02ad-444b-83ce-a20020637e5d', 'Badge', 'attribute', '2020-12-06 15:31:52.000000', 'jerome', 1);

--
-- Dumping data for table `point_scale_entity`
--

INSERT INTO `point_scale_entity` (`id`, `name`, `scale`, `application_id`) VALUES
(1, 'PS1', 201, 2),
(2, 'PS2', 58, 3);

--
-- Dumping data for table `point_scale_reward_entity`
--

INSERT INTO `point_scale_reward_entity` (`id`, `iduser`, `amount`, `timestamp`, `application_id`, `point_scale_entity_id`) VALUES
(1, 'affeab9b-8171-4853-83be-299bb35eba8f', 2, '2020-12-01 15:37:50.000000', 2, 1),
(2, '35ac951e-33c4-44e4-af54-e702c038af50', 35, '2020-12-02 15:37:50.000000', 3, 2),
(3, '5e55b4c6-ba1b-4c37-bdba-3cd20acfd58f', 90, '2020-12-10 05:35:55.000000', 1, 1);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
