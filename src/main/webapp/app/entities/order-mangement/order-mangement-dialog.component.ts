import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { OrderMangement } from './order-mangement.model';
import { OrderMangementPopupService } from './order-mangement-popup.service';
import { OrderMangementService } from './order-mangement.service';
import { ProductInfo, ProductInfoService } from '../product-info';
import { PaymentDetails, PaymentDetailsService } from '../payment-details';
import { CustomerInfo, CustomerInfoService } from '../customer-info';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-order-mangement-dialog',
    templateUrl: './order-mangement-dialog.component.html'
})
export class OrderMangementDialogComponent implements OnInit {

    orderMangement: OrderMangement;
    isSaving: boolean;

    productinfos: ProductInfo[];

    paymentdetails: PaymentDetails[];

    customerinfos: CustomerInfo[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private orderMangementService: OrderMangementService,
        private productInfoService: ProductInfoService,
        private paymentDetailsService: PaymentDetailsService,
        private customerInfoService: CustomerInfoService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.productInfoService.query()
            .subscribe((res: ResponseWrapper) => { this.productinfos = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.paymentDetailsService.query()
            .subscribe((res: ResponseWrapper) => { this.paymentdetails = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.customerInfoService.query()
            .subscribe((res: ResponseWrapper) => { this.customerinfos = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.orderMangement.id !== undefined) {
            this.subscribeToSaveResponse(
                this.orderMangementService.update(this.orderMangement));
        } else {
            this.subscribeToSaveResponse(
                this.orderMangementService.create(this.orderMangement));
        }
    }

    private subscribeToSaveResponse(result: Observable<OrderMangement>) {
        result.subscribe((res: OrderMangement) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: OrderMangement) {
        this.eventManager.broadcast({ name: 'orderMangementListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    trackProductInfoById(index: number, item: ProductInfo) {
        return item.id;
    }

    trackPaymentDetailsById(index: number, item: PaymentDetails) {
        return item.id;
    }

    trackCustomerInfoById(index: number, item: CustomerInfo) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-order-mangement-popup',
    template: ''
})
export class OrderMangementPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private orderMangementPopupService: OrderMangementPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.orderMangementPopupService
                    .open(OrderMangementDialogComponent as Component, params['id']);
            } else {
                this.orderMangementPopupService
                    .open(OrderMangementDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
