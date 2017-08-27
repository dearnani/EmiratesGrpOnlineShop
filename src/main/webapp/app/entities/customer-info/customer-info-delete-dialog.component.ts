import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { CustomerInfo } from './customer-info.model';
import { CustomerInfoPopupService } from './customer-info-popup.service';
import { CustomerInfoService } from './customer-info.service';

@Component({
    selector: 'jhi-customer-info-delete-dialog',
    templateUrl: './customer-info-delete-dialog.component.html'
})
export class CustomerInfoDeleteDialogComponent {

    customerInfo: CustomerInfo;

    constructor(
        private customerInfoService: CustomerInfoService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.customerInfoService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'customerInfoListModification',
                content: 'Deleted an customerInfo'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-customer-info-delete-popup',
    template: ''
})
export class CustomerInfoDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private customerInfoPopupService: CustomerInfoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.customerInfoPopupService
                .open(CustomerInfoDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
