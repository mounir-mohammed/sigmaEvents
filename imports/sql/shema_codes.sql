DROP TABLE IF EXISTS `accreditation`;
CREATE TABLE IF NOT EXISTS `accreditation` (
  `event_event_id` bigint DEFAULT NULL,
  `accreditation_type_accreditation_type_id` bigint DEFAULT NULL,
  `code_code_id` bigint DEFAULT NULL,
  KEY `fk_accreditation__event_id` (`event_event_id`),
  KEY `fk_accreditation__accreditation_type_id` (`accreditation_type_accreditation_type_id`),
  KEY `fk_accreditation__code_id` (`code_code_id`),
)

CREATE TABLE IF NOT EXISTS `accreditation_type` (
  `accreditation_type_id` bigint NOT NULL AUTO_INCREMENT,
  `accreditation_type_value` varchar(50) NOT NULL,
  `event_event_id` bigint DEFAULT NULL,
  KEY `fk_accreditation__event_id` (`event_event_id`),
)

CREATE TABLE IF NOT EXISTS `code` (
  `code_id` bigint NOT NULL AUTO_INCREMENT,
  `code_entity_value` varchar(255) DEFAULT NULL,
  `code_value` varchar(255) NOT NULL,
  `code_used` bit(1) DEFAULT NULL,
  `event_event_id` bigint DEFAULT NULL,
  PRIMARY KEY (`code_id`),
  KEY `fk_code__event_id` (`event_event_id`),
  CONSTRAINT `fk_code__event_id` FOREIGN KEY (`event_event_id`) REFERENCES `event` (`event_id`)
)

