import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { DatePipe } from '@angular/common';
import { UserService } from '../user.service';
import { TitleCasePipe } from '@angular/common';
import { IUser } from '../interfaces/User';
import { IProject } from '../interfaces/Project';
import { ProjectService } from '../project.service';
import { FilterProjectPipe } from '../filter-project.pipe';
import { removeSummaryDuplicates } from '@angular/compiler';


declare var $: any;

@Component({
  selector: 'app-add-project',
  templateUrl: './add-project.component.html',
  styleUrls: ['./add-project.component.css'],
  providers: [[UserService, ProjectService, TitleCasePipe, FilterProjectPipe]]
})

export class AddProjectComponent implements OnInit {

  addProjectForm: FormGroup;
  today: Date;
  tomorrow: Date;
  projects: IProject[];
  filteredProjects: IProject[];
  usersList: IUser[];
  searchText: string;
  searchUser: string;
  selectedUser: string;
  selectedUserId: string;
  saveUserId: string;
  saveUsereditId: string;
  saveUseronE: string;
  saveUserforE: string;
  oldUserId: string;
  newUserId: string;
  userupdateind: boolean;
  saveProjectId: string;
  editable: boolean;
  editId: string;
  error: string;
  tempProject: IProject;
  addProject: IProject;
  editProject: IProject;
  updateUser:IUser;
  tempUsr: IUser;
  projByNm: IProject;
  userOnEdit: IUser;



