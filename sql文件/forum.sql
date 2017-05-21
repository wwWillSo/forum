/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50635
Source Host           : localhost:3306
Source Database       : forum

Target Server Type    : MYSQL
Target Server Version : 50635
File Encoding         : 65001

Date: 2017-05-21 17:48:50
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `admin`
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `admin_id` int(11) NOT NULL AUTO_INCREMENT,
  `admin_name` varchar(255) NOT NULL,
  `admin_password` varchar(255) NOT NULL,
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`admin_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES ('1', 'admin', 'cfe1aae5e2549ed514c898ea6bf0d3bca448c91da0bec57ba5eafe6c5f04ba43', '2017-05-20 22:33:08');
INSERT INTO `admin` VALUES ('2', 'WillSo', 'cfe1aae5e2549ed514c898ea6bf0d3bca448c91da0bec57ba5eafe6c5f04ba43', '2017-05-21 17:41:19');

-- ----------------------------
-- Table structure for `comment`
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment` (
  `comment_id` int(11) NOT NULL AUTO_INCREMENT,
  `post_id` int(11) NOT NULL,
  `login_id` int(11) NOT NULL,
  `content` longtext NOT NULL,
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`comment_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of comment
-- ----------------------------

-- ----------------------------
-- Table structure for `login`
-- ----------------------------
DROP TABLE IF EXISTS `login`;
CREATE TABLE `login` (
  `login_id` int(11) NOT NULL AUTO_INCREMENT,
  `login_name` varchar(255) NOT NULL,
  `login_password` varchar(255) NOT NULL,
  `login_regist_date` datetime NOT NULL,
  `status` int(2) NOT NULL DEFAULT '0',
  PRIMARY KEY (`login_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of login
-- ----------------------------
INSERT INTO `login` VALUES ('1', 'WillSo', 'cfe1aae5e2549ed514c898ea6bf0d3bca448c91da0bec57ba5eafe6c5f04ba43', '2017-05-17 23:42:16', '0');
INSERT INTO `login` VALUES ('3', 'WillSo2', '24168ad0541e3e5e62ac8bc65b4b51c74acdc03d5976ed18925436dc1afdf407', '2017-05-21 15:58:42', '0');

-- ----------------------------
-- Table structure for `post`
-- ----------------------------
DROP TABLE IF EXISTS `post`;
CREATE TABLE `post` (
  `post_id` int(11) NOT NULL AUTO_INCREMENT,
  `post_title` varchar(255) NOT NULL,
  `post_content` longtext NOT NULL,
  `post_type` int(2) NOT NULL DEFAULT '1',
  `post_create_time` datetime NOT NULL,
  `post_create_by` int(11) NOT NULL,
  `post_update_time` datetime NOT NULL,
  `post_update_by` int(11) NOT NULL,
  `count_like` int(11) NOT NULL DEFAULT '0',
  `like_content` longtext NOT NULL,
  `count_comment` int(11) NOT NULL DEFAULT '0',
  `status` int(2) NOT NULL DEFAULT '0',
  PRIMARY KEY (`post_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of post
-- ----------------------------
INSERT INTO `post` VALUES ('1', 'test', 'asdasdasdasdasd', '1', '2017-05-19 22:00:59', '1', '2017-05-19 22:01:05', '1', '0', '', '0', '0');
INSERT INTO `post` VALUES ('2', 'test', 'testtesttest', '1', '2017-05-19 22:01:23', '1', '2017-05-19 22:01:27', '1', '0', '', '0', '0');
INSERT INTO `post` VALUES ('3', 'test', 'testtesttest', '1', '2017-05-19 22:01:43', '1', '2017-05-19 22:01:46', '1', '0', '', '0', '0');
INSERT INTO `post` VALUES ('4', 'test', 'testtesttest', '1', '2017-05-19 22:01:58', '1', '2017-05-19 22:02:02', '1', '0', '', '0', '0');
INSERT INTO `post` VALUES ('5', 'test', 'testtesttestasdasdasd', '1', '2017-05-19 22:02:14', '1', '2017-05-21 17:29:33', '1', '0', '', '0', '0');
INSERT INTO `post` VALUES ('6', 'test', 'testtesttestasdasdasdasdsaadadadasdasdasdasdasdasdczczcc\r\nasdasdasd\r\nasdasdasdasd', '3', '2017-05-19 22:02:30', '1', '2017-05-21 17:29:19', '1', '0', '', '0', '0');

-- ----------------------------
-- Table structure for `post_type`
-- ----------------------------
DROP TABLE IF EXISTS `post_type`;
CREATE TABLE `post_type` (
  `post_type_id` int(11) NOT NULL AUTO_INCREMENT,
  `post_type_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`post_type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of post_type
-- ----------------------------
INSERT INTO `post_type` VALUES ('1', 'type1');
INSERT INTO `post_type` VALUES ('2', 'type2');
INSERT INTO `post_type` VALUES ('3', 'type3');
