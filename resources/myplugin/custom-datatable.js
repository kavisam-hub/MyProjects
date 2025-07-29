var table;

jQuery(document).ready(function() {
	table = $('#it_assets_masterTable').dataTable({
		  	"bPaginate": true,
		  	"order": [ 0, 'asc' ],
		  	"bInfo":false,
		  	"iDisplayStart":0,  	
		  	"retrieve": true,
		  	"jqueryUI":true,
		  	"bProcessing" : true,
		 	"bServerSide" : true,
		 	"sAjaxSource" : path+"/materialmaster/ajaxtest",
		 	"lengthMenu": [[25,50,75,100], [25,50,75,100]],
		 	"dom": 'C<"clear">lfrtip',
			colVis: {				
				"align": "left",				
	            restore: "Restore",
	            showAll: "Show all",
	            showNone: "Show none",
				order: 'alpha',
				"buttonText": "columns <img src=\"./images/caaret.png\"/>"
	        },	        
		    "language": {
	            "infoFiltered": ""
	        },
	      //  "dom": 'Cf<"toolbar"">rtip',
	      //  "dom":'<"top"p>rt<"bottom"flp><"clear">';
	        "dom": '<"top"p,r,l>rt<"clear">'
	        	 
	      })
			  .columnFilter({
				  aoColumns: [ 
					             { type: "text"},
						         { type: "text" },
						         { type: "text" },
						         { type: "text" },
		                         { type: "text" },
		                         { type: "text" },
		                         { type: "text" },
		                         { type: "text" },
		                         { type: "text" },
		                         { type: "text" },
		                         { type: "text" },	                         
		                         { type: "text" }	                         
		                         
							],
						bUseColVis: true
			   }).fnSetFilteringDelay();
			$("#it_assets_master_length").hide();
			$("div.toolbar").append('<div class="btn-group" style="padding:5px "><button class="btn btn-default" id="refreshbtn" style="background:none;border:1px solid #ccc;height:30px" type="button"><span class="glyphicon glyphicon-refresh" style="padding:3px"></span></button></div>');
			   $("div.toolbar").css("float","right");
			   $('#refreshbtn').click(function(){
				   table.fnStandingRedraw();				   
		   });	 
			
			   
			   
			
	}); 