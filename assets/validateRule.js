/*检查字符串是否为空*/
function isEmpty(s){
    if (s == null || s.length <= 0){
            return true;
    }
    return false;
}
//提示信息
function tip(info){
	$(".msg").show();
	$(".msg").html(info);
	$("html,body").animate({scrollTop: $(".msg").offset().top}, 1000);
}

function loginPwd_rule(objValue) {
	 var reg=/^([0-9]+[a-zA-Z]+[0-9]*)|([a-zA-Z]+[0-9]+[a-zA-Z]*)+$/;//验证必须是字母加数字组合
	 if (objValue.match(reg) == null){
		 return true;
	 }else if(objValue.length<6){
		 return true;
	 }else if(objValue.length>16){
		 return true;
	 }
	return false;
}

//交易金额
function tradeAmount_rule(money) {

	var reg = /^\d+(\.\d{1,2})?$/;
	var reg2 = /^\d+(\.\d{1,10})?$/;
	//小于一千亿元
	if (reg.test(money) && money > 0 && money < 100000000000) {
		return true;
	}
	return false;
}

/**帐号等长数字字符4位一空格分隔**/
function formatCount(str){
	   var ret='';
	   str=str.replace(/ /g,"");
	   for (var i=0;i<str.length ;i++){
	    ret=ret+str.substr(i,1);
	    if(i!=str.length){
	    if (ret.length%5==4){
	    	ret=ret + " ";
	    	}   
	   } 
	   }  
	   return ret;
	}

//隐藏DIV
function closeDiv(element){
	$("#"+element).hide();
}

 //将帐号格式化显示 四位分开 value要格式化的值  inputName 要格式化的文本名字  inputDiv要显示的层，默认不显示
function showCount(value,inputName,inputDiv){
	if(value!=""){
	var _obj = $("#"+inputName);
	var pgY = _obj.offset().top;
	var pgX = _obj.offset().left;
	var pgHeight = _obj.height();
	var _top=pgY-pgHeight;
	 if(inputName!="idNo"){
		 _top=_top-11; //新用户开户时，页面调整
	 }
	$("#"+inputDiv).show();
	$("#"+inputDiv).css({"top":_top,"left":(pgX),"position":"absolute","border":"1px solid #ccc","background":"#fff","padding":"5px","font-weight":"bold","font-size":"14px"});
	$("#"+inputDiv).html(formatCount(value));
	}
}

function tradePwd_rule(objValue) {
	 var reg=/^[0-9]+$/;
		if(!objValue>""){
		   return false;
		}else if(objValue.length!=6){
			return false;
		}else if (objValue.match(reg)== null){
		   return false;
		}else if(validateContinuousNum(objValue)== false || validateSameNum(objValue)==false){
			return false;
		}
	return true;
}

function validateSameNum(str) {
	for(var j=0;j<str.length;j++){
		if(str.charAt(0)!=str.charAt(j)) break;
	}
	if(j == str.length) return false;

}
//验证连续的数字
function validateContinuousNum(str){
	for(var k=1;k<str.length;k++){
		if (str.charCodeAt(k -1) + 1 != str.charCodeAt(k)) {
			break;
		}
	}
	if(k == str.length) {return false;};
	for(var i=1;i<str.length;i++){
		if (str.charCodeAt(i -1) -1 != str.charCodeAt(i)) {
			break;
		}
	}
	if(i == str.length) {return false;};
}

function bankAcct_rule(objValue) {

	var pat = /^[0-9]\d{4,20}$/;
	if (objValue.match(pat) == null) {
		return false;
	}
	return true;
}

function mobile_rule(objValue){

	var patn = /^1[0-9]{10}$/;
	if(patn.test(objValue)) return true;
	return false;


}

function email_rule(objValue) {
	var pat = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
	if (objValue.match(pat) == null) {
		return false;
	}
	return true;
}
/**
 * 客户真实姓名
 * @param obj
 * @returns {Number}
 */
function custName_rule(obj){
	var str = obj;
	if (str.length<2 || str.length>10)
		return false;
	var patrn=/[\u4E00-\u9FA5\uF900-\uFA2D]+$/;
 	if (patrn.test(str))
 		return true;
 	else
 		return false;
}
/**
 * 验证用户输入的邮政编码是否合法
 * @param objValue 用户输入的邮政编码
 * @param errMessage 页面错误信息提示元素
 * @returns {Boolean} 如果合法则返回true,否则返回false.
 */
