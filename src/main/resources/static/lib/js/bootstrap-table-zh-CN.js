/**
 * bootstrap-table - An extended Bootstrap table with radio, checkbox, sort, pagination, and other added features. (supports twitter bootstrap v2 and v3).
 *
 * @version v1.13.1
 * @homepage http://bootstrap-table.wenzhixin.net.cn
 * @author wenzhixin <wenzhixin2010@gmail.com> (http://wenzhixin.net.cn/)
 * @license MIT
 */

(function(a,b){if('function'==typeof define&&define.amd)define([],b);else if('undefined'!=typeof exports)b();else{b(),a.bootstrapTableZhCN={exports:{}}.exports}})(this,function(){'use strict';(function(a){a.fn.bootstrapTable.locales['zh-CN']={formatLoadingMessage:function(){return'\u6B63\u5728\u52AA\u529B\u5730\u52A0\u8F7D\u6570\u636E\u4E2D\uFF0C\u8BF7\u7A0D\u5019\u2026\u2026'},formatRecordsPerPage:function(a){return'\u6BCF\u9875\u663E\u793A '+a+' \u6761\u8BB0\u5F55'},formatShowingRows:function(a,b,c){return'\u663E\u793A\u7B2C '+a+' \u5230\u7B2C '+b+' \u6761\u8BB0\u5F55\uFF0C\u603B\u5171 '+c+' \u6761\u8BB0\u5F55'},formatSearch:function(){return'\u641C\u7D22'},formatNoMatches:function(){return'\u6CA1\u6709\u627E\u5230\u5339\u914D\u7684\u8BB0\u5F55'},formatPaginationSwitch:function(){return'\u9690\u85CF/\u663E\u793A\u5206\u9875'},formatRefresh:function(){return'\u5237\u65B0'},formatToggle:function(){return'\u5207\u6362'},formatColumns:function(){return'\u5217'},formatExport:function(){return'\u5BFC\u51FA\u6570\u636E'},formatClearFilters:function(){return'\u6E05\u7A7A\u8FC7\u6EE4'}},a.extend(a.fn.bootstrapTable.defaults,a.fn.bootstrapTable.locales['zh-CN'])})(jQuery)});