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
			console.log("creating tasklist ")
			this.content = template.content.cloneNode(true);
			const path = import.meta.url.match(/.*\//)[0];
			this.shadow.appendChild(this.content)
			
			this.shadow.querySelector("dialog").show()

			
			this.shadow.querySelector("span").addEventListener("click", (event)=>{
				this.closeCallback()

			})
			
			this.possibleStatuses = []
			
			this.setStatuseslist()

        }

        
        closeCallback(){
			/*console.log("closing")
			this.shadow.querySelector("dialog").removeAttribute("open")*/ 
			var dialog = document.querySelector("task-box").shadowRoot.firstChild.nextSibling
			dialog.close() 
		}
		
		
		setStatuseslist(allstatuses) {
            // Fill in code
   			this.possibleStatuses = []
   			this.possibleStatuses[0] = "choose"
            this.possibleStatuses = this.possibleStatuses.concat(allstatuses)

       		console.log(this.shadow.querySelector("dialog > select"))

        }
        

    
	
	}
		
		
		
export default{
	TaskBox
}
		
		
		
	