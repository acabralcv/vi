modelModal = {

    autoClose: false,

    open : function(_autoClose){
        
        this.autoClose = _autoClose ? true : false;
        
        //$("#modalContentArea").show()
        $("#AppGlobalModal").modal()
    },
    

    close : function(){
        
        $("#AppGlobalModal").modal('hide')
    },
}