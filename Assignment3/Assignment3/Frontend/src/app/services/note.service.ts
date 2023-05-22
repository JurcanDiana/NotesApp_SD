import { HttpClient, HttpHeaderResponse, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class NoteService {

  url = environment.apiUrl;

  constructor(private httpClient: HttpClient) { }

  add(data:any) {
    return this.httpClient.post(this.url + "/note/add", data, {
      headers: new HttpHeaders().set('Content-type', "application/json")
    })
  }

  update(data:any) {
    return this.httpClient.post(this.url + "/note/update", data, {
      headers: new HttpHeaders().set('Content-type', "application/json")
    })
  }

  getNotes() {
    return this.httpClient.get(this.url + "/note/get");
  }

  updateStatus(data:any) {
    return this.httpClient.post(this.url + "/note/updateStatus", data, {
      headers: new HttpHeaders().set('Content-type', "application/json")
    })
  }

  delete(id:any) {
    return this.httpClient.post(this.url + "/note/delete/" + id, {
      headers: new HttpHeaders().set('Content-type', "application/json")
    })
  }

  getNoteByCategory(id:any) {
    return this.httpClient.get(this.url+"/note/getByCategory/" + id);
  }

  getById(id:any) {
    return this.httpClient.get(this.url+"/note/getById/" + id);
  }
}
