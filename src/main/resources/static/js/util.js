
function myAlert(value){
	$("#my-alert .am-modal-bd").text(value);
	$('#my-alert').modal('open');
	setTimeout(function(){
		$('#my-alert').modal('close');
	},3000)
}

function isNull(value){
	if((undefined == value || "" == value) && 0 != value){
		return true;
	}
	return false;
}

function getData(value){
	if(isNull(value)){
		return '';
	}
	return value;
}

function $post(options){
	var url = options.url || ''
	var data = options.data
	var callback = options.callback || function() {}
	var errors = options.errors || function(){myAlert('服务器出错')}
	$.ajax({
		url: url,
		data: JSON.stringify(data),
		type: 'POST',
		contentType:'application/json;charset=utf-8',
		dataType : "json",
		success: callback,
		error: errors
	})
}