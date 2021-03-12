package org.hbs.v7.beans;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipInputStream;

import org.apache.commons.compress.archivers.sevenz.SevenZFile;
import org.hbs.core.beans.model.channel.EExtension;
import org.hbs.core.util.IConstProperty;

public interface IAttachmentInfoBean extends IConstProperty
{
	boolean isCompressed();

	void setCompressed(boolean compressed);

	String getBaseFolderPath();

	String getFileName();

	ZipInputStream getZIPInputStream() throws FileNotFoundException;

	SevenZFile getSevenZFile() throws IOException;

	FileInputStream getInputStream() throws FileNotFoundException;

	FileOutputStream getOutputStream() throws FileNotFoundException;

	String getOutputPath();

	String getOutputPath(String fileName);

	File getOutputFile();

	void setBaseFolderPath(String fileFolderURL);

	void setFileName(String fileName);

	void setSubFolderPath(String subFolderPath);

	EExtension getExtension();

	void setExtension(EExtension extension);

	IAttachmentInfoBean appendSubFolderPath(String subFolderPath);
}