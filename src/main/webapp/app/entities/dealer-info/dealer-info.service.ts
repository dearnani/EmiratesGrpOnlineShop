import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { DealerInfo } from './dealer-info.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class DealerInfoService {

    private resourceUrl = 'api/dealer-infos';

    constructor(private http: Http) { }

    create(dealerInfo: DealerInfo): Observable<DealerInfo> {
        const copy = this.convert(dealerInfo);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(dealerInfo: DealerInfo): Observable<DealerInfo> {
        const copy = this.convert(dealerInfo);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<DealerInfo> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            return res.json();
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convert(dealerInfo: DealerInfo): DealerInfo {
        const copy: DealerInfo = Object.assign({}, dealerInfo);
        return copy;
    }
}
