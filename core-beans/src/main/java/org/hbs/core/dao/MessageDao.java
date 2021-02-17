package org.hbs.core.dao;

import java.util.List;

import org.hbs.core.beans.model.Messages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageDao extends JpaRepository<Messages, String>
{

	@Query("From Messages where producer.producerId = :producerId and messageId = :messageId and status = true")
	List<Messages> getByMessageId(@Param("producerId") String producerId, @Param("messageId") String messageId);

	@Query("From Messages where producer.producerId = :producerId and messageType = :messageType and status = true")
	List<Messages> getByMessageType(@Param("producerId") String producerId, @Param("messageType") String messageType);

}