function zipCode_rule(objValue) {
	var pat = /^[0-9]{6}$/;

	if (pat.test(objValue)) {
		return true;
	}
	return false;
}
function limitLen(str,min, max) {
	var len = ("" + str).replace(/[^\x0000-\xFF00]/gi, "xxx").length;
	if ((min >0 && min > len) || (max > 0 && max <len)) {
		return false;
	}
	return true;
}

/**
 * 身份证号码验证规则
 * @param idcard 用于输入的身份证号码
 * @returns
 *      err-0:验证通过
 *      err-1:身份证号码位数不对
 *      err-2:身份证号码出生日期超出范围或者含有非法字符
 *      err-3:身份证号码校验错误
 *      err-4:身份证地区非法
 */
function id_rule(idcard) {
	var Errors = new Array(true, false, false, false, false);
	var area = {
		11 :"北京",
		12 :"天津",
		13 :"河北",
		14 :"山西",
		15 :"内蒙古",
		21 :"辽宁",
		22 :"吉林",
		23 :"黑龙江",
		31 :"上海",
		32 :"江苏",
		33 :"浙江",
		34 :"安徽",
		35 :"福建",
		36 :"江西",
		37 :"山东",
		41 :"河南",
		42 :"湖北",
		43 :"湖南",
		44 :"广东",
		45 :"广西",
		46 :"海南",
		50 :"重庆",
		51 :"四川",
		52 :"贵州",
		53 :"云南",
		54 :"西藏",
		61 :"陕西",
		62 :"甘肃",
		63 :"青海",
		64 :"宁夏",
		65 :"新疆",
		71 :"台湾",
		81 :"香港",
		82 :"澳门",
		91 :"国外"
	}
	var idcard, Y, JYM;
	var S, M;
	var idcard_array = new Array();
	idcard_array = idcard.split("");
	//地区检验
	if (area[parseInt(idcard.substr(0, 2))] == null)
		return Errors[4];
	//身份号码位数及格式检验
	switch (idcard.length) {
	//case 15:
	//	if ((parseInt(idcard.substr(6, 2)) + 1900) % 4 == 0
	//			|| ((parseInt(idcard.substr(6, 2)) + 1900) % 100 == 0 && (parseInt(idcard
	//					.substr(6, 2)) + 1900) % 4 == 0)) {
	////		ereg = /^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}$/;//测试出生日期的合法性
	//	} else {
	//		ereg = /^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}$/;//测试出生日期的合法性
	//	}
	//	if (ereg.test(idcard))
		//	return Errors[0];
	//	else
		//	return Errors[2];
	//	break;
	case 18:
		//18位身份号码检测
		//出生日期的合法性检查
		//闰年月日:((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))
		//平年月日:((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))
		if (parseInt(idcard.substr(6, 4)) % 4 == 0
				|| (parseInt(idcard.substr(6, 4)) % 100 == 0 && parseInt(idcard
						.substr(6, 4)) % 4 == 0)) {
			ereg = /^[1-9][0-9]{5}(19|20)[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}[0-9Xx]$/;//闰年出生日期的合法性正则表达式
		} else {
			ereg = /^[1-9][0-9]{5}(19|20)[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}[0-9Xx]$/;//平年出生日期的合法性正则表达式
		}
		if (ereg.test(idcard)) {//测试出生日期的合法性
			//计算校验位
			S = (parseInt(idcard_array[0]) + parseInt(idcard_array[10]))
					* 7
					+ (parseInt(idcard_array[1]) + parseInt(idcard_array[11]))
					* 9
					+ (parseInt(idcard_array[2]) + parseInt(idcard_array[12]))
					* 10
					+ (parseInt(idcard_array[3]) + parseInt(idcard_array[13]))
					* 5
					+ (parseInt(idcard_array[4]) + parseInt(idcard_array[14]))
					* 8
					+ (parseInt(idcard_array[5]) + parseInt(idcard_array[15]))
					* 4
					+ (parseInt(idcard_array[6]) + parseInt(idcard_array[16]))
					* 2 + parseInt(idcard_array[7]) * 1
					+ parseInt(idcard_array[8]) * 6
					+ parseInt(idcard_array[9]) * 3;
			Y = S % 11;
			M = "F";
			JYM = "10X98765432";
			M = JYM.substr(Y, 1);//判断校验位
			if (M == idcard_array[17]){
				return true; //检测ID的校验位
			}
			else {
				return false;
			}
		} else {
			return false;
		}
		break;
	default:
		return false;
		break;
	}
}


