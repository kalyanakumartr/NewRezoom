package org.hbs.v7.reader.action.core;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.mail.MessagingException;

import org.apache.commons.compress.archivers.sevenz.SevenZArchiveEntry;
import org.apache.commons.compress.archivers.sevenz.SevenZFile;
import org.hbs.core.beans.model.channel.EExtension;
import org.hbs.core.util.CustomException;
import org.hbs.v7.beans.IAttachmentInfoBean;
import org.hbs.v7.beans.model.DataAttachments;
import org.hbs.v7.beans.model.DataAttachments.EDataTrace;
import org.hbs.v7.beans.model.IncomingData;

public class IncomingDataCreator implements Serializable
{

	private static final long			serialVersionUID	= 2834873910385716305L;
	private static IncomingDataCreator	readerFactory		= null;

	private IncomingDataCreator()
	{

	}

	public static IncomingDataCreator getInstance()
	{
		if (readerFactory == null)
		{
			readerFactory = new IncomingDataCreator();
		}
		return readerFactory;
	}

	public void unpackAndCreateDataAttachmentSet(IncomingData incomingData, IAttachmentInfoBean infoBean, Set<DataAttachments> attachmentsSet) throws IOException, CustomException, MessagingException
	{
		System.out.println(infoBean.getFileName() + " >>>>>>>>>>>>> IAttachmentInfoBean.getExtension()>>>>>>>>>>>>>>>>" + infoBean.getExtension());
		switch ( infoBean.getExtension() )
		{
			case Invalid :
				throw new CustomException("Unsupported File '" + infoBean.getFileName() + "'. Allow Files Only With Extensions " + EExtension.format());
			case Zip :
			{
				incomingData.setBulkAttachment(true);
				unpackZipFile(incomingData, infoBean, attachmentsSet);
				break;
			}
			case _7z :
			{
				incomingData.setBulkAttachment(true);
				unpack7zFile(incomingData, infoBean, attachmentsSet);
				break;
			}
			default :
			{
				System.out.println("Single Attachment File. " + infoBean.getFileName());
				createDataAttachment(incomingData, infoBean, attachmentsSet);
				System.out.println("CreateDataAttachment Completed For " + infoBean.getFileName());
				break;
			}
		}
	}

	private void createDataAttachment(IncomingData incomingData, IAttachmentInfoBean infoBean, Set<DataAttachments> attachmentsSet) throws MessagingException
	{
		DataAttachments attachment = new DataAttachments();
		attachment.setUploadFileName(infoBean.getFileName());
		attachment.setStatus(true);
		attachment.setPriority(incomingData.acquirePriority());
		attachment.setTrace(incomingData.isBulkAttachment() ? EDataTrace.MainDocument : EDataTrace.YetToTrace);
		attachment.setUploadFileFolderURL(infoBean.getOutputPath());
		attachment.setIncomingData(incomingData);
		attachment.setCreatedDate(new Timestamp(System.currentTimeMillis()));
		File file = infoBean.getOutputFile();// Don't Change Position

		attachment.setUploadFileSize(file.length());
		attachment.setUploadFileDate(new Timestamp(System.currentTimeMillis()));
		attachment.setUploadFileLastModifiedDate(new Timestamp(file.lastModified()));

		attachmentsSet.add(attachment);
		infoBean.setFileName(null); // Reset for safer side
	}

	private void unpack7zFile(IncomingData incomingData, IAttachmentInfoBean infoBean, Set<DataAttachments> attachmentsSet) throws IOException, MessagingException
	{
		SevenZFile sevenZFile = null;
		FileOutputStream out = null;
		try
		{
			sevenZFile = infoBean.getSevenZFile();
			SevenZArchiveEntry entry;
			while ( (entry = sevenZFile.getNextEntry()) != null )
			{
				if (entry.isDirectory())
				{
					continue;
				}
				File curfile = new File(infoBean.getOutputPath(entry.getName()));
				File parent = curfile.getParentFile();
				if (!parent.exists())
				{
					parent.mkdirs();
				}
				out = new FileOutputStream(curfile);
				byte[] content = new byte[(int) entry.getSize()];
				sevenZFile.read(content, 0, content.length);
				out.write(content);
				out.close();
				System.out.println("unpack7zFile :: Bulk Attachment File. Unzipped " + entry.getName());
				createDataAttachment(incomingData, infoBean, attachmentsSet);
				System.out.println("unpack7zFile :: CreateDataAttachment Completed For " + entry.getName());
			}
			sevenZFile.close();
		}
		finally
		{
			// Double check for Resource Free up
			if (sevenZFile != null)
				sevenZFile.close();
			if (out != null)
				out.close();
		}

	}

	private void unpackZipFile(IncomingData incomingData, IAttachmentInfoBean infoBean, Set<DataAttachments> attachmentsSet) throws IOException, MessagingException
	{
		ZipInputStream zipIn = null;
		FileOutputStream out = null;
		try
		{
			zipIn = infoBean.getZIPInputStream();
			ZipEntry entry = zipIn.getNextEntry();

			while ( (entry = zipIn.getNextEntry()) != null )
			{
				if (entry.isDirectory())
				{
					continue;
				}
				File curfile = new File(infoBean.getOutputPath(entry.getName()));
				File parent = curfile.getParentFile();
				if (!parent.exists())
				{
					parent.mkdirs();
				}
				out = new FileOutputStream(curfile);
				byte[] content = new byte[(int) entry.getSize()];
				zipIn.read(content, 0, content.length);
				out.write(content);
				out.close();

				System.out.println("unpackZipFile :: Bulk Attachment File. Unzipped " + entry.getName());
				createDataAttachment(incomingData, infoBean, attachmentsSet);
				System.out.println("unpackZipFile :: CreateDataAttachment Completed For " + entry.getName());
			}

			zipIn.close();
		}
		finally
		{
			// Double check for Resource Free up
			if (zipIn != null)
				zipIn.close();
			if (out != null)
				out.close();
		}
	}
}
