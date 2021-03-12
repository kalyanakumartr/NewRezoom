package org.hbs.v7.extractor.action.filetype;

import java.io.FileReader;
import java.io.PrintWriter;
import java.io.StringWriter;

import org.hbs.v7.beans.MediatorBean;
import org.hbs.v7.beans.model.DataAttachments.EDataTrace;
import org.hbs.v7.extractor.action.executor.DataExtractorBase;
import org.hbs.v7.extractor.action.executor.IDataExtractor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Component;

@Component
public class JSONDataExtractor extends DataExtractorBase implements IDataExtractor
{
	private static final long	serialVersionUID	= -3403166786849575868L;

	protected MediatorBean read()
	{
		JSONParser parser = new JSONParser();
		try
		{
			Object object = parser.parse(new FileReader(inBean.getOutputFile()));

			// A JSON object. Key value pairs are unordered. JSONObject supports java.util.Map
			// interface.
			mediatorBean.setJson((JSONObject)object);
			return mediatorBean; 

		}
		catch (Exception excep)
		{
			System.out.println("We had an error while reading the Open Office Document");
			if (inBean.isExternal() == false) // Internal Flow
			{
				StringWriter sw = new StringWriter();
				PrintWriter pw = new PrintWriter(sw);
				excep.printStackTrace(pw);
				datDao.updateReadStatus(inBean.getAttachmentAutoId(), EDataTrace.UnableToRead.name(), sw.toString());
			}
		}
		finally
		{
		}
		return null;
	}

}