  constructor(private fb: FormBuilder,
              private userService: UserService,
              private projectService: ProjectService,
              private titleCasePipe: TitleCasePipe,
              private filterProjectPipe: FilterProjectPipe
              ) { }

// On init , set default dates,  subscribe to list of users (we need it)
  ngOnInit() {
    this.setDefaultDate();
    this.createForm();
    this.userService.getusers().subscribe(data => {
      this.usersList = data;
    }, error => {
      console.log(error);
    });
    this.listProjects();
  }

// date formatter . we dont need lengthy unecessary data, so lets have whats needed
  dateFormatter(date: Date, format: string): any {
    if (!date) { return null; }
    return new DatePipe('en-US').transform(date, format);
  }

// Needed on init and to have default dates on add.
  setDefaultDate() {
    const date1 = new Date();
    const date2 = new Date(date1.setDate(date1.getDate() + 1));
    this.today = this.dateFormatter(new Date(), 'yyyy-MM-dd');
    this.tomorrow = this.dateFormatter(date2, 'yyyy-MM-dd');
    if (this.addProjectForm) {
      let start, end;
      start = this.addProjectForm.get('startDate').value;
      end = this.addProjectForm.get('endDate').value;
      if (!start || !end) {
        this.addProjectForm.patchValue({
          startDate: this.today,
          endDate: this.tomorrow
        });
      }
    }
  }

// Form creation validators go here.
  createForm() {
    this.addProjectForm = this.fb.group({
      project: [null, Validators.required],
      priority: [0, Validators.required],
      setDate: false,
      startDate: [{ value: this.today, disabled: true }, [Validators.required]],
      endDate: [{ value: this.tomorrow, disabled: true }, [Validators.required]],
      manager: [{ value: null, disabled: true }, Validators.required]
    }, { validator: this.DateValidator() });
  }


// reset procedures go here
  resetForm() {
    this.addProjectForm.reset({
      priority: 0,
      startDate: { value: this.today, disabled: true },
      endDate: { value: this.tomorrow, disabled: true },
      setDate: false
    });
    this.selectedUser = null;
    this.searchUser = null;
    this.selectedUserId = null;
  }

// Check box select process, defualt date or enable user provision??
  onSelect(event) {
    const status = event.target.checked;
    console.log(status);
    if (status) {
      this.addProjectForm.get('startDate').enable();
      this.addProjectForm.get('endDate').enable();
    } else {
      this.setDefaultDate();
      this.addProjectForm.get('startDate').disable();
      this.addProjectForm.get('endDate').disable();
    }
  }

// end must not be before start .. doesnt make sense you see
  DateValidator() {
    return (group: FormGroup): { [key: string]: any } => {
      const startDate = new Date(group.controls.startDate.value);
      const endDate = new Date(group.controls.endDate.value);
      const today = new Date(new DatePipe('en-US').transform(new Date(), 'yyyy-MM-dd'));
      if ((endDate.getTime() < startDate.getTime()) || (startDate.getTime() < today.getTime())) {
        return {
          dates: 'Start/End date is incorrect'
        };
      }
      return {};
    };
  }

// Lets add projects. Procedures below
  onAdd() {
    console.log(this.addProjectForm.value);
    this.addProject = this.addProjectForm.value;
    // console.log('printing the project to be added build ' + this.addProject);
    this.addProject.userId = this.selectedUserId;
    this.saveUserId = this.selectedUserId;
    // console.log('this is our user ' + this.saveUserId);
    console.log(this.addProject);
    this.projectService.addProject(this.addProject).subscribe(data => {
      // console.log('this was the project added' + data);
      this.resetForm();
      // console.log('logging user id', this.selectedUserId);
      this.updateUserOnAdd(this.saveUserId);
      this.listProjects();
      this.error = null;
    }, error => {
      this.error = 'Atleast one of the field has error !!';
      console.log(error);
    });
  }

//  Updating..................
  updateUserOnAdd(useridsent: string) {
    this.projectService.getProjectByPName(this.addProject.project).subscribe(projectret => {
      this.saveProjectId = projectret.projectId;
    }, error => {
        this.error = 'Error getting project by name!!';
        console.log(error);
    });
    // make sure users are tagged to right projects as well!!!!
    this.userService.getuser(useridsent).subscribe(result => {
      result.projectId = this.saveProjectId;
      this.updateUser = result;
      this.userService.updateuser(this.updateUser).subscribe(updates => {
        console.log('user updated!!!!!!!!!!!!!!!!!!');
      }, error => {
        this.error = 'Error updating User with project id !!';
        console.log(error);
      });
      }, error => {
        this.error = 'Error getting the user  !!';
        console.log(error);
    });
  }
// we need the data selected from the modal
  saveUser() {
    const temp = this.selectedUser.split('-');
    this.selectedUserId = temp[0].trim();
    this.addProjectForm.patchValue({
      manager: temp[1].trim()
    });
    $('#UserModal').modal('hide');
  }

// this goes to view screen. we list out all projects added there
  listProjects() {
    this.projectService.getProjects().subscribe(data => {
      this.projects = data;
      this.projects.forEach(project => {
        this.projectService.getTotalTasks(project.projectId).subscribe(result => {
          if (result) {
            project.tasks = result;
          } else {
            project.tasks = 0;
          }
        });
        this.projectService.getCompletedTasks(project.projectId).subscribe(result => {
          if (result) {
            project.completed = result;
          } else {
            project.completed = 0;
          }
        });
      });
      this.filteredProjects = this.projects;
    }, error => {
      console.log(error);
    });

  }

// when we are not filtering on parameter then list'em all
  clearFilter() {
    this.listProjects();
    this.searchText = null;
  }


// we have option to sort based on start date, end date priority and completion
  sort(basis) {
    console.log('sorting...........................');
    if (basis === 'startDate') {
      this.filteredProjects.sort((a, b) => new Date(a.startDate).getTime() - new Date(b.startDate).getTime());
    } else if (basis === 'endDate') {
      this.filteredProjects.sort((a, b) => new Date(a.endDate).getTime() - new Date(b.endDate).getTime());
    } else if (basis === 'Priority') {
      this.filteredProjects.sort((a, b) => +a.priority - +b.priority);
    } else if (basis === 'Completed') {
      this.filteredProjects.sort((a, b) => +a.completed - +b.completed);
    }
  }

//  cancel update process. reset the form
  cancelEdit() {
    this.resetForm();
    this.editable = false;
    this.editId = null;
    this.error = null;
  }

// Process that happens when user selects the project from the view portion
  onEdit(projectId) {
    this.projectService.getProject(projectId).subscribe(result => {
      console.log('result is', result);
      this.getuserIdfromprojectId(projectId);
      // console.log('our manager ' + this.userOnEdit);
      this.addProjectForm.patchValue({
        project: result.project,
        startDate: this.dateFormatter(new Date(result.startDate), 'yyyy-MM-dd'),
        endDate: this.dateFormatter(new Date(result.endDate), 'yyyy-MM-dd'),
        priority: result.priority,
        setDate: false,
        // manager: this.saveUseronE
      });
      this.editable = true;
      this.editId = projectId;
    }, error => {
      console.log(error);
    });
  }

