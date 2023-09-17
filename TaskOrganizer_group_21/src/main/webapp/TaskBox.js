/**
 * TaskBox is used to create new tasks. It takes name + status and on click on add Task
 * TaskView will receive a callback with the data 
 */

const template = document.createElement("template");

template.innerHTML = `
			<link rel="stylesheet" type="text/css"
			 href="${import.meta.url.match(/.*\//)[0]}/taskbox.css"/>
			 
			 
			 <dialog>
			 <!-- Modal content -->
			 <span>&times;</span>
			 <div>
			 <div>Title:</div>
			 <div>
			 <input type="text" size="25" maxlength="80"
			 placeholder="Task title" autofocus/>
			 </div>
			 <div>Status:</div><div><select></select></div>
			 </div>
			 <p><button type="submit">Add task</button></p>
			 </dialog>
			`;
	

class TaskBox extends HTMLElement {
	
	
		constructor() {
			// create the element 
            super();
            this.shadow = this.attachShadow ({ mode : 'open' });
			this.content = template.content.cloneNode(true);
			this.shadow.appendChild(this.content)
			

			// doesnt need callback from parent 
			this.addCloseCallback() 

			// initialize 
			this.possibleStatuses = []
		

        }

        /* when user clicks on x */ 
        addCloseCallback(){
			this.shadow.querySelector("span").addEventListener("click", (event)=>{
				this.close()

			})
			// i have decided to only add the listener to the taskbox. therefore a
			// keydown is not recognized when the user clicked somewhere else before 
			this.shadow.addEventListener("keydown",(event) =>{
				if(event.key === "Escape"){
				this.close() 
				}
			})

		}
		/* closes the dialog (hides it)*/
		close() {
			var dialog = this.shadow.querySelector("dialog")
			dialog.close() 
		}
		
		/* shows the dialog again */ 
		show(){
			var dialog = this.shadow.querySelector("dialog")
			dialog.show() 
		}
		
		/* callback when user clicks on add task -> callback to TaskView */ 
		addNewTaskCallback(callback){
			 var addTaskButton = this.shadow.querySelector("dialog > p > button")
				
			
			 addTaskButton.addEventListener("click", (event)=>{
				var title = this.shadow.querySelector("dialog > div > div > input").value
				var status = this.shadow.querySelector("dialog > div > div > select").value
				
				var task = {"title":title, "status":status}
				this.newtask(task)
				callback(task) 
				


			})
		}
		
		/* when user click on add task */ 
		 newtask(task){
			console.log(`Have '${task.title}' with status ${task.status}.`);
			 this.close();

		 }
		/* sets all statuses a new task can have -> fetched from the db */ 
		setStatuseslist(allstatuses) {
			// find select 
			var select_element = this.shadow.querySelector("dialog > div > div > select")
			
			// remove all options
			
			select_element.innerHTML = ""
			
            // Fill in code
   			this.possibleStatuses = []
   			this.possibleStatuses[0] = "&ltModify&gt"
            this.possibleStatuses = this.possibleStatuses.concat(allstatuses)

       		
       		
  
       		for (var e of this.possibleStatuses){
					
				   var element = document.createElement("option")
				   element.setAttribute("value",e)
				   element.innerHTML = e 
				   
				   select_element.appendChild(element)
			   }

        
        }
        

    
	
	}
		
		
		
export default{
	TaskBox
}
		
		
		
	