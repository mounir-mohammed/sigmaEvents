import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AccreditationStatsDTO } from '../report-sig.model';
import { ApplicationConfigService } from 'app/core/config/application-config.service';

@Injectable({ providedIn: 'root' })
export class ReportSigService {
  constructor(private http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/reports/accreditations');

  getAccreditationStats(eventId: number): Observable<AccreditationStatsDTO> {
    return this.http.get<AccreditationStatsDTO>(`${this.resourceUrl}/${eventId}`);
  }
}
