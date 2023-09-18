 /**
  * TaskList
  * Manage view with list of tasks
  */
const template = document.createElement("template");
template.innerHTML = `
    <link rel="stylesheet" type="text/css" href="${import.meta.url.match(/.*\//)[0]}/tasklist.css"/>

    <div id="tasklist"></div>`;

const tasktable = document.createElement("template");
tasktable.innerHTML = `
    <table>
        <thead><tr><th>Task</th><th>Status</th></tr></thead>
        <tbody></tbody>
    </table>`;

const taskrow = document.createElement("template");
taskrow.innerHTML = `
    <tr>
        <td></td>
        <td></td>
        <td>
            <select>
                <option value="0" selected>&lt;Modify&gt;</option>
            </select>
        </td>
        <td><button type="button">Remove</button></td>
    </tr>`;




class TaskList extends HTMLElement {

    constructor() {
		// Create a shadow DOM structure
        super();
		this.shadow = this.attachShadow ({ mode : 'open' });

		// initialize 
		this.tasklist = []
		this.possibleStatuses = []
		


    }

    
    
    display(){
		// clear dom 
		this.shadow.innerHTML = ''
		this.content = template.content.cloneNode(true);
		this.shadow.appendChild(this.content)

		
		// only show header when tasklist is not empty 
		if(this.tasklist.length != 0){
		
			const table_template = tasktable.content.cloneNode(true)
			this.shadow.appendChild ( table_template );
			

			const table = this.shadow.querySelector("table > tbody")
			/* items */ 
			
			
			for (var e of this.tasklist){
				var taskrow_template = taskrow.content.cloneNode(true)
				taskrow_template.querySelector("tr > td").innerHTML = e.title
				taskrow_template.querySelector("tr > td").nextSibling.nextSibling.innerHTML = e.status 
				
				var select = taskrow_template.querySelector("tr > td > select")
				for (var el of this.possibleStatuses){
					var opt = document.createElement("option")
					opt.innerHTML = el 
					opt.value = el
					select.appendChild(opt)
				}
				select.setAttribute("id",e.id)
				
				var button = taskrow_template.querySelector("tr>td>button")
				
				button.setAttribute("id",e.id)
				table.appendChild(taskrow_template)
			}
		
			
			
		}
		

		
	}
	

    /**
     * @public
     * @param {Array} list with all possible task statuses
     */
    setStatuseslist(allstatuses) {
        // Fill in code
		this.possibleStatuses = []
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
																
										var name = event.target.parentElement.parentElement.firstChild.nextSibling.innerHTML 
						
										 var check = confirm('Do you want to set ' +name + " to " + val + "?" ); 
										if(val == 0 ){
											window.alert("You cant chose &ltmodify &gt")
											check = false 
										}
										if (check == true) {
											var data = {id:id, status:val}
											console.log(data)
											callback(data)
											
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

		var deleteButton = this.shadow.querySelectorAll("table >tbody> tr > td > button")
		for (var element of deleteButton){
		element.addEventListener("click",(event)=>{
								var id = event.target.id 
								var name = event.target.parentElement.parentElement.firstChild.nextSibling.innerHTML
								 var check = confirm('Do you want to delete ' +name +"?" ); 
								if (check == true) {
										console.log("delete event listener ausgeführt")
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
