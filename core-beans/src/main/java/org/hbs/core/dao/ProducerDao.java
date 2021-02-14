package org.hbs.core.dao;

import java.util.List;

import org.hbs.core.bean.model.application.CustomerProducer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProducerDao extends JpaRepository<CustomerProducer, String>
{
	public CustomerProducer findByProducerId(String producerId);

	public int countByProducerName(String producerName);

	public List<CustomerProducer> findByProducerName(String producerName);

	@Query("Select CP From CustomerProducer CP where CP.producerName like %:producerName% ")
	public List<CustomerProducer> findLikeProducerName(@Param("producerName") String producerName);

	@Query("Select CP.producerName From CustomerProducer CP where CP.producerName like %:producerName% ")
	public Object[] checkProducerName(@Param("producerName") String producerName);

	@Query("Select CP From CustomerProducer CP where CP.status = :status")
	public List<CustomerProducer> findByStatus(@Param("status") Boolean status);

}
