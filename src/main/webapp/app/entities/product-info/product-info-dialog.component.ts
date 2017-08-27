import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ProductInfo } from './product-info.model';
import { ProductInfoPopupService } from './product-info-popup.service';
import { ProductInfoService } from './product-info.service';

@Component({
    selector: 'jhi-product-info-dialog',
    templateUrl: './product-info-dialog.component.html'
})
export class ProductInfoDialogComponent implements OnInit {

    productInfo: ProductInfo;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private productInfoService: ProductInfoService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.productInfo.id !== undefined) {
            this.subscribeToSaveResponse(
                this.productInfoService.update(this.productInfo));
        } else {
            this.subscribeToSaveResponse(
                this.productInfoService.create(this.productInfo));
        }
    }

    private subscribeToSaveResponse(result: Observable<ProductInfo>) {
        result.subscribe((res: ProductInfo) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: ProductInfo) {
        this.eventManager.broadcast({ name: 'productInfoListModification', content: 'OK'});
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
}

@Component({
    selector: 'jhi-product-info-popup',
    template: ''
})
export class ProductInfoPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private productInfoPopupService: ProductInfoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.productInfoPopupService
                    .open(ProductInfoDialogComponent as Component, params['id']);
            } else {
                this.productInfoPopupService
                    .open(ProductInfoDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
