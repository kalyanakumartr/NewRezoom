package org.hbs.core.extractor.action.core;

import java.io.Serializable;

import org.hbs.core.bean.model.IConfiguration;
import org.hbs.core.event.service.GenericKafkaProducer;
import org.hbs.core.extractor.bo.ExtractorBo;

public interface InBoxReader extends Serializable
{

	public void readDataFromChannel(IConfiguration config, GenericKafkaProducer gKafkaProducer, ExtractorBo extractorBo);

}
