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
		
		
		//display the tasklist 
		this.display(); 
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
			this.shadow . appendChild ( table );
			
			
		}
		
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

        this.display()

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
											callback({id:id, status:val})
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
										callback(id)
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


        this.display(); 
    }

    /**
     * @public
     * @return {Number} - Number of tasks on display in view
     */
    getNumtasks() {
        // Fill in code
        return this.tasklist.length 
    }
    

}
    



export default{
	TaskList
}
