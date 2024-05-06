/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50743
 Source Host           : localhost:3306
 Source Schema         : code_generate

 Target Server Type    : MySQL
 Target Server Version : 50743
 File Encoding         : 65001

 Date: 06/05/2024 19:13:46
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for generator
-- ----------------------------
DROP TABLE IF EXISTS `generator`;
CREATE TABLE `generator`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '名称',
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '描述',
  `basePackage` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '基础包',
  `versionControl` bit(1) NULL DEFAULT NULL COMMENT 'git版本控制',
  `forcedInteractiveSwitch` bit(1) NULL DEFAULT NULL COMMENT '强制交互式开关',
  `version` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '版本',
  `author` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '作者',
  `tags` json NULL COMMENT '标签列表（json 数组）',
  `picture` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '图片',
  `fileConfig` json NULL COMMENT '文件配置（json字符串）',
  `modelConfig` json NULL COMMENT '模型配置（json字符串）',
  `distPath` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL COMMENT '代码生成器产物路径',
  `status` int(11) NOT NULL DEFAULT 0 COMMENT '状态',
  `thumbNum` int(11) NOT NULL DEFAULT 0 COMMENT '点赞数',
  `favourNum` int(11) NOT NULL DEFAULT 0 COMMENT '收藏数',
  `userId` bigint(20) NOT NULL COMMENT '创建用户 id',
  `createTime` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `updateTime` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `isDelete` tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_userId`(`userId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '代码生成器' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of generator
