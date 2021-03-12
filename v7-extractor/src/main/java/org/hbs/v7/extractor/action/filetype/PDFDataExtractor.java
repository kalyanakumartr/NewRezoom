package org.hbs.v7.extractor.action.filetype;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.pdf.PDFParser;
import org.apache.tika.sax.BodyContentHandler;
import org.hbs.v7.beans.MediatorBean;
import org.hbs.v7.beans.model.DataAttachments.EDataTrace;
import org.hbs.v7.extractor.action.executor.DataExtractorBase;
import org.hbs.v7.extractor.action.executor.IDataExtractor;
import org.springframework.stereotype.Component;

@Component
public class PDFDataExtractor extends DataExtractorBase implements IDataExtractor
{
	private static final long	serialVersionUID	= -3403166786849575868L;

	protected MediatorBean read()
	{
		PDFParser pdfparser = null;
		try
		{

			BodyContentHandler handler = new BodyContentHandler();
			Metadata metadata = new Metadata();
			ParseContext pcontext = new ParseContext();

			pdfparser = new PDFParser();
			pdfparser.parse(inBean.getInputStream(), handler, metadata, pcontext);
			mediatorBean.content = handler.toString();
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
			try
			{
				if (inBean.getInputStream() != null)
				{
					inBean.getInputStream().close();
				}
			}
			catch (Exception ex)
			{
			}
		}
		return null;
	}
}
