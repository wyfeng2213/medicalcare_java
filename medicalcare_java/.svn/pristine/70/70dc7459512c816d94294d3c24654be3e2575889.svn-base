package com.cmcc.medicalcare.utils;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>Title: iSoftStone</p>
 * <p>Description: </p>
 * HTTP���ʹ���
 * <p>Copyright: Copyright (c) 2012</p>
 * <p>Company: iSoftStone</p>
 *
 * @author Lilibo
 * @version 1.0
 */
public class HTTPUtils {

	public static String httpRequestEncoding = "UTF-8"; // HTTP������뷽ʽ
	public static String httpResponseEncoding = "UTF-8"; // HTTP��Ӧ���뷽ʽ
	public static int httpConnectTimeout = 60; // ���ӳ�ʱ(��)
	public static int httpReadTimeout = 60; // ��ȡ��ݳ�ʱ(��)
	public static int httpRetryCount = 1; // ����ʱ���Դ���

	private static int tryHttpDoGet = 0; // HTTP Get �������Լ�¼
	private static int tryHttpsDoGet = 0; // HTTPS Get �������Լ�¼
	private static int tryHttpDoPost = 0; // HTTP Post �������Լ�¼
	private static int tryHttpsDoPost = 0; // HTTPS Post �������Լ�¼

	private static boolean isInitSSL = false; // HTTPS���ӳ�ʼ��SSL������ʶ
	
	private static final String DES_KEY="12345678";
	
	private static Log log = LogFactory.getLog(HTTPUtils.class);

