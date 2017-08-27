import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { PaymentDetails } from './payment-details.model';
import { PaymentDetailsPopupService } from './payment-details-popup.service';
import { PaymentDetailsService } from './payment-details.service';

@Component({
    selector: 'jhi-payment-details-dialog',
    templateUrl: './payment-details-dialog.component.html'
})
export class PaymentDetailsDialogComponent implements OnInit {

    paymentDetails: PaymentDetails;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private paymentDetailsService: PaymentDetailsService,
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
        if (this.paymentDetails.id !== undefined) {
            this.subscribeToSaveResponse(
                this.paymentDetailsService.update(this.paymentDetails));
        } else {
            this.subscribeToSaveResponse(
                this.paymentDetailsService.create(this.paymentDetails));
        }
    }

    private subscribeToSaveResponse(result: Observable<PaymentDetails>) {
        result.subscribe((res: PaymentDetails) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: PaymentDetails) {
        this.eventManager.broadcast({ name: 'paymentDetailsListModification', content: 'OK'});
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
    selector: 'jhi-payment-details-popup',
    template: ''
})
export class PaymentDetailsPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private paymentDetailsPopupService: PaymentDetailsPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.paymentDetailsPopupService
                    .open(PaymentDetailsDialogComponent as Component, params['id']);
            } else {
                this.paymentDetailsPopupService
                    .open(PaymentDetailsDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
