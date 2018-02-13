-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: questionnaire
-- ------------------------------------------------------
-- Server version	5.7.18-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `answer`
--

DROP TABLE IF EXISTS `answer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `answer` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Уникальный идентификатор',
  `content` text NOT NULL COMMENT 'Поле, хранящее содержание (текст) ответа',
  `author_id` int(11) NOT NULL COMMENT 'Поле, хранящее идентификатор автора ответа',
  `creation_time` datetime NOT NULL COMMENT 'Поле, хранящее время создание данного ответа',
  `question_id` int(11) NOT NULL COMMENT 'Поле, хранящее идентификатор вопроса, к которому был сделан данный ответ',
  PRIMARY KEY (`id`,`question_id`,`author_id`),
  KEY `question_idx` (`question_id`),
  KEY `question_author_idx` (`author_id`,`question_id`),
  CONSTRAINT `author_id` FOREIGN KEY (`author_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `question_id` FOREIGN KEY (`question_id`) REFERENCES `question` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8 COMMENT='Таблица, представляющая ответ пользователя на какой-либо вопрос.';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `answer`
--

LOCK TABLES `answer` WRITE;
/*!40000 ALTER TABLE `answer` DISABLE KEYS */;
INSERT INTO `answer` VALUES (1,'Wow, does that work?',2,'2016-12-26 02:11:00',7),(2,'This is the last random sentence I will be writing and I am going to stop mid-sent',1,'2016-12-28 03:46:00',4),(3,'Tom got a small piece of pie.',4,'2017-02-10 06:50:00',11),(4,'Cats are good pets, for they are clean and are not noisy.',5,'2017-02-19 07:18:00',1),(5,'Sometimes, all you need to do is completely make an ass of yourself and laugh it off to realise that life isn’t so bad after all.',3,'2017-03-16 07:51:00',8),(6,'I am never at home on Sundays.',2,'2017-04-06 07:53:00',3),(7,'Abstraction is often one floor above you.',5,'2017-04-08 08:34:00',5),(8,'I\'d rather be a bird than a fish.',1,'2017-05-16 08:48:00',9),(9,'Please wait outside of the house.',6,'2017-05-18 08:49:00',6),(10,'The river stole the gods.',2,'2017-06-12 10:02:00',8),(11,'Someone I know recently combined Maple Syrup & buttered Popcorn thinking it would taste like caramel popcorn. It didn’t and they don’t recommend anyone else do it either.',3,'2017-08-14 11:34:00',4),(12,'A song can make or ruin a person’s day if they let it get to them.',5,'2017-09-17 17:03:00',1),(13,'When I was little I had a car door slammed shut on my hand. I still remember it quite vividly.',1,'2017-11-12 17:10:00',6),(14,'She works two jobs to make ends meet; at least, that was her reason for not having time to join us.',5,'2017-11-29 18:53:00',3),(15,'If you like tuna and tomato sauce- try combining the two. It’s really not as bad as it sounds.',2,'2017-12-13 22:28:00',4),(20,'test',1,'2018-02-13 01:32:03',3),(21,'dd',1,'2018-02-13 01:41:05',1),(22,'sd',1,'2018-02-13 01:42:01',8),(23,'12',1,'2018-02-13 11:34:28',5);
/*!40000 ALTER TABLE `answer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mark`
--

DROP TABLE IF EXISTS `mark`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mark` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Уникальный идентификатор',
  `user_id` int(11) NOT NULL COMMENT 'Поле, хранящее идентификатор пользователя, который поставил оценку',
  `answer_id` int(11) NOT NULL COMMENT 'Поле, хранящее идентификатор ответа, которому была дана оценка',
  `value` tinyint(4) NOT NULL COMMENT 'Поле, хранящее значение оценки пользователя (от 1 до 10)',
  PRIMARY KEY (`id`,`user_id`,`answer_id`),
  KEY `user_idx` (`user_id`),
  KEY `answer_idx` (`answer_id`),
  CONSTRAINT `answer` FOREIGN KEY (`answer_id`) REFERENCES `answer` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8 COMMENT='Таблица, представляющая оценки, который пользователи выставляют ответам на вопросы';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mark`
--

LOCK TABLES `mark` WRITE;
/*!40000 ALTER TABLE `mark` DISABLE KEYS */;
INSERT INTO `mark` VALUES (26,1,8,4),(28,3,6,3),(29,3,14,4),(30,3,3,4),(31,3,10,1),(32,4,2,4),(33,4,11,4),(34,4,15,2),(35,2,4,4);
/*!40000 ALTER TABLE `mark` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `question`
--

DROP TABLE IF EXISTS `question`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `question` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Уникальный идентификатор',
  `topic` varchar(255) NOT NULL COMMENT 'Поле, хранящее тему вопроса',
  `content` text NOT NULL COMMENT 'Поле, хранящее содержание (описание) самого вопроса',
  `author_id` int(11) NOT NULL COMMENT 'Поле, хранящее идентификатор пользователя, который задал данный вопрос',
  `creation_time` datetime NOT NULL COMMENT 'Поле, хранящее время создание данного вопроса',
  PRIMARY KEY (`id`,`author_id`),
  KEY `author_idx` (`author_id`),
  CONSTRAINT `author` FOREIGN KEY (`author_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 COMMENT='Таблица, представляющая вопрос, заданный пользователем';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `question`
--

LOCK TABLES `question` WRITE;
/*!40000 ALTER TABLE `question` DISABLE KEYS */;
INSERT INTO `question` VALUES (1,'Viruses','My computer is freezing. I think it\'s viruses. How can I remove them?',2,'2017-11-11 11:11:07'),(3,'Update value in Excel Field based on Value in another','I am trying to update the value of Cell A1 to reflect the value in B1, but without using formulas in Cell A1. For Example, if the value in B1=5, then By default the value in A1 should be updated to 5. The user needs to be able to type in a number in A1 if B1 is blank, but once B1 has a value, then A1 should always reflect that value. I have tried VBA but it doesn\'t work. I am hoping there is a way to do this without VBA. Any help is appreciated.',3,'2017-12-22 09:02:12'),(4,'MySql: Optimizing Subquery with index_merge','I want to lists all users and the status of the newsletter-subscribtion. Since someone doesn\'t need to be a user if subscribed to the newsletter, I\'m doing something like:',4,'2017-12-22 07:12:01'),(5,'Can I get Unix timestamp automatically converted to a DATETIME column when importing from CSV to a (My)SQL database?','I created a table that has a timestamp DATETIME column and I am trying to import data from CSV files that have Unix timestamped rows.',5,'2017-12-20 10:22:09'),(6,'Conditional CASE Statement (Domo)','I\'ve run multiple searches both her on Stack OverFlow as well as with Google and have not found anything that helps with this question. We\'re trying to determine the number of customers that have to call more than a certain number of times in order to have their concern/issue resolved, i.e. those who are not able to receive first call resolution.',3,'2016-12-20 10:22:09'),(7,'JAXB - Ignore an XML element','I have an XML',2,'2016-03-16 10:55:00'),(8,'R : convert character date “Dec 31, 2016 07:29 PM” to 31/12/2016 19:29:00','I want to convert character date in format \"Dec 31, 2016 07:29 PM\" to 31/12/2016 19:29:00 in R.',3,'2016-04-29 11:30:00'),(9,'How to get the the account holder details from Indian UID (Aadhaar) Number in asp.net?','How to get the the account holder details from Indian UID (Aadhaar) Number in .net',1,'2017-01-31 11:50:00'),(10,'Point-in-time build with Gradle','Say I have modifiers in version numbers like \"3.+\" instead of specific version like \"3.1.5\"',4,'2017-04-18 12:20:00'),(11,'Handling and visualization of large data sets?','I have a tool I made for work to visualize trace data generated by an embedded processor. The RTS for it allows a hook to be called whenever a task switch occurs. I use this to generate a data file containing the current task id, new task id, and a time value. The test tool uses this data to create a graph of task transitions and execution times. The file also contains other useful debug markers. This is fine when the files are smaller(100MB or less). If they get big, they can be slow to deal with or simply crash the simple test GUI. I have implemented the drawing code in both GDI and OpenGL.',3,'2017-05-04 16:00:00'),(13,'[X, Y coordinates of SKSpriteNode do not change after the node has been moved','[I have the following code to move a SKSpriteNode from a position off-screen to a position on-screen. spawnNode creates the node with a starting position off-screen. The nodePositions is an array of struct containing x, y and Zposition points on-screen. The code does move the node to the position that I want. However, I want to use the current node position to later position more nodes. The print statements show the same result. Pre & Post are the same. This makes no sense to me. Can anyone explain this to me? It looks like may have to make the successive node\'s children. I didn\'t want to do this.',1,'2017-12-11 12:11:07');
/*!40000 ALTER TABLE `question` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'Уникальный идентификатор',
  `role` tinyint(4) NOT NULL DEFAULT '2' COMMENT 'Поле, хранящее роль',
  `login` varchar(45) NOT NULL COMMENT 'Поле, хранящее логин пользователя',
  `password` varchar(45) NOT NULL COMMENT 'Поле, хранящее пароль пользователя',
  `email` varchar(45) NOT NULL COMMENT 'Поле, хранящее электронную почту пользователя',
  `name` varchar(45) DEFAULT NULL COMMENT 'Поле, хранящее имя пользователя',
  `surname` varchar(45) DEFAULT NULL COMMENT 'Поле, хранящее фамилию пользователя',
  `registration_time` datetime NOT NULL COMMENT 'Поле, хранящее время, когда пользователь создал свой аккаунт.',
  `bio` varchar(255) DEFAULT NULL COMMENT 'Поле, в котором хранится описание пользователя. Каждый пользователь может заполнить краткую информацию о себе',
  `gender` enum('male','female','other') NOT NULL,
  `banned` bit(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (`id`),
  KEY `login_password_idx` (`login`,`password`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8 COMMENT='Таблица, в которой хранятся данные пользователя: роль, язык, логин, пароль, электронную почту, имя, фамилию, время регистрации, дату рождения';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,0,'liarkajn','1Password','liarkajn@gmail.com','Alexey','Tchaikovsky','2017-10-20 00:00:00','I\'m administrator. You must be afraid of me','male','\0'),(2,2,'christmas','1Password','christmas@gmail.com',NULL,NULL,'2017-10-20 00:00:00','Christmas is an annual festival commemorating the birth of Jesus Christ, observed most commonly on December 25','male','\0'),(3,0,'mYspace1','12QsT33TUC','uw8185322@rambler.ru','Nohjq','Manmeww','2017-07-12 15:22:01','Some words about me : I\'m awesome!','other','\0'),(4,2,'Werner','QWElknasd#sd12','Werner@mail.ru','Alex','Fridman','2017-09-22 12:02:31',NULL,'male','\0'),(5,2,'Morpheu5','ASD76f#ASD76f#','Morpheu5@gmail.com',NULL,NULL,'2011-07-12 03:15:12','Oompa Loompa of (computer) science, graphic designer, wannabe rockstar.','female',''),(6,2,'user2913809','myPassword23','ukoro@gmail.com',NULL,NULL,'2017-12-22 13:12:12',NULL,'male','\0'),(7,2,'russian','1loverussian','georgievsk@mail.ru','Алексей','Пушкин','2012-04-11 01:12:32','','male','\0'),(8,0,'testr','1Password','tes12t@gmal.com','Lucy','','2018-01-22 07:59:45','I\'m happy person I\'m happy person I\'m happy person I\'m happy person ','female','\0'),(9,2,'testLogin','1Password','testEmail@gmail.com',NULL,NULL,'2018-01-26 01:32:44',NULL,'male','\0'),(17,0,'login','1Password','login@gmail.com','Kirill','Yarec','2018-02-06 02:36:17','BROTHER!!!','male',''),(18,2,'ligin','G1Password','aAa.combat@mail.com',NULL,NULL,'2018-02-10 13:36:20',NULL,'female','\0');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-02-13 17:54:11
