$(function () {
    'use strict';

    window.Validator = function (val,rule) {

        this.validate_max = function () {
            val = parseFloat(val);
            return val<=rule.max;
        };
    }
});