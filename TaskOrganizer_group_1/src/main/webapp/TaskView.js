/**
 * api: http://localhost:8080/TaskServices/api/services/allstatuses
 * communication: https://www.freecodecamp.org/news/how-to-communicate-between-components-b48ef70bf913/
 */

  import TaskBox from "./TaskBox.js"
import TaskList from "./TaskList.js";

  
  
  
if (customElements.get('task-view') === undefined) {

    class TaskView extends HTMLElement {

        constructor() {
            super();
		
            /**
             * Fill inn rest of code
             */
			// Create a shadow DOM structure
			this.shadow = this.attachShadow ({ mode : 'closed' });
			this.display() 

			}
			
			
			
		
		
		 display(){
			this.shadow.innerHTML = ''
			
			var button = document.createElement("button")
			button.innerHTML = "add task "
			button.addEventListener("click", (event)=>{
				this.createTaskBox(event)

			})
			this.shadow.appendChild(button)


			
			
			var div = document.createElement("div")
			div.setAttribute("id","message")
			div.innerHTML = "Wait for Server data ..." 
			
			this.shadow.appendChild(div) 
			
			
			this.createTasklist() 
			
			}
			
			
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
	
						
						const tasklist = document.querySelector("task-list");
						
						
						/* get statuses from database */ 
						var statuses = await this.getStatusesFromDb()
						if(statuses == -1){
							statuses = ["Database Error"]
						}
						
						tasklist.setStatuseslist(statuses)
						
						
						/* get tasks from db */ 
						var tasks = await this.getTaskListFromDb() 
						var showDatabaseStatus = this.shadow.querySelector("div")
						if(tasks == -1 ){
							showDatabaseStatus.innerHTML = "Database Error"
							tasks = [{id: 1, status: 'Database Error', title: 'Database Error'}] 
						}else{
							showDatabaseStatus.innerHTML = "Found " + tasks.length + " tasks."
						}
						tasklist.tasklist = []
						for (let t of tasks) {
							tasklist.showTask(t)
						}
						
						/* add event listener to select */  
						var selects = tasklist.shadowRoot.querySelectorAll("select")
						for (var element of selects){
								element.addEventListener("change",(event)=>{
											var id = event.target.id 
											var val = event.target.value
										this.updateTaskInDb({id:id, status: val})	
													})
						}
						
						
						/* add event listener to remove button*/ 
						var buttons = tasklist.shadowRoot.querySelectorAll("button")
						for (var element of buttons){
								element.addEventListener("click",(event)=>{
										var id = event.target.id 
					
										this.deleteTaskInDb(id)
													})
						} 
			}
			
		test(){
			console.log("in task view ")
		}
			
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
							      // Fehlerbehandlung für nicht erfolgreiche Antworten
							      window.alert("Error while creating a new Task, Probably you have used a wrong status")
							      throw new Error("HTTP-Fehler, Statuscode: " + response.status);
							      
							   }; 
    
							return response.json()
							
					
						}).then((responseData)=> {
							if(responseData["responseStatus"] != true){
								window.alert("Error while creating a new Task: responseStatus is false ")
							}

							
						})	
						
					console.log(this)
					this.display() 
		
		
		
			
		}
		
		
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