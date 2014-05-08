package com.mixiaoxiao.android.util;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;

/**
 * HttpUtil Class Capsule Most Functions of Http Operations
 * 
 * @author sfshine
 * 
 */
public class MxxHttpUtil {
	private static Header[] headers = new BasicHeader[1];
	private static String TAG = "HTTPUTIL";
	private static int TIMEOUT = 5 * 1000;
	private static final String BOUNDARY = "---------------------------7db1c523809b2";
	/**
	 * Your header of http op
	 * 
	 * @return
	 */
	static {

		headers[0] = new BasicHeader("User-Agent",
				"Mozilla/4.0 (compatible; MSIE 5.0; Windows XP; DigExt)");

	}

	public static boolean delete(String murl) throws Exception {
		URL url = new URL(murl);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("DELETE");
		conn.setConnectTimeout(5000);
		if (conn.getResponseCode() == 204) {
			return true;
		}
		return false;
	}

	/**
	 * Op Http get request
	 * 
	 * @param url
	 * @param map
	 *            Values to request
	 * @return
	 */
	static public String get(String url) {
		return doHttpGet(url, null);

	}

	static public String doHttpGet(String url, HashMap<String, String> map) {

		HttpClient client = new DefaultHttpClient();
		HttpConnectionParams.setConnectionTimeout(client.getParams(), TIMEOUT);
		HttpConnectionParams.setSoTimeout(client.getParams(), TIMEOUT);
		ConnManagerParams.setTimeout(client.getParams(), TIMEOUT);
		String result = "ERROR";
		if (null != map) {
			int i = 0;
			for (Map.Entry<String, String> entry : map.entrySet()) {

				if (i == 0) {
					url = url + "?" + entry.getKey() + "=" + entry.getValue();
				} else {
					url = url + "&" + entry.getKey() + "=" + entry.getValue();
				}

				i++;

			}
		}
		HttpGet get = new HttpGet(url);
		get.setHeaders(headers);
		try {

			HttpResponse response = client.execute(get);

			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				// setCookie(response);
				result = EntityUtils.toString(response.getEntity(), "UTF-8");

			} else {
				result = EntityUtils.toString(response.getEntity(), "UTF-8")
						+ response.getStatusLine().getStatusCode() + "ERROR";
			}
			if (result != null){
				if (result.startsWith("\ufeff")) { 
					result = result.substring(1);
				}
			}

		} catch (ConnectTimeoutException e) {
			result = "TIMEOUTERROR";
		}

		catch (Exception e) {
			result = "OTHERERROR";
			e.printStackTrace();

		}

