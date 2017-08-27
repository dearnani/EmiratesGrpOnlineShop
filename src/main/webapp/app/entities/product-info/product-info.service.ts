import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { ProductInfo } from './product-info.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class ProductInfoService {

    private resourceUrl = 'api/product-infos';

    constructor(private http: Http) { }

    create(productInfo: ProductInfo): Observable<ProductInfo> {
        const copy = this.convert(productInfo);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(productInfo: ProductInfo): Observable<ProductInfo> {
        const copy = this.convert(productInfo);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<ProductInfo> {
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

    private convert(productInfo: ProductInfo): ProductInfo {
        const copy: ProductInfo = Object.assign({}, productInfo);
        return copy;
    }
}
