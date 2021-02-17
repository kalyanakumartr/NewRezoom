package org.hbs.core.extractor.action.core;

import java.io.Serializable;

import org.hbs.core.beans.model.IConfiguration;
import org.hbs.core.extractor.bo.ExtractorBo;
import org.hbs.core.kafka.GenericKafkaProducer;

public interface InBoxReader extends Serializable
{

	public void readDataFromChannel(IConfiguration config, GenericKafkaProducer gKafkaProducer, ExtractorBo extractorBo);

}
