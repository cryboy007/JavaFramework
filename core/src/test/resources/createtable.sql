use test;
DROP TABLE IF EXISTS `t_test`;
CREATE TABLE `t_test` (
  `id` bigint(20) NOT NULL auto_increment,
  `name` varchar(32) default NULL,
  `code_desc` varchar(32) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

INSERT INTO `t_test` VALUES ('1', 'stsetstst', 'ssssssss');
INSERT INTO `t_test` VALUES ('2', 'tttt', 'tttttttt');
INSERT INTO `t_test` VALUES ('3', 'tttt', 'tttttttt');
INSERT INTO `t_test` VALUES ('4', 'tttt', 'tttttttt');