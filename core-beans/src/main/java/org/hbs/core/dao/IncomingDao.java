package org.hbs.core.dao;

import java.util.List;

import org.hbs.core.bean.model.application.IncomingData;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface IncomingDao extends CrudRepository<IncomingData, String>
{

	@Query("Select ID.sentTime From IncomingData ID where ID.producer.producerId = :producerId order by sentTime desc")
	public List<Long> getLastEmailSentDate(@Param("producerId") String producerId, Pageable pageable);
}
