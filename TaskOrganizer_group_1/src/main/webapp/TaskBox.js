/**
 * 
 */

const template = document.createElement("template");
/* 			 <link rel="stylesheet" type="text/css"
			 href="${import.meta.url.match(/.*\//)[0]}/taskbox.css"/>*/ 
			template.innerHTML = `

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
            super();
            this.shadow = this.attachShadow ({ mode : 'open' });
			this.content = template.content.cloneNode(true);
			const path = import.meta.url.match(/.*\//)[0];
			this.shadow.appendChild(this.content)
			
			this.shadow.querySelector("dialog").show()

			this.addCloseCallback() 

			
			this.possibleStatuses = []
			
	

        }

        
        addCloseCallback(){
			this.shadow.querySelector("span").addEventListener("click", (event)=>{
				this.close()

			})

		}
		
		close() {
			var dialog = document.querySelector("task-box").shadowRoot.firstChild.nextSibling
			dialog.close() 
		}
		
		
		show(){
			/* aktuell in  tasklist.createTaskBox  implementiert */
		}
		
		 newtaskCallback(callback){
			 /* auch aktuell in tasklist implementiert in closeTaskBox*/ 
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
		
		
		
	