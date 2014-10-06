/*
 S4 Web Page Annotation Browser Plugin
 Copyright  2013, 2014, Ontotext AD

 This file is free software; you can redistribute it and/or modify it under
 the terms of the GNU Lesser General Public License as published by the Free
 Software Foundation; either version 2.1 of the License, or (at your option)
 any later version.
 This library is distributed in the hope that it will be useful, but WITHOUT
 ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 details.
 You should have received a copy of the GNU Lesser General Public License along
 with this library; if not, write to the Free Software Foundation, Inc.,
 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */

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
