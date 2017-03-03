var com = {};
com.reunion = {};
com.reunion.Page = function() {

	var that = {};

	that.activeNavigation = 'none';

	that.init = function() {
		initModals();
		initTable();
		initDatePicker();
		initSelectionDeMembre();
	};

	var initTable = function() {
		$('table').DataTable();
	}

	var initSelectionDeMembre = function() {
		$("input[id$='_membre']").each(function() {
			var idStr = $(this).attr("id");
			var arr = idStr.split(":");
			var id = arr[arr.length-1];
			arr = id.split("_");
			var idValue = arr[1];
			
			$("input[id$='ip_"+idValue+"_membre_co']").hide();
			$("input[id$='ip_"+idValue+"_membre_dp']").hide();
			$("input[id$='ip_"+idValue+"_membre_btn']").hide();
			
			$("input[id$='cb_"+idValue+"_membre']").change(function() {
			    if(this.checked) {
					$("input[id$='ip_"+idValue+"_membre_co']").show();
					$("input[id$='ip_"+idValue+"_membre_dp']").show();
					$("input[id$='ip_"+idValue+"_membre_btn']").show();
			    }else{
			    	$("input[id$='ip_"+idValue+"_membre_co']").hide();
					$("input[id$='ip_"+idValue+"_membre_dp']").hide();
					$("input[id$='ip_"+idValue+"_membre_btn']").hide();
			    }
			});
		});
		
	}

	var initDatePicker = function(){
		$("input[id$='_dp']").each(function() {
			$(this).datepicker({
				format : 'dd/mm/yyyy'
			});
		});
	}
	
	var initModals = function() {
		$("div[id$='Modal']").each(function() {
			if ($(this).attr('id').indexOf('js_') === 0) {
				$(this).modal({
					show : false,
					keyboard : false,
					backdrop : 'static'
				});
			}
		});

	};

	that.register = function() {
		$(document).ready(function() {
			that.init();
		});
	};

	return that;
};