package in.datashow.kuaidi.services;

import java.util.Map;

import in.datashow.kuaidi.CaptchaEntity;
import in.datashow.kuaidi.Order;

public interface QueryService {
	/**
	 * 获取验证码
	 * 
	 * @param orderNum
	 * @return
	 */
	public CaptchaEntity captcha(String orderNum);

	/**
	 *  查询获取原始的HTML
	* @param orderNum
	* @return
	 */
	public String queryHTML(String orderNum,Map<String, String> cookies);
	
	/**
	 *  查询概要的快递信息
	* @param orderNum
	* @return
	 */
	public Order query(String orderNum,String captchaCode,Map<String, String> cookies);
	
	/**
	 * 查询详细的快递信息
	 * @param orderNum
	 * @param captchaCode
	 * @return
	 */
	public Order queryDetail(String orderNum, String captchaCode, Map<String, String> cookies);
}
