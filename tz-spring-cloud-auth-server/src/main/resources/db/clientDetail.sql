# 客户端详情表
CREATE TABLE `oauth_client_details` (
                                        `client_id` varchar(256) COLLATE utf8mb4_general_ci NOT NULL,
                                        `resource_ids` varchar(256) COLLATE utf8mb4_general_ci DEFAULT NULL,
                                        `client_secret` varchar(256) COLLATE utf8mb4_general_ci DEFAULT NULL,
                                        `scope` varchar(256) COLLATE utf8mb4_general_ci DEFAULT NULL,
                                        `authorized_grant_types` varchar(256) COLLATE utf8mb4_general_ci DEFAULT NULL,
                                        `web_server_redirect_uri` varchar(256) COLLATE utf8mb4_general_ci DEFAULT NULL,
                                        `authorities` varchar(256) COLLATE utf8mb4_general_ci DEFAULT NULL,
                                        `access_token_validity` int(11) DEFAULT NULL,
                                        `refresh_token_validity` int(11) DEFAULT NULL,
                                        `additional_information` varchar(4096) COLLATE utf8mb4_general_ci DEFAULT NULL,
                                        `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                        `archived` tinyint(4) DEFAULT NULL,
                                        `trusted` tinyint(4) DEFAULT NULL,
                                        `autoapprove` varchar(256) COLLATE utf8mb4_general_ci DEFAULT NULL,
                                        PRIMARY KEY (`client_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

# 授权码表
CREATE TABLE `oauth_code` (
                              `code` varchar(256) COLLATE utf8mb4_general_ci DEFAULT NULL,
                              `authentication` blob,
                              `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;