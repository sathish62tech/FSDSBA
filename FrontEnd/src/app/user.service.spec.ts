import { UserService } from './user.service';
import { TestBed, inject, getTestBed, async } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

describe('UserService', () => {

  let injector: TestBed;
  let service: UserService;
  let httpMock: HttpTestingController;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [UserService]
    }).compileComponents().then(() => {
      injector = getTestBed();
      service = injector.get(UserService);
      httpMock = injector.get(HttpTestingController);
    });
  }));

  afterEach(async(() => {
    // To flush any outstanding requests
    httpMock.verify();
  }));

  it('should be created', () => {
    const service: UserService = TestBed.get(UserService);
    expect(service).toBeTruthy();
  });

  it(' post a User', () => {

    const userAdd = {
      userId: '1',
      firstName: 'Sathish',
      lastName: 'Pachayappan',
      employeeId: '457878',
      projectId: null,
      taskId: null
    }

    service.addUser(userAdd).subscribe();

    httpMock.expectOne((request) => {
      return request.method == 'POST'
        && request.url == `${service.baseUrl}addUser`;
    }).flush(userAdd);

  });

  it('lets test update', () => {

    const userEdit = {
      userId: '1',
      firstName: 'Sathish Kumar',
      lastName: 'Pachayappan',
      employeeId: '457878',
      projectId: null,
      taskId: null
    }

    service.updateuser(userEdit).subscribe();

    httpMock.expectOne((request) => {
      return request.method == 'PUT'
        && request.url == `${service.baseUrl}edituser`;
    }).flush(userEdit);

  });

  it('delete user check', () => {
    const userdel = {
      userId: '1',
      firstName: 'Sathish Kumar',
      lastName: 'Pachayappan',
      employeeId: '457878',
      projectId: null,
      taskId: null
    }
    service.deleteUser(userdel).subscribe();

    httpMock.expectOne((request) => {
      return request.method == 'PUT'
        && request.url == `${service.baseUrl}deleteUser`;
    }).flush(userdel);

  });

  it('Get uniq', () => {

    service.getuser(123).subscribe();

    httpMock.expectOne((request) => {
      return request.method == 'GET'
        && request.url == `${service.baseUrl}getuser/123`;
    });

  });

  it('Get user by project id', () => {

    service.getuserByProjectId(13).subscribe();

    httpMock.expectOne((request) => {
      return request.method == 'GET'
        && request.url == `${service.baseUrl}getuserbyproject/13`;
    });

  });
});
