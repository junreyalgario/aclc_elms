-- phpMyAdmin SQL Dump
-- version 4.9.0.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Mar 20, 2020 at 07:40 AM
-- Server version: 10.4.6-MariaDB
-- PHP Version: 7.3.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `elms`
--

-- --------------------------------------------------------

--
-- Table structure for table `employee`
--

CREATE TABLE `employee` (
  `p_id` int(10) NOT NULL,
  `employee_id` varchar(50) NOT NULL,
  `f_name` varchar(50) NOT NULL,
  `m_name` varchar(50) NOT NULL,
  `l_name` varchar(50) NOT NULL,
  `gender` varchar(50) NOT NULL,
  `dob` date NOT NULL,
  `contact_no` varchar(50) NOT NULL,
  `address` varchar(50) NOT NULL,
  `department` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `date_added` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `employee`
--

INSERT INTO `employee` (`p_id`, `employee_id`, `f_name`, `m_name`, `l_name`, `gender`, `dob`, `contact_no`, `address`, `department`, `password`, `date_added`) VALUES
(1, 'admin', 'Junrey', 'P', 'Dev', 'Male', '1992-02-23', '09955426109', 'Palo Layte', 'Admin', 'T7SilN4y7eVX2D4UgiU0khot0r4/1abVMFi3alcc39U=$/PortHRwbp3xBf8ta6/O34mFQn1MTxZ8MSOwQfpUmCM=', '2020-03-20 05:40:47'),
(13, 'CL1001', 'Bill', 'Gates', 'Microsoft', 'Male', '2000-03-15', '09955426109', 'Tacloban City, Philippines', 'College', 'AtrDOWhHFZ1ElTSvmt9q6JintRLdnnxlVYTcDmJscgc=$+Zy4ogBEugLdiIm8Ha1dmZpWldiuCRV/dan4HqlqTnk=', '2020-03-20 06:36:21'),
(14, 'CL1002', 'Mark', 'Zuck', 'Facebook', 'Male', '2002-03-06', '09955426109', 'Marasbaras, Tacloban City', 'College', 'T7SilN4y7eVX2D4UgiU0khot0r4/1abVMFi3alcc39U=$/PortHRwbp3xBf8ta6/O34mFQn1MTxZ8MSOwQfpUmCM=', '2020-03-20 05:40:45');

-- --------------------------------------------------------

--
-- Table structure for table `leave_request`
--

CREATE TABLE `leave_request` (
  `leave_request_id` int(10) NOT NULL,
  `start_date` date NOT NULL,
  `end_date` date NOT NULL,
  `reason` text NOT NULL,
  `command` text NOT NULL,
  `status` varchar(50) DEFAULT NULL,
  `seen` tinyint(4) NOT NULL,
  `leave_type_id` int(11) NOT NULL,
  `p_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `leave_request`
--

INSERT INTO `leave_request` (`leave_request_id`, `start_date`, `end_date`, `reason`, `command`, `status`, `seen`, `leave_type_id`, `p_id`) VALUES
(1, '2020-03-22', '2020-03-31', 'Naabat ak sintomas hit orona na virus. \r\nNakadamo na ak balik cr haha. \r\nMag self quarantine ak anay.', 'Hahaha sige kay bangin ka panapon nganhi\nACLC dilikado na.', 'Accepted', 1, 1, 13);

-- --------------------------------------------------------

--
-- Table structure for table `leave_type`
--

CREATE TABLE `leave_type` (
  `leave_type_id` int(10) NOT NULL,
  `leave_name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `leave_type`
--

INSERT INTO `leave_type` (`leave_type_id`, `leave_name`) VALUES
(1, 'Sick Leave'),
(2, 'Maternity');

-- --------------------------------------------------------

--
-- Table structure for table `message`
--

CREATE TABLE `message` (
  `message_id` int(10) NOT NULL,
  `message` varchar(50) NOT NULL,
  `sent_date` timestamp NOT NULL DEFAULT current_timestamp(),
  `status` int(11) NOT NULL,
  `reciever` int(11) NOT NULL,
  `sender` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `message`
--

INSERT INTO `message` (`message_id`, `message`, `sent_date`, `status`, `reciever`, `sender`) VALUES
(1, 'This message is a test.', '2020-03-20 06:23:52', 0, 13, 1),
(2, 'This message is a test.', '2020-03-20 06:23:54', 0, 14, 1),
(4, 'Hey Bill! San o ka masulod?', '2020-03-20 06:24:59', 0, 13, 1);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `employee`
--
ALTER TABLE `employee`
  ADD PRIMARY KEY (`p_id`);

--
-- Indexes for table `leave_request`
--
ALTER TABLE `leave_request`
  ADD PRIMARY KEY (`leave_request_id`),
  ADD KEY `FK_leave_request_leave_type` (`leave_type_id`),
  ADD KEY `FK_leave_request_employee` (`p_id`);

--
-- Indexes for table `leave_type`
--
ALTER TABLE `leave_type`
  ADD PRIMARY KEY (`leave_type_id`);

--
-- Indexes for table `message`
--
ALTER TABLE `message`
  ADD PRIMARY KEY (`message_id`),
  ADD KEY `FK_message_employee` (`reciever`),
  ADD KEY `FK_message_employee_2` (`sender`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `employee`
--
ALTER TABLE `employee`
  MODIFY `p_id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT for table `leave_request`
--
ALTER TABLE `leave_request`
  MODIFY `leave_request_id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `leave_type`
--
ALTER TABLE `leave_type`
  MODIFY `leave_type_id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT for table `message`
--
ALTER TABLE `message`
  MODIFY `message_id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `leave_request`
--
ALTER TABLE `leave_request`
  ADD CONSTRAINT `FK_leave_request_employee` FOREIGN KEY (`p_id`) REFERENCES `employee` (`p_id`),
  ADD CONSTRAINT `FK_leave_request_leave_type` FOREIGN KEY (`leave_type_id`) REFERENCES `leave_type` (`leave_type_id`);

--
-- Constraints for table `message`
--
ALTER TABLE `message`
  ADD CONSTRAINT `FK_message_employee` FOREIGN KEY (`reciever`) REFERENCES `employee` (`p_id`),
  ADD CONSTRAINT `FK_message_employee_2` FOREIGN KEY (`sender`) REFERENCES `employee` (`p_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
