 /**
  * TaskList
  * Manage view with list of tasks
  */
 import TaskBox from "./TaskBox.js"




class TaskList extends HTMLElement {

    constructor() {
		// Create a shadow DOM structure
        super();
		this.shadow = this.attachShadow ({ mode : 'open' });

		// initialize 
		this.tasklist = []
		this.possibleStatuses = []
		


    }
    addCallbacks(deleteCallback,updateCallback){
		this.deletetaskCallbackVar = deleteCallback
		this.updateCallback = updateCallback
	}
    
    
    display(){
		// clear dom 
		this.shadow.innerHTML = ''
		

		
		// only show header when tasklist is not empty 
		if(this.tasklist.length != 0){
			
			const table = document.createElement('table')

			/* header */ 
			var tr = document.createElement("tr");
			var td1 = document.createElement("td")
			td1.innerHTML = "Task"
			var td2 = document.createElement("td")
			td2.innerHTML = "Status"
			
			tr.appendChild(td1)
			tr.appendChild(td2)
			
			table.appendChild(tr)
			
			
			/* line*/ 
			var tr = document.createElement("tr")
			
			
			var td = document.createElement("td")
			td.setAttribute("colspan",4)
			var hr = document.createElement("hr")
			td.appendChild(hr)
			
			
			tr.appendChild(td)
			
			
			table.appendChild(tr)
			/* items */ 
			for( var e of this.tasklist){	
				if(typeof e == 'undefined'){
					continue 
				}
				var tr = document.createElement('tr');
			


				var td1 = document.createElement("td")
				td1.innerHTML = e.title
				
				var td2 = document.createElement("td")
				td2.innerHTML = e.status 
				
				var td3 = document.createElement("td")
				var select = document.createElement('select')
				for (var el of this.possibleStatuses){

					var opt = document.createElement("option")
					opt.innerHTML = el 
					select.appendChild(opt)
				}
				select.setAttribute("id",e.id)
				
				td3.appendChild(select)

				
				
				var td4 = document.createElement("td")
				var button = document.createElement("button")
				button.innerHTML = "Remove"

				button.setAttribute("id",e.id)
				td4.appendChild(button)
				
				
				tr.appendChild(td1)
				tr.appendChild(td2)
				tr.appendChild(td3)
				tr.appendChild(td4)
				
				
				table.appendChild(tr)
				
			}
							
			/* line*/ 
			var tr = document.createElement("tr")
			
			
			var td = document.createElement("td")
			td.setAttribute("colspan",4)
			var hr = document.createElement("hr")
			td.appendChild(hr)
			
			
			tr.appendChild(td)
			
			
			table.appendChild(tr)
			this.shadow.appendChild ( table );
			
			
		}
		
		this.changestatusCallback(this.updateCallback)
		this.deletetaskCallback(this.deletetaskCallbackVar)
		
	}
	

    /**
     * @public
     * @param {Array} list with all possible task statuses
     */
    setStatuseslist(allstatuses) {
        // Fill in code
		this.possibleStatuses = []
		this.possibleStatuses[0] = "choose"
        this.possibleStatuses = this.possibleStatuses.concat(allstatuses)

        

    }

    /**
     * Add callback to run on change of status of a task, i.e. on change in the SELECT element
     * @public
     * @param {function} callback
     */
    changestatusCallback(callback) { // callback points back to TaskView.updateTaskInDb
		var selects = this.shadow.querySelectorAll("select")
		for (var element of selects){
				element.addEventListener("change",(event)=>{
										var id = event.target.id 
										var val = event.target.value
										var name = event.target.parentElement.parentElement.firstChild.innerHTML
										 var check = confirm('Do you want to set ' +name + " to " + val + "?" ); 
										if (check == true) {
											var data = {id:id, status:val}
											callback(data)
											this.updateTask(data)
											}
							
									})
			}
		
    }

    /**
     * Add callback to run on click on delete button of a task
     * @public
     * @param {function} callback
     */
    deletetaskCallback(callback) { //callback points back to TaskView.deleteTaskInDb()
        // Fill in code

		var deleteButton = this.shadow.querySelectorAll("table > tr > td > button")
		for (var element of deleteButton){
		element.addEventListener("click",(event)=>{
								var id = event.target.id 
								var name = event.target.parentElement.parentElement.firstChild.innerHTML
								 var check = confirm('Do you want to delete ' +name +"?" ); 
								if (check == true) {
										console.log("delete event listener ausgeführt")
										callback(id)
										this.removeTask(id)
										
										}
			
							})
		}
    }




    /**
     * Add task at top in list of tasks in the view
     * @public
     * @param {Object} task - Object representing a task
     */
    showTask(task) {
        // Fill in code
        this.tasklist.unshift(task)
 		this.display() 
  
    }

    /**
     * @public
     * @return {Number} - Number of tasks on display in view
     */
    getNumtasks() {
        // Fill in code
        return this.tasklist.length 
    }
    
     /**
     * Update the status of a task in the view
     * @param {Object} task - Object with attributes {'id':taskId,'status':newStatus}
     */
    updateTask(task) {

        // Fill in code
        if(this.tasklist.length != 0 ){

            var i = 0
            for(var element of this.tasklist){

				
				
				if(element.id == task.id){
					var task_element = this.tasklist[i]
					task_element.status = task.status 
					this.tasklist[i] = task_element 
					break 
				}else{
				i++
				}
			}
			
		}
		this.display() 
    }
    
     /**
     * Remove a task from the view
     * @param {Integer} task - ID of task to remove
     */
    removeTask(id) {
        // Fill in code
        
        if(this.tasklist.length != 0 ){
        var i = 0
        for(var element of this.tasklist){

			
			
			if(element.id == id){
				this.tasklist.splice(i,1)
				break 
			}else{
			i++
			}
		}
		this.display()
		}
    }
    

}
    



export default{
	TaskList
}