-- ----------------------------
INSERT INTO `generator` VALUES (1, 'ACM-template', 'ACM模板项目生成器，犹如编程世界的魔法师，为程序员们创造了无穷可能。它就像一本充满智慧的魔法书，指引着你进入算法与数据结构的奇妙世界，让你在代码的舞台上翩翩起舞，展现无限的魔法与智慧。', 'com.azhang', b'0', b'1', '1.0', '小张', '[\"Java\"]', 'https://pic.yupi.icu/1/_r0_c1851-bf115939332e.jpg', '{\"type\": null, \"files\": [{\"type\": \"file\", \"files\": null, \"groupKey\": null, \"condition\": null, \"groupName\": null, \"inputPath\": \"src/com/azhang/acm/MainTemplate.java.ftl\", \"outputPath\": \"src/com/azhang/acm/MainTemplate.java\", \"generateType\": \"dynamic\"}, {\"type\": \"group\", \"files\": [{\"type\": \"file\", \"files\": null, \"groupKey\": null, \"condition\": \"needGit\", \"groupName\": null, \"inputPath\": \".gitignore\", \"outputPath\": \".gitignore\", \"generateType\": \"static\"}, {\"type\": \"file\", \"files\": null, \"groupKey\": null, \"condition\": null, \"groupName\": null, \"inputPath\": \"README.md\", \"outputPath\": \"README.md\", \"generateType\": \"static\"}], \"groupKey\": \"acmTemplate\", \"condition\": \"needGit\", \"groupName\": \"gitignore 和 README.md1\", \"inputPath\": null, \"outputPath\": null, \"generateType\": null}], \"inputRootPath\": null, \"outputRootPath\": null, \"sourceRootPath\": null}', '{\"models\": [{\"abbr\": \"n\", \"type\": \"Boolean\", \"models\": null, \"groupKey\": null, \"condition\": null, \"fieldName\": \"needGit\", \"groupName\": null, \"allArgsStr\": null, \"description\": \"是否生成.gitignore文件\", \"defaultValue\": true}, {\"abbr\": \"l\", \"type\": \"Boolean\", \"models\": null, \"groupKey\": null, \"condition\": null, \"fieldName\": \"loop\", \"groupName\": null, \"allArgsStr\": null, \"description\": \"是否生成循环\", \"defaultValue\": false}, {\"abbr\": null, \"type\": \"MainTemplate\", \"models\": [{\"abbr\": \"a\", \"type\": \"String\", \"models\": null, \"groupKey\": null, \"condition\": null, \"fieldName\": \"author\", \"groupName\": null, \"allArgsStr\": null, \"description\": \"作者注释\", \"defaultValue\": \"azhang\"}, {\"abbr\": \"o\", \"type\": \"String\", \"models\": null, \"groupKey\": null, \"condition\": null, \"fieldName\": \"outputText\", \"groupName\": null, \"allArgsStr\": null, \"description\": \"输出信息\", \"defaultValue\": \"sum = \"}], \"groupKey\": \"mainTemplate\", \"condition\": \"loop\", \"fieldName\": null, \"groupName\": \"核心模板\", \"allArgsStr\": null, \"description\": \"核心模板参数\", \"defaultValue\": null}]}', '/generator_dist/1/BqEJEMwB-ACM-template (1).zip', 0, 1, 0, 1, '2024-02-04 21:57:59', '2024-02-23 09:54:11', 0);
INSERT INTO `generator` VALUES (2, 'Spring Boot初始化模板', 'Spring Boot初始化模板代码生成器，为程序员们点亮了一盏灯塔，指引着前行的方向。它犹如春风化雨，让开发过程轻盈愉悦，仿佛自带魔法，赋予项目生机与活力。无论何时何地，它都像一个忠诚的朋友般，陪伴着你，护航你的代码之旅', 'com.azhang', NULL, NULL, '1.0', '小张', '[\"Java\"]', 'https://pic.yupi.icu/1/_r0_c0726-7e30f8db802a.jpg', '{\"files\": [{\"type\": \"group\", \"files\": [{\"type\": \"file\", \"inputPath\": \"src/main/resources/mapper/PostMapper.xml.ftl\", \"outputPath\": \"src/main/resources/mapper/PostMapper.xml\", \"generateType\": \"dynamic\"}, {\"type\": \"file\", \"inputPath\": \"src/main/java/com/yupi/springbootinit/model/entity/Post.java.ftl\", \"outputPath\": \"src/main/java/com/yupi/springbootinit/model/entity/Post.java\", \"generateType\": \"dynamic\"}, {\"type\": \"file\", \"inputPath\": \"src/main/java/com/yupi/springbootinit/service/impl/PostServiceImpl.java.ftl\", \"outputPath\": \"src/main/java/com/yupi/springbootinit/service/impl/PostServiceImpl.java\", \"generateType\": \"dynamic\"}, {\"type\": \"file\", \"inputPath\": \"src/main/java/com/yupi/springbootinit/model/dto/post/PostQueryRequest.java.ftl\", \"outputPath\": \"src/main/java/com/yupi/springbootinit/model/dto/post/PostQueryRequest.java\", \"generateType\": \"dynamic\"}, {\"type\": \"file\", \"inputPath\": \"src/main/java/com/yupi/springbootinit/controller/PostController.java.ftl\", \"outputPath\": \"src/main/java/com/yupi/springbootinit/controller/PostController.java\", \"generateType\": \"dynamic\"}, {\"type\": \"file\", \"inputPath\": \"src/main/java/com/yupi/springbootinit/service/PostService.java.ftl\", \"outputPath\": \"src/main/java/com/yupi/springbootinit/service/PostService.java\", \"generateType\": \"dynamic\"}, {\"type\": \"file\", \"inputPath\": \"src/main/java/com/yupi/springbootinit/mapper/PostMapper.java.ftl\", \"outputPath\": \"src/main/java/com/yupi/springbootinit/mapper/PostMapper.java\", \"generateType\": \"dynamic\"}, {\"type\": \"file\", \"inputPath\": \"src/main/java/com/yupi/springbootinit/model/dto/post/PostUpdateRequest.java.ftl\", \"outputPath\": \"src/main/java/com/yupi/springbootinit/model/dto/post/PostUpdateRequest.java\", \"generateType\": \"dynamic\"}, {\"type\": \"file\", \"inputPath\": \"src/main/java/com/yupi/springbootinit/model/dto/post/PostAddRequest.java.ftl\", \"outputPath\": \"src/main/java/com/yupi/springbootinit/model/dto/post/PostAddRequest.java\", \"generateType\": \"dynamic\"}], \"groupKey\": \"post\", \"condition\": \"needPost\", \"groupName\": \"帖子文件组\"}, {\"type\": \"file\", \"condition\": \"needPost && needEs\", \"inputPath\": \"src/main/java/com/yupi/springbootinit/model/dto/post/PostEsDTO.java.ftl\", \"outputPath\": \"src/main/java/com/yupi/springbootinit/model/dto/post/PostEsDTO.java\", \"generateType\": \"dynamic\"}, {\"type\": \"file\", \"inputPath\": \"src/main/java/com/yupi/springbootinit/model/entity/User.java.ftl\", \"outputPath\": \"src/main/java/com/yupi/springbootinit/model/entity/User.java\", \"generateType\": \"dynamic\"}, {\"type\": \"file\", \"inputPath\": \"src/main/resources/application.yml.ftl\", \"outputPath\": \"src/main/resources/application.yml\", \"generateType\": \"dynamic\"}, {\"type\": \"file\", \"inputPath\": \"src/main/java/com/yupi/springbootinit/common/ErrorCode.java.ftl\", \"outputPath\": \"src/main/java/com/yupi/springbootinit/common/ErrorCode.java\", \"generateType\": \"dynamic\"}, {\"type\": \"file\", \"inputPath\": \"src/main/java/com/yupi/springbootinit/model/enums/UserRoleEnum.java.ftl\", \"outputPath\": \"src/main/java/com/yupi/springbootinit/model/enums/UserRoleEnum.java\", \"generateType\": \"dynamic\"}, {\"type\": \"file\", \"inputPath\": \"src/main/java/com/yupi/springbootinit/config/MyBatisPlusConfig.java.ftl\", \"outputPath\": \"src/main/java/com/yupi/springbootinit/config/MyBatisPlusConfig.java\", \"generateType\": \"dynamic\"}, {\"type\": \"file\", \"inputPath\": \".gitignore\", \"outputPath\": \".gitignore\", \"generateType\": \"static\"}, {\"type\": \"file\", \"inputPath\": \"src/main/java/com/yupi/springbootinit/MainApplication.java.ftl\", \"outputPath\": \"src/main/java/com/yupi/springbootinit/MainApplication.java\", \"generateType\": \"dynamic\"}, {\"type\": \"file\", \"inputPath\": \"src/main/java/com/yupi/springbootinit/common/PageRequest.java.ftl\", \"outputPath\": \"src/main/java/com/yupi/springbootinit/common/PageRequest.java\", \"generateType\": \"dynamic\"}, {\"type\": \"file\", \"inputPath\": \"src/main/java/com/yupi/springbootinit/model/dto/user/UserAddRequest.java.ftl\", \"outputPath\": \"src/main/java/com/yupi/springbootinit/model/dto/user/UserAddRequest.java\", \"generateType\": \"dynamic\"}, {\"type\": \"file\", \"inputPath\": \"src/test/.DS_Store\", \"outputPath\": \"src/test/.DS_Store\", \"generateType\": \"static\"}, {\"type\": \"file\", \"inputPath\": \"pom.xml.ftl\", \"outputPath\": \"pom.xml\", \"generateType\": \"dynamic\"}, {\"type\": \"file\", \"inputPath\": \"src/main/java/com/yupi/springbootinit/model/dto/user/UserUpdateRequest.java.ftl\", \"outputPath\": \"src/main/java/com/yupi/springbootinit/model/dto/user/UserUpdateRequest.java\", \"generateType\": \"dynamic\"}, {\"type\": \"file\", \"inputPath\": \"src/main/java/com/yupi/springbootinit/mapper/UserMapper.java.ftl\", \"outputPath\": \"src/main/java/com/yupi/springbootinit/mapper/UserMapper.java\", \"generateType\": \"dynamic\"}, {\"type\": \"file\", \"inputPath\": \"src/main/java/com/yupi/springbootinit/controller/UserController.java.ftl\", \"outputPath\": \"src/main/java/com/yupi/springbootinit/controller/UserController.java\", \"generateType\": \"dynamic\"}, {\"type\": \"file\", \"inputPath\": \"src/main/java/com/yupi/springbootinit/constant/UserConstant.java.ftl\", \"outputPath\": \"src/main/java/com/yupi/springbootinit/constant/UserConstant.java\", \"generateType\": \"dynamic\"}, {\"type\": \"file\", \"inputPath\": \"src/.DS_Store\", \"outputPath\": \"src/.DS_Store\", \"generateType\": \"static\"}, {\"type\": \"file\", \"inputPath\": \"src/main/java/com/yupi/springbootinit/model/dto/user/UserQueryRequest.java.ftl\", \"outputPath\": \"src/main/java/com/yupi/springbootinit/model/dto/user/UserQueryRequest.java\", \"generateType\": \"dynamic\"}, {\"type\": \"file\", \"inputPath\": \"src/main/java/com/yupi/springbootinit/exception/ThrowUtils.java.ftl\", \"outputPath\": \"src/main/java/com/yupi/springbootinit/exception/ThrowUtils.java\", \"generateType\": \"dynamic\"}, {\"type\": \"file\", \"inputPath\": \"src/main/java/com/yupi/springbootinit/model/dto/user/UserLoginRequest.java.ftl\", \"outputPath\": \"src/main/java/com/yupi/springbootinit/model/dto/user/UserLoginRequest.java\", \"generateType\": \"dynamic\"}, {\"type\": \"file\", \"inputPath\": \"src/main/java/com/yupi/springbootinit/common/ResultUtils.java.ftl\", \"outputPath\": \"src/main/java/com/yupi/springbootinit/common/ResultUtils.java\", \"generateType\": \"dynamic\"}, {\"type\": \"file\", \"condition\": \"needCors\", \"inputPath\": \"src/main/java/com/yupi/springbootinit/config/CorsConfig.java.ftl\", \"outputPath\": \"src/main/java/com/yupi/springbootinit/config/CorsConfig.java\", \"generateType\": \"dynamic\"}, {\"type\": \"file\", \"inputPath\": \"README.md\", \"outputPath\": \"README.md\", \"generateType\": \"static\"}, {\"type\": \"file\", \"inputPath\": \"src/main/java/com/yupi/springbootinit/service/UserService.java.ftl\", \"outputPath\": \"src/main/java/com/yupi/springbootinit/service/UserService.java\", \"generateType\": \"dynamic\"}, {\"type\": \"file\", \"inputPath\": \"src/main/java/com/yupi/springbootinit/config/JsonConfig.java.ftl\", \"outputPath\": \"src/main/java/com/yupi/springbootinit/config/JsonConfig.java\", \"generateType\": \"dynamic\"}, {\"type\": \"file\", \"inputPath\": \"Dockerfile\", \"outputPath\": \"Dockerfile\", \"generateType\": \"static\"}, {\"type\": \"file\", \"inputPath\": \"src/main/java/com/yupi/springbootinit/model/dto/user/UserRegisterRequest.java.ftl\", \"outputPath\": \"src/main/java/com/yupi/springbootinit/model/dto/user/UserRegisterRequest.java\", \"generateType\": \"dynamic\"}, {\"type\": \"file\", \"inputPath\": \"src/main/java/com/yupi/springbootinit/service/impl/UserServiceImpl.java.ftl\", \"outputPath\": \"src/main/java/com/yupi/springbootinit/service/impl/UserServiceImpl.java\", \"generateType\": \"dynamic\"}, {\"type\": \"file\", \"inputPath\": \"src/main/java/com/yupi/springbootinit/exception/GlobalExceptionHandler.java.ftl\", \"outputPath\": \"src/main/java/com/yupi/springbootinit/exception/GlobalExceptionHandler.java\", \"generateType\": \"dynamic\"}, {\"type\": \"file\", \"inputPath\": \"src/main/java/com/yupi/springbootinit/common/DeleteRequest.java.ftl\", \"outputPath\": \"src/main/java/com/yupi/springbootinit/common/DeleteRequest.java\", \"generateType\": \"dynamic\"}, {\"type\": \"file\", \"condition\": \"needDocs\", \"inputPath\": \"src/main/java/com/yupi/springbootinit/config/Knife4jConfig.java.ftl\", \"outputPath\": \"src/main/java/com/yupi/springbootinit/config/Knife4jConfig.java\", \"generateType\": \"dynamic\"}, {\"type\": \"file\", \"inputPath\": \"src/main/java/com/yupi/springbootinit/common/BaseResponse.java.ftl\", \"outputPath\": \"src/main/java/com/yupi/springbootinit/common/BaseResponse.java\", \"generateType\": \"dynamic\"}, {\"type\": \"file\", \"inputPath\": \"src/main/resources/application.yml\", \"outputPath\": \"src/main/resources/application.yml\", \"generateType\": \"static\"}, {\"type\": \"file\", \"inputPath\": \"src/main/java/com/yupi/springbootinit/exception/BusinessException.java.ftl\", \"outputPath\": \"src/main/java/com/yupi/springbootinit/exception/BusinessException.java\", \"generateType\": \"dynamic\"}, {\"type\": \"file\", \"inputPath\": \"src/main/resources/mapper/UserMapper.xml.ftl\", \"outputPath\": \"src/main/resources/mapper/UserMapper.xml\", \"generateType\": \"dynamic\"}], \"sourceRootPath\": \"D:/Program Files/code/me/zhangPro/azhang-generator/azhang-generator-maker/.temp/1/springboot-init\"}', '{\"models\": [{\"type\": \"MysqlConfig\", \"models\": [{\"type\": \"String\", \"fieldName\": \"password\", \"description\": \"密码\", \"defaultValue\": \"123456\"}, {\"type\": \"String\", \"fieldName\": \"url\", \"description\": \"地址\", \"defaultValue\": \"jdbc:mysql://localhost:3306/my_db\"}, {\"type\": \"String\", \"fieldName\": \"username\", \"description\": \"用户名\", \"defaultValue\": \"root\"}], \"groupKey\": \"mysqlConfig\", \"groupName\": \"MySQL数据库配置\", \"description\": \"用于生成MySQL数据库配置\"}, {\"type\": \"DocsConfig\", \"models\": [{\"type\": \"String\", \"fieldName\": \"description\", \"description\": \"接口文档描述\", \"defaultValue\": \"springboot-init\"}, {\"type\": \"String\", \"fieldName\": \"title\", \"description\": \"接口文档标题\", \"defaultValue\": \"接口文档\"}, {\"type\": \"String\", \"fieldName\": \"version\", \"description\": \"接口文档版本\", \"defaultValue\": \"1.0\"}], \"groupKey\": \"docsConfig\", \"condition\": \"needDocs\", \"groupName\": \"接口文档配置\", \"description\": \"用于生成接口文档配置\"}, {\"type\": \"Boolean\", \"fieldName\": \"needDocs\", \"description\": \"是否开启接口文档功能\", \"defaultValue\": true}, {\"type\": \"Boolean\", \"fieldName\": \"needPost\", \"description\": \"是否开启帖子功能\", \"defaultValue\": true}, {\"type\": \"Boolean\", \"fieldName\": \"needCors\", \"description\": \"是否开启跨域功能\", \"defaultValue\": true}, {\"type\": \"Boolean\", \"fieldName\": \"needEs\", \"description\": \"是否开启ES功能\", \"defaultValue\": true}, {\"type\": \"String\", \"fieldName\": \"basePackage\", \"description\": \"基础包名\", \"defaultValue\": \"com.azhang\"}, {\"type\": \"Boolean\", \"fieldName\": \"needRedis\", \"description\": \"是否开启Redis功能\", \"defaultValue\": true}]}', '/generator_dist/1/qvlyusDC-springboot-init-generator-dist.zip', 0, 2, 2, 1, '2024-02-04 21:57:59', '2024-02-20 16:05:10', 0);
INSERT INTO `generator` VALUES (3, '阿张外卖', '阿张外卖项目生成器，就像是您的私人大厨，为您创造了一个无与伦比的美食王国。它犹如一位智慧的料理大师，为您准备了丰盛的代码盛宴，让您在外卖项目的旅程中尽情享用创意与便利的美味佳肴。', 'com.azhang', NULL, NULL, '1.0', '小张', '[\"Java\", \"前端\"]', 'https://pic.yupi.icu/1/_r1_c0cf7-f8e4bd865b4b.jpg', '{}', '{}', NULL, 0, 2, 1, 1, '2024-02-04 21:57:59', '2024-02-20 16:05:11', 0);
INSERT INTO `generator` VALUES (4, '阿张用户中心', '阿张用户中心项目生成器', 'com.azhang', NULL, NULL, '1.0', '小张', '[\"Java\", \"前端\"]', 'https://pic.yupi.icu/1/_r1_c1c15-79cdecf24aed.jpg', '{}', '{}', NULL, 0, 2, 2, 1, '2024-02-04 21:57:59', '2024-02-20 16:05:12', 0);
INSERT INTO `generator` VALUES (5, '阿张商城', '阿张商城项目生成器', 'com.azhang', b'0', b'1', '1.0', '小张', '[\"Java\", \"前端\"]', 'https://pic.yupi.icu/1/_r1_c0709-8e80689ac1da.jpg', '{\"type\": null, \"files\": null, \"inputRootPath\": null, \"outputRootPath\": null, \"sourceRootPath\": null}', '{\"models\": [{\"abbr\": null, \"type\": null, \"models\": null, \"groupKey\": null, \"condition\": null, \"fieldName\": \"1231\", \"groupName\": null, \"allArgsStr\": null, \"description\": null, \"defaultValue\": null}]}', NULL, 0, 0, 0, 1, '2024-02-04 21:57:59', '2024-02-09 17:41:34', 0);
INSERT INTO `generator` VALUES (6, '测试生成器', '测试添加111', 'com.azhang', b'1', b'1', '', '小张', '[\"java\"]', '', '{\"type\": null, \"files\": null, \"inputRootPath\": null, \"outputRootPath\": null, \"sourceRootPath\": null}', '{\"models\": null}', 'wwwwww', 0, 0, 0, 1, '2024-02-05 10:01:25', '2024-02-09 17:47:56', 1);

