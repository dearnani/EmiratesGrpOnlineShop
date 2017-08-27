import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { DealerInfo } from './dealer-info.model';
import { DealerInfoPopupService } from './dealer-info-popup.service';
import { DealerInfoService } from './dealer-info.service';
import { ProductInfo, ProductInfoService } from '../product-info';
import { Location, LocationService } from '../location';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-dealer-info-dialog',
    templateUrl: './dealer-info-dialog.component.html'
})
export class DealerInfoDialogComponent implements OnInit {

    dealerInfo: DealerInfo;
    isSaving: boolean;

    productinfos: ProductInfo[];

    locations: Location[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private dealerInfoService: DealerInfoService,
        private productInfoService: ProductInfoService,
        private locationService: LocationService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.productInfoService.query()
            .subscribe((res: ResponseWrapper) => { this.productinfos = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.locationService.query()
            .subscribe((res: ResponseWrapper) => { this.locations = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.dealerInfo.id !== undefined) {
            this.subscribeToSaveResponse(
                this.dealerInfoService.update(this.dealerInfo));
        } else {
            this.subscribeToSaveResponse(
                this.dealerInfoService.create(this.dealerInfo));
        }
    }

    private subscribeToSaveResponse(result: Observable<DealerInfo>) {
        result.subscribe((res: DealerInfo) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: DealerInfo) {
        this.eventManager.broadcast({ name: 'dealerInfoListModification', content: 'OK'});
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

    trackLocationById(index: number, item: Location) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-dealer-info-popup',
    template: ''
})
export class DealerInfoPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private dealerInfoPopupService: DealerInfoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.dealerInfoPopupService
                    .open(DealerInfoDialogComponent as Component, params['id']);
            } else {
                this.dealerInfoPopupService
                    .open(DealerInfoDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
