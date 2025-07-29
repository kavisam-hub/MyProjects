/**
  Below JS is used for Bootstrap  Collapsible Panel 
 */

$(document).on('click', '.panel-heading span.clickable', function(e){
    var $this = $(this);
	if(!$this.hasClass('panel-collapsed')) {
		$this.parents('.panel').find('.panel-body').slideUp();
		$this.addClass('panel-collapsed');
		$this.find('i').removeClass('glyphicon-chevron-up').addClass('glyphicon-chevron-down');
	} else {
		$this.parents('.panel').find('.panel-body').slideDown();
		$this.removeClass('panel-collapsed');
		$this.find('i').removeClass('glyphicon-chevron-down').addClass('glyphicon-chevron-up');
	}
});

/* var $table=$('#materialListTable');
 if($table.length)
{	
	$table.DataTable({
		pageLength:5,
	});
}*/

/* For Excel Uploaded Material Request List */
/* var table = $('#materialReqTable').DataTable({
  "dom": '<"top"fl>rt<"bottom"ip><"clear">'	  
} ); */
$(document).ready(function() {
    $('#materialReqTable').DataTable( {
        "scrollX": true,
        lengthMenu:[[25,50,75,100,-1],['25','50','75','100','All']],
    } );       
} );


$(document).ready(function() {
	$('#mydashboard').DataTable( {
        "scrollX": true,
        lengthMenu:[[25,50,75,100,-1],['25','50','75','100','All']],
        
    } ); 
});

$(document).ready(function() {
	$('#hoddashboard').DataTable( {
        "scrollX": true,
        lengthMenu:[[25,50,75,100,-1],['25','50','75','100','All']],
        
    } ); 
});

$(document).ready(function() {
	$('#pcldashboard').DataTable( {
        "scrollX": true,
        lengthMenu:[[25,50,75,100,-1],['25','50','75','100','All']],
        
    } ); 
});

$(document).ready(function() {
	$('#plantdashboard').DataTable( {
        "scrollX": true,
        lengthMenu:[[25,50,75,100,-1],['25','50','75','100','All']],
        
    } ); 
});
/* $(document).ready(function() {
    // Setup - add a text input to each footer cell
    $('#materialReqTable tfoot th').each( function () {
        var title = $(this).text();
        $(this).html( '<input type="text" placeholder="Search '+title+'" />' );        
    } );   */  
    
    
    // DataTable
    var table = $('#mmsearch').DataTable();    

    // Apply the search
    table.columns().every( function () {
        var that = this;
 
        $( 'input', this.footer() ).on( 'keyup change', function () {
            if ( that.search() !== this.value ) {
                that
                    .search( this.value )
                    .draw();
            }
        } );
    } );


/* For Material List to be approved */
// var table = $('#materialListTable').DataTable();
 $(document).ready(function() {
	    $('#materialListTable').DataTable( {
	        "scrollX": true, 
	        lengthMenu:[[25,50,75,100,-1],['25','50','75','100','All']],
	    } ); 	    
	} ); 
/*select all checkboxes */
 $("#select_all_hod").change(function(){  // select_all_hod is the checkbox (for selecting all) id in a table
 	var status = this.checked; // "select all" checked status
 	$('.checkbox').each(function(){ //iterate all listed checkbox items
 		this.checked = status; //change ".checkbox" checked status
 	});
 }); 
 
// PCL datatable details 
 $(document).ready(function() {
	    $('#materialListPCLTable').DataTable( {
	        "scrollX": true,
	        lengthMenu:[[25,50,75,100,-1],['25','50','75','100','All']],
	    } ); 	    
	} ); 
/* select all checkboxes */
$("#select_all_pcl").change(function(){  //"select all" change 
	var status = this.checked; // "select all" checked status
	$('.checkbox').each(function(){ //iterate all listed checkbox items
		this.checked = status; //change ".checkbox" checked status
	});
}); 
 
/* $(document).ready(function() {
    // Setup - add a text input to each footer cell
    $('#materialListTable tfoot th').each( function () {
        var title = $(this).text();
        $(this).html( '<input type="text" placeholder="Search '+title+'" />' );      
	 
    } );
    
     
    
    // DataTable
     

    // Apply the search
    table.columns().every( function () {
        var that = this;
 
        $( 'input', this.footer() ).on( 'keyup change', function () {
            if ( that.search() !== this.value ) {
                that
                    .search( this.value )
                    .draw();
            }
        } );
    } );
} );  */


var $table=$('#example');
/* if($table.length)
{	
	$table.DataTable({
		lengthMenu:[[5,10,20,30,-1],['5 Records','10 Records','20 Records','30 Records','All']],
		pageLength:5,
	});	
} */

$(document).ready(function() {
    $('#example').DataTable( {
        initComplete: function () {
            this.api().columns().every( function () {
                var column = this;
                var select = $('<select><option value=""></option></select>')
                    .appendTo( $(column.footer()).empty() )
                    .on( 'change', function () {
                        var val = $.fn.dataTable.util.escapeRegex(
                            $(this).val()
                        );
 
                        column
                            .search( val ? '^'+val+'$' : '', true, false )
                            .draw();
                    } );
 
                column.data().unique().sort().each( function ( d, j ) {
                    select.append( '<option value="'+d+'">'+d+'</option>' )
                } );
            } );
        }
    } );
} );

