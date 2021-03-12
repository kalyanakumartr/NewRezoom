package org.hbs.v7.util;

import java.io.Serializable;

import org.hbs.core.beans.model.channel.EExtension;
import org.hbs.core.kafka.IKAFKAPartition;
import org.hbs.v7.util.IKafkaTopicConstants.EPartition;
import org.hbs.v7.util.IKafkaTopicConstants.ETopic;

public class PartitionFinder implements Serializable
{
	private static final long		serialVersionUID	= 2676577784202651153L;
	private static PartitionFinder	partitionFinder		= null;

	private PartitionFinder()
	{

	}

	public static PartitionFinder getInstance()
	{
		if (partitionFinder == null)
		{
			partitionFinder = new PartitionFinder();
		}
		return partitionFinder;
	}

	public IKAFKAPartition find(ETopic eTopic, EMessagePriority priority) throws Exception
	{
		return find(eTopic, priority, EExtension.Invalid);
	}

	public IKAFKAPartition find(ETopic eTopic, EMessagePriority priority, EExtension extension) throws Exception
	{
		switch ( eTopic )
		{
			case Message :
			{
				switch ( priority )
				{
					case Expedite :
						return EPartition.Message_In_Expedite;
					case Normal :
					default :
						return EPartition.Message_In;
				}
			}
			case DataExtract :
			{
				switch ( priority )
				{
					case Normal :
					{
						switch ( extension )
						{
							case Doc :
							case Docx :
								return EPartition.Document;
							case ODT :
								return EPartition.OpenOffice;
							case XLS :
							case XLSX :
							case ODS :
								return EPartition.Excel;
							case PDF :
								return EPartition.Pdf;
							case HTML :
								return EPartition.Html;
							case Json :
								return EPartition.Json;
							case Csv :
								return EPartition.Csv;
							default :
								throw new Exception("Not Able To Find Normal For DataExtract");
						}
					}
					case Expedite :
					{
						switch ( extension )
						{
							case Doc :
							case Docx :
								return EPartition.Document_Expedite;
							case ODT :
								return EPartition.OpenOffice_Expedite;
							case XLS :
							case XLSX :
							case ODS :
								return EPartition.Excel_Expedite;
							case PDF :
								return EPartition.Pdf_Expedite;
							case HTML :
								return EPartition.Html_Expedite;
							case Json :
								return EPartition.Json_Expedite;
							case Csv :
								return EPartition.Csv_Expedite;
							default :
								throw new Exception("Not Able To Find Expedite For DataExtract");
						}
					}
					default :
						throw new Exception("Not Able To Find Init Priority For DataExtract");
				}
			}
			default :
				break;

		}
		return null;

	}

}
