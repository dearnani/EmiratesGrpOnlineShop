import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { DeliverManagement } from './deliver-management.model';
import { DeliverManagementPopupService } from './deliver-management-popup.service';
import { DeliverManagementService } from './deliver-management.service';
import { Location, LocationService } from '../location';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-deliver-management-dialog',
    templateUrl: './deliver-management-dialog.component.html'
})
export class DeliverManagementDialogComponent implements OnInit {

    deliverManagement: DeliverManagement;
    isSaving: boolean;

    locations: Location[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private deliverManagementService: DeliverManagementService,
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
        if (this.deliverManagement.id !== undefined) {
            this.subscribeToSaveResponse(
                this.deliverManagementService.update(this.deliverManagement));
        } else {
            this.subscribeToSaveResponse(
                this.deliverManagementService.create(this.deliverManagement));
        }
    }

    private subscribeToSaveResponse(result: Observable<DeliverManagement>) {
        result.subscribe((res: DeliverManagement) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: DeliverManagement) {
        this.eventManager.broadcast({ name: 'deliverManagementListModification', content: 'OK'});
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
    selector: 'jhi-deliver-management-popup',
    template: ''
})
export class DeliverManagementPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private deliverManagementPopupService: DeliverManagementPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.deliverManagementPopupService
                    .open(DeliverManagementDialogComponent as Component, params['id']);
            } else {
                this.deliverManagementPopupService
                    .open(DeliverManagementDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
