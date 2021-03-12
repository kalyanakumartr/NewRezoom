package org.hbs.v7.reader.action.webupload;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

public interface IWebUploadRestController extends IPathWebUpload
{
	@PostMapping
	@RequestMapping(value = WEB_UPLOAD)
	@PreAuthorize(HAS_AUTHORITY_BOTH)
	ResponseEntity<?> processUpload(Authentication auth, @PathVariable String uploadFileType, @RequestParam("files") MultipartFile[] files);

}