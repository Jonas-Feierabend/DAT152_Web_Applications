/**
 * 
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
			this.createTasklist() 
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
			div.innerHTML = "warten auf server data (aktuell einfach fixtext)" 
			
			this.shadow.appendChild(div) 
			
			}
			
			
			
		createTasklist(){
						customElements.define('task-list', TaskList.TaskList);	
						 const allstatuses = ["WAITING", "ACTIVE", "DONE"];
		    				const tasks = [
											 {
											 id: 1,
											 status: "WAITING",
											 title: "Paint roof"
											 },
											 {
											 id: 2,
											 status: "ACTIVE",
											 title: "Wash windows"
											 },
											 {
											 id: 3,
											 status: "DONE",
											 title: "Wash flooer"
											 }
												];
			
						const tasklist = document.querySelector("task-list");
						tasklist.setStatuseslist(allstatuses)
						for (let t of tasks) {
							tasklist.showTask(t)
						}
			}
			
		}
		
		

		
	customElements.define('task-view', TaskView);	
}