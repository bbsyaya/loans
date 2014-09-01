/**
 * 禁用提交按钮
 * @param id 按钮ID
 * @param isDisabled 是否禁用
 * @param operateAttribute 操作的属性名称
 * @param availColor 有效颜色
 * @param processMethod 处理方法
 */
function disabledSubmitBtn(id, isDisabled, operateAttribute, availColor, processMethod) {
	if(isDisabled) {
		document.getElementById(id).setAttribute(operateAttribute, "void(0);");
		document.getElementById(id).style.color = "gray";
	} else {
		document.getElementById(id).setAttribute(operateAttribute, processMethod);
		document.getElementById(id).style.color = availColor;
	}
}