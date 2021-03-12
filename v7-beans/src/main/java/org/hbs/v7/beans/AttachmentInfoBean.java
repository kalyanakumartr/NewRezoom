package org.hbs.v7.beans;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipInputStream;

import org.apache.commons.compress.archivers.sevenz.SevenZFile;
import org.hbs.core.beans.model.channel.EExtension;
import org.hbs.core.util.CommonValidator;

import com.fasterxml.jackson.annotation.JsonIgnore;

public abstract class AttachmentInfoBean implements IAttachmentInfoBean
{

	private static final long	serialVersionUID	= -3434772327583545293L;

	private String				baseFolderPath		= "";

	private String				fileName			= "";

	private String				subFolderPath;

	private EExtension			extension			= EExtension.Invalid;

	private boolean				compressed			= false;				// Represents_ZIPPED_File

	public AttachmentInfoBean()
	{
		super();
	}

	@Override
	public boolean isCompressed()
	{
		return compressed;
	}

	@Override
	public void setCompressed(boolean compressed)
	{
		this.compressed = compressed;
	}

	@Override
	public String getBaseFolderPath()
	{
		return baseFolderPath;
	}

	@Override
	public String getFileName()
	{
		return CommonValidator.isNotNullNotEmpty(fileName) ? fileName : "";
	}

	@Override
	@JsonIgnore
	public ZipInputStream getZIPInputStream() throws FileNotFoundException
	{
		return new ZipInputStream(new FileInputStream(new File(getOutputPath() + File.separator + getFileName())));
	}

	@Override
	@JsonIgnore
	public SevenZFile getSevenZFile() throws IOException
	{
		return new SevenZFile(new File(getOutputPath() + File.separator + getFileName()));
	}

	@Override
	@JsonIgnore
	public FileInputStream getInputStream() throws FileNotFoundException
	{
		return new FileInputStream(getOutputFile());
	}

	@Override
	@JsonIgnore
	public FileOutputStream getOutputStream() throws FileNotFoundException
	{
		File attFileDir = new File(getOutputPath());

		if (!attFileDir.exists())
		{
			attFileDir.mkdirs();
		}

		return new FileOutputStream(attFileDir + File.separator + getFileName());
	}

	@Override
	@JsonIgnore
	public String getOutputPath()
	{
		return (getBaseFolderPath() + getSubFolderPath()).replace(BACKSLASH, SLASH);
	}

	@Override
	@JsonIgnore
	public String getOutputPath(String fileName)
	{
		this.setFileName(fileName);
		return getOutputPath() + SLASH + getFileName();
	}

	@Override
	@JsonIgnore
	public File getOutputFile()
	{
		return new File(getOutputPath() + SLASH + getFileName());
	}

	@JsonIgnore
	private String getSubFolderPath()
	{
		if (CommonValidator.isNotNullNotEmpty(subFolderPath))
		{
			if (subFolderPath.startsWith(SLASH))
				return this.subFolderPath;
			else
				return SLASH + subFolderPath;
		}
		return "";
	}

	@Override
	public void setBaseFolderPath(String baseFolderPath)
	{
		this.baseFolderPath = baseFolderPath;
	}

	@Override
	public void setFileName(String fileName)
	{
		if (fileName != null)
		{
			if (fileName.indexOf(File.separator) > 0)
				fileName = fileName.substring(fileName.lastIndexOf(File.separator) + 1);
			else if (fileName.indexOf(SLASH) > 0)
				fileName = fileName.substring(fileName.lastIndexOf(SLASH) + 1);

			this.setExtension(EExtension.isValid(fileName));

			this.compressed = (this.extension == EExtension.Zip || this.extension == EExtension._7z);
		}
		this.fileName = fileName;

	}

	@Override
	public void setSubFolderPath(String subFolderPath)
	{
		this.subFolderPath = subFolderPath;
	}

	@Override
	public IAttachmentInfoBean appendSubFolderPath(String subFolderPath)
	{
		this.subFolderPath += SLASH + String.valueOf(subFolderPath);
		return this;
	}

	public EExtension getExtension()
	{
		return extension;
	}

	public void setExtension(EExtension extension)
	{
		this.extension = extension;
	}

}