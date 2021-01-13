-- ----------------------------
-- Table structure for b_qj_xtpz
-- ----------------------------
DROP TABLE IF EXISTS `b_qj_xtpz`;
CREATE TABLE `b_qj_xtpz`  (
  `id` int(32) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '变量名',
  `name_cn` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '变量中文名',
  `val` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '变量对应的配置值',
  `expand` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '代码项扩展',
  `description` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '代码项描述',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of b_qj_xtpz
-- ----------------------------

-- ----------------------------
-- Table structure for b_z_user
-- ----------------------------
DROP TABLE IF EXISTS `b_z_user`;
CREATE TABLE `b_z_user`  (
  `xlh` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '序列号UUID',
  `username` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
  `ip` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'ip地址',
  `email` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '邮箱',
  `zcsj` datetime(0) NULL DEFAULT NULL COMMENT '注册时间',
  `phone` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '电话号码',
  `photo` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `yhqx` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '1' COMMENT '权限',
  PRIMARY KEY (`xlh`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of b_z_user
-- ----------------------------
INSERT INTO `b_z_user` VALUES ('1bc9c099-d88a-4c27-b001-e135f8d74c77', 'test', 'test', '192.168.7.7', 'test@163.com', '2021-01-13 06:08:52', NULL, NULL, NULL);
INSERT INTO `b_z_user` VALUES ('c9cc9174d9894fee92223266df36f649', 'admin', '930109', '127.0.0.1', 'admin@qq.com', NULL, NULL, NULL, '0');
INSERT INTO `b_z_user` VALUES ('d931b50f1db44ee197e175c9d217c37b', 'godness', '888888', '127.0.0.1', 'godness@qq.com', NULL, NULL, NULL, '1');

SET FOREIGN_KEY_CHECKS = 1;
