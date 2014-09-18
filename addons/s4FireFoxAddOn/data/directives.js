s4App.directive("visualizePipeLineResult", ['createHighlightedArea', function(createHighlightedArea) {
	return {
		require: '^ngModel',
		scope: {
		      ngModel: '='
		},
		template: '<div class="textContainer"></div>',
		link: function(scope, iElement, iAttrs, ctrl) {
			scope.$watch('ngModel', function(newVal) {
				if(newVal) {
                    scope.$parent.colorLegend  = createHighlightedArea(iElement, newVal);
				}
			});
		}
	}
}]);
