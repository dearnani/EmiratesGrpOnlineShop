import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { CustomerInfo } from './customer-info.model';
import { CustomerInfoPopupService } from './customer-info-popup.service';
import { CustomerInfoService } from './customer-info.service';
import { Location, LocationService } from '../location';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-customer-info-dialog',
    templateUrl: './customer-info-dialog.component.html'
})
export class CustomerInfoDialogComponent implements OnInit {

    customerInfo: CustomerInfo;
    isSaving: boolean;

    locations: Location[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private customerInfoService: CustomerInfoService,
        private locationService: LocationService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.locationService.query()
            .subscribe((res: ResponseWrapper) => { this.locations = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.customerInfo.id !== undefined) {
            this.subscribeToSaveResponse(
                this.customerInfoService.update(this.customerInfo));
        } else {
            this.subscribeToSaveResponse(
                this.customerInfoService.create(this.customerInfo));
        }
    }

    private subscribeToSaveResponse(result: Observable<CustomerInfo>) {
        result.subscribe((res: CustomerInfo) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: CustomerInfo) {
        this.eventManager.broadcast({ name: 'customerInfoListModification', content: 'OK'});
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

    trackLocationById(index: number, item: Location) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-customer-info-popup',
    template: ''
})
export class CustomerInfoPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private customerInfoPopupService: CustomerInfoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.customerInfoPopupService
                    .open(CustomerInfoDialogComponent as Component, params['id']);
            } else {
                this.customerInfoPopupService
                    .open(CustomerInfoDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