function numToCny(num){
	    var capUnit = ['万','亿','万','份',''];
	    var capDigit = { 2:['','',''], 4:['仟','佰','拾','']};
        var capNum=['零','壹','贰','叁','肆','伍','陆','柒','捌','玖'];
	    if (((num.toString()).indexOf('.') > 16)||(isNaN(num)))
	         return '';
	      num = (Math.round(num*100)/100).toString();
	      num =((Math.pow(10,19-num.length)).toString()).substring(1)+num;
	      var i,ret,j,nodeNum,k,subret,len,subChr,CurChr=[];
	      for (i=0,ret='';i<5;i++,j=i*4+Math.floor(i/4)){
	         nodeNum=num.substring(j,j+4);
	         for(k=0,subret='',len=nodeNum.length;((k<len) && (parseInt(nodeNum.substring(k))!=0));k++){
	             CurChr[k%2] = capNum[nodeNum.charAt(k)]+((nodeNum.charAt(k)==0)?'':capDigit[len][k]);
	             if (!((CurChr[0]==CurChr[1]) && (CurChr[0]==capNum[0])))
	                 if(!((CurChr[k%2] == capNum[0]) && (subret=='') && (ret=='')))
	                     subret += CurChr[k%2];
	         }
	        subChr = subret + ((subret=='')?'':capUnit[i]);
	        if(!((subChr == capNum[0]) && (ret=='')))
	            ret += subChr;
	    }
	     ret=(ret=='')? capNum[0]+capUnit[3]: ret;
	     return ret;
}


