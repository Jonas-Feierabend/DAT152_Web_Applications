/**
 * api: http://localhost:8080/TaskServices/api/services/allstatuses
 * communication: https://www.freecodecamp.org/news/how-to-communicate-between-components-b48ef70bf913/
 */

  import TaskBox from "./TaskBox.js"
import TaskList from "./TaskList.js";

  
const template = document.createElement("template");
template.innerHTML = `
 <link rel="stylesheet" type="text/css"
 href="${import.meta.url.match(/.*\//)[0]}/taskview.css"/>
 <h1>Tasks</h1>
 <div id="message"><p>Waiting for server data.</p></div>
 <div id="newtask">
 <button type="button" disabled>New task</button>
 </div>
 <!-- The task list -->
 <task-list></task-list>
 
 <!-- The Modal -->
 <task-box></task-box>
`;

  
if (customElements.get('task-view') === undefined) {

    class TaskView extends HTMLElement {

        constructor() {
            super();
		
			// Create a shadow DOM structure
			this.shadow = this.attachShadow ({ mode : 'closed' });
			this.display() 

			}
			
			
			
		
		
		 display(){
			 //clear dom 
			this.shadow.innerHTML = ''
			
			
			// headline
			var head = document.createElement("h1")
			head.innerHTML = "Tasks" 
			
			this.shadow.appendChild(head)
			// ADD Task button 
			var button = document.createElement("button")
			button.innerHTML = "add task "
			button.addEventListener("click", (event)=>{
				this.createTaskBox(event)

			})
			this.shadow.appendChild(button)


			
			// Message element which shows the current database status (waiting, error, found x elements)
			var div = document.createElement("div")
			div.setAttribute("id","message")
			div.innerHTML = "Wait for Server data ..." 
			
			this.shadow.appendChild(div) 
			
			
			this.createTasklist() 
			
			}
		/** 
		 * Creates the TaskBox element if it does not exists
		 * if it already exists it just reopens it because
		 * its just hidden 
		 * */	
		async createTaskBox(event){

				
				if (customElements.get('task-box') === undefined) {
					// create the task box 
					this.TaskBox = customElements.define('task-box', TaskBox.TaskBox);
	
					/* get statuses from database */ 
					var statuses = await this.getStatusesFromDb()
					if(statuses == -1){
						statuses = ["Database Error"]
					}
					
					/* set statuses */ 
					var task_box = document.querySelector("task-box")
					task_box.setStatuseslist(statuses)
					
					
					/* add new task callback */ 
					task_box.addNewTaskCallback(this.addTaskToDb.bind(this))
					
					
				
				
				}else{
					// just reopen it; its just hidden 
					var task_box = document.querySelector("task-box")
					task_box.show() 
				}
				
		}
	
			
			
			
		async createTasklist(){
						if (customElements.get('task-list') === undefined) {
							customElements.define('task-list', TaskList.TaskList);	
							}
	
						
						var tasklist = document.querySelector("task-list");		
						
						/* get statuses from database */ 
						var statuses = await this.getStatusesFromDb()
						// error handling in case of db errors 
						if(statuses == -1){
							statuses = ["Database Error"]
						}
						
						tasklist.setStatuseslist(statuses)
						
						
						/* get tasks from db */ 
						var tasks = await this.getTaskListFromDb() 
						var showDatabaseStatus = this.shadow.querySelector("div")
						
						// error handling if there is a db error 
						if(tasks == -1 ){
							showDatabaseStatus.innerHTML = "Database Error"
							tasks = [{id: 1, status: 'Database Error', title: 'Database Error'}] 
						}else{
							showDatabaseStatus.innerHTML = "Found " + tasks.length + " tasks."
						}
						// fill tasklist with statuses 
						tasklist.tasklist = []
						for (let t of tasks) {
							tasklist.showTask(t)
						}
						
						tasklist.display() 
						
						
						/* add remove callback */ 
						tasklist.deletetaskCallback(this.deleteTaskInDb.bind(this))
						
						/* add change callback */ 
						tasklist.changestatusCallback(this.updateTaskInDb.bind(this)) 
						
						
						
			}
			
		/**
		 * Gets Statuses from db
		 * no params 
		 * returns -1 in case of error 
		 * or the status from the db when succeding 
		 */
		async getStatusesFromDb(){

			var url = "../TaskServices/api/services/allstatuses"
			try{
					const response = await fetch(url);
					var dict = await response.json() 
			
			
					if (await dict["responseStatus"] != true) {
						return -1 
					}
					else{
						return await dict["allstatuses"]
					}
					
			} catch (error) {
    				console.error(`Download error: ${error.message}`);
    				return -1 
  					}
		}

		/**
		 * Gets Tasklist from db
		 * no params 
		 * returns -1 in case of error 
		 * or the status from the db when succeding 
		 */
		async getTaskListFromDb(){
			var url = "../TaskServices/api/services/tasklist"
			try{
					const response = await fetch(url);
					var dict = await response.json() 
			
			
					if (await dict["responseStatus"] != true) {
						return -1 
					}
					else{
						return await dict["tasks"]
					}
					
			} catch (error) {
	
    				console.error(`Download error: ${error.message}`);
    				return -1 
  					}
		}
		
		/**
		 * Adds a task to the db 
		 * Takes an object with title and status 
		 * e.g {title:"test":status:"test"}]
		 * status must be a valid word or else the database will complain 
		 * returns nothing but will pop up an alert in case of error 
		 */
		async addTaskToDb(data){
		
					fetch("../TaskServices/api/services/task", {
						  method: "POST",
						  body: JSON.stringify({
						    title: data["title"],
						    status: data["status"],
						  }),
						  headers: {
						    "Content-type": "application/json; charset=UTF-8"
						  }
					}).then((response) => {
						
							if (!response.ok) {
							      // Error handling 
							      window.alert("Error while creating a new Task, Probably you have used a wrong status")
							      throw new Error("HTTP-Fehler, Statuscode: " + response.status);
							      
							   }; 
    
							return response.json()
							
					
						}).then((responseData)=> {
							if(responseData["responseStatus"] != true){
								window.alert("Error while creating a new Task: responseStatus is false ")
							}

							this.display() 
						})	
						
					
		
		
		
			
		}
		/**
		 * Updates a task in the db 
		 * Takes an object with id and status 
		 * e.g {id:1:status:"test"}]
		 * status must be a valid word or else the database will complain 
		 * returns nothing but will pop up an alert in case of error  
		 */	
		
		async updateTaskInDb(data){
			console.log("update: "+ data["id"]+ data["status"] )
			var id = data["id"]
			fetch("../TaskServices/api/services/task/"+id, {
						  method: "PUT",
						  body: JSON.stringify({
						    status: data["status"],
						  }),
						  headers: {
						    "Content-type": "application/json; charset=UTF-8"
						  }
					}).then((response) => {
							console.log("resp: "+response)
							if (!response.ok) {
							      // Fehlerbehandlung für nicht erfolgreiche Antworten
							      window.alert("Error while updating a Task, Probably you have used a wrong status")
							      throw new Error("HTTP-Fehler, Statuscode: " + response.status);
							      
							   }; 
    
							return response.json()
							
					
						}).then((responseData)=> {
							if(responseData["responseStatus"] != true){
								window.alert("Error while updating a Task: responseStatus is false ")
							}
							
							this.display() 
						})	
			
			
		}
		
		/**
		 * Deletes a task from the db 
		 * Takes an id
		 * e.g 1
		 * returns nothing but will pop up an alert in case of error 
		 */
		async deleteTaskInDb(id){
			console.log("deleting: "+ id )
			
			fetch("../TaskServices/api/services/task/"+id, {
						  method: "DELETE",
				
						  headers: {
						    "Content-type": "application/json; charset=UTF-8"
						  }
					}).then((response) => {
							console.log("resp: "+response)
							if (!response.ok) {
							      // Fehlerbehandlung für nicht erfolgreiche Antworten
							      window.alert("Error while Deleting a Task")
							      throw new Error("HTTP-Fehler, Statuscode: " + response.status);
							      
							   }; 
    
							return response.json()
							
					
						}).then((responseData)=> {
							if(responseData["responseStatus"] != true){
								window.alert("Error while Deleting a Task: responseStatus is false ")
							}
							
							this.display() 
						})	
		}
		
		
		}

		
	customElements.define('task-view', TaskView);	
}