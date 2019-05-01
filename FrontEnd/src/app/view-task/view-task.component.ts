import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { ProjectService } from '../project.service';
import { TaskService } from '../task.service';
import { IProject } from '../interfaces/Project';
import { ITask } from '../interfaces/Task';
declare var $: any;
@Component({
  selector: 'app-view-task',
  templateUrl: './view-task.component.html',
  styleUrls: ['./view-task.component.css'],
  providers: [ProjectService, TaskService]
})
export class ViewTaskComponent implements OnInit {

  project: string;
  projects: IProject[];
  searchProject: string;
  selectedProject: string;
  tasks: ITask[];

  constructor(private projectService: ProjectService,
              private taskService: TaskService, 
              private router: Router, 
              private route: ActivatedRoute
  ) { }

  ngOnInit() {
    this.getProjects();
  }
//  save the selected project to see the tasks
  saveProject() {
    const temp = this.selectedProject.split('-');
    this.project = temp[1];
    this.getTasks(temp[0]);
    $('#ProjectModal').modal('hide');
  }

//  we can order tasks by start/end date priority completion status
  sort(basis) {
    if (basis === 'startDate') {
      this.tasks.sort((a, b) => new Date(a.startDate).getTime() - new Date(b.startDate).getTime());
    } else if (basis === 'endDate') {
      this.tasks.sort((a, b) => new Date(a.endDate).getTime() - new Date(b.endDate).getTime());
    } else if (basis === 'Priority') {
      this.tasks.sort((a, b) => +a.priority - +b.priority);
    } else if (basis === 'Completed') {
      this.tasks.sort(function(a, b) {
        if (a.status < b.status) {
          return -1;
        }
        if (a.status > b.status) {
          return 1;
        }
        return 0;
      });
    }
  }

  clearFilter() {
    const temp = this.selectedProject.split('-');
    this.getTasks(temp[0]);
  }

  getProjects() {
    this.projectService.getProjects().subscribe(data => {
      this.projects = data;
    }, error => {
      console.log(error);
    });
  }

  getTasks(id) {
    this.taskService.getTasks(id).subscribe(data => {
      // console.log(data)
      this.tasks = data;
    }, error => {
      console.log(error);
    });
  }

  endTask(id) {
    this.taskService.setTaskAsComplete(id).subscribe(data => {
      this.clearFilter();
    });
  }
// route to add task screen to edit task.
  editTask(id) {
    this.router.navigate(['/add-task'], {
      queryParams: {
        taskId: id
      }
    });
  }

}
