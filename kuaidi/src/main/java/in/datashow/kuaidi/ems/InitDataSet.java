package in.datashow.kuaidi.ems;

import in.datashow.kuaidi.Order;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**  
 * Description： 
 * Author:lucktyy@gmail.com
 * Date:2015-3-10下午03:03:41
 */
public class InitDataSet {

	public static Order createInfo(){
		String s = "{	'orderNum':'XA24435342142',	'records':[{		'address':'湖北宜昌市',		'datetime':1423900800000,		'status':'【宜昌市本口投递支局】已妥投 收发室收'	}]}";
		
		Order  userLogin =  JSONObject.parseObject(s, Order.class); 
		return userLogin;
	}
	
	public static  Order createDetail(){
		String s = "{	'orderNum':'XA24435342142',	'records':[		{			'address':'【湖北省远安东庄坪营业厅】已收寄',			'datetime':1423719399000,			'status':'1'		},		{			'address':'离开【远安东庄坪营业厅】，下一站【宜昌市】',			'datetime':1423798163000,			'status':'2'		},		{			'address':'到达【宜昌市】',			'datetime':1423815600000,			'status':'3'		},		{			'address':'离开【宜昌】，下一站【福绥路投】',			'datetime':1423845349000,			'status':'4'		},		{			'address':'到达【宜昌市本口投递支局】',			'datetime':1423874396000,			'status':'5'		},		{			'address':'【宜昌市本口投递支局】正在投递',			'datetime':1423874434000,			'status':'6'		},		{			'address':'【宜昌市本口投递支局】已妥投 收发室收',			'datetime':1423900800000,			'status':'7'		}	]}";
		Order  userLogin =  JSONObject.parseObject(s, Order.class); 
		return userLogin;
	}
	
}
