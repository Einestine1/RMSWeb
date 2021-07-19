/**
 * 
 */
var objType ="";
var data_url;
var window_title;
var this_parent;
 function InitGrid(data,title,type,parent) {
	 
	 
     function getType(strColumnName)
     {
    	 var CM = $grid.pqGrid("getColModel");
    	 for(i=0;i<CM.length;i++)
    		 {
    		 	if(CM[i].objType==strColumnName)
    		 		{
    		 			return CM[i].dataType;
    		 		}
    		 }
    	 return undefined;
     }
     function getColumnDef(strColumnName)
     {
    	 var CM = $grid.pqGrid("getColModel");
    	 for(i=0;i<CM.length;i++)
    		 {
    		 	if(CM[i].objType==strColumnName)
    		 		{
    		 			return CM[i];
    		 		}
    		 }
    	 return undefined;
     }
	 //Filter handler function 
	 
     function filterhandler(evt, ui) {
//alert("I am called!");
         var $toolbar = $grid.find('.pq-toolbar-search'),
             $value = $toolbar.find(".filterValue"),
             value = $value.val(),
             condition = $toolbar.find(".filterCondition").val(),
             dataIndx = $toolbar.find(".filterColumn").val(),
             filterObject;
         		
         if (dataIndx == "") {//search through all fields when no field selected.
             filterObject = [];
             var CM = $grid.pqGrid("getColModel");
             for (var i = 0, len = CM.length; i < len; i++) {
                 var dataIndx = CM[i].dataIndx;
                 var objType = CM[i].objType;
                 var dataType = CM[i].dataType;
                 filterObject.push({objType:{ dataIndx: objType, condition: condition, value: value,dataType:dataType,colDef:CM[i] }});
             }
         }
         else {//search through selected field.
             filterObject = { dataIndx: dataIndx, condition: condition, value: value,dataType:getType(dataIndx),colDef:getColumnDef(dataIndx)};
         }
         
         
//         $.ajax({
//        	   url: data_url,
//        	   type: 'get',
//        	   data: {"pq_filter" : JSON.stringify(filterObject),objType:objType},
//        	   success: function(data) {
//        	        // Do something with data that came back. 
//        		   console.log(JSON.stringify(data));
//        	   }
//        	});

//         $grid.pqGrid("filter", {
//             oper: 'replace',
//             data: filterObject
//         });
     
     
         dataObj= {"pq_filter" : JSON.stringify(filterObject),objType:objType};
         $grid.pqGrid( "option" , "dataModel.url", data_url+"&"+$.param(dataObj));
         $grid.pqGrid("refreshDataAndView");
     
     
     }

	 
	 //End Filter handler function 
	 
	 
     
     
     //filterRender to highlight matching cell text.
     function filterRender(ui) {
         var val = ui.cellData,
             filter = ui.column.filter;
         if (filter && filter.on && filter.value) {
             var condition = filter.condition,
                 valUpper = val.toUpperCase(),
                 txt = filter.value,
                 txt = (txt == null) ? "" : txt.toString(),
                 txtUpper = txt.toUpperCase(),
                 indx = -1;
             if (condition == "end") {
                 indx = valUpper.lastIndexOf(txtUpper);
                 //if not at the end
                 if (indx + txtUpper.length != valUpper.length) {
                     indx = -1;
                 }
             }
             else if (condition == "contain") {
                 indx = valUpper.indexOf(txtUpper);
             }
             else if (condition == "begin") {
                 indx = valUpper.indexOf(txtUpper);
                 //if not at the beginning.
                 if (indx > 0) {
                     indx = -1;
                 }
             }
             if (indx >= 0) {
                 var txt1 = val.substring(0, indx);
                 var txt2 = val.substring(indx, indx + txt.length);
                 var txt3 = val.substring(indx + txt.length);
                 return txt1 + "<span style='background:yellow;color:#333;'>" + txt2 + "</span>" + txt3;
             }
             else {
                 return val;
             }
         }
         else {
             return val;
         }
     }//End filterRender to highlight matching cell text.
     
     
     
     
     
     
     
	 
	 
	 
//	 alert(type);
	 objType =type;//.substring(0, type.length - 1); //remove the extra s as all the calls have an extra 's'
	 
	 var options = [10,25,50,100];//Values for records per page options list
	 var obj = { 
         width: parent.innerWidth-30,//700, 
         height: parent.innerHeight-40,//400, 
         filterModel: { mode: 'AND' },
         numberCell:{resizable:true,title:"#",width:30,minWidth:30,show: false},
// 	editor: {type: 'textbox'},
         editable: false,
         title: title,
         resizable:true,
         scrollModel:{autoFit:true, theme:true},
         draggable:false,
         collapsible: false,
//         pageModel: { type: "remote", rPPOptions: [5,10,20] }
         pageModel: { type: "remote", rPP: options[0], strRpp: "{0}",rPPOptions: options }
     };
      
     
     
     obj.colModel = colModels[type.toLowerCase()];//pick the type based on input domain object

     
     var dataModel = {
             location: "remote",            
             dataType: "JSON",
             method: "GET",
             url : data_url,
             //url: "/pro/invoice/paging",
             //url: "/pro/invoice.php",//for PHP
             getData: function (dataJSON) {                
                 return { curPage: dataJSON.curPage, totalRecords: dataJSON.totalRecords, data: dataJSON.data };                
             }
         }
//     obj.dataModel = { data: data };
     obj.dataModel = { //data: data, 
    		 url : data_url,
    		 location: "remote",            
    		 dataType: "JSON",
	         method: "GET",
	         getData: function (dataJSON) {                
	                return { curPage: dataJSON.pageNumber ,totalRecords: dataJSON.NumberOfRecords, data: dataJSON.data };                
	            }};
     
     
     //define the toolbar
     obj.toolbar = {
    	 cls: "pq-toolbar-search",
         items: [
             { type: "<span style='margin:5px;'>Filter</span>" },
             { type: 'textbox', attr: 'placeholder="Enter your keyword"', cls: "filterValue", listeners: [{ 'keyup': filterhandler}],style:'margin:5px;margin-left: 30px' },
             { type: "<span style='margin:5px;'> @ </span>" },
             { type: 'select', cls: "filterColumn",
                 listeners: [{ 'change': filterhandler}],
                 style: "width: 150px",
                 options: function (ui) {
                     var CM = ui.colModel;
//                     console.log(JSON.stringify(CM));
                     var opts = [];//[{ '': '[ All Fields ]'}];
                     for (var i = 0; i < CM.length; i++) {
                         var column = CM[i];
                         var obj = {};
                         if(!column.hidden)
                         {
                        	 obj[column.objType] = column.title;
                        	 opts.push(obj);
                         }
                     }
                     return opts;
                 }
             },
             { type: 'select', style: "margin:0px 5px;", cls: "filterCondition",
            	 style: "display:none;",
                 listeners: [{ 'change': filterhandler}],
                 options: [
//                 { "begin": "Begins With" },
                 { "contain": "Contains" }//,
//                 { "end": "Ends With" },
//                 { "notcontain": "Does not contain" },
//                 { "equal": "Equal To" },
//                 { "notequal": "Not Equal To" },
//                 { "empty": "Empty" },
//                 { "notempty": "Not Empty" },
//                 { "less": "Less Than" },
//                 { "great": "Great Than" },
//                 { "regexp": "Regex" }
                 ]
             },
             { type: "<br>" },
             { type: "<span style='margin:5px;' ng-show='false'>Records </span>" },
             { type: 'button', label: 'Add', listeners: [{ click: addRow}], icon: 'ui-icon-plus',style:'margin:5px; width:100px; height :25px' },
             //{ type: 'button', label: 'Edit', listeners: [{ click: editRow}], icon: 'ui-icon-pencil' },
             { type: 'button', label: 'Delete', listeners: [{ click: deleteRow}], icon: 'ui-icon-minus',style:'margin:5px; width:100px; height:25px' },
             { type: 'button', label: 'Map', listeners: [{ click: showMap}],style:'margin:5px; width:100px; height :25px'}
  ]
     };
//     $("#grid_json").pqGrid(obj);
     var $grid = $("#jsGrid").pqGrid(obj);
   //  $("#jsGrid").pqGrid(obj);
     //The function to launch edit 
     $("#jsGrid").on( "pqgridcelldblclick", function( event, ui ) {
    	 										var rowIndex = getRowIndx();
    	 										var row = $grid.pqGrid('getRowData', {rowIndx: rowIndex});
    	 										var strValues = JSON.stringify(row);
//    	 										var street_name = $('[ng-controller="add"]').scope().streetName;
//    	 										for(i=0;i<Object.keys(row).length;i++)
//    	 											{
//    	 												
//    	 											}
    	 										var child = openPopup('../jsp/addupdateobject.jsp?ObjType='+objType+'&request_type=Update&IdValue='+row[Object.keys(row)[0]],true,500,350);    	 										
    	 										var checkChild = function () {
    	 										    if (child.closed) {
    	 										    	 $("#jsGrid").pqGrid("refreshDataAndView");
    	 										        clearInterval(timer);
    	 										    }
    	 										}
    	 										var timer = setInterval(checkChild, 500);

    	 										
     });
     
   //append Row
     function addRow() {

//         var $frm = $("form#crud-form");
//         $frm.find("input").val("");
//
//         $("#popup-dialog-crud").dialog({ title: "Add Record", buttons: {
//             Add: function () {
//                 var row = [];
//                 //save the record in DM.data.
//                 row[0] = $frm.find("input[name='company']").val();
//                 row[1] = $frm.find("input[name='symbol']").val();
//                 row[3] = $frm.find("input[name='price']").val();
//                 row[4] = $frm.find("input[name='change']").val();
//                 row[5] = $frm.find("input[name='pchange']").val();
//                 row[6] = $frm.find("input[name='volume']").val();
//
//                 $grid.pqGrid('addRow', { rowData: row });
//                 $(this).dialog("close");
//             },
//             Cancel: function () {
//                 $(this).dialog("close");
//             }
//         } ,
////             autoOpen: false,
//             resizable: false,
//             modal: true,
//             width:'auto'
//         });
//         $("#popup-dialog-crud").dialog("open");
    	 var child =openPopup('../jsp/addupdateobject.jsp?ObjType='+objType+'&request_type=Add',true,500,350);
    	 var checkChild = function () {
			    if (child.closed) {
			    	 $("#jsGrid").pqGrid("refreshDataAndView");
			        clearInterval(timer);
			    }
			}
			var timer = setInterval(checkChild, 500);
     }
     //delete Row.
     function deleteRow() {
         var rowIndx = getRowIndx();
         if (rowIndx != null) {
             $grid.pqGrid("deleteRow", { rowIndx: rowIndx });
             $grid.pqGrid("setSelection", { rowIndx: rowIndx });
         }
     }
     function getRowIndx() {
         var arr = $grid.pqGrid("selection", { type: 'row', method: 'getSelection' });
         if (arr && arr.length > 0) {
             return arr[0].rowIndx;                                
         }
         else {
             alert("Select a row.");
             return null;
         }
     }
     
     
     //edit Row
     function editRow() {
         var rowIndx = getRowIndx();
         if (rowIndx != null) {

             var row = $grid.pqGrid('getRowData', {rowIndx: rowIndx});

             var $frm = $("form#crud-form");
             $frm.find("input[name='company']").val(row[0]);
             $frm.find("input[name='symbol']").val(row[1]);
             $frm.find("input[name='price']").val(row[3]);
             $frm.find("input[name='change']").val(row[4]);
             $frm.find("input[name='pchange']").val(row[5]);
             $frm.find("input[name='volume']").val(row[6]);

             $("#popup-dialog-crud").dialog({ title: "Edit Record (" + (rowIndx + 1) + ")", buttons: {
                 Update: function () {
                     //update row.
                     var row = [];
                     row[0] = $frm.find("input[name='company']").val();
                     row[1] = $frm.find("input[name='symbol']").val();
                     row[3] = $frm.find("input[name='price']").val();
                     row[4] = $frm.find("input[name='change']").val();
                     row[5] = $frm.find("input[name='pchange']").val();
                     row[6] = $frm.find("input[name='volume']").val();

                     $grid.pqGrid('updateRow', { rowIndx: rowIndx, row: row });

                     $(this).dialog("close");
                 },
                 Cancel: function () {
                     $(this).dialog("close");
                 }
             }
             }).dialog("open");
         }
     }
     
     function showMap()
     {
//    	 alert("Show Map!!!");
    	 var child = openPopup('../jsp/map.jsp?',true,610,420);
     }

 };
 