-- ----------------------------
-- Table structure for generator_favour
-- ----------------------------
DROP TABLE IF EXISTS `generator_favour`;
CREATE TABLE `generator_favour`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `generatorId` bigint(20) NOT NULL COMMENT '生成器 id',
  `userId` bigint(20) NOT NULL COMMENT '创建用户 id',
  `createTime` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `updateTime` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_postId`(`generatorId`) USING BTREE,
  INDEX `idx_userId`(`userId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 27 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci COMMENT = '代码生成器收藏表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of generator_favour
-- ----------------------------
INSERT INTO `generator_favour` VALUES (5, 3, 1, '2024-02-10 21:45:14', '2024-02-10 21:45:14');
INSERT INTO `generator_favour` VALUES (19, 2, 1, '2024-02-10 22:36:13', '2024-02-10 22:36:13');
INSERT INTO `generator_favour` VALUES (23, 4, 1, '2024-02-20 16:03:53', '2024-02-20 16:03:53');
INSERT INTO `generator_favour` VALUES (24, 1, 2, '2024-02-20 16:05:06', '2024-02-20 16:05:06');
INSERT INTO `generator_favour` VALUES (25, 2, 2, '2024-02-20 16:05:07', '2024-02-20 16:05:07');
INSERT INTO `generator_favour` VALUES (26, 4, 2, '2024-02-20 16:05:09', '2024-02-20 16:05:09');

