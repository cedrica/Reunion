function uploadFile() {
	var preview = document.querySelector('#preview');
	var file = document.querySelector('input[type=file]').files[0];
	var reader = new FileReader();
	var imageContent = "";
	reader.addEventListener("load", function() {
		preview.src = reader.result;
		imageContent = reader.result;
	}, false);

	if (file) {
		reader.readAsDataURL(file);
		imageContent = reader.result;
		var json = [];
		json[0] = {name : 'data',	value : imageContent};
		passToJSFManagedBean(json);
	}
}
(function($) {
	com.reunion.Membre = function() {
		var that = new com.reunion.Page();

		that.activeNavigation = 'membre';

		return that;
	};

	new com.reunion.Membre().register();

})(jQuery);
