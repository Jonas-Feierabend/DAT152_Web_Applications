 /**
  * TaskList
  * Manage view with list of tasks
  */
 console.log("comp wird aufsgeführt ")
if (customElements.get('task-list') === undefined) {
	console.log("is undefined")
    class TaskList extends HTMLElement {

        constructor() {
            super();
			console.log("creating tasklist ")
            /**
             * Fill inn rest of code
             */
			// Create a shadow DOM structure
			this.shadow = this.attachShadow ({ mode : 'closed' });

			
			this.tasklist = []
			this.possibleStatuses = []
			
			this.display(); 
			
		

        }
        
        
        display(){
			this.shadow.innerHTML = ''

			if(this.tasklist.length != 0){
				const hElement = document . createElement ( 'h1');
				hElement.textContent = "header"
				this.shadow.appendChild(hElement)
				
				const table = document.createElement('table')

				
				
				for( var e of this.tasklist){	
					if(typeof e == 'undefined'){
						console.log("undefined")
						continue 
					}
					var tr = document.createElement('tr');
				


					var td1 = document.createElement("td")
					td1.innerHTML = e.title
					
					var td2 = document.createElement("td")
					td2.innerHTML = e.status 
					
					var td3 = document.createElement("td")
					var select = document.createElement('select')
					for (var el of this.possibleStatuses){

						var opt = document.createElement("option")
						opt.innerHTML = el 
						select.appendChild(opt)
					}
					select.setAttribute("id",e.id)
					select.addEventListener("change",(event)=>{
						this.changestatusCallback(event)
					})
					td3.appendChild(select)

					
					
					var td4 = document.createElement("td")
					var button = document.createElement("button")
					button.innerHTML = "Remove"

					button.setAttribute("id",e.id)
					button.addEventListener("click", (event)=>{
						this.deletetaskCallback(event)
	
					})
					td4.appendChild(button)
					
					
					tr.appendChild(td1)
					tr.appendChild(td2)
					tr.appendChild(td3)
					tr.appendChild(td4)
					
					
					table.appendChild(tr)
					
				}
				this.shadow . appendChild ( table );
			}else{
				console.log("no showing")
			}
			
		}

        /**
         * @public
         * @param {Array} list with all possible task statuses
         */
        setStatuseslist(allstatuses) {
            // Fill in code
   			this.possibleStatuses = []
   			this.possibleStatuses[0] = "choose"
            this.possibleStatuses = this.possibleStatuses.concat(allstatuses)

            this.display()

        }

        /**
         * Add callback to run on change on change of status of a task, i.e. on change in the SELECT element
         * @public
         * @param {function} callback
         */
        changestatusCallback(callback) {
            // Fill in code
			var id = callback.target.id 
			var val = callback.target.value 
			this.updateTask({"id": id,"status": val})
    		
        }

        /**
         * Add callback to run on click on delete button of a task
         * @public
         * @param {function} callback
         */
        deletetaskCallback(callback) {
            // Fill in code
            this.removeTask(callback.target.id)
        }

        /**
         * Add task at top in list of tasks in the view
         * @public
         * @param {Object} task - Object representing a task
         */
        showTask(task) {
            // Fill in code
            this.tasklist.unshift(task)
            console.log("tasklist" + this.tasklist)

            this.display(); 
        }

        /**
         * Update the status of a task in the view
         * @param {Object} task - Object with attributes {'id':taskId,'status':newStatus}
         */
        updateTask(task) {
			console.log("update")
            // Fill in code
            if(this.tasklist.length != 0 ){

	            var i = 0
	            for(var element of this.tasklist){

					
					
					if(element.id == task.id){
						console.log("changing " + element.title + "to : " + task.status )
						var task_element = this.tasklist[i]
						task_element.status = task.status 
						this.tasklist[i] = task_element 
						break 
					}else{
					i++
					}
				}
				this.display()
			}
        }

        /**
         * Remove a task from the view
         * @param {Integer} task - ID of task to remove
         */
        removeTask(id) {
            // Fill in code
            
            if(this.tasklist.length != 0 ){
				console.log("drinnen")
            var i = 0
            for(var element of this.tasklist){
				console.log("element")
				
				
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

        /**
         * @public
         * @return {Number} - Number of tasks on display in view
         */
        getNumtasks() {
            // Fill in code
            return this.tasklist.length 
        }
    }
    customElements.define('task-list', TaskList);
    
    
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



