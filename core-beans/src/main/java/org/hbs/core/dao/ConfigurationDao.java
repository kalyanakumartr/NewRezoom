package org.hbs.core.dao;

import java.util.List;

import org.hbs.core.beans.model.ProducersProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigurationDao extends JpaRepository<ProducersProperty, String>
{
	@Query("Select count(*) From ProducersProperty Where  producer.producerId = :producerId AND groupName Like %:groupName% ")
	int checkConfigurationExists(@Param("producerId") String producerId, @Param("groupName") String groupName);

	@Query("From ProducersProperty Where producer.producerId = :producerId AND autoId = :autoId")
	ProducersProperty fetchByAutoId(@Param("producerId") String producerId, @Param("autoId") String autoId);

	@Query("From ProducersProperty Where status = true AND producer.producerId = :producerId AND media = :media AND mediatype = :mediaType AND mediamode = :mediaMode")
	List<ProducersProperty> getConfigurationByType(@Param("producerId") String producerId, @Param("media") String media, @Param("mediaType") String mediaType,
			@Param("mediaMode") String mediaMode);

	@Query("From ProducersProperty Where producer.producerId = :producerId AND ( groupName Like %:searchParam% OR mediaType Like %:searchParam% OR mediaMode Like %:searchParam% OR media Like %:searchParam% OR property Like %:searchParam% OR value Like %:searchParam%)")
	List<ProducersProperty> getConfigurationList(@Param("producerId") String producerId, @Param("searchParam") String searchParam);
	
	@Query("From ProducersProperty Where producer.producerId = :producerId AND groupName Like %:searchParam%")
	List<ProducersProperty> searchByConfigurationName(@Param("producerId") String producerId, @Param("searchParam") String searchParam);


}
