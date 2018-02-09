-- 数据库初始化脚本
-- 创建数据库

CREATE DATABASE seckill;
-- 使用数据库
USE seckill;
-- 创建秒杀库存表
CREATE TABLE seckill(
 `seckill_id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '商品库存ID',
 `name` VARCHAR(120) NOT NULL COMMENT '商品名称',
 `number` INT NOT NULL COMMENT '库存数量',
 `start_time` TIMESTAMP NOT NULL COMMENT '秒杀开始时间',
 `end_time` TIMESTAMP NOT NULL COMMENT '秒杀结束时间',
 `create_time` TIMESTAMP NOT NULL DEFAULT current_timestamp COMMENT '创建时间',
 PRIMARY KEY (seckill_id),
 KEY idx_start_time(start_time),
 KEY idx_end_time(end_time),
 KEY idx_create_time(create_time)
)ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT = '秒杀库存表';

-- 初始化数据
INSERT INTO seckill(name,number,start_time,end_time)
VALUE
 ('1000元秒杀iphonex',100,'2018-01-31 19:58:24','2018-02-01 19:58:24'),
 ('500元秒杀ipad2',200,'2018-01-31 19:58:24','2018-02-01 19:58:24'),
 ('300元秒杀小米5',300,'2018-01-31 19:58:24','2018-02-01 19:58:24'),
 ('3000元秒杀ThinkPad',400,'2018-01-31 19:58:24','2018-02-01 19:58:24');

-- 秒杀成功明细表
-- 用户登录认证相关的信息
CREATE TABLE success_killed (
 `seckill_id` BIGINT NOT NULL COMMENT '秒杀商品id',
 `user_phone` BIGINT NOT NULL COMMENT '用户手机号',
 `state` TINYINT NOT NULL DEFAULT -1 COMMENT '状态表示：-1:无效 0:成功 1:已付款 2:已发货',
 `create_time` TIMESTAMP NOT NULL DEFAULT current_timestamp COMMENT '创建时间',
 PRIMARY KEY (seckill_id,user_phone), /*联合主键*/
 KEY idx_create_time(create_time)
)ENGINE = InnoDB DEFAULT CHARSET = utf8 COMMENT = '秒杀成功明细表';

-- 链接数据库的控制台