function LoadGridData(url,title,type,window){
	 
	 objType =type;
	 data_url = url;
	 window_title = title;
	 this_parent = window;

	 $.ajax({url:data_url,success: function(result){
		 InitGrid(result,title,objType,window);
		 }})}

function ReloadloadGridData(){
	 $.ajax({url:data_url,success: function(result){
//	 	load(result,objType+"s Information",objType,window);
		 
	 }})}
 
/*
 * Columns Models for Domain Objects for loading data in the Grid 
 */
 
// var colModels = {'plots' :[
//                      { title: "Plot ID", width: 100, dataType: "integer", dataIndx: "id",hidden: true },
//                      { title: "Plot #", width: 100, dataType: "integer", dataIndx: "plotNumber" },
//                      { title: "Category", width: 200, dataType: "string", dataIndx:"data" ,render: function (ui) {
//                          return ui.data[ui.rowIndx].plotCategory.name;
//                      }},//dataIndx: "plotStatus.name" },
//                      { title: "Status", width: 150, dataType: "string", align: "left", dataIndx: "data", render: function (ui) {
//                          return ui.data[ui.rowIndx].plotStatus.name;
//                      }},
//                      { title: "Size", width: 150, dataType: "float", align: "right", dataIndx: "data",render: function (ui) {
//                          return ui.data[ui.rowIndx].plotSize.area;
//                      }}
//                      ],//end plots
// 					
// 					'users' :[
// 		                      { title: "User Id", width: 100, dataType: "integer", dataIndx: "id",hidden: false },
// 		                      { title: "User Name", width: 200, dataType: "string", dataIndx: "userName" },
// 		                      { title: "First Name", width: 200, dataType: "string", dataIndx:"firstName" },//dataIndx: "plotStatus.name" },
// 		                      { title: "Last Name", width: 150, dataType: "string", align: "left", dataIndx: "lastName"},
// 		                      { title: "email", width: 150, dataType: "string", align: "left", dataIndx: "email"}
// 		                      
// 		                      ]//end users
// 					
// 
// };
 
 
 
 
// var colModelUsers = 
 //End -- Columns Models for Domain Objects for loading data in the Grid