	 /**
     * @param request IP
     * @return IP Address
     */
    public static String getIpAddrByRequest(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
	
	/**
	 * HTTP GET����
	 * 
	 * @param urlstr ����URL
	 * @return ��������
	 */
	public static String httpDoGet(String urlstr) {
		return httpDoGet(urlstr, httpConnectTimeout, httpReadTimeout, httpResponseEncoding, httpRetryCount);
	}

	/**
	 * HTTP GET����
	 * 
	 * @param urlstr ����URL
	 * @param resencoding ��Ӧ�ı��뷽ʽ
	 * @return ��������
	 */
	public static String httpDoGet(String urlstr, String resencoding) {
		return httpDoGet(urlstr, httpConnectTimeout, httpReadTimeout, resencoding, httpRetryCount);
	}

	/**
	 * HTTP GET����
	 * 
	 * @param urlstr ����URL
	 * @param connecttimeout ���ӳ�ʱʱ��(��)
	 * @param readtimeout ��ȡ��ݳ�ʱʱ��(��)
	 * @param resencoding ��Ӧ�ı��뷽ʽ
	 * @param retrycount ʧ��ʱ���Դ���
	 * @return ��������
	 */
	public static String httpDoGet(String urlstr, int connecttimeout, int readtimeout, String resencoding, int retrycount) {
		// System.out.println("------------ [Start] HTTP DoGet URL : " + urlstr);
		// long t1 = System.currentTimeMillis();
		String response = null;
		InputStreamReader resinput = null;
		HttpURLConnection httpurlconn = null;
		try {
			URL url = new URL(urlstr);
			httpurlconn = (HttpURLConnection) url.openConnection();
			httpurlconn.setConnectTimeout(1000 * (connecttimeout < 0 ? httpConnectTimeout : connecttimeout));
			httpurlconn.setReadTimeout(1000 * (readtimeout < 0 ? httpReadTimeout : readtimeout));
			httpurlconn.setRequestMethod("GET");

			int icode = httpurlconn.getResponseCode();
			// // System.out.println("HTTP Response Code : " + icode + " (" + httpurlconn.getResponseMessage() + ") ");
			if (icode == HttpURLConnection.HTTP_OK) {
				int length = httpurlconn.getContentLength();
				if (length <= 0) {
					return null;
				}
				resinput = new InputStreamReader(httpurlconn.getInputStream(), (resencoding == null || "".equals(resencoding.trim()) ? httpResponseEncoding : resencoding.trim()));
				StringBuilder strbuf = new StringBuilder(length);
				char[] charbuff = new char[length];
		        int i;
		        while ((i = resinput.read(charbuff, 0, length - 1)) != -1) {
		        	strbuf.append(charbuff, 0, i);
		        }
		        response = strbuf.toString();
		        tryHttpDoGet = 0;
				// System.out.println("HTTP Response Content : \n------------ (" + length + ") \n" + response + "\n------------");
			} else {
				tryHttpDoGet++;
				if (tryHttpDoGet <= (retrycount < 0 ? httpRetryCount : retrycount)) {
					// System.out.println("------------ [Retry] HTTP DoGet TryCount : " + tryHttpDoGet);
					return httpDoGet(urlstr, connecttimeout, readtimeout, resencoding, retrycount);
				} else {
					return null;
				}
			}
		} catch (Exception e) {
			// System.out.println("HTTP DoGet Exception " + e);
			log.info("url"+urlstr); 
			e.printStackTrace();
		     
			tryHttpDoGet++;
			if (tryHttpDoGet <= (retrycount < 0 ? httpRetryCount : retrycount)) {
				// System.out.println("------------ [Retry] HTTP DoGet TryCount : " + tryHttpDoGet);
				return httpDoGet(urlstr, connecttimeout, readtimeout, resencoding, retrycount);
			} else {
				return null;
			}
		} finally {
			if (null != resinput) {
				try {
					resinput.close();
				} catch (Exception e) {
					// System.out.println("HTTP DoGet InputStreamReader Close Exception " + e);
				}
			}
			if (null != httpurlconn) {
				try {
					httpurlconn.disconnect();
				} catch (Exception e) {
					// System.out.println("HTTP DoGet HttpURLConnection Close Exception " + e);
				}
			}
			// long t2 = System.currentTimeMillis();
			// System.out.println("------------ [End] HTTP DoGet ( " + (t2 - t1) + " ms ) ");
		}
		return response;
	}

	/**
	 * HTTP POST����
	 * 
	 * @param urlstr ����URL
	 * @param params �������
	 * @return ��������
	 */
	public static String httpDoPost(String urlstr, Map<String, String> params) {
		return httpDoPost(urlstr, null, params, httpConnectTimeout, httpReadTimeout, httpRequestEncoding, httpResponseEncoding, httpRetryCount);
	}

	/**
	 * HTTP POST����
	 * 
	 * @param urlstr ����URL
	 * @param params �������
	 * @param reqencoding ����ı��뷽ʽ
	 * @param resencoding ��Ӧ�ı��뷽ʽ
	 * @return ��������
	 */
	public static String httpDoPost(String urlstr, Map<String, String> params, String reqencoding, String resencoding) {
		return httpDoPost(urlstr, null, params, httpConnectTimeout, httpReadTimeout, reqencoding, resencoding, httpRetryCount);
	}

	/**
	 * HTTP POST����
	 * 
	 * @param urlstr ����URL
	 * @param header �������
	 * @param params �������
	 * @param reqencoding ����ı��뷽ʽ
	 * @param resencoding ��Ӧ�ı��뷽ʽ
	 * @return ��������
	 */
	public static String httpDoPost(String urlstr, Map<String, String> header, Map<String, String> params, String reqencoding, String resencoding) {
		return httpDoPost(urlstr, header, params, httpConnectTimeout, httpReadTimeout, reqencoding, resencoding, httpRetryCount);
	}

	/**
	 * HTTP POST����
	 * 
	 * @param urlstr ����URL
	 * @param header ͷ������
	 * @param params �������
	 * @param connecttimeout ���ӳ�ʱʱ��(��)
	 * @param readtimeout ��ȡ��ݳ�ʱʱ��(��)
	 * @param reqencoding ����ı��뷽ʽ
	 * @param resencoding ��Ӧ�ı��뷽ʽ
	 * @param retrycount ʧ��ʱ���Դ���
	 * @return ��������
	 */
	public static String httpDoPost(String urlstr, Map<String, String> header, Map<String, String> params, int connecttimeout, int readtimeout, String reqencoding, String resencoding, int retrycount) {
		// System.out.println("------------ [Start] HTTP DoPost URL : " + urlstr);
		// long t1 = System.currentTimeMillis();
		String response = null;
		InputStreamReader resinput = null;
		HttpURLConnection httpurlconn = null;
		try {
			URL url = new URL(urlstr);
			httpurlconn = (HttpURLConnection) url.openConnection();
			httpurlconn.setConnectTimeout(1000 * (connecttimeout < 0 ? httpConnectTimeout : connecttimeout));
			httpurlconn.setReadTimeout(1000 * (readtimeout < 0 ? httpReadTimeout : readtimeout));
			httpurlconn.setRequestMethod("POST");
			httpurlconn.setRequestProperty("Content-type", "application/x-www-form-urlencoded;charset=" + (reqencoding == null || "".equals(reqencoding.trim()) ? httpRequestEncoding : reqencoding.trim()));
			httpurlconn.setDoInput(true);
			httpurlconn.setDoOutput(true);
			if (null != header) {
				for (String header_key : header.keySet()) {
					httpurlconn.setRequestProperty(header_key, header.get(header_key));
				}
			}
			if (null != params) {
				StringBuilder strbuf = new StringBuilder();
				for (String key : params.keySet()) {
					strbuf.append(key).append("=").append(params.get(key)).append("&");
				}
				String postparams = strbuf.toString();
				postparams = postparams.substring(0, postparams.length() -1);
				// System.out.println("HTTPS Ruquest Params : " + postparams);
				byte[] data = postparams.getBytes(reqencoding);
				int length = data.length;
				httpurlconn.setRequestProperty("Content-Length", String.valueOf(length));
				if (length != 0) {
					OutputStream resoutput = null;
					try {
						resoutput = httpurlconn.getOutputStream();
						resoutput.write(data);
						resoutput.flush();
					} catch (Exception e) {
						// System.out.println("HTTPS Ruquest Params Exception " + e);
						log.info("url"+urlstr); 
						e.printStackTrace();
					} finally {
						if (null != resoutput) {
							try {
								resoutput.close();
							} catch (Exception e) {
								// System.out.println("HTTPS Ruquest Params OutputStreamWriter Close Exception " + e);
							}
						}
					}
				}
			}

			int icode = httpurlconn.getResponseCode();
			// System.out.println("HTTP Response Code : " + icode + " (" + httpurlconn.getResponseMessage() + ") ");
			if (icode == HttpURLConnection.HTTP_OK) {
				int length = httpurlconn.getContentLength();
				if (length <= 0) {
					return null;
				}
				resinput = new InputStreamReader(httpurlconn.getInputStream(), (resencoding == null || "".equals(resencoding.trim()) ? httpResponseEncoding : resencoding.trim()));
				StringBuilder strbuf = new StringBuilder(length);
				char[] charbuff = new char[length];
		        int i;
		        while ((i = resinput.read(charbuff, 0, length - 1)) != -1) {
		        	strbuf.append(charbuff, 0, i);
		        }
		        response = strbuf.toString();
		        tryHttpDoPost = 0;
				// System.out.println("HTTP Response Content : \n------------ (" + length + ") \n" + response + "\n------------");
			} else {
				tryHttpDoPost++;
				if (tryHttpDoPost <= (retrycount < 0 ? httpRetryCount : retrycount)) {
					// System.out.println("------------ [Retry] HTTP DoPost TryCount : " + tryHttpDoPost);
					return httpDoPost(urlstr, header, params, connecttimeout, readtimeout, reqencoding, resencoding, retrycount);
				} else {
					return null;
				}
			}
		} catch (Exception e) {
			// System.out.println("HTTP DoPost Exception " + e);
			log.info("url"+urlstr); 
			e.printStackTrace();
			tryHttpDoPost++;
			if (tryHttpDoPost <= (retrycount < 0 ? httpRetryCount : retrycount)) {
				// System.out.println("------------ [Retry] HTTP DoPost TryCount : " + tryHttpDoPost);
				return httpDoPost(urlstr, header, params, connecttimeout, readtimeout, reqencoding, resencoding, retrycount);
			} else {
				return null;
			}
		} finally {
			if (null != resinput) {
				try {
					resinput.close();
				} catch (Exception e) {
					// System.out.println("HTTP DoPost InputStreamReader Close Exception " + e);
				}
			}
			if (null != httpurlconn) {
				try {
					httpurlconn.disconnect();
				} catch (Exception e) {
					// System.out.println("HTTP DoPost HttpURLConnection Close Exception " + e);
				}
			}
			// long t2 = System.currentTimeMillis();
			// System.out.println("------------ [End] HTTP DoPost ( " + (t2 - t1) + " ms ) ");
		}
		return response;
	}
	public static String httpDoPostStrT(String urlstr, String content, String reqencoding, String resencoding) {
		System.out.println("------------ [Start] HTTPS DoPost URL : " + urlstr);
		long t1 = System.currentTimeMillis();
		String response = null;
		InputStreamReader resinput = null;
		HttpURLConnection httpsurlconn = null;
	
		try {
			URL url = new URL(urlstr);
			httpsurlconn = (HttpURLConnection) url.openConnection();
			httpsurlconn.setConnectTimeout(httpConnectTimeout*1000);
			httpsurlconn.setReadTimeout(httpReadTimeout*1000);
			httpsurlconn.setRequestMethod("POST");
			httpsurlconn.setRequestProperty("Content-Type", "application/json; charset=" +reqencoding);// �����ļ�����
			
			httpsurlconn.setDoInput(true);
			httpsurlconn.setDoOutput(true);
			httpsurlconn.setRequestProperty("token", TokenEncryptUtils.sxyEncryMD5(content));
				byte[] data = content.getBytes(reqencoding);
				int dlength = data.length;
				httpsurlconn.setRequestProperty("Content-Length", String.valueOf(dlength));
				if (dlength != 0) {
					OutputStream resoutput = null;
					try {
						resoutput = httpsurlconn.getOutputStream();
						resoutput.write(data);
						resoutput.flush();
					} catch (Exception e) {
						log.info("url"+urlstr); 
						e.printStackTrace();
					} finally {
						if (null != resoutput) {
							try {
								resoutput.close();
							} catch (Exception e) {
								System.out.println("HTTPS Ruquest Params OutputStreamWriter Close Exception " + e);
							}
						}
					}
				}
			

			int icode = httpsurlconn.getResponseCode();
			System.out.println("HTTPS Response Code : " + icode + " (" + httpsurlconn.getResponseMessage() + ") ");
			if (icode == HttpURLConnection.HTTP_OK) {
				int length = httpsurlconn.getContentLength();
				if (length <= 0) {
					return null;
				}
				resinput = new InputStreamReader(httpsurlconn.getInputStream(), (resencoding == null || "".equals(resencoding.trim()) ? httpResponseEncoding : resencoding.trim()));
				StringBuilder strbuf = new StringBuilder(length);
				char[] charbuff = new char[length];
		        int i;
		        while ((i = resinput.read(charbuff, 0, length - 1)) != -1) {
		        	strbuf.append(charbuff, 0, i);
		        }
		        response = strbuf.toString();
		        tryHttpsDoPost = 0;
				System.out.println("HTTPS Response Content : \n------------ (" + length + ") \n" + response + "\n------------");
			} else {
				tryHttpsDoPost++;
				if (tryHttpsDoPost <= httpRetryCount) {
					System.out.println("------------ [Retry] HTTPS DoPost TryCount : " + tryHttpsDoPost);
					return httpDoPostStr(urlstr, content, reqencoding, resencoding);
				} else {
					return null;
				}
			}
		} catch (Exception e) {
			log.info("url"+urlstr); 
			e.printStackTrace();
			tryHttpsDoPost++;
			if (tryHttpsDoPost <= httpRetryCount) {
				System.out.println("------------ [Retry] HTTPS DoPost TryCount : " + tryHttpsDoPost);
				return httpDoPostStrT(urlstr, content, reqencoding, resencoding);
			} else {
				return null;
			}
		} finally {
			if (null != resinput) {
				try {
					resinput.close();
				} catch (Exception e) {
					log.info("url"+urlstr); 
					e.printStackTrace();
				}
			}
			if (null != httpsurlconn) {
				try {
					httpsurlconn.disconnect();
				} catch (Exception e) {
					log.info("url"+urlstr); 
					e.printStackTrace();
				}
			}
			long t2 = System.currentTimeMillis();
			System.out.println("------------ [End] HTTPS DoPost ( " + (t2 - t1) + " ms ) ");
		}
		return response;
	}
	/**
	 * HTTPS GET����
	 * 
	 * @param urlstr ����URL
	 * @return ��������
	 * 
	 * @see  #initSSL(String keyStorePath, String password, String trustStorePath)
	 */
	public static String httpsDoGet(String urlstr) {
		return httpsDoGet(urlstr, httpConnectTimeout, httpReadTimeout, httpResponseEncoding, httpRetryCount);
	}

	/**
	 * HTTPS GET����
	 * 
	 * @param urlstr ����URL
	 * @param resencoding ��Ӧ�ı��뷽ʽ
	 * @return ��������
	 * 
	 * @see  #initSSL(String keyStorePath, String password, String trustStorePath)
	 */
	public static String httpsDoGet(String urlstr, String resencoding) {
		return httpsDoGet(urlstr, httpConnectTimeout, httpReadTimeout, resencoding, httpRetryCount);
	}

	/**
	 * HTTPS GET����
	 * 
	 * @param urlstr ����URL
	 * @param connecttimeout ���ӳ�ʱʱ��(��)
	 * @param readtimeout ��ȡ��ݳ�ʱʱ��(��)
	 * @param resencoding ��Ӧ�ı��뷽ʽ
	 * @param retrycount ʧ��ʱ���Դ���
	 * @return ��������
	 * 
	 * @see  #initSSL(String keyStorePath, String password, String trustStorePath)
	 */
	public static String httpsDoGet(String urlstr, int connecttimeout, int readtimeout, String resencoding, int retrycount) {
		// System.out.println("------------ [Start] HTTPS DoGet URL : " + urlstr);
		// long t1 = System.currentTimeMillis();
		String response = null;
		InputStreamReader resinput = null;
		HttpsURLConnection httpsurlconn = null;
		try {
			URL url = new URL(urlstr);
			httpsurlconn = (HttpsURLConnection) url.openConnection();
			httpsurlconn.setConnectTimeout(1000 * (connecttimeout < 0 ? httpConnectTimeout : connecttimeout));
			httpsurlconn.setReadTimeout(1000 * (readtimeout < 0 ? httpReadTimeout : readtimeout));
			httpsurlconn.setRequestMethod("GET");

			int icode = httpsurlconn.getResponseCode();
			// System.out.println("HTTPS Response Code : " + icode + " (" + httpsurlconn.getResponseMessage() + ") ");
			if (icode == HttpURLConnection.HTTP_OK) {
				int length = httpsurlconn.getContentLength();
				if (length <= 0) {
					return null;
				}
				resinput = new InputStreamReader(httpsurlconn.getInputStream(), (resencoding == null || "".equals(resencoding.trim()) ? httpResponseEncoding : resencoding.trim()));
				StringBuilder strbuf = new StringBuilder(length);
				char[] charbuff = new char[length];
		        int i;
		        while ((i = resinput.read(charbuff, 0, length - 1)) != -1) {
		        	strbuf.append(charbuff, 0, i);
		        }
		        response = strbuf.toString();
		        tryHttpsDoGet = 0;
				// System.out.println("HTTPS Response Content : \n------------ (" + length + ") \n" + response + "\n------------");
			} else {
				tryHttpsDoGet++;
				if (tryHttpsDoGet <= (retrycount < 0 ? httpRetryCount : retrycount)) {
					// System.out.println("------------ [Retry] HTTPS DoGet TryCount : " + tryHttpsDoGet);
					return httpsDoGet(urlstr, connecttimeout, readtimeout, resencoding, retrycount);
				} else {
					return null;
				}
			}
		} catch (Exception e) {
			log.info("url"+urlstr); 
			e.printStackTrace();
			tryHttpsDoGet++;
			if (tryHttpsDoGet <= (retrycount < 0 ? httpRetryCount : retrycount)) {
				// System.out.println("------------ [Retry] HTTPS DoGet TryCount : " + tryHttpsDoGet);
				return httpsDoGet(urlstr, connecttimeout, readtimeout, resencoding, retrycount);
			} else {
				return null;
			}
		} finally {
			if (null != resinput) {
				try {
					resinput.close();
				} catch (Exception e) {
					// System.out.println("HTTPS DoGet InputStreamReader Close Exception " + e);
				}
			}
			if (null != httpsurlconn) {
				try {
					httpsurlconn.disconnect();
				} catch (Exception e) {
					// System.out.println("HTTPS DoGet HttpURLConnection Close Exception " + e);
				}
			}
			// long t2 = System.currentTimeMillis();
			// System.out.println("------------ [End] HTTPS DoGet ( " + (t2 - t1) + " ms ) ");
		}
		return response;
	}

	/**
	 * HTTPS POST����
	 * 
	 * @param urlstr ����URL
	 * @param params �������
	 * @return ��������
	 * 
	 * @see  #initSSL(String keyStorePath, String password, String trustStorePath)
	 */
	public static String httpsDoPost(String urlstr, Map<String, String> params) {
		return httpsDoPost(urlstr, null, params, httpConnectTimeout, httpReadTimeout, httpRequestEncoding, httpResponseEncoding, httpRetryCount);
	}

	/**
	 * HTTPS POST����
	 * 
	 * @param urlstr ����URL
	 * @param params �������
	 * @param reqencoding ����ı��뷽ʽ
	 * @param resencoding ��Ӧ�ı��뷽ʽ
	 * @return ��������
	 * 
	 * @see  #initSSL(String keyStorePath, String password, String trustStorePath)
	 */
	public static String httpsDoPost(String urlstr, Map<String, String> params, String reqencoding, String resencoding) {
		return httpsDoPost(urlstr, null, params, httpConnectTimeout, httpReadTimeout, reqencoding, resencoding, httpRetryCount);
	}

	/**
	 * HTTPS POST����
	 * 
	 * @param urlstr ����URL
	 * @param header ͷ������
	 * @param params �������
	 * @param reqencoding ����ı��뷽ʽ
	 * @param resencoding ��Ӧ�ı��뷽ʽ
	 * @return ��������
	 * 
	 * @see  #initSSL(String keyStorePath, String password, String trustStorePath)
	 */
	public static String httpsDoPost(String urlstr, Map<String, String> header, Map<String, String> params, String reqencoding, String resencoding) {
		return httpsDoPost(urlstr, header, params, httpConnectTimeout, httpReadTimeout, reqencoding, resencoding, httpRetryCount);
	}

	/**
	 * HTTPS POST����
	 * 
	 * @param urlstr ����URL
	 * @param header ͷ������
	 * @param params �������
	 * @param connecttimeout ���ӳ�ʱʱ��(��)
	 * @param readtimeout ��ȡ��ݳ�ʱʱ��(��)
	 * @param reqencoding ����ı��뷽ʽ
	 * @param resencoding ��Ӧ�ı��뷽ʽ
	 * @param retrycount ʧ��ʱ���Դ���
	 * @return ��������
	 * 
	 * @see  #initSSL(String keyStorePath, String password, String trustStorePath)
	 */
	public static String httpsDoPost(String urlstr, Map<String, String> header, Map<String, String> params, int connecttimeout, int readtimeout, String reqencoding, String resencoding, int retrycount) {
		System.out.println("------------ [Start] HTTPS DoPost URL : " + urlstr);
		long t1 = System.currentTimeMillis();
		String response = null;
		InputStreamReader resinput = null;
		HttpsURLConnection httpsurlconn = null;
		try {
			URL url = new URL(urlstr);
			httpsurlconn = (HttpsURLConnection) url.openConnection();
			httpsurlconn.setConnectTimeout(1000 * (connecttimeout < 0 ? httpConnectTimeout : connecttimeout));
			httpsurlconn.setReadTimeout(1000 * (readtimeout < 0 ? httpReadTimeout : readtimeout));
			httpsurlconn.setRequestMethod("POST");
			httpsurlconn.setRequestProperty("Content-type", "application/x-www-form-urlencoded;charset=" + (reqencoding = (reqencoding == null || "".equals(reqencoding.trim()) ? httpRequestEncoding : reqencoding.trim())));
			httpsurlconn.setDoInput(true);
			httpsurlconn.setDoOutput(true);
			if (null != header) {
				for (String header_key : header.keySet()) {
					httpsurlconn.setRequestProperty(header_key, header.get(header_key));
				}
			}
			if (null != params) {
				StringBuilder strbuf = new StringBuilder();
				for (String key : params.keySet()) {
					strbuf.append(key).append("=").append(params.get(key)).append("&");
				}
				String postparams = strbuf.toString();
				postparams = postparams.substring(0, postparams.length() -1);
				System.out.println("HTTPS Ruquest Params : " + postparams);
				byte[] data = postparams.getBytes(reqencoding);
				int length = data.length;
				httpsurlconn.setRequestProperty("Content-Length", String.valueOf(length));
				if (length != 0) {
					OutputStream resoutput = null;
					try {
						resoutput = httpsurlconn.getOutputStream();
						resoutput.write(data);
						resoutput.flush();
					} catch (Exception e) {
						log.info("url"+urlstr); 
						e.printStackTrace();
					} finally {
						if (null != resoutput) {
							try {
								resoutput.close();
							} catch (Exception e) {
								System.out.println("HTTPS Ruquest Params OutputStreamWriter Close Exception " + e);
							}
						}
					}
				}
			}

			int icode = httpsurlconn.getResponseCode();
			System.out.println("HTTPS Response Code : " + icode + " (" + httpsurlconn.getResponseMessage() + ") ");
			if (icode == HttpURLConnection.HTTP_OK) {
				int length = httpsurlconn.getContentLength();
				if (length <= 0) {
					return null;
				}
				resinput = new InputStreamReader(httpsurlconn.getInputStream(), (resencoding == null || "".equals(resencoding.trim()) ? httpResponseEncoding : resencoding.trim()));
				StringBuilder strbuf = new StringBuilder(length);
				char[] charbuff = new char[length];
		        int i;
		        while ((i = resinput.read(charbuff, 0, length - 1)) != -1) {
		        	strbuf.append(charbuff, 0, i);
		        }
		        response = strbuf.toString();
		        tryHttpsDoPost = 0;
				System.out.println("HTTPS Response Content : \n------------ (" + length + ") \n" + response + "\n------------");
			} else {
				tryHttpsDoPost++;
				if (tryHttpsDoPost <= (retrycount < 0 ? httpRetryCount : retrycount)) {
					System.out.println("------------ [Retry] HTTPS DoPost TryCount : " + tryHttpsDoPost);
					return httpsDoPost(urlstr, header, params, connecttimeout, readtimeout, reqencoding, resencoding, retrycount);
				} else {
					return null;
				}
			}
		} catch (Exception e) {
			log.info("url"+urlstr); 
			e.printStackTrace();
			tryHttpsDoPost++;
			if (tryHttpsDoPost <= (retrycount < 0 ? httpRetryCount : retrycount)) {
				System.out.println("------------ [Retry] HTTPS DoPost TryCount : " + tryHttpsDoPost);
				return httpsDoPost(urlstr, header, params, connecttimeout, readtimeout, reqencoding, resencoding, retrycount);
			} else {
				return null;
			}
		} finally {
			if (null != resinput) {
				try {
					resinput.close();
				} catch (Exception e) {
					System.out.println("HTTPS DoPost InputStreamReader Close Exception " + e);
				}
			}
			if (null != httpsurlconn) {
				try {
					httpsurlconn.disconnect();
				} catch (Exception e) {
					System.out.println("HTTPS DoPost HttpsURLConnection Close Exception " + e);
				}
			}
			long t2 = System.currentTimeMillis();
			System.out.println("------------ [End] HTTPS DoPost ( " + (t2 - t1) + " ms ) ");
		}
		return response;
	}

	public static String httpDoPostStr(String urlstr, String content, String reqencoding, String resencoding) {
		System.out.println("------------ [Start] HTTPS DoPost URL : " + urlstr);
		long t1 = System.currentTimeMillis();
		String response = null;
		InputStreamReader resinput = null;
		HttpURLConnection httpsurlconn = null;
	
		try {
			URL url = new URL(urlstr);
			httpsurlconn = (HttpURLConnection) url.openConnection();
			httpsurlconn.setConnectTimeout(httpConnectTimeout * 1000);
			httpsurlconn.setReadTimeout(httpReadTimeout * 1000);
			httpsurlconn.setRequestMethod("POST");
			httpsurlconn.setRequestProperty("Content-Type", "text/xml; charset=" +reqencoding);// �����ļ�����
			
			httpsurlconn.setDoInput(true);
			httpsurlconn.setDoOutput(true);
		
				
				byte[] data = content.getBytes(reqencoding);
				int dlength = data.length;
				httpsurlconn.setRequestProperty("Content-Length", String.valueOf(dlength));
				if (dlength != 0) {
					OutputStream resoutput = null;
					try {
						resoutput = httpsurlconn.getOutputStream();
						resoutput.write(data);
						resoutput.flush();
					} catch (Exception e) {
						log.info("url"+urlstr); 
						e.printStackTrace();
					} finally {
						if (null != resoutput) {
							try {
								resoutput.close();
							} catch (Exception e) {
								System.out.println("HTTPS Ruquest Params OutputStreamWriter Close Exception " + e);
							}
						}
					}
				}
			

			int icode = httpsurlconn.getResponseCode();
			System.out.println("HTTPS Response Code : " + icode + " (" + httpsurlconn.getResponseMessage() + ") ");
			if (icode == HttpURLConnection.HTTP_OK) {
				int length = httpsurlconn.getContentLength();
				if (length <= 0) {
					return null;
				}
				resinput = new InputStreamReader(httpsurlconn.getInputStream(), (resencoding == null || "".equals(resencoding.trim()) ? httpResponseEncoding : resencoding.trim()));
				StringBuilder strbuf = new StringBuilder(length);
				char[] charbuff = new char[length];
		        int i;
		        while ((i = resinput.read(charbuff, 0, length - 1)) != -1) {
		        	strbuf.append(charbuff, 0, i);
		        }
		        response = strbuf.toString();
		        tryHttpsDoPost = 0;
				System.out.println("HTTPS Response Content : \n------------ (" + length + ") \n" + response + "\n------------");
			} else {
				tryHttpsDoPost++;
				if (tryHttpsDoPost <= httpRetryCount) {
					System.out.println("------------ [Retry] HTTPS DoPost TryCount : " + tryHttpsDoPost);
					return httpDoPostStr(urlstr, content, reqencoding, resencoding);
				} else {
					return null;
				}
			}
		} catch (Exception e) {
			System.out.println("HTTPS DoPost Exception " + e);
			tryHttpsDoPost++;
			if (tryHttpsDoPost <= httpRetryCount) {
				log.info("url"+urlstr); 
				e.printStackTrace();
				return httpDoPostStr(urlstr, content, reqencoding, resencoding);
			} else {
				return null;
			}
		} finally {
			if (null != resinput) {
				try {
					resinput.close();
				} catch (Exception e) {
					System.out.println("HTTPS DoPost InputStreamReader Close Exception " + e);
				}
			}
			if (null != httpsurlconn) {
				try {
					httpsurlconn.disconnect();
				} catch (Exception e) {
					System.out.println("HTTPS DoPost HttpsURLConnection Close Exception " + e);
				}
			}
			long t2 = System.currentTimeMillis();
			System.out.println("------------ [End] HTTPS DoPost ( " + (t2 - t1) + " ms ) ");
		}
		return response;
	}
	
	/**
	 * ��ʼ��HTTPS����, ����֤�� (����HTTPS����֮ǰ��ʼ��, ��ֻ���ʼ��һ��)
	 * <br/><br/>
	 * ���KeyStore����:
	 * <br/>
	 * keytool -genkey -alias tomcat -keyalg RSA -keysize 1024 -validity 365 -keystore tomcat.keystore
	 * 
	 * @return ��ʼ���ɹ�
	 */
	public static boolean initSSL() {
		if (isInitSSL) { // �ж��Ƿ��ѳ�ʼ����ʶ, ��ֻ֤��ʼ��һ��
			return true;
		}
		try {
			// ʵ��SSL������
			SSLContext context = SSLContext.getInstance("TLS");
			context.init(null, new TrustManager[]{new MyX509TrustManager()}, null);

			// ʵ��HTTPS����
			HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());
			HttpsURLConnection.setDefaultHostnameVerifier(new MyHostnameVerifier());
		} catch (Exception e) {
			e.printStackTrace();
			isInitSSL = false; // ���ó�ʼ����ʶ
			return false;
		}
		isInitSSL = true; // ���ó�ʼ����ʶ
		return true;
	}

