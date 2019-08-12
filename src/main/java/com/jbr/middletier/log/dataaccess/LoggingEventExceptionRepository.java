package com.jbr.middletier.log.dataaccess;

import com.jbr.middletier.log.data.LoggingEventException;
import com.jbr.middletier.log.data.LoggingEventExceptionId;
import org.springframework.data.repository.CrudRepository;

public interface LoggingEventExceptionRepository extends CrudRepository<LoggingEventException, LoggingEventExceptionId> {
}
