
console.log('script');

YUI().use(
	'aui-base',
	function(A) {
		// code goes here
		console.log(A);

		var searchInput = A.one('.search-input');

		searchInput.on(
			'click',
			function() {
				console.log('search');
			}
		);

		searchInput.on(
			'keypress',
			function(event) {
				console.log(event.charCode);
			}
		);
	}
);