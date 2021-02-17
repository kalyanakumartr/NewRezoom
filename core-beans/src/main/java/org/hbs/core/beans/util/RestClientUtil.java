package org.hbs.core.beans.util;

import java.util.List;

import org.hbs.core.beans.IFormBean;
import org.hbs.core.security.resource.EnumResourceInterface;
import org.hbs.core.security.resource.IPath;
import org.hbs.core.util.CommonValidator;
import org.hbs.core.util.ICRUDBean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class RestClientUtil implements IPath
{

	private static final long serialVersionUID = 8910837520833417678L;

	public static List<? extends ICRUDBean> getDataByPOST(String _BaseURL, HttpHeaders headers, Object iFormBean)
	{
		return getDataByPOST(_BaseURL, null, headers, iFormBean);
	}

	public static List<? extends ICRUDBean> getDataByPOST(String _BaseURL, EnumResourceInterface resourcePath, String token, Object iFormBean)
	{
		HttpHeaders headers = new HttpHeaders();
		headers.add(ACCEPT, MediaType.APPLICATION_JSON_VALUE);
		headers.add(AUTHORIZATION, token);
		return getDataByPOST(_BaseURL, resourcePath, headers, iFormBean);
	}

	@SuppressWarnings("unchecked")
	public static <T> List<T> getDataByPOST(String _BaseURL, EnumResourceInterface resourcePath, HttpHeaders headers, Object iFormBean)
	{
		// HttpEntity<Object> httpEntity = new HttpEntity<Object>(iFormBean, headers);
		//
		// if (_BaseURL.trim().endsWith(SLASH))
		// _BaseURL = _BaseURL.substring(0, _BaseURL.lastIndexOf(SLASH));
		// _BaseURL = _BaseURL + (resourcePath != null ? resourcePath.getPath() : "");
		//
		// ResponseEntity<List<T>> response = new RestTemplate().exchange(_BaseURL, HttpMethod.POST,
		// httpEntity, new ParameterizedTypeReference<List<T>>() {
		// });

		ResponseEntity<?> response = getEntityByPOST(_BaseURL, resourcePath, headers, iFormBean);
		if (CommonValidator.isNotNullNotEmpty(response) && HttpStatus.OK == response.getStatusCode())
			return (List<T>) response.getBody();
		return null;

	}

	public static <T> ResponseEntity<T> getEntityByPOST(String _BaseURL, EnumResourceInterface resourcePath, String token, IFormBean iFormBean)
	{
		HttpHeaders headers = new HttpHeaders();
		if (CommonValidator.isNotNullNotEmpty(token))
		{
			headers.add(ACCEPT, MediaType.APPLICATION_JSON_VALUE);
			headers.add(AUTHORIZATION, token);
		}
		return getEntityByPOST(_BaseURL, resourcePath, headers, iFormBean);

	}

	public static <T> ResponseEntity<T> getEntityByPOST(String _BaseURL, EnumResourceInterface resourcePath, HttpHeaders headers, Object iFormBean)
	{
		HttpEntity<?> httpEntity = new HttpEntity<>(iFormBean, headers);

		if (_BaseURL.trim().endsWith(SLASH))
			_BaseURL = _BaseURL.substring(0, _BaseURL.lastIndexOf(SLASH));
		_BaseURL = _BaseURL + (resourcePath != null ? resourcePath.getPath() : "");

		return new RestTemplate().exchange(_BaseURL, HttpMethod.POST, httpEntity, new ParameterizedTypeReference<T>() {
		});

	}
}
