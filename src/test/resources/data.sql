-- noinspection SqlNoDataSourceInspectionForFile

-- Setup the test log type.
INSERT INTO log_type(log_type_id, log_name, log_image, log_class) VALUES ( 'TST',  'Test Log', '<svg/>', 'com.jbr.middletier.podcast.manage' );
INSERT INTO log_type(log_type_id, log_name, log_image, log_class) VALUES ( 'TST2', 'Test Log 2', '<svg/>', 'com.jbr.middletier.backup.manage' );

-- Setup log entries.
SET @OLDDATE = DATEADD('DAY', -6, CURRENT_DATE());
SET @DATEM4  = DATEADD('DAY', -4, CURRENT_DATE());
SET @DATEM3  = DATEADD('DAY', -3, CURRENT_DATE());
SET @DATEM2  = DATEADD('DAY', -2, CURRENT_DATE());
SET @DATEM1  = DATEADD('DAY', -1, CURRENT_DATE());
SET @NEWDATE = DATEADD('DAY',  0, CURRENT_DATE());
SET @NXTDATE = DATEADD('DAY',  1, CURRENT_DATE());

/*
 * timestmp is a unix time stamp (milliseconds since 01-Jan-1970)
 *
 * To convert the value to a date time - DATEADD('SECOND',<value> / 1000, '1970-01-01')
 *
 * To convert a date to the value - DATEDIFF('SECOND', '1970-01-01', <date>) * 1000
 */

SET @OLDDATE_TS = DATEDIFF('SECOND', '1970-01-01', @OLDDATE) * 1000;
SET @DATEM4_TS  = DATEDIFF('SECOND', '1970-01-01', @DATEM4)  * 1000;
SET @DATEM3_TS  = DATEDIFF('SECOND', '1970-01-01', @DATEM3)  * 1000;
SET @DATEM2_TS  = DATEDIFF('SECOND', '1970-01-01', @DATEM2)  * 1000;
SET @DATEM1_TS  = DATEDIFF('SECOND', '1970-01-01', @DATEM1)  * 1000;
SET @NEWDATE_TS = DATEDIFF('SECOND', '1970-01-01', @NEWDATE) * 1000;
SET @NXTDATE_TS = DATEDIFF('SECOND', '1970-01-01', @NXTDATE) * 1000;

/* 1 */
INSERT INTO logging_event(timestmp, formatted_message, logger_name, level_string, thread_name, reference_flag, arg0, arg1, arg2, arg3, caller_filename, caller_class, caller_method, caller_line) VALUES (
  @NEWDATE_TS + 90000,
  'Podcast Manager started up 2.',
  'com.jbr.middletier.podcast.manage.PodcastManager',
  'INFO',
  'main',
  '1',
  NULL, NULL, NULL, NULL,
  'PodcastManager.java',
  'com.jbr.middletier.podcast.manage.PodcastManager',
  '<init>',
  '56' );

/* 2 */
INSERT INTO logging_event(timestmp, formatted_message, logger_name, level_string, thread_name, reference_flag, arg0, arg1, arg2, arg3, caller_filename, caller_class, caller_method, caller_line) VALUES (
  @NEWDATE_TS + 150000,
  'Podcast Manager initialising.',
  'com.jbr.middletier.podcast.manage.PodcastManager',
  'WARN',
  'main',
  '1',
  NULL, NULL, NULL, NULL,
  'PodcastManager.java',
  'com.jbr.middletier.podcast.manage.PodcastManager',
  '<init>',
  '56' );

/* 3 */
INSERT INTO logging_event(timestmp, formatted_message, logger_name, level_string, thread_name, reference_flag, arg0, arg1, arg2, arg3, caller_filename, caller_class, caller_method, caller_line) VALUES (
  @NEWDATE_TS,
  'Podcast Manager started up.',
  'com.jbr.middletier.podcast.manage.PodcastManager',
  'DEBUG',
  'main',
  '1',
  NULL, NULL, NULL, NULL,
  'PodcastManager.java',
  'com.jbr.middletier.podcast.manage.PodcastManager',
  '<init>',
  '56' );

/* 4 */
INSERT INTO logging_event(timestmp, formatted_message, logger_name, level_string, thread_name, reference_flag, arg0, arg1, arg2, arg3, caller_filename, caller_class, caller_method, caller_line) VALUES (
  @NXTDATE_TS - 3600001,
  'Podcast Manager stopped.',
  'com.jbr.middletier.podcast.manage.PodcastManager',
  'WARN',
  'main',
  '1',
  NULL, NULL, NULL, NULL,
  'PodcastManager.java',
  'com.jbr.middletier.podcast.manage.PodcastManager',
  '<init>',
  '56' );

/* 5 */
INSERT INTO logging_event(timestmp, formatted_message, logger_name, level_string, thread_name, reference_flag, arg0, arg1, arg2, arg3, caller_filename, caller_class, caller_method, caller_line) VALUES (
  @DATEM1_TS,
  'Podcast Manager checking. 1',
  'com.jbr.middletier.podcast.manage.PodcastManager',
  'INFO',
  'main',
  '1',
  NULL, NULL, NULL, NULL,
  'PodcastManager.java',
  'com.jbr.middletier.podcast.manage.PodcastManager',
  '<init>',
  '56' );