	/**
	 * ��ʼ��HTTPS���� (����HTTPS����֮ǰ��ʼ��, ��ֻ���ʼ��һ��)
	 * <br/><br/>
	 * ���KeyStore����:
	 * <br/>
	 * keytool -genkey -alias tomcat -keyalg RSA -keysize 1024 -validity 365 -keystore tomcat.keystore
	 * 
	 * @param keyStorePath ��Կ��·��
	 * @param password ��Կ������
	 * @param trustStorePath ���ο�·��
	 * @return ��ʼ���ɹ�
	 */
	public static boolean initSSL(String keyStorePath, String password, String trustStorePath) {
		if (isInitSSL) { // �ж��Ƿ��ѳ�ʼ����ʶ, ��ֻ֤��ʼ��һ��
			return true;
		}
		try {
			// ʵ����Կ��
			KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
			// �����Կ��
			KeyStore keyStore = getKeyStore(keyStorePath, password);
			if (keyStore != null) {
				keyManagerFactory.init(keyStore, password.toCharArray());
			}

			// ʵ�����ο�
			TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
			KeyStore trustStore = getKeyStore(trustStorePath, password);
			if (trustStore != null) {
				trustManagerFactory.init(trustStore);
			}

			// ʵ��SSL������
			SSLContext context = SSLContext.getInstance("TLS");
			context.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(), null);

			// ʵ��HTTPS����
			HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());
			HttpsURLConnection.setDefaultHostnameVerifier(new MyHostnameVerifier());
		} catch (Exception e) {
			e.printStackTrace();
			isInitSSL = false; // ���ó�ʼ����ʶ
			return false;
		}
		isInitSSL = true; // ���ó�ʼ����ʶ
		return true;
	}

	/**
	 * �����Կ��
	 * 
	 * @param keyStorePath ��Կ��·��
	 * @param password ��Կ������
	 * @return ��Կ��
	 */
	public static KeyStore getKeyStore(String keyStorePath, String password) {
		if (keyStorePath == null) {
			return null;
		}
		KeyStore keyStore = null;
		FileInputStream input = null;
		try {
			keyStore = KeyStore.getInstance("JKS"); // ʵ����Կ��
			input = new FileInputStream(keyStorePath); // �����Կ���ļ���
			keyStore.load(input, password.toCharArray()); // ������Կ��
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return keyStore;
	}

}


class MyX509TrustManager implements X509TrustManager {

	public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
	}

	public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
	}

	public X509Certificate[] getAcceptedIssuers() {
		return null;
	}

}


class MyHostnameVerifier implements HostnameVerifier {

	public boolean verify(String hostname, SSLSession session) {
		return true;
	}

}
