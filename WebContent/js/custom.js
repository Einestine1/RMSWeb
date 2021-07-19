/*
 * 
 *This is the shared colModels structure 
 *shared between angularjs and pqgrid 
 *the purpose is to make the code as reusable as possible so 
 *When ever we want to add a new domain object and the associated form we just add a new Row in this array 'colModels' 
 * 
 */
	colModels = {
	'plot' : [// start plot
	{
		title : "Plot ID",
		width : 100,
		dataType : "integer",
		dataIndx : "id",
		hidden : true,
		showOnForm : false,
		inputType : 'text',
		objType : "id",
		jsonSpecifier : "id"
	}, {
		title : "Plot #",
		width : 100,
		dataType : "string",
		dataIndx : "plotNumber",
		showOnForm : true,
		inputType : 'text',
		objType : "plotNumber",
		jsonSpecifier : "plotNumber"
	}, {
		title : "Category",
		width : 200,
		dataType : "string",
		dataIndx : "data",
		showOnForm : true,
		inputType : 'select',
		objType : "plotCategory",
		jsonSpecifier : "plotCategory",
		visibleField : "name",
		render : function(ui) {
			return ui.data[ui.rowIndx].plotCategory.name;
		}
	},// dataIndx: "plotStatus.name" },
	{
		title : "Status",
		width : 150,
		dataType : "string",
		align : "left",
		dataIndx : "data",
		showOnForm : true,
		inputType : 'select',
		objType : "plotStatus",
		jsonSpecifier : "plotStatus",
		visibleField : "name",
		render : function(ui) {
			return ui.data[ui.rowIndx].plotStatus.name;
		}
	}, {
		title : "Size",
		width : 150,
		dataType : "float",
		align : "right",
		dataIndx : "data",
		showOnForm : true,
		inputType : 'select',
		objType : "plotSize",
		jsonSpecifier : "plotSize",
		visibleField : "area",
		render : function(ui) {
			return ui.data[ui.rowIndx].plotSize.area;
		}
	} ],// end plot

	'user' : [// start user

	{
		title : "UserId",
		width : 100,
		dataType : "integer",
		dataIndx : "id",
		hidden : true,
		showOnForm : false,
		inputType : 'text',
		objType : "id",
		jsonSpecifier : "id"
	}, {
		title : "User Name",
		width : 200,
		dataType : "string",
		dataIndx : "userName",
		showOnForm : true,
		inputType : 'text',
		objType : "userName",
		jsonSpecifier : "userName"
	}, {
		title : "First Name",
		width : 200,
		dataType : "string",
		dataIndx : "firstName",
		showOnForm : true,
		inputType : 'text',
		objType : "firstName",
		jsonSpecifier : "firstName"
	},// dataIndx: "plotStatus.name" },
	{
		title : "Last Name",
		width : 150,
		dataType : "string",
		align : "left",
		dataIndx : "lastName",
		showOnForm : true,
		inputType : 'text',
		objType : "lastName",
		jsonSpecifier : "lastName"
	}, {
		title : "email",
		width : 150,
		dataType : "string",
		align : "left",
		dataIndx : "email",
		showOnForm : true,
		inputType : 'text',
		objType : "email",
		jsonSpecifier : "email"
	}, {
		title : "Password",
		width : 150,
		dataType : "string",
		align : "left",
		dataIndx : "password",
		hidden : true,
		showOnForm : true,
		inputType : 'password',
		objType : "password",
		jsonSpecifier : "password"
	}, {
		title : "Is Admin",
		width : 150,
		dataType : "boolean",
		align : "left",
		dataIndx : "isAdmin",
		showOnForm : true,
		inputType : 'radio',
		objType : "isAdmin",
		jsonSpecifier : "isAdmin"
	} ],// end user
	
	'member' : [// start Member

	{
		title : "MemberId",
		width : 100,
		dataType : "integer",
		dataIndx : "id",
		hidden : true,
		showOnForm : true,
		inputType : 'text',
		objType : "id",
		jsonSpecifier : "id"
	}, {
		title : "First Name",
		width : 200,
		dataType : "string",
		dataIndx : "fName",
		showOnForm : true,
		inputType : 'text',
		objType : "fName",
		jsonSpecifier : "fName"
	}, {
		title : "Middle Name",
		width : 200,
		dataType : "string",
		dataIndx : "mName",
		hidden : true,
		showOnForm : true,
		inputType : 'text',
		objType : "mName",
		jsonSpecifier : "mName"
	}, {
		title : "Last Name",
		width : 150,
		dataType : "string",
		align : "left",
		dataIndx : "lName",
		showOnForm : true,
		inputType : 'text',
		objType : "lName",
		jsonSpecifier : "lName"
	}, {
		title : "Membership Number",
		width : 150,
		dataType : "string",
		align : "left",
		dataIndx : "membershipNumber",
		showOnForm : true,
		inputType : 'text',
		objType : "membershipNumber",
		jsonSpecifier : "membershipNumber"
	} ],// end Member

	'plotcategory' : [// start Category

	{
		title : "CategoryId",
		width : 100,
		dataType : "integer",
		dataIndx : "id",
		hidden : false,
		showOnForm : true,
		inputType : 'text',
		objType : "id",
		jsonSpecifier : "id"
	}, {
		title : "Category Name",
		width : 200,
		dataType : "string",
		dataIndx : "name",
		showOnForm : true,
		inputType : 'text',
		objType : "name",
		jsonSpecifier : "name"
	},

	],// end Category

	'plotsize' : [// start Size

	{
		title : "Size Id",
		width : 100,
		dataType : "integer",
		dataIndx : "id",
		hidden : true,
		showOnForm : false,
		inputType : 'text',
		objType : "id",
		jsonSpecifier : "id"
	}, {
		title : "Length",
		width : 200,
		dataType : "string",
		dataIndx : "length",
		showOnForm : true,
		inputType : 'text',
		objType : "length",
		jsonSpecifier : "length"
	}, {
		title : "Area",
		width : 200,
		dataType : "string",
		dataIndx : "area",
		showOnForm : true,
		inputType : 'text',
		objType : "area",
		jsonSpecifier : "area"
	} ],// end Size
	'plotstatus' : [// start Category

	{
		title : "Status Id",
		width : 100,
		dataType : "integer",
		dataIndx : "id",
		hidden : true,
		showOnForm : false,
		inputType : 'text',
		objType : "id",
		jsonSpecifier : "id"
	}, {
		title : "Status Name",
		width : 200,
		dataType : "string",
		dataIndx : "name",
		showOnForm : true,
		inputType : 'text',
		objType : "name",
		jsonSpecifier : "name"
	} ],//End Category
	
	'paymentschedule' : [// start PaymentSchedule

	                 	{
	                 		title : "CategoryId",
	                 		width : 100,
	                 		dataType : "integer",
	                 		dataIndx : "id",
	                 		hidden : false,
	                 		showOnForm : true,
	                 		inputType : 'text',
	                 		objType : "id",
	                 		jsonSpecifier : "id"
	                 	}, {
	                 		title : "Category Name",
	                 		width : 200,
	                 		dataType : "string",
	                 		dataIndx : "name",
	                 		showOnForm : true,
	                 		inputType : 'text',
	                 		objType : "name",
	                 		jsonSpecifier : "name"
	                 	},

	                 	]// end  Payment Schedule
};
	// password
 
