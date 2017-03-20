var com = {};
com.reunion = {};
com.reunion.Page = function() {

	var that = {};

	that.activeNavigation = 'none';

	that.init = function() {
		initDatePicker();
		initModals();
		togglePopOver();
		initTable();
		
	};

	var initTable = function() {
		$('table').DataTable();
	}
	var togglePopOver = function() {
		 $('[data-toggle="popover"]').popover();   
		 $('[data-toggle="tooltip"]').tooltip(); 
	}

	var initDatePicker = function() {
		$("div[id$='_dp']").each(function() {
			$(this).datetimepicker({
				format : 'MMM D, YYYY hh:mm:ss a',
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