-- ----------------------------
-- Table structure for generator_thumb
-- ----------------------------
DROP TABLE IF EXISTS `generator_thumb`;
CREATE TABLE `generator_thumb`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `generatorId` bigint(20) NOT NULL COMMENT '生成器 id',
  `userId` bigint(20) NOT NULL COMMENT '创建用户 id',
  `createTime` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `updateTime` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_postId`(`generatorId`) USING BTREE,
  INDEX `idx_userId`(`userId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 29 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci COMMENT = '代码生成器点赞表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of generator_thumb
-- ----------------------------
INSERT INTO `generator_thumb` VALUES (15, 4, 1, '2024-02-10 01:18:15', '2024-02-10 01:18:15');
INSERT INTO `generator_thumb` VALUES (16, 2, 1, '2024-02-10 21:45:09', '2024-02-10 21:45:09');
INSERT INTO `generator_thumb` VALUES (17, 3, 1, '2024-02-10 21:45:11', '2024-02-10 21:45:11');
INSERT INTO `generator_thumb` VALUES (18, 10, 1, '2024-02-10 21:45:18', '2024-02-10 21:45:18');
INSERT INTO `generator_thumb` VALUES (22, 1, 2, '2024-02-18 09:32:49', '2024-02-18 09:32:49');
INSERT INTO `generator_thumb` VALUES (25, 2, 2, '2024-02-20 16:05:10', '2024-02-20 16:05:10');
INSERT INTO `generator_thumb` VALUES (26, 3, 2, '2024-02-20 16:05:11', '2024-02-20 16:05:11');
INSERT INTO `generator_thumb` VALUES (27, 4, 2, '2024-02-20 16:05:12', '2024-02-20 16:05:12');
INSERT INTO `generator_thumb` VALUES (28, 1, 1, '2024-02-23 09:54:11', '2024-02-23 09:54:11');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `userAccount` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '账号',
  `userPassword` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '密码',
  `userName` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户昵称',
  `userAvatar` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户头像',
  `userProfile` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户简介',
  `userRole` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'user' COMMENT '用户角色：user/admin/ban',
  `createTime` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `updateTime` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `isDelete` tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否删除',
  `userEmail` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '用户邮箱',
  `accountStatus` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '1 正常 2冻结',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_unionId`(`userAccount`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1754500716679798791 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci COMMENT = '用户' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'admin', '6bff2b21e13232ae8cfaacdf150e6326', '超级管理员', 'https://gw.alipayobjects.com/zos/rmsportal/BiazfanxmamNRoxxVxka.png', '我有一头小毛驴我从来也不骑', 'admin', '2024-02-04 21:57:59', '2024-03-20 12:10:19', 0, '2*********@qq.com', '1');
INSERT INTO `user` VALUES (2, 'codeZhang', 'b03d227f78c0c79334fca76e7b8eb46a', '小张', 'https://gw.alipayobjects.com/zos/rmsportal/BiazfanxmamNRoxxVxka.png', '我有一头小毛驴我从来也不骑', 'user', '2024-02-04 21:57:59', '2024-03-20 11:39:44', 0, '2*********@qq.com', '2');
INSERT INTO `user` VALUES (1754500572760645633, '小张测试', 'b03d227f78c0c79334fca76e7b8eb46a', NULL, NULL, NULL, 'user', '2024-02-05 21:41:48', '2024-03-20 11:39:47', 1, '2*********@qq.com', '2');
INSERT INTO `user` VALUES (1754500716679798786, '小张测试1', 'b03d227f78c0c79334fca76e7b8eb46a', NULL, NULL, NULL, 'user', '2024-02-05 21:42:22', '2024-03-20 11:39:50', 1, '2*********@qq.com', '2');
INSERT INTO `user` VALUES (1754500716679798787, 'codezhang1', 'b03d227f78c0c79334fca76e7b8eb46a', NULL, NULL, NULL, 'user', '2024-03-18 16:33:35', '2024-03-20 11:40:07', 1, '2*********@qq.com', '2');
INSERT INTO `user` VALUES (1754500716679798788, '123123123', 'b03d227f78c0c79334fca76e7b8eb46a', NULL, NULL, NULL, 'user', '2024-03-18 16:36:00', '2024-03-20 11:40:07', 1, '2*********@qq.com', '2');
INSERT INTO `user` VALUES (1754500716679798789, 'codezhang11', 'b03d227f78c0c79334fca76e7b8eb46a', NULL, NULL, NULL, 'user', '2024-03-18 16:41:43', '2024-03-20 11:40:07', 1, '2*********@qq.com', '1');
INSERT INTO `user` VALUES (1754500716679798790, 'codezhang01', '9fed56f3369e1058817232c9562944b8', NULL, NULL, NULL, 'user', '2024-03-20 11:39:18', '2024-03-20 11:39:18', 0, '1*********@qq.com', '1');

SET FOREIGN_KEY_CHECKS = 1;
