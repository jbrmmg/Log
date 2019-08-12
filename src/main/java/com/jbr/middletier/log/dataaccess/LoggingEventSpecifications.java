package com.jbr.middletier.log.dataaccess;

import com.jbr.middletier.log.data.LoggingEvent;
import org.springframework.data.jpa.domain.Specification;

public class LoggingEventSpecifications {

    public static Specification<LoggingEvent> logIsLikeClass(String classType) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("callerClass"),classType + "%");
    }

    public static Specification<LoggingEvent> logIsBetween(long from, long to) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.between(root.get("timeStamp"), from, to);
    }

    public static Specification<LoggingEvent> logIsAfter(long from) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.greaterThan(root.get("timeStamp"), from);
    }
}
