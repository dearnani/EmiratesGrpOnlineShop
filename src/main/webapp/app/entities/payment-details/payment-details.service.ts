import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils } from 'ng-jhipster';

import { PaymentDetails } from './payment-details.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class PaymentDetailsService {

    private resourceUrl = 'api/payment-details';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(paymentDetails: PaymentDetails): Observable<PaymentDetails> {
        const copy = this.convert(paymentDetails);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(paymentDetails: PaymentDetails): Observable<PaymentDetails> {
        const copy = this.convert(paymentDetails);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: number): Observable<PaymentDetails> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
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
        for (let i = 0; i < jsonResponse.length; i++) {
            this.convertItemFromServer(jsonResponse[i]);
        }
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convertItemFromServer(entity: any) {
        entity.oderDate = this.dateUtils
            .convertDateTimeFromServer(entity.oderDate);
    }

    private convert(paymentDetails: PaymentDetails): PaymentDetails {
        const copy: PaymentDetails = Object.assign({}, paymentDetails);

        copy.oderDate = this.dateUtils.toDate(paymentDetails.oderDate);
        return copy;
    }
}
