package org.darcstarsolutions.ironworker.mongodb.configuration;

import org.darcstarsolutions.ironworker.mongodb.entities.TimeStamp;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.math.BigInteger;

/**
 * Created by mharris on 5/27/15.
 */


public interface TimeStampRepository extends MongoRepository<TimeStamp, BigInteger> {
}
