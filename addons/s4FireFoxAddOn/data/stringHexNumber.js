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
