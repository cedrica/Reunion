function poll() {
	var url = "/ajaxpollng";
	$.ajax({
		url : url,
		dataType : 'text',
		async : true,
		beforeSend : function(request) {
			request.setRequestHeader("IS_AJAX_POLL", "Y");
		},
		type : 'POST',
		success : function(data, testStatus, request) {
			var isSessionTimeout = request
					.getResponseHeader("IS_SESSION_TIMEOUT");
			if (isSessionTimeout != null && isSessionTimeout == 'Y') {
				logout();
			}
		}
	});
}

function logout() {
	window.location.href = "/index.xhtml"; // Logout action or time out page.
}

$( document ).ready(function() {
	setInterval("poll()",1);
	alert("dfd");
});