  getuserIdfromprojectId(projectId) {
    this.userService.getuserByProjectId(projectId).subscribe(data =>{
      this.userOnEdit = data;
      this.saveUseronE = data.firstName + ' ' + data.lastName;
      this.saveUserforE = data.userId;
      console.log(this.userOnEdit);
      console.log('here is our user' + this.saveUseronE);
      this.addProjectForm.patchValue({
        manager: this.saveUseronE
      });
    }, error => {
        this.error = 'Error user fromproject id !!';
        console.log(error);
    });
  }

  getManager(user_id) {
    // console.log('ger passed to get manager' + user_id);
    this.userService.getuser(user_id).subscribe(data => {
      this.selectedUser = data.projectId + ' - ' + data.firstName + ' ' + data.lastName;
      this.addProjectForm.patchValue({
        manager: this.titleCasePipe.transform(this.selectedUser.split('-')[1].trim())
      });
    }, error => {
      console.log(error);
    });
  }

// process that happens when actual editing is done and submitted
  onEditSave() {
    this.editProject = this.addProjectForm.value;
    if (this.selectedUser != null) {
      this.editProject.userId = this.selectedUser.split('-')[0].trim();
      this.userupdateind = true;
      this.oldUserId = this.saveUserforE;
      this.newUserId = this.selectedUser.split('-')[0].trim();
    } else {
      this.editProject.userId = this.saveUserforE ;
      this.userupdateind = false;
      this.oldUserId = this.saveUserforE;
    }
    this.editProject.projectId = this.editId;
    console.log('lets finaly get down to editing ' + this.editProject);
    this.projectService.updateProject(this.editProject).subscribe(data => {
      this.editable = false;
      this.editId = null;
      this.resetForm();
      this.listProjects();
      this.error = null;
    }, error => {
      this.error = 'Atleast one of the field has error !!';
      console.log(error);
    });
    if (this.userupdateind) {
      // update project for new user
      this.userService.getuser(this.newUserId).subscribe(newusr => {
        console.log('get uniq done');
        newusr.projectId = this.editProject.projectId;
        this.tempUsr = newusr;
        // update here
        this.userService.updateuser(this.tempUsr).subscribe(updatdusr => {
          console.log('user updaed for edit project on prject manger change');
        }, error => {
          this.error = 'failed on new user update!!!';
          console.log(error);
        });
      }, error => {
        this.error = 'failed getting the new user for update !!';
        console.log(error);
      });

      // old user update................
      // update project for new user
      this.userService.getuser(this.oldUserId).subscribe(oldusr => {
        console.log('get uniq  for old user done');
        oldusr.projectId = '0';
        this.tempUsr = oldusr;
        // update here
        this.userService.updateuser(this.tempUsr).subscribe(updatOusr => {
          console.log('Old user updaed for edit project on prject manger change');
        }, error => {
          this.error = 'failed on old user update!!!';
          console.log(error);
        });
      }, error => {
        this.error = 'failed getting the old user for update !!';
        console.log(error);
      });
    }
  }

  suspendProject(project: IProject) {
    this.projectService.deleteProject(project).subscribe(data => {
      this.listProjects();
    }, error => {
      console.log(error);
    });
  }
// filter on search
  onSearch(text) {
    this.filteredProjects = this.filterProjectPipe.transform(this.projects, text);
  }

}
