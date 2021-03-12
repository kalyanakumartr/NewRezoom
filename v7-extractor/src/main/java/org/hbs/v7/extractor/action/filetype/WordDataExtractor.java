package org.hbs.v7.extractor.action.filetype;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.hbs.v7.beans.MediatorBean;
import org.hbs.v7.beans.model.DataAttachments.EDataTrace;
import org.hbs.v7.extractor.action.executor.DataExtractorBase;
import org.hbs.v7.extractor.action.executor.IDataExtractor;
import org.springframework.stereotype.Component;

@Component
public class WordDataExtractor extends DataExtractorBase implements IDataExtractor
{
	private static final long	serialVersionUID	= -3403166786849575868L;

	@SuppressWarnings("resource")
	protected MediatorBean read()
	{
		XWPFDocument document = null;
		try
		{
			document = new XWPFDocument(inBean.getInputStream());
			mediatorBean.content = new XWPFWordExtractor(document).getText();
			return mediatorBean; 
		}
		catch (Exception excep)
		{
			System.out.println("We had an error while reading the Word Doc");
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
				if (document != null)
				{
					document.close();
				}
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
