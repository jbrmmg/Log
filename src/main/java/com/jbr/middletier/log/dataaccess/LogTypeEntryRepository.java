package com.jbr.middletier.log.dataaccess;

import com.jbr.middletier.log.data.LogTypeEntry;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by jason on 07/12/16.
 */

@SuppressWarnings("ALL")
@Repository
public interface LogTypeEntryRepository extends CrudRepository<LogTypeEntry, String> {
}
