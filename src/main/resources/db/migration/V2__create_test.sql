
-- ----------------------------
-- Table structure for dict_type
-- ----------------------------
DROP TABLE IF EXISTS `dict_type`;
CREATE TABLE IF NOT EXISTS `dict_type` (
  `dict_type_code` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL DEFAULT '' COMMENT '字典类型名称',
  `remark` varchar(255) NOT NULL DEFAULT '' COMMENT '字典类型描述',
  `editable` tinyint(1) NOT NULL DEFAULT '1' COMMENT '该项是否可编辑',
  `creator` varchar(255) DEFAULT NULL COMMENT '创建者, 无创建者视为系统创建',
  PRIMARY KEY (`dict_type_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='字典类型表';

DROP TABLE IF EXISTS `dict_detail`;
CREATE TABLE IF NOT EXISTS `dict_detail` (
  `id` varchar(255) NOT NULL,
  `dict_type_id` varchar(255) NOT NULL COMMENT '字典类型外键',
  `name` varchar(255) NOT NULL COMMENT '字典明细名称',
  `remark` varchar(255) DEFAULT NULL COMMENT '字典明细描述',
  `editable` tinyint(1) DEFAULT NULL COMMENT '否可编辑',
  PRIMARY KEY (`id`),
  KEY `dict_type_id` (`dict_type_id`),
  CONSTRAINT `dict_detail_ibfk_1` FOREIGN KEY (`dict_type_id`) REFERENCES `dict_type` (`dict_type_code`) ON DELETE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='字典值表';