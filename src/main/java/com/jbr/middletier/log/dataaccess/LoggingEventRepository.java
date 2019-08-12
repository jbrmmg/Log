package com.jbr.middletier.log.dataaccess;

import com.jbr.middletier.log.data.LoggingEvent;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface LoggingEventRepository  extends CrudRepository<LoggingEvent, Long>, JpaSpecificationExecutor {
    @Transactional
    void deleteByTimeStampBefore(long date);
}
