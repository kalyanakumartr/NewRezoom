package org.hbs.core.beans.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.hbs.core.beans.model.IUsersBase.EResource;
import org.hbs.core.util.CommonValidator;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author AnanthMalBal
 *
 */
@MappedSuperclass
public abstract class CommonFileUploadBase extends CommonDateAndStatusFields implements IUploadImageOrDocuments
{

	private static final long	serialVersionUID		= 8419978751136386549L;
	protected String			uploadDocumentForType;
	protected Timestamp			uploadFileDate;
	protected String			uploadFileFolderURL;  // Will get prepend and append with SLASH, while saving
	protected Timestamp			uploadFileLastModifiedDate;
	protected String			uploadFileName;
	protected long				uploadFileSize			= 0L;
	protected String			uploadFileVirtualURL	= "javascript:void(0);";
	protected MultipartFile		uploadMultiPartFile;
	protected String			uploadResourceHandler	= EResource.Default.name();
	protected String			uploadSubFolderPath;

	public CommonFileUploadBase()
	{
		super();
	}

	public CommonFileUploadBase(String uploadFileFolderURL, String uploadFileName, String uploadResourceHandler)
	{
		super();
		this.uploadFileFolderURL = uploadFileFolderURL;
		this.uploadFileName = uploadFileName;
		this.uploadResourceHandler = uploadResourceHandler;
	}

	public CommonFileUploadBase(String uploadDocumentForType, Timestamp uploadFileDate, String uploadFileFolderURL, Timestamp uploadFileLastModifiedDate, String uploadFileName, long uploadFileSize,
			String uploadFileVirtualURL, MultipartFile uploadMultiPartFile, String uploadResourceHandler, String uploadSubFolderPath)
	{
		super();
		this.uploadDocumentForType = uploadDocumentForType;
		this.uploadFileDate = uploadFileDate;
		this.uploadFileFolderURL = uploadFileFolderURL;
		this.uploadFileLastModifiedDate = uploadFileLastModifiedDate;
		this.uploadFileName = uploadFileName;
		this.uploadFileSize = uploadFileSize;
		this.uploadFileVirtualURL = uploadFileVirtualURL;
		this.uploadMultiPartFile = uploadMultiPartFile;
		this.uploadResourceHandler = uploadResourceHandler;
		this.uploadSubFolderPath = uploadSubFolderPath;
	}

	@Column(name = "uploadDocumentForType")
	public String getUploadDocumentForType()
	{
		return uploadDocumentForType;
	}

	@Column(name = "uploadFileDate")
	public Timestamp getUploadFileDate()
	{
		return uploadFileDate;
	}

	/**
	 * This field will be used to store the sub folder path structure. For ex: '/document/profile', 
	 * where as your physical folder 'C:/HBS/Attachments/Data' and mapped uploadResourceHandler is 'view'
	 * 
	 * For Physical access (alter or delete), get the respective base folder either from application.properties or producers property table
	 * 
	 * For Virtual URL, use this field value along with uploadResourceHandler field and uploadFileName
	 * For ex: if uploadResourceHandler = 'view' and uploadFileName = 'logo.png' then, URL will be '[your context path]/view/document/profile/logo.png'
	 * 
	 * # Your file will be store under 'C:/HBS/Attachments/Data/document/profile/logo.png'
	 * # Your file access URL will be 'www.hatchbird.com/view/document/profile/logo.png'
	 * 
	 */
	@Column(name = "uploadFileFolderURL")
	@JsonIgnore
	public String getUploadFileFolderURL()
	{
		return uploadFileFolderURL;
	}

	@Column(name = "uploadFileLastModifiedDate")
	public Timestamp getUploadFileLastModifiedDate()
	{
		return uploadFileLastModifiedDate;
	}

	@Column(name = "uploadFileName")
	public String getUploadFileName()
	{
		return uploadFileName;
	}

	@Column(name = "uploadFileSize")
	public long getUploadFileSize()
	{
		return uploadFileSize;
	}

	@Transient
	public String getUploadFileVirtualURL()
	{
		if (CommonValidator.isNotNullNotEmpty(this.uploadResourceHandler, this.uploadFileFolderURL, this.uploadFileName))
		{
			this.uploadFileVirtualURL = this.uploadResourceHandler + SLASH + this.uploadFileFolderURL + SLASH + this.uploadFileName;
		}
		
		return uploadFileVirtualURL;
	}

	@Transient
	@JsonIgnore
	public MultipartFile getUploadMultiPartFile()
	{
		return uploadMultiPartFile;
	}

	@Column(name = "uploadResourceHandler")
	public String getUploadResourceHandler()
	{
		return uploadResourceHandler;
	}

	@Transient
	@JsonIgnore
	public String getUploadSubFolderPath()
	{
		return uploadSubFolderPath;
	}

	public void setUploadDocumentForType(String uploadDocumentForType)
	{
		this.uploadDocumentForType = uploadDocumentForType;
	}

	public void setUploadFileDate(Timestamp uploadFileDate)
	{
		this.uploadFileDate = uploadFileDate;
	}

	public void setUploadFileFolderURL(String uploadFileFolderURL)
	{
		this.uploadFileFolderURL = SLASH + uploadFileFolderURL + SLASH;
	}

	public void setUploadFileLastModifiedDate(Timestamp uploadFileLastModifiedDate)
	{
		this.uploadFileLastModifiedDate = uploadFileLastModifiedDate;
	}

	public void setUploadFileName(String uploadFileName)
	{
		this.uploadFileName = uploadFileName;
	}

	public void setUploadFileSize(long uploadFileSize)
	{
		this.uploadFileSize = uploadFileSize;
	}

	public void setUploadFileVirtualURL(String uploadFileVirtualURL)
	{
		this.uploadFileVirtualURL = uploadFileVirtualURL;
	}

	public void setUploadMultiPartFile(MultipartFile uploadMultiPartFile)
	{
		this.uploadMultiPartFile = uploadMultiPartFile;
	}

	public void setUploadResourceHandler(String uploadResourceHandler)
	{
		this.uploadResourceHandler = uploadResourceHandler;
	}

	public void setUploadSubFolderPath(String uploadSubFolderPath)
	{
		this.uploadSubFolderPath = uploadSubFolderPath;
	}

}
