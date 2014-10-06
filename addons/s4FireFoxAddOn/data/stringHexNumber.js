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
(function (module) {
    var stringHexNumber = (function () {
        /**
         * Here is hardcoded 12 base colors.
         */
        var baseColors = [
            '#0000DC',
            '#DA0000',
            '#FFEB2C',
            '#00817D',
            '#AD0167',
            '#FFCA2C',
            '#238E24',
            '#4D136A',
            '#FC7300',
            '#AFD510',
            '#60398A',
            '#F32A00'
        ];

        return function () {
            var outputColors = angular.copy(baseColors);
            return function (inputString) {
                if (outputColors.length > 0) {
                    return outputColors.splice(0, 1)[0];
                }

                var stringHexNumberC = (
                    parseInt(
                        parseInt(inputString, 36)
                            .toExponential()
                            .slice(2, -5)
                        , 10) & 0xFFFFFF
                    ).toString(16).toUpperCase();
                return '#' + ('000000' + stringHexNumberC).slice(-6);
            }
        }
    });

    module.factory("stringHexNumber", [stringHexNumber]);
}(angular.module("s4App")));