		return result;
	}

	/**
	 * Op Http post request , "404error" response if failed
	 * 
	 * @param url
	 * @param map
	 *            Values to request
	 * @return
	 */

	static public String doHttpPost(String url, HashMap<String, String> map) {

		HttpClient client = new DefaultHttpClient();
		HttpConnectionParams.setConnectionTimeout(client.getParams(), TIMEOUT);
		HttpConnectionParams.setSoTimeout(client.getParams(), TIMEOUT);
		ConnManagerParams.setTimeout(client.getParams(), TIMEOUT);
		HttpPost post = new HttpPost(url);
		post.setHeaders(headers);
		String result = "ERROR";
		ArrayList<BasicNameValuePair> pairList = new ArrayList<BasicNameValuePair>();
		if (map != null) {
			for (Map.Entry<String, String> entry : map.entrySet()) {
				BasicNameValuePair pair = new BasicNameValuePair(
						entry.getKey(), entry.getValue());
				pairList.add(pair);
			}

		}
		try {
			HttpEntity entity = new UrlEncodedFormEntity(pairList, "UTF-8");
			post.setEntity(entity);
			HttpResponse response = client.execute(post);

			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {

				result = EntityUtils.toString(response.getEntity(), "UTF-8");

			} else {
				result = EntityUtils.toString(response.getEntity(), "UTF-8")
						+ response.getStatusLine().getStatusCode() + "ERROR";
			}

		} catch (ConnectTimeoutException e) {
			result = "TIMEOUTERROR";
		}

		catch (Exception e) {
			result = "OTHERERROR";
			e.printStackTrace();

		}
		return result;
	}

	/**
	 * 自定义的http请求可以设置为DELETE PUT等而不是GET
	 * 
	 * @param url
	 * @param params
	 * @param method
	 * @throws IOException
	 */

	public static String customrequest(String url,
			HashMap<String, String> params, String method) {
		try {

			URL postUrl = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) postUrl
					.openConnection();
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setConnectTimeout(5 * 1000);

			conn.setRequestMethod(method);
			conn.setUseCaches(false);
			conn.setInstanceFollowRedirects(true);
			conn.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			conn.setRequestProperty("User-Agent",
					"Mozilla/4.0 (compatible; MSIE 5.0; Windows XP; DigExt)");

			conn.connect();
			OutputStream out = conn.getOutputStream();
			StringBuilder sb = new StringBuilder();
			if (null != params) {
				int i = params.size();
				for (Map.Entry<String, String> entry : params.entrySet()) {
					if (i == 1) {
						sb.append(entry.getKey() + "=" + entry.getValue());
					} else {
						sb.append(entry.getKey() + "=" + entry.getValue() + "&");
					}

					i--;
				}
			}
			String content = sb.toString();
			out.write(content.getBytes("UTF-8"));
			out.flush();
			out.close();
			InputStream inStream = conn.getInputStream();
			String result = inputStream2String(inStream);
			conn.disconnect();
			return result;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	/**
	 * 必须严格限制get请求所以增加这个方法 这个方法也可以自定义请求
	 * 
	 * @param url
	 * @param method
	 * @throws Exception
	 */

	public static String customrequestget(String url,
			HashMap<String, String> map, String method) {

		if (null != map) {
			int i = 0;
			for (Map.Entry<String, String> entry : map.entrySet()) {

				if (i == 0) {
					url = url + "?" + entry.getKey() + "=" + entry.getValue();
				} else {
					url = url + "&" + entry.getKey() + "=" + entry.getValue();
				}

				i++;
			}
		}
		try {

			URL murl = new URL(url);
			System.out.print(url);
			HttpURLConnection conn = (HttpURLConnection) murl.openConnection();
			conn.setConnectTimeout(5 * 1000);
			conn.setRequestMethod(method);

			conn.setRequestProperty("User-Agent",
					"Mozilla/4.0 (compatible; MSIE 5.0; Windows XP; DigExt)");

			InputStream inStream = conn.getInputStream();
			String result = inputStream2String(inStream);
			conn.disconnect();
			return result;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	/**
	 * 上传多张图片
	 */
	public static void post(String actionUrl, Map<String, String> params,
			Map<String, File> files) throws IOException {

		String BOUNDARY = java.util.UUID.randomUUID().toString();
		String PREFIX = "--", LINEND = "\r\n";
		String MULTIPART_FROM_DATA = "multipart/form-data";
		String CHARSET = "UTF-8";

		URL uri = new URL(actionUrl);
		HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
		conn.setReadTimeout(5 * 1000); // 缓存的最长时间
		conn.setDoInput(true);// 允许输入
		conn.setDoOutput(true);// 允许输出
		conn.setUseCaches(false); // 不允许使用缓存
		conn.setRequestMethod("POST");
		conn.setRequestProperty("connection", "keep-alive");
		conn.setRequestProperty("Charsert", "UTF-8");
		conn.setRequestProperty("Content-Type", MULTIPART_FROM_DATA
				+ ";boundary=" + BOUNDARY);

		// 首先组拼文本类型的参数
		StringBuilder sb = new StringBuilder();
		for (Map.Entry<String, String> entry : params.entrySet()) {
			sb.append(PREFIX);
			sb.append(BOUNDARY);
			sb.append(LINEND);
			sb.append("Content-Disposition: form-data; name=\""
					+ entry.getKey() + "\"" + LINEND);
			sb.append("Content-Type: text/plain; charset=" + CHARSET + LINEND);
			sb.append("Content-Transfer-Encoding: 8bit" + LINEND);
			sb.append(LINEND);
			sb.append(entry.getValue());
			sb.append(LINEND);
		}

		DataOutputStream outStream = new DataOutputStream(
				conn.getOutputStream());
		outStream.write(sb.toString().getBytes());
		InputStream in = null;
		// 发送文件数据
		if (files != null) {
			for (Map.Entry<String, File> file : files.entrySet()) {

				StringBuilder sb1 = new StringBuilder();
				sb1.append(PREFIX);
				sb1.append(BOUNDARY);
				sb1.append(LINEND);
				sb1.append("Content-Disposition: form-data; name=\"source\"; filename=\""
						+ file.getValue().getName() + "\"" + LINEND);
				sb1.append("Content-Type: image/pjpeg; " + LINEND);
				sb1.append(LINEND);
				outStream.write(sb1.toString().getBytes());

				InputStream is = new FileInputStream(file.getValue());
				byte[] buffer = new byte[1024];
				int len = 0;
				while ((len = is.read(buffer)) != -1) {
					outStream.write(buffer, 0, len);
				}

				is.close();
				outStream.write(LINEND.getBytes());
			}

			// 请求结束标志
			byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINEND).getBytes();
			outStream.write(end_data);
			outStream.flush();
			// 得到响应码
			int res = conn.getResponseCode();
			// if (res == 200) {
			in = conn.getInputStream();
			int ch;
			StringBuilder sb2 = new StringBuilder();
			while ((ch = in.read()) != -1) {
				sb2.append((char) ch);
			}

			// }
			outStream.close();
			conn.disconnect();
		}
		// return in.toString();

	}

	/**
	 * is转String
	 * 
	 * @param in
	 * @return
	 * @throws IOException
	 */
	public static String inputStream2String(InputStream in) throws IOException {
		StringBuffer out = new StringBuffer();
		byte[] b = new byte[4096];
		for (int n; (n = in.read(b)) != -1;) {
			out.append(new String(b, 0, n));
		}
		return out.toString();
	}

	/***
	 * @category check if the string is null
	 * @return true if is null
	 * */
	public static boolean isNull(String string) {
		boolean t1 = "".equals(string);
		boolean t2 = string == null;
		boolean t3 = string.equals("null");
		if (t1 || t2 || t3) {
			return true;
		} else {
			return false;
		}
	}

	static public byte[] getBytes(File file) throws IOException {
		InputStream ios = null;
		ByteArrayOutputStream ous = null;
		try {
			byte[] buffer = new byte[4096];
			ous = new ByteArrayOutputStream();
			ios = new FileInputStream(file);
			int read = 0;
			while ((read = ios.read(buffer)) != -1) {
				ous.write(buffer, 0, read);
			}
		} finally {
			try {
				if (ous != null)
					ous.close();
			} catch (IOException e) {
			}

			try {
				if (ios != null)
					ios.close();
			} catch (IOException e) {
			}
		}

		return ous.toByteArray();
	}

}