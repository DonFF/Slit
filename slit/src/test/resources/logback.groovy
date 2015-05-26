//
// Built on Fri Feb 14 07:38:28 CET 2014 by logback-translator
// For more information on configuration files in Groovy
// please see http://logback.qos.ch/manual/groovy.html

// For assistance related to this tool or configuration files
// in general, please contact the logback user mailing list at
//    http://qos.ch/mailman/listinfo/logback-user

// For professional support please see
//   http://www.qos.ch/shop/products/professionalSupport

import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import ch.qos.logback.core.ConsoleAppender

import static ch.qos.logback.classic.Level.DEBUG
import static ch.qos.logback.classic.Level.INFO

appender("stdout", ConsoleAppender) {
  encoder(PatternLayoutEncoder) {
    pattern = "%d{YYYY-MM-dd HH:mm:ss} [%level] %logger{50} -> %X{rpid} %msg %n%ex"
  }
}
root(INFO, ["stdout"])
logger("com.edu.crawler.slit", DEBUG)
logger("com.ibatis", DEBUG)
logger("java.sql.Connection", DEBUG)
logger("java.sql.Statement", DEBUG)
logger("java.sql.PreparedStatement", DEBUG)
logger("java.sql.ResultSet", DEBUG)
logger("org.perf4j.TimingLogger", DEBUG, ["stdout"], false)