// End shared column structure

// Start Shared data model
	 dataModel = {
	            location: "remote",            
	            dataType: "JSON",
	            method: "GET",
	            url: "/pro/invoice/paging",
	            // url: "/pro/invoice.php",//for PHP
	            getData: function (dataJSON) {                
	                return { curPage: dataJSON.curPage, totalRecords: dataJSON.totalRecords, data: dataJSON.data };                
	            }
	        }
// End Shared data model

/**
 * 
 */

/*
 * Test Functions
 */

// call this to check if this js file is accessible from the code
function testScriptAccess()
{
	alert("custom:I am called!");
};


function testEditUser()
{

	OpenWindowWithPost("jsp/AddUser.jsp","width=500,height=250,toolbar=no,location=no","newWindow",{userId:"1",request_type:"Update"});

};

/*
 * END--Test Functions
 */


/*
 * General purpose Functions
 */
function openPopup(link,mustCenter,width,height) {
	var myWindow;
	if(mustCenter==true)
		{

	    	wLeft = window.screenLeft ? window.screenLeft : window.screenX;
	    	wTop = window.screenTop ? window.screenTop : window.screenY;

	    	var left = wLeft + (window.innerWidth / 2) - (width/2);
	    	var top = wTop + (window.innerHeight / 2) - (height/2);
		   	
	    	myWindow = window.open(link, "_blank", "width="+width+",height="+height+",toolbar=no,location=no" + ", top=" + top + ", left=" + left);
		}
	else
		{
			myWindow = window.open(link, "_blank", "width="+width+",height="+height+",toolbar=no,location=no");
		}
	return myWindow;
};

