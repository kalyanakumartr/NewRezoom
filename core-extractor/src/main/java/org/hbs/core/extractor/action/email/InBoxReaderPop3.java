package org.hbs.core.extractor.action.email;

import org.hbs.core.beans.model.IConfiguration;
import org.hbs.core.extractor.bo.ExtractorBo;
import org.hbs.core.kafka.GenericKafkaProducer;

public class InBoxReaderPop3 extends InBoxReaderBase
{

	private static final long serialVersionUID = 4813150068221933347L;

	@Override
	public void readDataFromChannel(IConfiguration config, GenericKafkaProducer gKafkaProducer,  ExtractorBo extractorBo)
	{

	}

}