function Arabia_to_Chinese(Num) {
	for (i = Num.length - 1; i >= 0; i--) {
		Num = Num.replace(",", "")//替换tomoney()中的“,”
	    Num = Num.replace(" ", "")//替换tomoney()中的空格
	}
	Num = Num.replace("￥", "")//替换掉可能出现的￥字符
	if (isNaN(Num)) { //验证输入的字符是否为数字
	    alert("请检查小写金额是否正确");
	    return;
	}
	//---字符处理完毕，开始转换，转换采用前后两部分分别转换---//
	part = String(Num).split(".");
	newchar = "";
	//小数点前进行转化
	for (i = part[0].length - 1; i >= 0; i--) {
	    if (part[0].length > 10) {
			alert("位数过大，无法计算");
			return "";
	    }//若数量超过拾亿单位，提示
	    tmpnewchar = ""
	    perchar = part[0].charAt(i);
	    switch (perchar) {
			case "0":
				tmpnewchar = "零" + tmpnewchar;
				break;
			case "1":
				tmpnewchar = "壹" + tmpnewchar;
				break;
			case "2":
				tmpnewchar = "贰" + tmpnewchar;
				break;
			case "3":
				tmpnewchar = "叁" + tmpnewchar;
				break;
			case "4":
				tmpnewchar = "肆" + tmpnewchar;
				break;
			case "5":
				tmpnewchar = "伍" + tmpnewchar;
				break;
			case "6":
				tmpnewchar = "陆" + tmpnewchar;
				break;
			case "7":
				tmpnewchar = "柒" + tmpnewchar;
				break;
			case "8":
				tmpnewchar = "捌" + tmpnewchar;
				break;
			case "9":
				tmpnewchar = "玖" + tmpnewchar;
				break;
		}
		switch (part[0].length - i - 1) {
			case 1:
				if (perchar != 0)
					tmpnewchar = tmpnewchar + "拾";
				break;
			case 2:
				if (perchar != 0)
					tmpnewchar = tmpnewchar + "佰";
				break;
			case 3:
				if (perchar != 0)
					tmpnewchar = tmpnewchar + "仟";
				break;
			case 4:
				tmpnewchar = tmpnewchar + "万";
				break;
			case 5:
				if (perchar != 0)
					tmpnewchar = tmpnewchar + "拾";
				break;
			case 6:
				if (perchar != 0)
					tmpnewchar = tmpnewchar + "佰";
				break;
			case 7:
				if (perchar != 0)
					tmpnewchar = tmpnewchar + "仟";
				break;
			case 8:
				tmpnewchar = tmpnewchar + "亿";
				break;
			case 9:
				tmpnewchar = tmpnewchar + "拾";
				break;
	    }
	    newchar = tmpnewchar + newchar;
	}

	// added for 卤蛋
	// 去掉2个连续的零，如果最后为零也去掉
	while (newchar.indexOf("零零") != -1)
	{
		newchar = newchar.replace("零零", "零");
	}
	if (newchar.length > 0 && newchar.charAt(newchar.length-1) == '零')
	{
		newchar = newchar.substring(0, newchar.length-1);
	}

	//小数点之后进行转化
	if (Num.indexOf(".") != -1) {
	    if (part[1].length > 2) {
			alert("小数点之后只能保留两位,系统将自动截段");
			part[1] = part[1].substr(0, 2)
	    }
		for (i = 0; i < part[1].length; i++) {
			tmpnewchar = ""
			perchar = part[1].charAt(i)
			switch (perchar) {
				case "0":
					tmpnewchar = "零" + tmpnewchar;
					break;
				case "1":
					tmpnewchar = "壹" + tmpnewchar;
					break;
				case "2":
					tmpnewchar = "贰" + tmpnewchar;
					break;
				case "3":
					tmpnewchar = "叁" + tmpnewchar;
					break;
				case "4":
					tmpnewchar = "肆" + tmpnewchar;
					break;
				case "5":
					tmpnewchar = "伍" + tmpnewchar;
					break;
				case "6":
					tmpnewchar = "陆" + tmpnewchar;
					break;
				case "7":
					tmpnewchar = "柒" + tmpnewchar;
					break;
				case "8":
					tmpnewchar = "捌" + tmpnewchar;
					break;
				case "9":
					tmpnewchar = "玖" + tmpnewchar;
					break;
			}
			if (i == 0)
				tmpnewchar = tmpnewchar + "角";
			if (i == 1)
				tmpnewchar = tmpnewchar + "分";
			newchar = newchar + tmpnewchar;
		}
	}
	return newchar;
}

/**
 * 手机号码部分隐藏
 */
function encMobile(mobile){
	return mobile.replace(/(\d{3})(\d{4})(\d{4})/,"$1****$3");
}

/**
 * 银行卡部分隐藏
 */
function encBankAccount(bankAccount){
	return bankAccount.replace(/(\d{4})(\d{8})(\d{4})/,"$1**********$3");
}

//验证日期
function checkDate(value){ 
	var a=/^(\d{1,4})(-|\/)(\d{2})\2(\d{2})/ 
	if (a.test(value)){ 
	return true 
	} 
	else 
	return false 
	}

//防止重复提交 禁用提交按钮
function checkValid(id)
{
	var obj=$("#"+id);
	obj.attr("disabled", true); 
	obj.removeClass("continueBtn");
	obj.addClass("cancelBtn");
}

//防止重复提交 禁用提交按钮 
function validSubmit(id)
{
	var obj=$("#"+id);
	obj.attr("disabled", true); 
}

//去除空格
function trimSpace(str){
	return str.replace(/\s+/g,""); 
}
//日期比较 如果date大于currDate返回true
function compCurrDate(date,currDate){
	var d1Arr=date.split('-');
	var d2Arr=currDate.split('-');
	d1=new Date(d1Arr[0],d1Arr[1],d1Arr[2]);
	d2=new Date(d2Arr[0],d2Arr[1],d2Arr[2]);
	return d1>d2;
}

//得到当前日期，以yyyy-MM-dd格式输出
function getDate(){
	var today = new Date();      
    var day   = today.getDate();                 
    var month = today.getMonth() + 1;           //显示月份比实际月份小1,所以要加1  
    var year  = today.getFullYear();  
    month     = month<10?"0"+month:month;       //数字<10，实际显示为，如5，要改成05  
    day       = day<10?"0"+day:day;             //和月份显示一样  
    return year + "-" + month + "-" + day; 		//结果:2008-05-08,2008-12-29  
}