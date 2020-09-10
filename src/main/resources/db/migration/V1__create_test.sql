
-- ----------------------------
-- Table structure for test_dict_type
-- ----------------------------
DROP TABLE IF EXISTS `test_dict_type`;
CREATE TABLE `test_dict_type` (
  `dict_type_code` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL DEFAULT '' COMMENT '字典类型名称',
  `remark` varchar(255) NOT NULL DEFAULT '' COMMENT '字典类型描述',
  `editable` tinyint(1) NOT NULL DEFAULT '1' COMMENT '该项是否可编辑',
  `creator` varchar(255) DEFAULT NULL COMMENT '创建者, 无创建者视为系统创建',
  PRIMARY KEY (`dict_type_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='字典类型表';
