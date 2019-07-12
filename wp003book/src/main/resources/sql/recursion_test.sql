/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50715
 Source Host           : localhost:3306
 Source Schema         : test

 Target Server Type    : MySQL
 Target Server Version : 50715
 File Encoding         : 65001

 Date: 12/07/2019 14:19:03
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for recursion_test
-- ----------------------------
DROP TABLE IF EXISTS `recursion_test`;
CREATE TABLE `recursion_test`  (
  `id` int(11) NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `pid` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of recursion_test
-- ----------------------------
INSERT INTO `recursion_test` VALUES (1, 'r1', 0);
INSERT INTO `recursion_test` VALUES (2, 'r2', 1);
INSERT INTO `recursion_test` VALUES (3, 'r3', 2);
INSERT INTO `recursion_test` VALUES (4, 'r4', 3);
INSERT INTO `recursion_test` VALUES (5, 'r5', 4);
INSERT INTO `recursion_test` VALUES (6, 'r6', 4);
INSERT INTO `recursion_test` VALUES (7, 'r7', 4);
INSERT INTO `recursion_test` VALUES (8, 'r8', 5);

SET FOREIGN_KEY_CHECKS = 1;
