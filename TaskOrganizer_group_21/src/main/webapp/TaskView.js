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
			// get dataService url 
			this.dataServiceUrl = this.getAttribute("data-serviceurl"); 

			// Create a shadow DOM structure
			this.shadow = this.attachShadow ({ mode : 'closed' });

	
			//clear dom 
			this.shadow.innerHTML = ''
			// add template 
			this.content = template.content.cloneNode(true);
			this.shadow.appendChild(this.content)
			

			//open taskbox button 
			this.shadow.querySelector("div >button").addEventListener("click", (event)=>{
				// open or reopen the taskbox 
				console.log("added event listener ")
				this.createTaskBox(event)

			})
			
			// create tasklist 
			this.createTasklist() 
			}
			
			
			
	
					/** 
		 * Creates the TaskBox element if it does not exists
		 * if it already exists it just reopens it because
		 * its just hidden 
		 * */	
		async createTaskBox(event){
				console.log("create taskbox")
				
				if (customElements.get('task-box') === undefined) {
					// create the task box 
					this.TaskBox = customElements.define('task-box', TaskBox.TaskBox);

				}

				/* get statuses from database */ 
				var statuses = await this.getStatusesFromDb()
				if(statuses == -1){
					statuses = ["Database Error"]
				}
				
				/* set statuses */ 
				var task_box = this.shadow.querySelector("task-box")
				task_box.setStatuseslist(statuses)
				task_box.show() 
				/* add new task callback */ 
				task_box.addNewTaskCallback(this.closeTaskBox.bind(this))
					
				
		}
		
		async closeTaskBox(data){
			var tasklist = this.shadow.querySelector("task-list")
			var ret = this.addTaskToDb(data)
			if(ret ==  1){
				// success 
				tasklist.showTask(data)
			}
		}
	
			
			
			
		async createTasklist(){
						if (customElements.get('task-list') === undefined) {
							customElements.define('task-list', TaskList.TaskList);	
							}
	
						
						var tasklist = this.shadow.querySelector("task-list");		
						
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

						
						/* save callback functions in tasklist so they can be later reused */ 
						tasklist.addCallbacks(this.deleteTaskInDb.bind(this),this.updateTaskInDb.bind(this))
						
						/* initially display tasklist */ 
						tasklist.display() 
	
						
						/* user can now add tasks */ 
						this.shadow.querySelector("div > button").removeAttribute("disabled")
						
						
			}
			
		/**
		 * Gets Statuses from db
		 * no params 
		 * returns -1 in case of error 
		 * or the status from the db when succeding 
		 */
		async getStatusesFromDb(){

			var url = this.dataServiceUrl + "/allstatuses"
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
			var url = this.dataServiceUrl + "/tasklist"
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
		
					fetch(this.dataServiceUrl + "/task", {
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
								return -1 
							}
							return 1 
	

					
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
			fetch(this.dataServiceUrl + "/task/"+id, {
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
							
							var taskList = this.shadow.querySelector("task-list")
							taskList.updateTask(data)
							
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
			
			fetch(this.dataServiceUrl + "/task/"+id, {
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
							
							var taskList = this.shadow.querySelector("task-list")
							taskList.removeTask(id)
						
						})	
		}
		
		
		}

		
	customElements.define('task-view', TaskView);	
}