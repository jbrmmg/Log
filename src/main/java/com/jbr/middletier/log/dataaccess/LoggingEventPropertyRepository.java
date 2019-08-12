package com.jbr.middletier.log.dataaccess;

import com.jbr.middletier.log.data.LoggingEventProperty;
import org.springframework.data.repository.CrudRepository;

public interface LoggingEventPropertyRepository  extends CrudRepository<LoggingEventProperty, Long> {
}
