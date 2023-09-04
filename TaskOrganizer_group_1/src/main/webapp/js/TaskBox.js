/**
 * 
 */

class TaskBox extends HTMLElement {
	
		constructor() {
            super();
            this.shadow = this.attachShadow ({ mode : 'closed' });
			console.log("creating tasklist ")
      		window.alert("task box ")
			this.display()
			

		

        }
        
        startup(){
			var modal = document.querySelector("dialog")
			modal.show()
		}
        display(){
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
			
			this.shadow.appendChild(template)
					}
	
	}
		
		
		
export default{
	TaskBox
}
		
		
		
	