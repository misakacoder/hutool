package cn.hutool.crypto.asymmetric;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.text.StrUtil;
import cn.hutool.crypto.SignUtil;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 签名单元测试
 *
 * @author looly
 */
public class SignTest {

	@Test
	public void signAndVerifyUseKeyTest() {
		final String content = "我是Hanley.";

		final String privateKey = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAJ4fG8vJ0tzu7tjXMSJhyNjlE5B7GkTKMKEQlR6LY3IhIhMFVjuA6W+DqH1VMxl9h3GIM4yCKG2VRZEYEPazgVxa5/ifO8W0pfmrzWCPrddUq4t0Slz5u2lLKymLpPjCzboHoDb8VlF+1HOxjKQckAXq9q7U7dV5VxOzJDuZXlz3AgMBAAECgYABo2LfVqT3owYYewpIR+kTzjPIsG3SPqIIWSqiWWFbYlp/BfQhw7EndZ6+Ra602ecYVwfpscOHdx90ZGJwm+WAMkKT4HiWYwyb0ZqQzRBGYDHFjPpfCBxrzSIJ3QL+B8c8YHq4HaLKRKmq7VUF1gtyWaek87rETWAmQoGjt8DyAQJBAOG4OxsT901zjfxrgKwCv6fV8wGXrNfDSViP1t9r3u6tRPsE6Gli0dfMyzxwENDTI75sOEAfyu6xBlemQGmNsfcCQQCzVWQkl9YUoVDWEitvI5MpkvVKYsFLRXKvLfyxLcY3LxpLKBcEeJ/n5wLxjH0GorhJMmM2Rw3hkjUTJCoqqe0BAkATt8FKC0N2O5ryqv1xiUfuxGzW/cX2jzOwDdiqacTuuqok93fKBPzpyhUS8YM2iss7jj6Xs29JzKMOMxK7ZcpfAkAf21lwzrAu9gEgJhYlJhKsXfjJAAYKUwnuaKLs7o65mtp242ZDWxI85eK1+hjzptBJ4HOTXsfufESFY/VBovIBAkAltO886qQRoNSc0OsVlCi4X1DGo6x2RqQ9EsWPrxWEZGYuyEdODrc54b8L+zaUJLfMJdsCIHEUbM7WXxvFVXNv";
		Sign sign = SignUtil.sign(SignAlgorithm.SHA1withRSA, privateKey, null);
		Assert.assertNull(sign.getPublicKeyBase64());
		// 签名
		final byte[] signed = sign.sign(content.getBytes());

		final String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCeHxvLydLc7u7Y1zEiYcjY5ROQexpEyjChEJUei2NyISITBVY7gOlvg6h9VTMZfYdxiDOMgihtlUWRGBD2s4FcWuf4nzvFtKX5q81gj63XVKuLdEpc+btpSyspi6T4ws26B6A2/FZRftRzsYykHJAF6vau1O3VeVcTsyQ7mV5c9wIDAQAB";
		sign = SignUtil.sign(SignAlgorithm.SHA1withRSA, null, publicKey);
		// 验证签名
		final boolean verify = sign.verify(content.getBytes(), signed);
		Assert.assertTrue(verify);
	}

	@Test
	public void signAndVerifyTest() {
		signAndVerify(SignAlgorithm.NONEwithRSA);
		signAndVerify(SignAlgorithm.MD2withRSA);
		signAndVerify(SignAlgorithm.MD5withRSA);

		signAndVerify(SignAlgorithm.SHA1withRSA);
		signAndVerify(SignAlgorithm.SHA256withRSA);
		signAndVerify(SignAlgorithm.SHA384withRSA);
		signAndVerify(SignAlgorithm.SHA512withRSA);

		signAndVerify(SignAlgorithm.NONEwithDSA);
		signAndVerify(SignAlgorithm.SHA1withDSA);

		signAndVerify(SignAlgorithm.NONEwithECDSA);
		signAndVerify(SignAlgorithm.SHA1withECDSA);
		signAndVerify(SignAlgorithm.SHA1withECDSA);
		signAndVerify(SignAlgorithm.SHA256withECDSA);
		signAndVerify(SignAlgorithm.SHA384withECDSA);
		signAndVerify(SignAlgorithm.SHA512withECDSA);
	}

	/**
	 * 测试各种算法的签名和验证签名
	 *
	 * @param signAlgorithm 算法
	 */
	private void signAndVerify(final SignAlgorithm signAlgorithm) {
		final byte[] data = StrUtil.utf8Bytes("我是一段测试ab");
		final Sign sign = SignUtil.sign(signAlgorithm);

		// 签名
		final byte[] signed = sign.sign(data);

		// 验证签名
		final boolean verify = sign.verify(data, signed);
		Assert.assertTrue(verify);
	}

	/**
	 * 测试MD5withRSA算法的签名和验证签名
	 */
	@Test
	public void signAndVerifyTest2() {
		final String str = "wx2421b1c4370ec43b 支付测试 JSAPI支付测试 10000100 1add1a30ac87aa2db72f57a2375d8fec http://wxpay.wxutil.com/pub_v2/pay/notify.v2.php oUpF8uMuAJO_M2pxb1Q9zNjWeS6o 1415659990 14.23.150.211 1 JSAPI 0CB01533B8C1EF103065174F50BCA001";
		final byte[] data = StrUtil.utf8Bytes(str);
		final Sign sign = SignUtil.sign(SignAlgorithm.MD5withRSA);

		// 签名
		final byte[] signed = sign.sign(data);

		// 验证签名
		final boolean verify = sign.verify(data, signed);
		Assert.assertTrue(verify);
	}

	@Test
	public void signParamsTest() {
		final Map<String, String> build = MapUtil.builder(new HashMap<String, String>())
				.put("key1", "value1")
				.put("key2", "value2").build();

		final String sign1 = SignUtil.signParamsSha1(build);
		Assert.assertEquals("9ed30bfe2efbc7038a824b6c55c24a11bfc0dce5", sign1);
		final String sign2 = SignUtil.signParamsSha1(build, "12345678");
		Assert.assertEquals("944b68d94c952ec178c4caf16b9416b6661f7720", sign2);
		final String sign3 = SignUtil.signParamsSha1(build, "12345678", "abc");
		Assert.assertEquals("edee1b477af1b96ebd20fdf08d818f352928d25d", sign3);
	}

	/**
	 * 测试MD5withRSA算法的签名和验证签名
	 */
	@Test
	public void signAndVerifyPSSTest() {
		final String str = "wx2421b1c4370ec43b 支付测试 JSAPI支付测试 10000100 1add1a30ac87aa2db72f57a2375d8fec http://wxpay.wxutil.com/pub_v2/pay/notify.v2.php oUpF8uMuAJO_M2pxb1Q9zNjWeS6o 1415659990 14.23.150.211 1 JSAPI 0CB01533B8C1EF103065174F50BCA001";
		final byte[] data = StrUtil.utf8Bytes(str);
		final Sign sign = SignUtil.sign(SignAlgorithm.SHA256withRSA_PSS);

		// 签名
		final byte[] signed = sign.sign(data);

		// 验证签名
		final boolean verify = sign.verify(data, signed);
		Assert.assertTrue(verify);
	}
}