function loadGridData(url,objType)
{
	$.ajax({url: url,success: function(result){
		load(result,objType+"s Information",objTYpe,window);
	}});
}

function closeWindow(refWindow)
{
	refWindow.close();
};

/*
 * If we want to open a child window and also want to post data to the child
 * window
 * 
 */
function OpenWindowWithPost(url, windowoption, name, params)
{
         var form = document.createElement("form");
         form.setAttribute("method", "post");
         form.setAttribute("action", url);
         form.setAttribute("target", name);

         for (var i in params) {
             if (params.hasOwnProperty(i)) {
                 var input = document.createElement('input');
                 input.type = 'hidden';
                 input.name = i;
                 input.value = params[i];
                 form.appendChild(input);
             }
         }
         
         document.body.appendChild(form);
         
         window.open(url, name, windowoption);
         form.target =name;
         form.submit();
         
         document.body.removeChild(form);
 };

 
// function formToJSON(strFormId)
// {
//	 var formData = JSON.stringify($("#"+strFormId).serializeArray());
//	 alert(formData);
// }

/*
 * END--General purpose Functions
 */


/*
 * Following are Form specific functions 
*/
 function loginUser()
 {
 	
 	var url ="/RMSWeb/doLogin";
 	$("#div1").html("<html><body></body></html>");
 	
 	var loginname = document.getElementById("Login").value;
 	var pass = document.getElementById("password").value;
 	
 	var postData = {Login:loginname , password:pass};

 	$.ajax({type:"POST", data:postData ,url: url,success: function(result){
 	            $("#div1").html(result);
 	            var patt = /Hello/i;
 	            if(patt.test(result))
 	            	{
 	            		window.location.replace("jsp/home.jsp");
 	            	}
 	        }});

 	return false;
 	
 };
 
function createUser()
{

	if(!validateUser())
	{
		return false;
	};

	var url1  ="/RMSWeb/ManageUsers";

	var pwd = document.getElementById("password1").value;

	var name = document.getElementById("userName").value;
	
	var fname = document.getElementById("firstname").value;
	
	var lname = document.getElementById("lastname").value;

	var email = document.getElementById("email").value;
	
	//this code checks the value of isAdmin radio buttons and prepares it be passed back-end component
	var isAdminuser = "No";
	var selected = $("input[type='radio'][name='isAdmin']:checked");
	if (selected.length > 0) {
		isAdminuser = selected.val();
	}
	
	
	var reqType = document.getElementById("hidden_request_type").value;

	var data1 =   {userName:name ,pass:pwd,request_type:reqType,firstName:fname,lastName:lname,emailAddress:email,isAdmin:isAdminuser };
	
	$.ajaxSetup({async:false});
	 $.post(url1,data1,function(res){
		 alert(res);
			$.ajaxSetup({async:true});		 
	 });
	return true;
};

function validateUser()
{

	var pwd1 = document.getElementById("password1").value;
	
	var pwd2 = document.getElementById("password2").value;
	var name = document.getElementById("userName").value;

	if(pwd1!=pwd2) 
	{
		alert("Password did not match!");
		return false;
	}
	
	if(name=="")
	{
		alert("User name must not be blank!");
		return false;
	}
	
	if(pwd1=="" || pwd2=="")
	{
		alert("Password can't be blank!");
		return false;
	}
	
	return true;
};
//End Form specific