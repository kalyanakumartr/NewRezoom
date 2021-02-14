package org.hbs.core.bean.model.application;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hbs.core.bean.model.CommonFileUpload;
import org.hbs.core.util.EnumInterface;

@Entity
@Table(name = "resume_attachments")
public class ResumeAttachments extends CommonFileUpload
{

	public enum EResumeTrace implements EnumInterface
	{
		AdditionalDocuments, MainDocument, YetToTrace
	}

	private static final long	serialVersionUID	= 3340835331638013651L;

	protected IncomingData		incomingData;
	protected Resume			resume;
	protected EResumeTrace		trace				= EResumeTrace.YetToTrace;

	public ResumeAttachments()
	{
		super();
	}

	public ResumeAttachments(Resume resume)
	{
		super();
		this.resume = resume;
	}

	@ManyToOne(targetEntity = IncomingData.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "incomingId")
	public IncomingData getIncomingData()
	{
		return incomingData;
	}

	@ManyToOne(targetEntity = Resume.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "resumeURN")
	public Resume getResume()
	{
		return resume;
	}

	@Enumerated(EnumType.STRING)
	@Column(name = "trace")
	public EResumeTrace getTrace()
	{
		return trace;
	}

	public void setIncomingData(IncomingData incomingData)
	{
		this.incomingData = incomingData;
	}

	public void setResume(Resume resume)
	{
		this.resume = resume;
	}

	public void setTrace(EResumeTrace trace)
	{
		this.trace = trace;
	}

}
