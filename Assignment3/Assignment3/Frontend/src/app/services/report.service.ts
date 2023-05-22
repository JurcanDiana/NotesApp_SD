import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ReportService {

  url = environment.apiUrl;

  constructor(private httpClient:HttpClient) { }

  generateReport(data:any) {
    return this.httpClient.post(this.url + "/report/generateReport", data, {
      headers: new HttpHeaders().set('Content-type', "application/json")
    })
  }

  getPdf(data:any) : Observable<Blob> {
    return this.httpClient.post(this.url + "/report/getPdf", data, {
      responseType: 'blob'
    });
  }

  getReports() {
    return this.httpClient.get(this.url + "/report/getReports");
  }

  delete(id:any) {
    return this.httpClient.post(this.url + "/report/delete/" + id, {
      headers: new HttpHeaders().set('Content-type', "application/json")
    })
  }
}
