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

DROP TABLE IF EXISTS `testtablob`;
CREATE TABLE `testtablob` (
  `id` bigint(20) NOT NULL auto_increment,
  `name` varchar(32) default NULL,
  `lob1` text,
  `lob2` longblob,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;