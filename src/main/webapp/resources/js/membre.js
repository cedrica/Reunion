(function($) {
	com.reunion.Membre = function() {
		var that = new com.reunion.Page();

		that.activeNavigation = 'membre';

		return that;
	};

	new com.reunion.Membre().register();

})(jQuery);
