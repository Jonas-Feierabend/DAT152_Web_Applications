/**
 * 
 */

const template = document.createElement("template");
// TODO: css file 
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
			
			
			// make it visible 
			this.show() 


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

		}
		
		close() {
			var dialog = this.shadow.querySelector("dialog")
			dialog.close() 
		}
		
		
		show(){
			var dialog = this.shadow.querySelector("dialog")
			dialog.show() 
		}
		
		
		addNewTaskCallback(callback){
			 var addTaskButton = this.shadow.querySelector("dialog > p > button")
				
			
			 addTaskButton.addEventListener("click", (event)=>{
				var title = this.shadow.querySelector("dialog > div > div > input").value
				var status = this.shadow.querySelector("dialog > div > div > select").value
				
				var task = {"title":title, "status":status}
				this.newtaskCallback(task)
				callback(task) 
				


			})
		}
		
		/* when user click on add task */ 
		 newtaskCallback(task){
			console.log(`Have '${task.title}' with status ${task.status}.`);
			 this.close();

		 }
		
		setStatuseslist(allstatuses) {
            // Fill in code
   			this.possibleStatuses = []
   			this.possibleStatuses[0] = "choose"
            this.possibleStatuses = this.possibleStatuses.concat(allstatuses)

       		var select_element = this.shadow.querySelector("dialog > div > div > select")
       		
  
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
		
		
		
	