/* 6 */
INSERT INTO logging_event(timestmp, formatted_message, logger_name, level_string, thread_name, reference_flag, arg0, arg1, arg2, arg3, caller_filename, caller_class, caller_method, caller_line) VALUES (
  @NEWDATE_TS - 3600001,
  'Podcast Manager stopped.',
  'com.jbr.middletier.podcast.manage.PodcastManager',
  'WARN',
  'main',
  '1',
  NULL, NULL, NULL, NULL,
  'PodcastManager.java',
  'com.jbr.middletier.podcast.manage.PodcastManager',
  '<init>',
  '56' );

/* 7 */
INSERT INTO logging_event(timestmp, formatted_message, logger_name, level_string, thread_name, reference_flag, arg0, arg1, arg2, arg3, caller_filename, caller_class, caller_method, caller_line) VALUES (
  @DATEM2_TS,
  'Podcast Manager started up. 2',
  'com.jbr.middletier.podcast.manage.PodcastManager',
  'DEBUG',
  'main',
  '1',
  NULL, NULL, NULL, NULL,
  'PodcastManager.java',
  'com.jbr.middletier.podcast.manage.PodcastManager',
  '<init>',
  '56' );

/* 8 */
INSERT INTO logging_event(timestmp, formatted_message, logger_name, level_string, thread_name, reference_flag, arg0, arg1, arg2, arg3, caller_filename, caller_class, caller_method, caller_line) VALUES (
  @DATEM1_TS - 3600001,
  'Podcast Manager stopped.',
  'com.jbr.middletier.podcast.manage.PodcastManager',
  'WARN',
  'main',
  '1',
  NULL, NULL, NULL, NULL,
  'PodcastManager.java',
  'com.jbr.middletier.podcast.manage.PodcastManager',
  '<init>',
  '56' );

/* 9 */
INSERT INTO logging_event(timestmp, formatted_message, logger_name, level_string, thread_name, reference_flag, arg0, arg1, arg2, arg3, caller_filename, caller_class, caller_method, caller_line) VALUES (
  @DATEM3_TS,
  'Podcast Manager started up. 3',
  'com.jbr.middletier.podcast.manage.PodcastManager',
  'DEBUG',
  'main',
  '1',
  NULL, NULL, NULL, NULL,
  'PodcastManager.java',
  'com.jbr.middletier.podcast.manage.PodcastManager',
  '<init>',
  '56' );

/* 10 */
INSERT INTO logging_event(timestmp, formatted_message, logger_name, level_string, thread_name, reference_flag, arg0, arg1, arg2, arg3, caller_filename, caller_class, caller_method, caller_line) VALUES (
  @DATEM2_TS - 3600001,
  'Podcast Manager stopped.',
  'com.jbr.middletier.podcast.manage.PodcastManager',
  'WARN',
  'main',
  '1',
  NULL, NULL, NULL, NULL,
  'PodcastManager.java',
  'com.jbr.middletier.podcast.manage.PodcastManager',
  '<init>',
  '56' );

/* 11 */
INSERT INTO logging_event(timestmp, formatted_message, logger_name, level_string, thread_name, reference_flag, arg0, arg1, arg2, arg3, caller_filename, caller_class, caller_method, caller_line) VALUES (
  @DATEM4_TS,
  'Podcast Manager started up. 4',
  'com.jbr.middletier.podcast.manage.PodcastManager',
  'DEBUG',
  'main',
  '1',
  NULL, NULL, NULL, NULL,
  'PodcastManager.java',
  'com.jbr.middletier.podcast.manage.PodcastManager',
  '<init>',
  '56' );

/* 12 */
INSERT INTO logging_event(timestmp, formatted_message, logger_name, level_string, thread_name, reference_flag, arg0, arg1, arg2, arg3, caller_filename, caller_class, caller_method, caller_line) VALUES (
  @DATEM3_TS - 3600001,
  'Podcast Manager stopped.',
  'com.jbr.middletier.podcast.manage.PodcastManager',
  'WARN',
  'main',
  '1',
  NULL, NULL, NULL, NULL,
  'PodcastManager.java',
  'com.jbr.middletier.podcast.manage.PodcastManager',
  '<init>',
  '56' );

/* 13 */
INSERT INTO logging_event(timestmp, formatted_message, logger_name, level_string, thread_name, reference_flag, arg0, arg1, arg2, arg3, caller_filename, caller_class, caller_method, caller_line) VALUES (
  @OLDDATE_TS,
  'Podcast Manager started up. 5',
  'com.jbr.middletier.podcast.manage.PodcastManager',
  'DEBUG',
  'main',
  '1',
  NULL, NULL, NULL, NULL,
  'PodcastManager.java',
  'com.jbr.middletier.podcast.manage.PodcastManager',
  '<init>',
  '56' );

/* 14 */
INSERT INTO logging_event(timestmp, formatted_message, logger_name, level_string, thread_name, reference_flag, arg0, arg1, arg2, arg3, caller_filename, caller_class, caller_method, caller_line) VALUES (
  @OLDDATE_TS,
  'Backup startup.',
  'com.jbr.middletier.backup.manage.BackupManager',
  'DEBUG',
  'main',
  '1',
  NULL, NULL, NULL, NULL,
  'BackupManager.java',
  'com.jbr.middletier.backup.manage.BackupManager',
  '<init>',
  '56' );
